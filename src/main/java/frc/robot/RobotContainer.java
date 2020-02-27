// FRC Team 3770 - BlitzCreek - OLLE 2020
// RobotContainer Class
// Declare and instantiate robot objects.  Assign button actions.  
// Set default drive.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.subsystems.DriveSystem;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.FrontIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spinner;
import frc.robot.subsystems.GyroPID;
import frc.robot.subsystems.VisionPID;
import frc.robot.subsystems.Loader;

import frc.robot.commands.DriveHuman;
import frc.robot.commands.FrontIntakeDriver;
import frc.robot.commands.PrepareToShoot;
import frc.robot.commands.ShootBall;
import frc.robot.commands.SpinnerControl;
import frc.robot.commands.AutonSimple;
import frc.robot.commands.AutonStages;
import frc.robot.commands.DriveAlignToTarget;
import frc.robot.commands.DriveElevator;
import frc.robot.commands.ShootDefaultActions;
import frc.robot.commands.GetColorData;
import frc.robot.commands.PneumaticManager;
import frc.robot.commands.QueueManager;

public class RobotContainer 
{
  
  // Instantiate joystick objects
  Joystick leftStick  = new Joystick(Constants.LEFT_STICK_USB_PORT);
  Joystick rightStick = new Joystick(Constants.RIGHT_STICK_USB_PORT);
  XboxController controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);

  // Instantiate all robot subsystems
  private final DriveSystem   driveSystem   = new DriveSystem();
  private final Shooter       shooter       = new Shooter();
  private final Spinner       spinner       = new Spinner();
  private final GyroPID       gyroPID       = new GyroPID();
  private final VisionPID     visionPID     = new VisionPID();
  private final Loader        loader        = new Loader();
  private final FrontIntake   frontIntake   = new FrontIntake();
  private final Elevator      elevator      = new Elevator();

  // -----------------------------------------------
  // Class constructor.  Call method to define joystick buttons.  
  // Define default drive action.
  public RobotContainer()
  {
    configureButtonBindings();

    driveSystem.setDefaultCommand(new DriveHuman(
      driveSystem,
      () -> rightStick.getY(),
      () -> leftStick.getY()));

    shooter.setDefaultCommand(new ShootDefaultActions(shooter, visionPID, elevator, spinner));
    loader.setDefaultCommand(new QueueManager(loader, shooter));
    frontIntake.setDefaultCommand(new FrontIntakeDriver(frontIntake, controller));
  }

  // -----------------------------------------------
  // Define drive button interface control bindings
  private void configureButtonBindings() 
  {
    /*
    kA              1
    kB              2
    kX              3
    kY              4
    kBumperLeft     5
    kBumperRight    6
    kBack           7
    kStart          8
    kStickLeft      9  
    kStickRight     10
    */
    new JoystickButton(leftStick, 4).toggleWhenPressed(new PrepareToShoot(shooter, visionPID));
    new JoystickButton(leftStick, 6).toggleWhenPressed(new DriveAlignToTarget(driveSystem, visionPID));

    new JoystickButton(rightStick, 1).whileHeld(new ShootBall(shooter, visionPID));
    new JoystickButton(rightStick, 3).whenPressed(new SpinnerControl(spinner));
    new JoystickButton(rightStick, 4).toggleWhenPressed(new PrepareToShoot(shooter, visionPID));
    new JoystickButton(rightStick, 6).toggleWhenPressed(new DriveAlignToTarget(driveSystem, visionPID));
    new JoystickButton(rightStick, 10).whenPressed(new GetColorData(spinner));
    
    new JoystickButton(controller, 2).whenPressed(new SpinnerControl(spinner));
    new JoystickButton(controller, 6).whileHeld(new ShootBall(shooter, visionPID));
    new JoystickButton(controller, 5).toggleWhenPressed(new DriveElevator(elevator));
    //Toggling pneumatics
    new JoystickButton(controller, XboxController.Button.kBumperLeft.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, shooter, Constants.IntakeMovementActions.TOGGLE_INTAKE_UP_DOWN));
    new JoystickButton(controller, XboxController.Button.kBumperRight.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, shooter, Constants.IntakeMovementActions.ELEVATOR_BOTTOM_CYLINDERS));
    new JoystickButton(controller, XboxController.Button.kBack.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, shooter, Constants.IntakeMovementActions.ELEVATOR_TOP_CYLINDERS));
    new JoystickButton(controller, XboxController.Button.kStart.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, shooter, Constants.IntakeMovementActions.WOF_CONTACT_DISENGAGE));    
    new JoystickButton(controller, XboxController.Button.kStickLeft.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, shooter, Constants.IntakeMovementActions.WOF_UP_DOWN));
  }

  public Command getAutonomousCommand() 
  {
    // Set simple auton routine as default
    Command autonCommandChoice = new AutonSimple(driveSystem);

    if (SmartDashboard.getBoolean("Auton Stages",true))
        autonCommandChoice = new AutonStages(driveSystem,gyroPID);
    return autonCommandChoice;
  }
}

