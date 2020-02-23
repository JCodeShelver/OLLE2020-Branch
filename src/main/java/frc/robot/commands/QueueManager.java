// FRC Team 3770 - BlitzCreek - OLLE 2020
// Queue Manager Command
// Manages the Shooter and Loader subsystems
// to control what triggers when.

package frc.robot.commands;

import frc.robot.subsystems.Loader;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class QueueManager extends CommandBase 
{
    private Loader loader;
    private Shooter shooter;

    boolean ballInQueue = false, ballComingIn = false, ballAtBack = false, ballReady = false;

  // ----------------------------------------------------------------------------
  public QueueManager(Loader l, Shooter s) 
  {
    loader = l;
    shooter = s;
    addRequirements(loader, shooter);
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


    // Logic for belt movement
    if (ballAtBack)
    {
        loader.MovingMotorOff();
    }
    else if (ballComingIn)
        loader.MovingMotorOn();

    else if (ballInQueue)
        loader.MovingMotorOff();
    

    // Load a ball if vacant
    if (ballAtBack && Constants.BallInShooter== false)
        loader.LoadBallMotorOn();
        loader.MovingMotorOn();
    
    if (Constants.BallInShooter == true)
        loader.MovingMotorOff();


}
  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return false;
  }
}