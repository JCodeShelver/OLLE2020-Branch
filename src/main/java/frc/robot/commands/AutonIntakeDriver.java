// FRC Team 3770 - BlitzCreek - OLLE 2020
// Autonomous Intake Driver Command
// Manages the Intake mechanism during the
// Autonomous period.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.FrontIntake;

import frc.robot.Constants;

public class AutonIntakeDriver extends CommandBase 
{
  private final FrontIntake frontIntake;   
  private double input;

  // ----------------------------------------------------------------------------
  // Constructor
  public AutonIntakeDriver(FrontIntake i, double in) 
  {
    frontIntake = i;
    input = in;

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
      System.out.println(input);
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