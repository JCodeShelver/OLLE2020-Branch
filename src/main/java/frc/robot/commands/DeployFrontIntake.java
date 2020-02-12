// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to 

package frc.robot.commands;

import frc.robot.subsystems.FrontIntake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DeployFrontIntake extends CommandBase 
{
  private final FrontIntake frontIntake;   

  // ----------------------------------------------------------------------------
  public DeployFrontIntake(FrontIntake i) 
  {
    frontIntake = i;
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

  }

  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return true;
  }

}