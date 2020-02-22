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
  private boolean isDeployed1 = false, isDeployed2 = false;
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
    isDeployed1 = true;
  }
  
  public void retractBottomCylinders() 
  {
    bottomCylinders.set(DoubleSolenoid.Value.kReverse);
    isDeployed1 = false;
  }
    
  // -----------------------------------------------------
  // Manage middle and top solenoids
  public void extendTop2Cylinders() 
  {
    top2Cylinders.set(DoubleSolenoid.Value.kForward);
    isDeployed2 = true;
  }
      
  public void retractTop2Cylinders() 
  {
    top2Cylinders.set(DoubleSolenoid.Value.kReverse);
    isDeployed2 = false;
  }

  public void stopTop2Cylinders()
  {
    top2Cylinders.set(DoubleSolenoid.Value.kOff);
  }

  public boolean TopDeployed()
  {
    return isDeployed2;
  }

  public boolean BottomDeployed()
  {
    return isDeployed1;
  }
  // -----------------------------------------------------
  // Elevator rail drive motor
  public void driveElevator(double motorLevel)
  {
    elevatorDriveMotor.set(ControlMode.PercentOutput,motorLevel); 
  }
 
}