// FRC Team 3770 - BlitzCreek - OLLE 2020
// Spin To Color Command
// Command to spin spinner until given color
// Requires access to spinner system.  Performs 7 spin cycles.

package frc.robot.commands;

import frc.robot.subsystems.Spinner;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinnerControl extends CommandBase
{
  private final Spinner spinner;   // Reference to spinner system object 

  private int onColorCount;
  private boolean onTargetColor;
  private boolean isRotationControlDone;
  private boolean isPositionControlDone;

  // ----------------------------------------------------------------------------
  public SpinnerControl(Spinner s) 
  {
    spinner = s;
    onColorCount = 0;
    isRotationControlDone = SmartDashboard.getBoolean("Rotation Control", false);
    isPositionControlDone = SmartDashboard.getBoolean("Position Control", false);
  }

  // ----------------------------------------------------------------------------
  // Initialization
  // If Position control is not done
  @Override
  public void initialize() 
  {
    if (!isRotationControlDone)
    {
      spinner.initiateColorSampler();  // Start sampling colors
      onTargetColor = false;
    }
  }

  // ----------------------------------------------------------------------------
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
    if (!isPositionControlDone)
    { 
      spinner.sampleRecentColors();      // Build sample set of most recent colors sensed
      spinner.motorOn();
      if (!isRotationControlDone)
      {
        // Manage boolean value used for counting
        if (spinner.isSensorOnTargetColor("RtnCtrl")) // If spinner is on blue (easy to count)
        {
          if (!onTargetColor)
            onTargetColor = true;
        }
        else
        {
          if (onTargetColor)
          {
            onTargetColor = false;
            onColorCount++;           // If on color and now off, count one visit
          }
        }
        SmartDashboard.putNumber("Spin Count", onColorCount);
      }
    }
  }
  // ----------------------------------------------------------------------------
  // Return true when color reaches input color / when 
  @Override
  public boolean isFinished() 
  {
    // If Rotation Control is done and Position Control is not:
    if (isRotationControlDone && !isPositionControlDone)
    {
      if (spinner.isSensorOnTargetColor("PosCtrl")) // If sensor is on the target color, end
      {
        spinner.motorOff();
        SmartDashboard.putBoolean("Position Control", true);
        return true;
      }
      else                                          // Otherwise continue.
        return false;
    }
    else if (!isRotationControlDone) // If Rotation Control is not done:
    {
      if (spinner.isSensorOnTargetColor("RtnCtrl") && onColorCount >= 7) // If >= 3 rotations done, end 
      {
        spinner.motorOff();
        SmartDashboard.putBoolean("RotationControl", true);
        return true;
      }
      else                                                               // Otherwise continue.
        return false;                                                   
    }
    else // If command was accidentally run:
    {
      return true;
    }
  }
}
