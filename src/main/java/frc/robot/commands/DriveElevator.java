// FRC Team 3770 - BlitzCreek - OLLE 2020
// Drive Elevator Command
// Deploys Elevator structure to deliver 
// the Bar Crawler mechanism.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import frc.robot.subsystems.Elevator;

import frc.robot.Constants;

public class DriveElevator extends CommandBase 
{
  private final Elevator elevator;   
  
  private final XboxController controller;

  // ----------------------------------------------------------------------------
  // Constructor
  public DriveElevator(Elevator e) 
  {
    elevator = e;
    //addRequirements(elevator);
    controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);
  }

  // ----------------------------------------------------------------------------
  // Initialization
  @Override
  public void initialize() 
  { 
     
  }

  // ----------------------------------------------------------------------------
  //  
  @Override
  public void execute() 
  {
    double crawlerInput = controller.getY(Hand.kLeft);
    elevator.driveElevator(crawlerInput);

    double winchInput = Math.abs(controller.getY(Hand.kRight));
    elevator.driveWinch(winchInput);

    Constants.EndgameEnabled = true;
  }

  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return false;
  }
}