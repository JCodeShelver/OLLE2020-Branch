// FRC Team 3770 - BlitzCreek - OLLE 20
// Basic auton routine
// Drive forward two seconds and stop

package frc.robot.commands;

import frc.robot.subsystems.DriveSystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.Timer;

public class AutonSimple extends CommandBase {

  private final DriveSystem driveSystem;   // Reference to drive system object 
  private Timer driveTimer = new Timer();

  private double DRIVE_TIME = 0.5;    // Duration of action

  // ----------------------------------------------------------------------------
  public AutonSimple(DriveSystem d) {
    driveSystem = d;
    addRequirements(driveSystem);
  }

  // ----------------------------------------------------------------------------
  // Initiate shooting by starting action timer
  @Override
  public void initialize() 
  { 
    driveSystem.zeroEncoder();
    driveTimer.reset();
    driveTimer.start();
  }

  // ----------------------------------------------------------------------------
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {


    if (driveTimer.get() < DRIVE_TIME)
      driveSystem.drive(0.3,0.3);
    else
      driveSystem.drive(0.0,0.0);

  }

  // ----------------------------------------------------------------------------
  // Return true when timer reaches designated target time.
  @Override
  public boolean isFinished() 
  {
    if (driveTimer.get() >= DRIVE_TIME)
       return true;
    else
        return false;
  }
}
