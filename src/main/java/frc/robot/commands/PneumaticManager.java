package frc.robot.commands;

import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.FrontIntake;
import frc.robot.subsystems.Spinner;
import frc.robot.Constants;
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
      if(actionCode == Constants.IntakeMovementActions.TOGGLE_INTAKE_UP_DOWN)
      {
        if(frontIntake.isOut())
            frontIntake.pullUp();
        else
            frontIntake.pushDown();
      }
      if(actionCode == Constants.IntakeMovementActions.WOF_CONTACT_DISENGAGE)
      {
        if(spinner.engaged())
            spinner.wheelCylinderExtend();
        else
            spinner.wheelCylinderRetract();
      }
      if(actionCode == Constants.IntakeMovementActions.WOF_UP_DOWN)
      {
        if(spinner.systemUp())
            spinner.assemblyCylinderRetract();
        else
            spinner.assemblyCylinderExtend();
      }
      if(actionCode == Constants.IntakeMovementActions.ELEVATOR_BOTTOM_CYLINDERS)
      {
        if(elevator.TopDeployed())
            elevator.retractTop2Cylinders();
        else
            elevator.extendTop2Cylinders();
      }
      if(actionCode == Constants.IntakeMovementActions.ELEVATOR_TOP_CYLINDERS)
      {
        if(elevator.BottomDeployed())
            elevator.retractBottomCylinders();
        else
            elevator.extendBottomCylinders();
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