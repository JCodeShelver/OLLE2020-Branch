// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to 

package frc.robot.commands;

import frc.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RetractElevator extends CommandBase 
{
  private final Elevator elevator;   

  // ----------------------------------------------------------------------------
  public RetractElevator(Elevator e) 
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
    elevator.retractBottomCylinders();
    elevator.retractTop2Cylinders();
  }

  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return true;
  }

}