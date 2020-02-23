// FRC Team 3770 - BlitzCreek - OLLE 2020
// Pneumatic Manager Command
// Manages MOST OF THE PNEMUATICS in one
// file. It does not manage the Shooter
// or Conveyor pneumatics. 
package frc.robot.commands;

import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.FrontIntake;
import frc.robot.subsystems.Spinner;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PneumaticManager extends CommandBase 
{
  private final FrontIntake frontIntake;   
  private final Spinner spinner;
  private final Elevator elevator;
  private Constants.IntakeMovementActions actionCode;

  // ----------------------------------------------------------------------------
  public PneumaticManager(FrontIntake i, Spinner s, Elevator e, Constants.IntakeMovementActions code) 
  {
    frontIntake = i;
    spinner     = s;
    elevator    = e;
    actionCode = code;
  }

  // ----------------------------------------------------------------------------
  // Initization
  @Override
  public void initialize() 
  {

  }

  // ----------------------------------------------------------------------------
  //  
  @Override
  public void execute() 
  {
      if (actionCode == Constants.IntakeMovementActions.TOGGLE_INTAKE_UP_DOWN)
      {
        if (frontIntake.isOut())
            frontIntake.pullUp();
        else
            frontIntake.pushDown();
        SmartDashboard.putBoolean("Intake Pneumatic", frontIntake.isOut());
      }
      if (actionCode == Constants.IntakeMovementActions.WOF_CONTACT_DISENGAGE)
      {
        if (spinner.engaged())
            spinner.wheelCylinderExtend();
        else
            spinner.wheelCylinderRetract();
        SmartDashboard.putBoolean("Wheel Pneumatic", spinner.engaged());
      }
      if (actionCode == Constants.IntakeMovementActions.WOF_UP_DOWN)
      {
        if (spinner.systemUp())
            spinner.assemblyCylinderRetract();
        else
            spinner.assemblyCylinderExtend();
        SmartDashboard.putBoolean("Assembly Pneumatic", spinner.systemUp());
      }
      if (actionCode == Constants.IntakeMovementActions.ELEVATOR_TOP_CYLINDERS)
      {
        if (elevator.TopDeployed())
            elevator.retractTop2Cylinders();
        else
            elevator.extendTop2Cylinders();
        SmartDashboard.putBoolean("Elevator Stages 2-3", elevator.TopDeployed());
      }
      if (actionCode == Constants.IntakeMovementActions.ELEVATOR_BOTTOM_CYLINDERS)
      {
        if (elevator.BottomDeployed())
            elevator.retractBottomCylinders();
        else
            elevator.extendBottomCylinders();
        SmartDashboard.putBoolean("Elevator Stage 1", elevator.BottomDeployed());
      }
  }
  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return true;
  }
}