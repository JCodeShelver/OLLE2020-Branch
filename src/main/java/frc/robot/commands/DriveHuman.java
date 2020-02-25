// FRC Team 3770 - BlitzCreek - OLLE 20
// Default human driving command
// Has access to drive system.  Receives joystick values and sets them to
// drive system.  Defined as the default command so never ends.

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
  private FrontIntake frontIntake;
  private DoubleSupplier leftStickValue;
  private DoubleSupplier rightStickValue;
  private XboxController controller;

  public DriveHuman(DriveSystem d, DoubleSupplier right, DoubleSupplier left)
  {
    driveSystem = d;
    controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);
    leftStickValue  = left;
    rightStickValue = right;

    addRequirements(driveSystem);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
      SmartDashboard.updateValues();
      driveSystem.Quadraticdrive(leftStickValue.getAsDouble(),rightStickValue.getAsDouble());
  }

}
