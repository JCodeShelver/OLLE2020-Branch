// FRC Team 3770 - BlitzCreek - OLLE 2020
// Queue Manager Command
// Manages the Shooter and Loader subsystems
// to control what triggers when.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.subsystems.Loader;

import frc.robot.Constants;

public class QueueManager extends CommandBase 
{
    private Loader loader;
    private XboxController controller;

    private boolean ballInQueue = false, ballComingIn = false, ballAtBack = false;

    // ----------------------------------------------------------------------------
    // Constructor
    public QueueManager(Loader l) 
    {
        loader     = l;
        controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);

        addRequirements(loader);
    }
    
    // ----------------------------------------------------------------------------
    // Initialization
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
            loader.MovingMotorOn(.25);
        }
        else if (ballAtBack)
            loader.MovingMotorOff();
        else if(Constants.shooterSystemActive)
            loader.MovingMotorOn(.5);
        else if (ballComingIn)
            loader.MovingMotorOn(.5);
        else if (!ballComingIn)
            loader.MovingMotorOff();

        if (Constants.ballInShooter)
            loader.LoadBallMotorOff();

        if(controller.getRawButton(XboxController.Button.kStickRight.value))
            loader.MovingMotorOn(.5);
        else   
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