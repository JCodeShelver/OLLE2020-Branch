// FRC Team 3770 - BlitzCreek - OLLE 2020
// Front Intake Driver Command
// Command that manages the front intake 
// mechanism.

package frc.robot.commands;

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
  public FrontIntakeDriver(FrontIntake i, XboxController c)
  {
    frontIntake = i;
    controller = c;
    addRequirements(frontIntake);
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
      if(controller.getTriggerAxis(Hand.kRight) > 0.0)
        input = controller.getTriggerAxis(Hand.kRight);
      else if(controller.getTriggerAxis(Hand.kLeft) > 0.0)
        input = -controller.getTriggerAxis(Hand.kLeft);
      else
        input = 0;

      frontIntake.driveIntakeMotors(input);
    }
    else
      frontIntake.driveIntakeMotors(0.0);

    if (controller.getTriggerAxis(Hand.kRight) > .5) // Set speed to RT if > halfway 
      frontIntake.driveIntakeMotors(controller.getTriggerAxis(Hand.kRight));

    if (controller.getBumper(Hand.kRight)) // Kill Button
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