package frc.robot.commands;

import frc.robot.subsystems.Loader;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class QueueManager extends CommandBase 
{
    private Loader loader;
    private Shooter shooter;

    boolean ballInQueue = false, ballComingIn = false, ballAtBack = false;

  // ----------------------------------------------------------------------------
  public QueueManager(Loader l, Shooter s) 
  {
      loader = l;
      shooter = s;

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
    if(shooter.isBallInShooter()== false)
        shooter.lowerShootingPiston();
    
    
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
    if(ballAtBack)
    {
        loader.MovingMotorOff();
    }
    else if(ballComingIn)
        loader.MovingMotorOn();

    else if(ballInQueue)
        loader.MovingMotorOff();
    

    // load a ball if you can
    if(ballAtBack && shooter.isBallInShooter()  == false)
        loader.LoadBallMotorOn();
        loader.MovingMotorOn();
    
    if(shooter.isBallInShooter() == true)
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