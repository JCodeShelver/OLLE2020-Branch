// FRC Team 3770 - BlitzCreek - OLLE 2020
// Drive Human Command
// Has access to the DriveSystem subsystem.
// Receives joystick values and sets them to
// drive system. It is defined as the default 
// command so it never ends.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.subsystems.DriveSystem;
import frc.robot.subsystems.FrontIntake;

import frc.robot.Constants;

public class DriveHuman extends CommandBase 
{
  private final DriveSystem driveSystem;   // Reference to drive system object 
  
  private DoubleSupplier leftStickValue, rightStickXValue, rightStickYValue;
  private final Joystick leftStick, rightStick;
  private final XboxController controller;

  // ----------------------------------------------------------------------------
  // Constructor
  public DriveHuman(DriveSystem d, DoubleSupplier righty, DoubleSupplier left, DoubleSupplier rightx)
  {
    driveSystem = d;
    
    controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);
    
    leftStick  = new Joystick(Constants.LEFT_STICK_USB_PORT);
    rightStick = new Joystick(Constants.RIGHT_STICK_USB_PORT);
    
    leftStickValue  =  left;
    rightStickXValue = rightx;
    rightStickYValue = righty;

    addRequirements(driveSystem);
  }

  // ----------------------------------------------------------------------------
  // Initialization
  @Override
  public void execute()
  {
      SmartDashboard.updateValues();

      if (rightStick.getRawButton(11))
        driveSystem.ArcadeDrive(rightStickYValue.getAsDouble() / 2, -rightStickXValue.getAsDouble() / 2);
      else
        driveSystem.ArcadeDrive(rightStickYValue.getAsDouble(), -rightStickXValue.getAsDouble());

      // if(leftStick.getRawButton(1))
      //   driveSystem.Quadraticdrive(leftStickValue.getAsDouble(),rightStickYValue.getAsDouble());
      // else
      
        // driveSystem.Quadraticdrive(leftStickValue.getAsDouble(),rightStickYValue.getAsDouble());
  }
}
