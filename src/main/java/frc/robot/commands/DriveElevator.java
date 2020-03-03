// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to 

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Elevator;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveElevator extends CommandBase 
{
  private final Elevator elevator;   
  XboxController controller;

  // ----------------------------------------------------------------------------
  public DriveElevator(Elevator e) 
  {
    elevator = e;
    //addRequirements(elevator);
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
    // double crawlerInput = controller.getY(Hand.kLeft);
    // elevator.driveElevator(crawlerInput);

    double elevatorInput = controller.getY(Hand.kLeft);

    if(elevatorInput > 0.05)
      elevator.retractBottomCylinders();

    else if(elevatorInput < -0.05)
      elevator.extendBottomCylinders();

    else 
      elevator.stopBottomCylinders();

    
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