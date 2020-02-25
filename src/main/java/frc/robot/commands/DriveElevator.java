// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to 

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveElevator extends CommandBase 
{
  private final Elevator elevator;   
  XboxController controller;

  // ----------------------------------------------------------------------------
  public DriveElevator(Elevator e) 
  {
    elevator = e;
    controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);
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
    double crawlerInput = controller.getAxisType(1);
    elevator.driveElevator(crawlerInput);

    double winchInput = Math.abs(controller.getAxisType(5));
    elevator.driveWinch(winchInput);
    
  }



  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return false;
  }

}