// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to 

package frc.robot.commands;

import frc.robot.subsystems.Spinner;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RetractSpinner extends CommandBase 
{

  private final Spinner spinner;   

  // ----------------------------------------------------------------------------
  public RetractSpinner(Spinner s) 
  {
    spinner = s;
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
    spinner.assemblyCylinderRetract();
    spinner.wheelCylinderRetract();
  }

  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return true;
  }

}