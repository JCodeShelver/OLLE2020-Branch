// FRC Team 3770 - BlitzCreek - OLLE 20
// Default human driving command
// Has access to drive system.  Receives joystick values and sets them to
// drive system.  Defined as the default command so never ends.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import frc.robot.subsystems.DriveSystem;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class DriveHuman extends CommandBase 
{

  private final DriveSystem driveSystem;   // Reference to drive system object 
  private DoubleSupplier leftStickValue;
  private DoubleSupplier rightStickValue;

  public DriveHuman(DriveSystem d, DoubleSupplier left, DoubleSupplier right)
  {
    driveSystem = d;

    leftStickValue  = left;
    rightStickValue = right;

    addRequirements(driveSystem);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
      driveSystem.drive(leftStickValue.getAsDouble(),rightStickValue.getAsDouble());
  }

}
