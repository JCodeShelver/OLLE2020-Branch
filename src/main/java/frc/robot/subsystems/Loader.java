// FRC Team 3770 - BlitzCreek - OLLE 2020
// Loader Subsystem
// Controlls the conveyor mechanism and
// ball detection sensors.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;  

import frc.robot.Constants;

public class Loader extends SubsystemBase 
{ 
  private final DigitalInput backSwitch, closeSwitch, intakeSwitch;
  private final TalonSRX loadingMotor, movingMotor;

  // ----------------------------------------------------------------------------
  // Constructor
  public Loader() 
  {
    loadingMotor   = new TalonSRX(Constants.LOADING_MOTOR_CAN_ID);
    movingMotor    = new TalonSRX(Constants.MOVING_MOTOR_CAN_ID); 
    
    backSwitch     = new DigitalInput(2);
    closeSwitch    = new DigitalInput(1);
    intakeSwitch   = new DigitalInput(0);   
  }

  // ----------------------------------------------------------------------------
  // Returns the state of the back sensor.
  public boolean ballAtBack()
  {
    return !backSwitch.get();
  }

  // ----------------------------------------------------------------------------
  // Returns the state of the intake sensor.
  public boolean ballAtIntake()
  {
    return !intakeSwitch.get();
  }

  // ----------------------------------------------------------------------------
  // Returns the state of the close sensor.
  public boolean ballInSystem()
  {
    return !closeSwitch.get();
  }

  // ----------------------------------------------------------------------------
  // Turn the loading motor of the Loader mechanism off.
  public void LoadBallMotorOff()
  {
    loadingMotor.set(ControlMode.PercentOutput, 0.0);
  }

  // ----------------------------------------------------------------------------
  // Turn the loading motor  of the Loader mechanism on.
  public void LoadBallMotorOn()
  {
    loadingMotor.set(ControlMode.PercentOutput, 1.0);
  }  

  // ----------------------------------------------------------------------------
  // Turn the moving motor of the Loader mechanism off.
  public void MovingMotorOff() 
  {
    movingMotor.set(ControlMode.PercentOutput,0.0); 
  }  

  // ----------------------------------------------------------------------------
  // Turn the moving motor of the Loader mechanism on.
  public void MovingMotorOn(double in) 
  {
    movingMotor.set(ControlMode.PercentOutput, in); 
  }  
}