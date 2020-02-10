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
  private TalonSRX topMotor,  bottomMotor;

  private DigitalInput switch1,switch2,switch3,switch4;

  // -----------------------------------------------------
  // Constructor
  public Loader() 
  {
    topMotor    = new TalonSRX(Constants.LOADER_TOP_MOTOR_CAN_ID); 
    bottomMotor = new TalonSRX(Constants.LOADER_BOTTOM_MOTOR_CAN_ID); 

    switch1 =  new DigitalInput(Constants.LOADER_SWITCH_1_DIGITAL_PORT);
    switch2 =  new DigitalInput(Constants.LOADER_SWITCH_2_DIGITAL_PORT);
    switch3 =  new DigitalInput(Constants.LOADER_SWITCH_3_DIGITAL_PORT);
    switch4 =  new DigitalInput(Constants.LOADER_SWITCH_4_DIGITAL_PORT);
  }

  // -----------------------------------------------------
  // Manage top motor
  public void topMotorOn() 
  {
    topMotor.set(ControlMode.PercentOutput,1.0); 
  }

  public void topMotorOff() 
  {
    topMotor.set(ControlMode.PercentOutput,0.0); 
  }

  // -----------------------------------------------------
  // Manage top motor
  public void bottomMotorOn() 
  {
    bottomMotor.set(ControlMode.PercentOutput,1.0); 
  }

  public void bottomSideMotorOff() 
  {
    bottomMotor.set(ControlMode.PercentOutput,0.0); 
  }

  // -----------------------------------------------------
  // Receive a switch number as integer and returns true
  // if indicated switch is engaged
  public boolean switchEngaged(int switchNumber)
  {
    boolean returnValue = false;

    if (switchNumber == 1 && switch1.get() == false)
       returnValue = true;
    if (switchNumber == 2 && switch2.get() == false)
       returnValue = true;
    if (switchNumber == 3 && switch3.get() == false)
       returnValue = true;
    if (switchNumber == 4 && switch4.get() == false)
       returnValue = true;

    return returnValue;
  }
 
}