// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to 

package frc.robot.commands;

import frc.robot.subsystems.Loader;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LoadOneBall extends CommandBase 
{

  private final Loader loader;   

  // ----------------------------------------------------------------------------
  public LoadOneBall(Loader l) 
  {
    loader = l;
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