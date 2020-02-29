// FRC Team 3770 - BlitzCreek - OLLE 2020
// Queue Manager Command
// Manages the Shooter and Loader subsystems
// to control what triggers when.

package frc.robot.commands;

import frc.robot.subsystems.Loader;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class QueueManager extends CommandBase 
{
    private Loader loader;

    boolean ballInQueue = false, ballComingIn = false, ballAtBack = false;

  // ----------------------------------------------------------------------------
  public QueueManager(Loader l) 
  {
      loader = l;
      addRequirements(loader);
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
    if (loader.ballAtIntake())
        ballComingIn = true;
    else
        ballComingIn = false;

    if (loader.ballInSystem())
        ballInQueue = true;
    else
        ballInQueue = false;

    if (loader.ballAtBack())
        ballAtBack = true;
    else
        ballAtBack = false;

    // Logic for Belt Movement

    if (ballAtBack && !Constants.ballInShooter)
    {
        loader.LoadBallMotorOn();
        loader.MovingMotorOn();
    }
    else if (ballAtBack)
        loader.MovingMotorOff();
    else if (ballComingIn)
        loader.MovingMotorOn();
    else if (!ballComingIn)
        loader.MovingMotorOff();

    if (Constants.ballInShooter)
        loader.LoadBallMotorOff();
}

  // ----------------------------------------------------------------------------
  @Override
  public boolean isFinished() 
  {
    return false;
  }
}