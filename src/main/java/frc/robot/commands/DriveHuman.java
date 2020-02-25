// FRC Team 3770 - BlitzCreek - OLLE 2020
// Drive Human Command
// Has access to the DriveSystem subsystem.
// Receives joystick values and sets them to
// drive system. It is defined as the default 
// command so it never ends.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import frc.robot.Constants;
import frc.robot.subsystems.DriveSystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FrontIntake;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveHuman extends CommandBase 
{

  private final DriveSystem driveSystem;   // Reference to drive system object 
  private final FrontIntake frontIntake;
  private DoubleSupplier leftStickValue;
  private DoubleSupplier rightStickValue;
  private XboxController controller;

  public DriveHuman(DriveSystem d, DoubleSupplier left, DoubleSupplier right, FrontIntake f)
  {
    driveSystem     = d;
    frontIntake     = f;
    controller      = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);
    leftStickValue  = left;
    rightStickValue = right;

    addRequirements(frontIntake, driveSystem);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
      SmartDashboard.updateValues();
      driveSystem.drive(leftStickValue.getAsDouble(),rightStickValue.getAsDouble());
  }
}
