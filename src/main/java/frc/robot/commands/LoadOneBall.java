// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to 

package frc.robot.commands;

import frc.robot.subsystems.Loader;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LoadOneBall extends CommandBase 
{

  private final Loader loader;   
  private Timer loadingTimer;

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
    loadingTimer.reset();
    loadingTimer.start();
  }

  // ----------------------------------------------------------------------------
  //  
  @Override
  public void execute() 
  {
    if(loadingTimer.get() < 0.2) //however long it takes to retract piston
      loader.LoadBall();
    else
      loader.resetLoadingPiston();
  }

  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return true;
  }

}