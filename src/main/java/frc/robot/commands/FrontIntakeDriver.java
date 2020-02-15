// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to 

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.FrontIntake;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FrontIntakeDriver extends CommandBase 
{
    private XboxController controller;
    private final FrontIntake frontIntake;   
    private double input;

  // ----------------------------------------------------------------------------
  public FrontIntakeDriver(FrontIntake i) 
  {
    frontIntake = i;
    controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);
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
    if(frontIntake.isOut())
    {
      input = controller.getTriggerAxis(Hand.kRight);
      frontIntake.driveIntakeMotors(input);
    }
    else
      frontIntake.driveIntakeMotors(0.0);

  }

  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return false;
  }

}