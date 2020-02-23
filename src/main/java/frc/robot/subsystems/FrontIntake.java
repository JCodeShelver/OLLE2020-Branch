// FRC Team 3770 - BlitzCreek - OLLE 2020
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
    private TalonSRX sideToSideMotor, intakeMotor;
    private DoubleSolenoid deployCylinder;
    private boolean isOut = false;

  // -----------------------------------------------------
  // Constructor
  public FrontIntake() 
  {
    sideToSideMotor = new TalonSRX(Constants.INTAKE_SIDE_MOTOR_CAN_ID);
    intakeMotor     = new TalonSRX(Constants.INTAKE_FRONTBACK_MOTOR_CAN_ID); 
    deployCylinder  = new DoubleSolenoid(Constants.PCM1,Constants.INTAKE_CYLINDER_INPORT,Constants.INTAKE_CYLINDER_OUTPORT);
  }
  
  // -----------------------------------------------------
  // Manage intake motors action
  public void driveIntakeMotors(double input) 
  {
    intakeMotor.set(ControlMode.PercentOutput, input); 
    sideToSideMotor.set(ControlMode.PercentOutput, input);
  }

  // -----------------------------------------------------
  // Manage pneumatic unit for front intake
  public void pushDown() 
  {
    deployCylinder.set(DoubleSolenoid.Value.kForward);
    isOut = true;
  }
  
  public void pullUp() 
  {
    deployCylinder.set(DoubleSolenoid.Value.kReverse);
    isOut = false;
  }

  public boolean isOut()
  {
    return isOut;
  }
}