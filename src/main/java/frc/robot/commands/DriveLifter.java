// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to 

package frc.robot.commands;

import frc.robot.subsystems.Lifter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveLifter extends CommandBase 
{
  private final Lifter lifter;   

  // ----------------------------------------------------------------------------
  public DriveLifter(Lifter l) 
  {
    lifter = l;
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

  }

  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return true;
  }

}