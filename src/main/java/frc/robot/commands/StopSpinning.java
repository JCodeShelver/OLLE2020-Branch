// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to spin spinner until given color
// Requires access to spinner system.  Performs one spin cycle.

package frc.robot.commands;

import frc.robot.subsystems.Spinner;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class StopSpinning extends CommandBase {

  private final Spinner spinner;   // Reference to spinner system object 


  // ----------------------------------------------------------------------------
  public StopSpinning(Spinner s) 
  {
    spinner = s;
  }

  // ----------------------------------------------------------------------------
  // Initization
  @Override
  public void initialize() 
  { 
    spinner.initiateColorSampler();  // Start sampling colors
  }

  // ----------------------------------------------------------------------------
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {

    // spinner.sampleRecentColors();      // Build sample set of most recent colors sensed

    spinner.motorOff();

  }

  // ----------------------------------------------------------------------------
  // Return true when color reaches input color
  @Override
  public boolean isFinished() 
  {
    return true;

  }



}
