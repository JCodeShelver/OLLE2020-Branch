// FRC Team 3770 - BlitzCreek - OLLE 20
// Shooter subsystem
// Manage controls ball shooter system.  Utilizes PID controller for motor speed.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

public class Shooter extends SubsystemBase 
{

  TalonSRX shooterMotor;
  PIDController ShooterPID;
  double currentSetPoint;
  int TPM;
  DoubleSolenoid ShootingPiston;


  // ----------------------------------------------------------------------------
  // Constructor - (Do nothing)
  public Shooter() 
  {
    ShootingPiston = new DoubleSolenoid(Constants.PCM_MODULE_1,Constants.SHOOTING_PISTON_OUTPORT,Constants.SHOOTING_PISTON_INPORT);
    shooterMotor = new TalonSRX(Constants.SHOOTER_MOTOR_CAN_ID);
    shooterMotor.setInverted(true);
    ShooterPID = new PIDController(Constants.SHOOTER_PID_P, Constants.SHOOTER_PID_I, Constants.SHOOTER_PID_D);
    TPM = 0;
  }

  // ----------------------------------------------------------------------------
  // Spin motor up to current set point.  Designed to be periodically called.
  // Precondition:  SetPoint has been set!
  public void spinToSetPoint()
  {
    currentSetPoint = 3000;
    TPM = -shooterMotor.getSelectedSensorVelocity();

    System.out.println("Current Set point for RPM: " + currentSetPoint);
    System.out.println("Current RPM: " + TPM/Constants.SHOOTER_TICKS_PER_RPM);
    
    double pidOutput = ShooterPID.calculate(TPM/Constants.SHOOTER_TICKS_PER_RPM, currentSetPoint);

    System.out.println("Motor: " + pidOutput);

    shooterMotor.set(ControlMode.PercentOutput, -pidOutput);
  }
  
  public void shootBall()
  {
    ShootingPiston.set(DoubleSolenoid.Value.kForward);
  }
  public void lowerShootingPiston()
  {
    ShootingPiston.set(DoubleSolenoid.Value.kReverse);
    Constants.BallInShooter = false;
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

  // ----------------------------------------------------------------------------
  // Turn motor off
  public void stop()
  {
    currentSetPoint = 0.0;
    shooterMotor.set(ControlMode.PercentOutput, 0.0);
    this.lowerShootingPiston();
  } 
  
}
