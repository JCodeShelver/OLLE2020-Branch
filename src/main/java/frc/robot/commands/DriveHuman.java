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
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveHuman extends CommandBase 
{

  private final DriveSystem driveSystem;   // Reference to drive system object 
  private FrontIntake frontIntake;
  private DoubleSupplier leftStickValue;
  private DoubleSupplier rightStickYValue;
  private DoubleSupplier rightStickXValue;
  private XboxController controller;
  private Joystick rightStick;

  public DriveHuman(DriveSystem d, DoubleSupplier righty, DoubleSupplier left, DoubleSupplier rightx)
  {
    driveSystem = d;
    controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);
    rightStick = new Joystick(Constants.RIGHT_STICK_USB_PORT);
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
      if(rightStick.getRawButton(11))
        driveSystem.ArcadeDrive(rightStickYValue.getAsDouble()/2, -rightStickXValue.getAsDouble()/2);
      else
        driveSystem.ArcadeDrive(rightStickYValue.getAsDouble(), -rightStickXValue.getAsDouble());
  }

}
