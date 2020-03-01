// FRC Team 3770 - BlitzCreek - OLLE 2020
// Shooter Subsystem
// Controls the ball shooter system, and 
// utilizes PID controller for motor speed.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

import frc.robot.Constants;

public class Shooter extends SubsystemBase 
{
  private final DoubleSolenoid ShootingPiston;

  private final DigitalInput   BallInShooter;
  
  private final PIDController  ShooterPID;
  
  private final TalonSRX       shooterMotor;

  private boolean shooterPistonDown;
  
  private double  currentSetPoint;
  
  private int     TPM;

  // ----------------------------------------------------------------------------
  // Constructor - (Do nothing)
  public Shooter() 
  {
    BallInShooter  = new DigitalInput(3);

    shooterMotor   = new TalonSRX(Constants.SHOOTER_MOTOR_CAN_ID);
    
    ShooterPID     = new PIDController(Constants.SHOOTER_PID_P, Constants.SHOOTER_PID_I, Constants.SHOOTER_PID_D);
    
    ShootingPiston = new DoubleSolenoid(Constants.PCM0, Constants.SHOOTER_FIRE_CYLINDER_INPORT, Constants.SHOOTER_FIRE_CYLINDER_OUTPORT);
    
    TPM            = 0;

    shooterMotor.setInverted(true);
    shooterPistonDown = true;
  }

  // ----------------------------------------------------------------------------
  // Get the RPM of the motor of the Shooter mechanism.
  public double getRPM()
  {
    return -shooterMotor.getSelectedSensorVelocity()/Constants.SHOOTER_TICKS_PER_RPM;
  }
  
  // ----------------------------------------------------------------------------
  // Get the Set Point.
  public double getSetPoint()
  {
    return currentSetPoint;
  }
  
  // ----------------------------------------------------------------------------
  // Return the state of the Shooter mechanism pneumatic.
  public boolean isShooterPistonDown()
  {
    return shooterPistonDown;
  }
  
  // ----------------------------------------------------------------------------
  // Lower the Shooter mechanism pneumatic.
  public void lowerShootingPiston()
  {
    ShootingPiston.set(DoubleSolenoid.Value.kReverse);
    shooterPistonDown = true;
  }
  
  // ----------------------------------------------------------------------------
  // Set the motor of the Shooter mechanism to full speed.
  public void motorOnFull()
  {
    shooterMotor.set(ControlMode.PercentOutput, 1.0);
  }
  
  // ----------------------------------------------------------------------------
  // Set the Set Point.
  public void setSetPoint(double s)
  {
    currentSetPoint = -s;
  }
  
  // ----------------------------------------------------------------------------
  // Raise the Shooter mechanism pneumatic.
  public void shootBall()
  {
    ShootingPiston.set(DoubleSolenoid.Value.kForward);
    shooterPistonDown = false;
  }
  
  // ----------------------------------------------------------------------------
  // Spin motor up to current set point.  Designed to be periodically called.
  // Precondition:  SetPoint has been set!
  public void spinToSetPoint()
  {
    //currentSetPoint = -3700;
    System.out.println("Current Set point for RPM: " + currentSetPoint);
    System.out.println("Current RPM: " +   this.getRPM());
    SmartDashboard.putNumber("Shooter RPM", this.getRPM());

    double pidOutput = ShooterPID.calculate(this.getRPM(), currentSetPoint);

    //System.out.println("Motor: " + pidOutput);
    shooterMotor.set(ControlMode.PercentOutput, pidOutput);
  }

  // ----------------------------------------------------------------------------
  // Turn the Shooter mechanism off.
  public void stop()
  {
    currentSetPoint = 0.0;
    shooterMotor.set(ControlMode.PercentOutput, 0.0);
    this.lowerShootingPiston();
  } 

  // ----------------------------------------------------------------------------
  // Update state of variables concerning balls in the Shooter mechanism.
  public void updateBallInShooter()
  {
    Constants.ballInShooter = !BallInShooter.get();
    SmartDashboard.putBoolean("Ball In Shooter", Constants.ballInShooter);
  }
}
