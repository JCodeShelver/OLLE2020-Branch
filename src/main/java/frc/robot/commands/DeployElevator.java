// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to 

package frc.robot.commands;

import frc.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DeployElevator extends CommandBase 
{
  private final Elevator elevator;   

  // ----------------------------------------------------------------------------
  public DeployElevator(Elevator e) 
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