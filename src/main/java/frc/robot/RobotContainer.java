// FRC Team 3770 - BlitzCreek - OLLE 20
// RobotContainer Class
// Declare and instantiate robot objects.  Assign button actions.  
// Set default drive.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveSystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spinner;
import frc.robot.subsystems.GyroPID;
import frc.robot.subsystems.VisionPID;

import frc.robot.commands.DriveHuman;
import frc.robot.commands.PrepareToShoot;
import frc.robot.commands.ShootBall;
import frc.robot.commands.SpinToColor;
import frc.robot.commands.AutonSimple;
import frc.robot.commands.AutonStages;
import frc.robot.commands.DriveAlignToTarget;
import frc.robot.commands.ShootDefaultActions;
import frc.robot.commands.GetColorData;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer 
{

  // Instantiate joystick objects
  Joystick leftStick  = new Joystick(Constants.LEFT_STICK_USB_PORT);
  Joystick rightStick = new Joystick(Constants.RIGHT_STICK_USB_PORT);

  // Instantiate all robot subsystems
  private final DriveSystem   driveSystem   = new DriveSystem();
  private final Shooter       shooter       = new Shooter();
  private final Spinner       spinner       = new Spinner();
  private final GyroPID       gyroPID       = new GyroPID();
  private final VisionPID     visionPID     = new VisionPID();

  // -----------------------------------------------
  // Class constructor.  Call method to define joystick buttons.  
  // Define default drive action.
  public RobotContainer()
  {
    configureButtonBindings();

    driveSystem.setDefaultCommand(
       new DriveHuman(driveSystem,
                      () -> rightStick.getY(),
                      () -> leftStick.getY()
                      ));

    shooter.setDefaultCommand(new ShootDefaultActions(shooter, visionPID));
  }

  // -----------------------------------------------
  // Define drive button interface control bindings
  private void configureButtonBindings() 
  {
    new JoystickButton(rightStick, 3).whenPressed(new SpinToColor(spinner));
    new JoystickButton(leftStick, 6).toggleWhenPressed(new DriveAlignToTarget(driveSystem, visionPID));
    new JoystickButton(leftStick, 4).toggleWhenPressed(new PrepareToShoot(shooter, visionPID));
    new JoystickButton(rightStick, 1).whileHeld(new ShootBall(shooter, visionPID));

    new JoystickButton(rightStick, 10).whenPressed(new GetColorData(spinner));
  }

  // Determine choice for auton from basic dashboard buttons.  Set choice
  // and return.
  public Command getAutonomousCommand() 
  {
    // Set simple auton routine as default
    Command autonCommandChoice =  new AutonSimple(driveSystem);

    if (SmartDashboard.getBoolean("DB/Button 1",false))
        autonCommandChoice = new AutonStages(driveSystem,gyroPID);

    return autonCommandChoice;
  }
}

