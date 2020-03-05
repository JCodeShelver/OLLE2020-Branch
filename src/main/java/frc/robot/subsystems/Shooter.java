// FRC Team 3770 - BlitzCreek - OLLE 20
// Shooter subsystem
// Manage controls ball shooter system.  Utilizes PID controller for motor speed.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

public class Shooter extends SubsystemBase 
{

  TalonSRX shooterMotor;
  PIDController ShooterPID;
  boolean shooterPistonDown;
  double currentSetPoint;
  int TPM;
  DoubleSolenoid ShootingPiston;
  DigitalInput BallInShooter;



  // ----------------------------------------------------------------------------
  // Constructor - (Do nothing)
  public Shooter() 
  {
    BallInShooter = new DigitalInput(3);
    ShootingPiston = new DoubleSolenoid(Constants.PCM_MODULE_0,Constants.SHOOTER_FIRE_CYLINDER_INPORT,Constants.SHOOTER_FIRE_CYLINDER_OUTPORT);
    shooterMotor = new TalonSRX(Constants.SHOOTER_MOTOR_CAN_ID);
    shooterMotor.setInverted(true);
    ShooterPID = new PIDController(Constants.SHOOTER_PID_P, Constants.SHOOTER_PID_I, Constants.SHOOTER_PID_D);
    TPM = 0;
    shooterPistonDown = true;
  }

  // ----------------------------------------------------------------------------
  // Spin motor up to current set point.  Designed to be periodically called.
  // Precondition:  SetPoint has been set!
  public void spinToSetPoint()
  {
    //currentSetPoint = -4200;
    TPM = shooterMotor.getSelectedSensorVelocity();

    System.out.println("Current Set point for RPM: " + currentSetPoint);
    System.out.println("Current RPM: " + TPM/Constants.SHOOTER_TICKS_PER_RPM);
    SmartDashboard.putNumber("Shooter RPM", TPM/Constants.SHOOTER_TICKS_PER_RPM);

    double pidOutput = -ShooterPID.calculate(TPM/Constants.SHOOTER_TICKS_PER_RPM, currentSetPoint);

    System.out.println("Motor: " + pidOutput);

    shooterMotor.set(ControlMode.PercentOutput, pidOutput);
  }
  
  public void shootBall()
  {
    ShootingPiston.set(DoubleSolenoid.Value.kReverse);
    shooterPistonDown = false;
  }
  public void lowerShootingPiston()
  {
    ShootingPiston.set(DoubleSolenoid.Value.kForward);
    shooterPistonDown = true;
  }
  public boolean isShooterPistonDown()
  {
    return shooterPistonDown;
  }
  // ----------------------------------------------------------------------------
  // Turn motor off
  public void setSetPoint(double s)
  {
    currentSetPoint = -s;
  }

  public double getSetPoint()
  {
    return currentSetPoint;
  }

  public double getRPM()
  {
    return shooterMotor.getSelectedSensorVelocity()/Constants.SHOOTER_TICKS_PER_RPM;
  }

  // Set shooter motor full speed
  public void motorOnFull()
  {
    shooterMotor.set(ControlMode.PercentOutput, 1.0);
  }

  public void updateBallInShooter()
  {
    Constants.ballInShooter = !BallInShooter.get();
    SmartDashboard.putBoolean("Ball In Shooter", Constants.ballInShooter);
  }

  // ----------------------------------------------------------------------------
  // Turn motor off
  public void stop()
  {
    currentSetPoint = 0.0;
    shooterMotor.set(ControlMode.PercentOutput, 0.0);
    this.lowerShootingPiston();
  } 
  
}
