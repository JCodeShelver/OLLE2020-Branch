// FRC Team 3770 - BlitzCreek - OLLE 2020
// Drive Elevator Command
// Deploys Elevator structure to deliver 
// the Bar Crawler mechanism.

package frc.robot.commands;

import frc.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveElevator extends CommandBase 
{
  private final Elevator elevator;   

  // ----------------------------------------------------------------------------
  public DriveElevator(Elevator e) 
  {
    elevator = e;
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
    elevator.extendBottomCylinders();
    elevator.extendTop2Cylinders();
  }

  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return true;
  }
}