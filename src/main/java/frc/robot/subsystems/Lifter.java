// FRC Team 3770 - BlitzCreek - OLLE 20
// Lifter subsystem
// Manage robot lifter for end game

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Lifter extends SubsystemBase 
{ 
  private DoubleSolenoid cylinder1;
  private DoubleSolenoid cylinder2;

  // -----------------------------------------------------
  // Constructor
  public Lifter() 
  {
    cylinder1 = new DoubleSolenoid(Constants.LIFTER_CYLINDER1_INPORT,Constants.LIFTER_CYLINDER1_OUTPORT);
    cylinder2 = new DoubleSolenoid(Constants.LIFTER_CYLINDER2_INPORT,Constants.LIFTER_CYLINDER2_OUTPORT);
  }
  
  // -----------------------------------------------------
  // Extend both pneumatic cyliders to fully extend the lifter
  public void extend() 
  {
    cylinder1.set(DoubleSolenoid.Value.kForward);
    cylinder2.set(DoubleSolenoid.Value.kForward);
  }
  
  // -----------------------------------------------------
  // Retract both pneumatic cyliders on lifter
  public void retract() 
  {
    cylinder1.set(DoubleSolenoid.Value.kReverse);
    cylinder2.set(DoubleSolenoid.Value.kReverse);
  }
 
}