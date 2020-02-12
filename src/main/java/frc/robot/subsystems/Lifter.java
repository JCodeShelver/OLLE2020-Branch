// FRC Team 3770 - BlitzCreek - OLLE 20
// Lifter subsystem
// Manage robot lifter for end game

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.*;  
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

public class Lifter extends SubsystemBase 
{ 
  private DoubleSolenoid bottomCylinder1;
  private DoubleSolenoid bottomCylinder2;
  private Solenoid       middleCylinder;
  private Solenoid       topCylinder;

  private TalonSRX liftDriveMotor;

  // -----------------------------------------------------
  // Constructor
  public Lifter() 
  {
    bottomCylinder1 = new DoubleSolenoid(Constants.LIFTER_BOTTOM_CYLINDER1_INPORT,Constants.LIFTER_BOTTOM_CYLINDER1_OUTPORT);
    bottomCylinder2 = new DoubleSolenoid(Constants.LIFTER_BOTTOM_CYLINDER2_INPORT,Constants.LIFTER_BOTTOM_CYLINDER2_OUTPORT);

    middleCylinder  = new Solenoid(Constants.LIFTER_MIDDLE_CYLINDER_PORT);
    topCylinder     = new Solenoid(Constants.LIFTER_TOP_CYLINDER_PORT);

    liftDriveMotor = new TalonSRX(Constants.LIFT_DRIVE_MOTOR_CAN_ID); 
  }
  
  // -----------------------------------------------------
  // Manage bottom double pneumatic cyliders synchronously
  public void extendBottomCylinders() 
  {
    bottomCylinder1.set(DoubleSolenoid.Value.kForward);
    bottomCylinder2.set(DoubleSolenoid.Value.kForward);
  }
  
  public void retractBottomCylinders() 
  {
    bottomCylinder1.set(DoubleSolenoid.Value.kReverse);
    bottomCylinder2.set(DoubleSolenoid.Value.kReverse);
  }
    
  // -----------------------------------------------------
  // Manage middle single solenoid
  public void extendeMiddleCylinder() 
  {
    middleCylinder.set(true);
  }
      
  public void retractMiddleCylinder() 
  {
    middleCylinder.set(false);
  }
    
  // -----------------------------------------------------
  // Manage bottom single solenoid
  public void extendTopCylinder() 
  {
    topCylinder.set(true);
  }
      
  public void retractTopCylinder() 
  {
    topCylinder.set(false);
  }
  // -----------------------------------------------------
  // Lifter rail drive motor
  public void driveLifter(double motorLevel)
  {
    liftDriveMotor.set(ControlMode.PercentOutput,motorLevel); 
  }
 
}