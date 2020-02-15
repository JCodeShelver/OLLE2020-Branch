// FRC Team 3770 - BlitzCreek - OLLE 20
// Ball loader manager

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.*;  
import com.ctre.phoenix.motorcontrol.*;

public class Loader extends SubsystemBase 
{ 
  private DoubleSolenoid loadingPiston;
  private TalonSRX movingMotor;

  private DigitalInput switch2,switch3;
  private DigitalInput backSwitch, intakeSwitch;

  // -----------------------------------------------------
  // Constructor
  public Loader() 
  {
    movingMotor    = new TalonSRX(Constants.LOADER_MOTOR_CAN_ID); 

    loadingPiston = new DoubleSolenoid(Constants.PCM_MODULE_1,Constants.LOADING_PISTON_OUTPORT,Constants.LOADING_PISTON_INPORT);

    switch2 =  new DigitalInput(Constants.LOADER_SWITCH_2_DIGITAL_PORT);
    switch3 =  new DigitalInput(Constants.LOADER_SWITCH_3_DIGITAL_PORT);
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

  public void LoadBall()
  {
    loadingPiston.set(DoubleSolenoid.Value.kForward);
  }

  public void resetLoadingPiston()
  {
    loadingPiston.set(DoubleSolenoid.Value.kReverse);
  }


  // -----------------------------------------------------
  // Receive a switch number as integer and returns true
  // if indicated switch is engaged

  public boolean switchAtIntakeEngaged()
  {
    return intakeSwitch.get();
  }
  public boolean BallInSwitch()
  {
    return switch2.get();
  }

  public boolean ballAtBack()
  {
    return backSwitch.get();
  }
 
}