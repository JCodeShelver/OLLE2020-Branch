package frc.robot.commands;

import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.FrontIntake;
import frc.robot.subsystems.Spinner;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PneumaticManager extends CommandBase 
{

  private final FrontIntake frontIntake;   
  private final Spinner spinner;
  private final Elevator elevator;
  private final Shooter shooter;
  private Constants.IntakeMovementActions actionCode;

  // ----------------------------------------------------------------------------
  public PneumaticManager(FrontIntake i, Spinner s, Elevator e, Shooter sh, Constants.IntakeMovementActions code) 
  {
    frontIntake = i;
    spinner     = s;
    elevator    = e;
    shooter     = sh;
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
        SmartDashboard.putBoolean("Intake Pneumatic", frontIntake.isOut());
      }
      
      if(actionCode == Constants.IntakeMovementActions.WOF_CONTACT_DISENGAGE)
      {
        if(spinner.engaged())
            spinner.wheelCylinderExtend();
        else
            spinner.wheelCylinderRetract();
        SmartDashboard.putBoolean("Wheel Pneumatic", spinner.engaged());
      }
      if(actionCode == Constants.IntakeMovementActions.WOF_UP_DOWN)
      {
        if(spinner.systemUp())
            spinner.assemblyCylinderRetract();
        else
            spinner.assemblyCylinderExtend();
        SmartDashboard.putBoolean("Assembly Pneumatic", spinner.systemUp());
      }
      if(actionCode == Constants.IntakeMovementActions.ELEVATOR_TOP_CYLINDERS)
      {
        if(elevator.TopDeployed())
            elevator.retractTop2Cylinders();
        else
            elevator.extendTop2Cylinders();
        SmartDashboard.putBoolean("Elevator Stages 2-3", elevator.TopDeployed());
      }
      // if(actionCode == Constants.IntakeMovementActions.ELEVATOR_BOTTOM_CYLINDERS)
      // {
      //   if(elevator.BottomDeployed())
      //       elevator.retractBottomCylinders();
      //   else
      //       elevator.extendBottomCylinders();
      //   SmartDashboard.putBoolean("Elevator Stage 1", elevator.BottomDeployed());
      // }
      if(actionCode == Constants.IntakeMovementActions.SHOOTER_FIRE_PISTON)
      {
        if(shooter.isShooterPistonDown())
            shooter.shootBall();
        else
            shooter.lowerShootingPiston();

        //SmartDashboard.putBoolean("Shooter Piston Down", elevator.BottomDeployed());
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