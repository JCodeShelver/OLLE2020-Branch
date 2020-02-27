// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to spin spinner until given color
// Requires access to spinner system.  Performs one spin cycle.

package frc.robot.commands;

import frc.robot.subsystems.Spinner;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinToColor extends CommandBase {

  private final Spinner spinner;   // Reference to spinner system object 

  private int onColorCount;
  private boolean onTargetColor;

  // ----------------------------------------------------------------------------
  public SpinToColor(Spinner s) 
  {
    spinner = s;
    addRequirements(spinner);
    onColorCount = 0;
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

    spinner.motorOn();


    

    // Manage boolean value used for counting
    // if (spinner.isSensorOnTargetColor())
    // {
    //   if (onTargetColor == false)
    //       onTargetColor = true;   
    // }
    // else
    // {
    //   if (onTargetColor == true)
    //   {
    //       onTargetColor = false;
    //       onColorCount++;           // If on color and now off, count one visit
    //   }
    // }

    System.out.println("Count: " + onColorCount);
  }

  // ----------------------------------------------------------------------------
  // Return true when color reaches input color
  @Override
  public boolean isFinished() 
  {

    return false;

    // if (spinner.isSensorOnTargetColor() && onColorCount >= 7)
    // {
    //    spinner.motorOff();
    //    return true;
    // }
    // else
    //     return false;
  }
  public void isInterupted()
  {
    spinner.motorOff();
  }

}
