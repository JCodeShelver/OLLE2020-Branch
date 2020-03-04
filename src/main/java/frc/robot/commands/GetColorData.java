// FRC Team 3770 - BlitzCreek - OLLE 2020
// Get Color Data Command
// Command to read color sensor and retrieve
// color values. Use for calibration of 
// spinner color sensor.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Spinner;

public class GetColorData extends CommandBase {

  private final Spinner spinner;   // Reference to spinner system object 
  private final int MAX_CYCLES_TO_COUNT = 20;
  private int cycleCount;

  // ----------------------------------------------------------------------------
  // Constructor
  public GetColorData(Spinner s) 
  {
    spinner = s;
  }

  // ----------------------------------------------------------------------------
  // Initialization
  @Override
  public void initialize() 
  { 
    cycleCount = 0;
  }

  // ----------------------------------------------------------------------------
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
      spinner.getSensorColorRGB();
      cycleCount++;
  }

  // ----------------------------------------------------------------------------
  // Return true when color reaches input color
  @Override
  public boolean isFinished() 
  {
    if (cycleCount >= MAX_CYCLES_TO_COUNT)
       return true;
    else
        return false;    
  }
}
