// FRC Team 3770 - BlitzCreek - OLLE 2020
// Drive Elevator Command
// Deploys Elevator structure to deliver 
// the Bar Crawler mechanism.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;

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

    controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);
    
    //addRequirements(elevator);
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
    // double crawlerInput = controller.getY(Hand.kLeft);
    // elevator.driveElevator(crawlerInput);

    double elevatorInput = controller.getY(Hand.kLeft);

    if (elevatorInput > 0.05)
      elevator.retractBottomCylinders();
    else if (elevatorInput < -0.05)
      elevator.extendBottomCylinders();
    else 
      elevator.stopBottomCylinders();

    double winchInput = Math.abs(controller.getY(Hand.kRight));

    if (controller.getPOV(0) > 0)
    {
      elevator.driveWinch(-winchInput);
      SmartDashboard.putBoolean("Winch Inverted", true);
    }
    else
    {
      elevator.driveWinch(winchInput);
      SmartDashboard.putBoolean("Winch Inverted", false);
    }
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