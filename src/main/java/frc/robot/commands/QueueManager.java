package frc.robot.commands;

import frc.robot.subsystems.Loader;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class QueueManager extends CommandBase 
{
    private Loader loader;
    private XboxController controller;
    private Joystick leftStick;

    boolean ballInQueue = false, ballComingIn = false, ballAtBack = false;

  // ----------------------------------------------------------------------------
  public QueueManager(Loader l) 
  {
      loader = l;
      controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);
      leftStick = new Joystick(Constants.LEFT_STICK_USB_PORT);
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
    
    //Intake sensor
    if(loader.ballAtIntake())
        ballComingIn = true;
    else

        ballComingIn = false;
    
    //Inside of Conveyor
    if(loader.ballInSystem())
        ballInQueue = true;
    else
        ballInQueue = false;

    //Back of queue area
    if(loader.ballAtBack())
        ballAtBack = true;
    else
        ballAtBack = false;




        // logic for the moving of the belts



    if(ballAtBack && !Constants.ballInShooter)
    {

        loader.LoadBallMotorOn();
        loader.MovingMotorOn(.5);
    }
    else if(ballAtBack)
    {
        loader.MovingMotorOff();
    }
    else if(Constants.shooterSystemActive)
    {
        loader.MovingMotorOn(.5);
    }
    else if(ballComingIn)
        loader.MovingMotorOn(.5);
    else if(!ballComingIn)
        loader.MovingMotorOff();


     if(Constants.ballInShooter)
    {
        //loader.MovingMotorOff();
        loader.LoadBallMotorOff();
    }

    // if(controller.getRawButton(10))
    //     loader.MovingMotorOn(.5);
    // else if(controller.getRawButton(9))
    //     loader.MovingMotorOn(-.5);
    // else
    //     loader.MovingMotorOff();

    if(leftStick.getY() > .1 || leftStick.getY() < -.1)
        loader.MovingMotorOn(leftStick.getY());





}
  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return false;
  }

}