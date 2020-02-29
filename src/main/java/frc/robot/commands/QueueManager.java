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
    
    if(loader.ballAtIntake())
        ballComingIn = true;
    else

        ballComingIn = false;
    
    if(loader.ballInSystem())
        ballInQueue = true;
    else
        ballInQueue = false;

    if(loader.ballAtBack())
        ballAtBack = true;
    else
        ballAtBack = false;




        // logic for the moving of the belts



    if(ballAtBack && !Constants.ballInShooter)
    {

        loader.LoadBallMotorOn();
        loader.MovingMotorOn();
    }
    else if(ballAtBack)
    {
        loader.MovingMotorOff();
    }
    else if(Constants.shooterSystemActive)
    {
        loader.MovingMotorOn();
    }
    else if(ballComingIn)
        loader.MovingMotorOn();
    else if(!ballComingIn)
        loader.MovingMotorOff();


     if(Constants.ballInShooter)
    {
        //loader.MovingMotorOff();
        loader.LoadBallMotorOff();
    }





}
  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return false;
  }

}