// FRC Team 3770 - BlitzCreek - OLLE 20
// Front Intake subsystem
// Manage robot ball intake system

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.*;  
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class FrontIntake extends SubsystemBase 
{ 
    private TalonSRX sideToSideMotor,  intakeMotor;
    private DoubleSolenoid deployCylinder;

  // -----------------------------------------------------
  // Constructor
  public FrontIntake() 
  {
    sideToSideMotor = new TalonSRX(Constants.INTAKE_SIDE_MOTOR_CAN_ID);
    intakeMotor     = new TalonSRX(Constants.INTAKE_FRONTBACK_MOTOR_CAN_ID); 

    deployCylinder = new DoubleSolenoid(Constants.PCM_MODULE_1,Constants.INTAKE_CYLINDER_INPORT,Constants.INTAKE_CYLINDER_OUTPORT);
  }

  // -----------------------------------------------------
  // Manage side-to-side motor action
  public void sideSideMotorOn() 
  {
    sideToSideMotor.set(ControlMode.PercentOutput,1.0); 
  }

  public void sideSideMotorOff() 
  {
    sideToSideMotor.set(ControlMode.PercentOutput,0.0); 
  }
  
  // -----------------------------------------------------
  // Manage intake motor action
  public void intakeMotorOn() 
  {
    intakeMotor.set(ControlMode.PercentOutput,1.0); 
  }

  public void intakeMotorOff() 
  {
    intakeMotor.set(ControlMode.PercentOutput,0.0); 
  }

  // -----------------------------------------------------
  // Manage pneumatic unit for front intake
  public void moveDown() 
  {
    deployCylinder.set(DoubleSolenoid.Value.kForward);
  }

  public void moveUp() 
  {
    deployCylinder.set(DoubleSolenoid.Value.kReverse);
  }
}