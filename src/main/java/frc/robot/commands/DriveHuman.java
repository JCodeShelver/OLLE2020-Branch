// FRC Team 3770 - BlitzCreek - OLLE 2020
// Drive Human Command
// Has access to the DriveSystem subsystem.
// Receives joystick values and sets them to
// drive system. It is defined as the default 
// command so it never ends.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import frc.robot.subsystems.DriveSystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveHuman extends CommandBase 
{
  private final DriveSystem driveSystem;   // Reference to drive system object 
  private DoubleSupplier leftStickValue;
  private DoubleSupplier rightStickYValue;
  private DoubleSupplier rightStickXValue;

  public DriveHuman(DriveSystem d, DoubleSupplier righty, DoubleSupplier left, DoubleSupplier rightx)
  {
    driveSystem     = d;
    leftStickValue  = left;
    rightStickYValue = righty;
    rightStickXValue = rightx;

    addRequirements(driveSystem);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
      SmartDashboard.updateValues();
      //driveSystem.Quadraticdrive(leftStickValue.getAsDouble(),rightStickYValue.getAsDouble());
      driveSystem.ArcadeDrive(rightStickYValue.getAsDouble(), -rightStickXValue.getAsDouble());
  }
}
