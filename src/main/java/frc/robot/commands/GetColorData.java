// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to read color sensor and retrieve color values.
// Use for calibration of spinner color sensor

package frc.robot.commands;

import frc.robot.subsystems.SpinnerSystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class GetColorData extends CommandBase {

  private final SpinnerSystem spinner;   // Reference to spinner system object 

  private int onColorCount;
  private boolean onTargetColor;
  private int cycleCount;
  
  private final int MAX_CYCLES_TO_COUNT = 20;

  // ----------------------------------------------------------------------------
  public GetColorData(SpinnerSystem s) 
  {
    spinner = s;
  }

  // ----------------------------------------------------------------------------
  // Initization
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
      spinner.getRawSensorColor();
      cycleCount++;
  }

  // ----------------------------------------------------------------------------
  // Return true when color reaches input color
  @Override
  public boolean isFinished() 
  {
    if (cycleCount >= MAX_CYCLES_TO_COUNT)
    {
       return true;
    }
    else
        return false;
    
  }

}
