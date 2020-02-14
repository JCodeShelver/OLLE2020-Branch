// FRC Team 3770 - BlitzCreek - OLLE 20
// Lifter subsystem
// Manage robot lifter for end game

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.*;  
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Elevator extends SubsystemBase 
{ 
  private DoubleSolenoid bottomCylinders;
  private DoubleSolenoid top2Cylinders;

  private TalonSRX elevatorDriveMotor;

  // -----------------------------------------------------
  // Constructor
  public Elevator() 
  {
    bottomCylinders = new DoubleSolenoid(Constants.PCM_MODULE_1,Constants.ELEVATOR_BOTTOM_CYLINDERS_OUTPORT,Constants.ELEVATOR_BOTTOM_CYLINDERS_INPORT);
    top2Cylinders  = new DoubleSolenoid(Constants.PCM_MODULE_1,Constants.ELEVATOR_TOP2_CYLINDERS_OUTPORT,Constants.ELEVATOR_TOP2_CYLINDERS_INPORT);

    elevatorDriveMotor = new TalonSRX(Constants.ELEVATOR_DRIVE_MOTOR_CAN_ID); 
  }
  
  // -----------------------------------------------------
  // Manage bottom double pneumatic cyliders synchronously
  public void extendBottomCylinders() 
  {
    bottomCylinders.set(DoubleSolenoid.Value.kForward);
  }
  
  public void retractBottomCylinders() 
  {
    bottomCylinders.set(DoubleSolenoid.Value.kReverse);
  }
    
  // -----------------------------------------------------
  // Manage middle and top solenoids
  public void extendTop2Cylinders() 
  {
    top2Cylinders.set(DoubleSolenoid.Value.kForward);
  }
      
  public void retractTop2Cylinders() 
  {
    top2Cylinders.set(DoubleSolenoid.Value.kReverse);
  }

  public void stopTop2Cylinders()
  {
    top2Cylinders.set(DoubleSolenoid.Value.kOff);
  }
  // -----------------------------------------------------
  // Elevator rail drive motor
  public void driveElevator(double motorLevel)
  {
    elevatorDriveMotor.set(ControlMode.PercentOutput,motorLevel); 
  }
 
}