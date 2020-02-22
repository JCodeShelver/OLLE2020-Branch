// FRC Team 3770 - BlitzCreek - OLLE 20
// Ball loader manager

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.*;  
import com.ctre.phoenix.motorcontrol.*;

public class Loader extends SubsystemBase 
{ 
  private TalonSRX movingMotor, loadingMotor;

  private DigitalInput closeSwitch;
  private DigitalInput backSwitch, intakeSwitch;

  // -----------------------------------------------------
  // Constructor
  public Loader() 
  {
    movingMotor    = new TalonSRX(Constants.MOVING_MOTOR_CAN_ID); 
    loadingMotor  = new TalonSRX(Constants.LOADING_MOTOR_CAN_ID);
    closeSwitch =  new DigitalInput(Constants.LOADER_SWITCH_2_DIGITAL_PORT);
    backSwitch =  new DigitalInput(Constants.LOADER_SWITCH_4_DIGITAL_PORT);
    intakeSwitch =  new DigitalInput(Constants.LOADER_SWITCH_1_DIGITAL_PORT); 

  }

  // -----------------------------------------------------
  // Manage top motor
  public void MovingMotorOn() 
  {
    movingMotor.set(ControlMode.PercentOutput,1.0); 
  }

  public void MovingMotorOff() 
  {
    movingMotor.set(ControlMode.PercentOutput,0.0); 
  }

  public void LoadBallMotorOn()
  {
    loadingMotor.set(ControlMode.PercentOutput, 1.0);
  }

  public void LoadBallMotorOff()
  {
    loadingMotor.set(ControlMode.PercentOutput, 0.0);
  }


  // -----------------------------------------------------
  // Receive a switch number as integer and returns true
  // if indicated switch is engaged

  public boolean ballAtIntake()
  {
    return intakeSwitch.get();
  }

  public boolean ballInSystem()
  {
    return closeSwitch.get();
  }

  public boolean ballAtBack()
  {
    return backSwitch.get();
  }
 
}