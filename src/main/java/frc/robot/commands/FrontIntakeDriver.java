// FRC Team 3770 - BlitzCreek - OLLE 2020
// Front Intake Driver Command
// Command that manages the front intake 
// mechanism.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.subsystems.FrontIntake;

public class FrontIntakeDriver extends CommandBase 
{
  private final FrontIntake frontIntake;   
  private XboxController controller;
  private double input;

  // ----------------------------------------------------------------------------
  // Constructor
  public FrontIntakeDriver(FrontIntake i, XboxController c)
  {
    frontIntake = i;
    controller = c;
  
    addRequirements(frontIntake);
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
    if (frontIntake.isOut())
    {
      if (controller.getTriggerAxis(Hand.kRight) > 0.5)
        input = controller.getTriggerAxis(Hand.kRight);
      else if (controller.getTriggerAxis(Hand.kLeft) > 0.5)
        input = -controller.getTriggerAxis(Hand.kLeft);
      else
        input = 0;

      frontIntake.driveIntakeMotors(input);
    }
    else
      frontIntake.driveIntakeMotors(0.0);

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