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
  // Extend both pneumatic cyliders to fully extend the lifter
  public void extend() 
  {
    bottomCylinder1.set(DoubleSolenoid.Value.kForward);
    bottomCylinder2.set(DoubleSolenoid.Value.kForward);
  }
  
  // -----------------------------------------------------
  // Retract both pneumatic cyliders on lifter
  public void retract() 
  {
    bottomCylinder1.set(DoubleSolenoid.Value.kReverse);
    bottomCylinder2.set(DoubleSolenoid.Value.kReverse);
  }
  
  // -----------------------------------------------------
  // Lifter rail drive motor
  public void driveLifter(double motorLevel)
  {
    liftDriveMotor.set(ControlMode.PercentOutput,motorLevel); 
  }
 
}