// FRC Team 3770 - BlitzCreek - OLLE 2020
// RobotContainer Class
// Declare and instantiate robot objects.  Assign button actions.  
// Set default drive.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.commands.AutonIntakeDriver;
import frc.robot.commands.AutonShooting;
import frc.robot.commands.AutonSimple;
import frc.robot.commands.AutonStages;
import frc.robot.commands.AutoPickUpBalls;
import frc.robot.commands.DriveAlignToTarget;
import frc.robot.commands.DriveElevator;
import frc.robot.commands.DriveHuman;
import frc.robot.commands.FrontIntakeDriver;
import frc.robot.commands.GetColorData;
import frc.robot.commands.PneumaticManager;
import frc.robot.commands.PrepareToShoot;
import frc.robot.commands.QueueManager;
import frc.robot.commands.ShootDefaultActions;
import frc.robot.commands.SpinnerControl;

import frc.robot.subsystems.DriveSystem;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.FrontIntake;
import frc.robot.subsystems.GyroPID;
import frc.robot.subsystems.Loader;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spinner;
import frc.robot.subsystems.VisionPID;

public class RobotContainer 
{  
  // Instantiate joystick objects
  private final Joystick leftStick  = new Joystick(Constants.LEFT_STICK_USB_PORT);
  private final Joystick rightStick = new Joystick(Constants.RIGHT_STICK_USB_PORT);
  private final XboxController controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);

  // Instantiate all robot subsystems
  private final DriveSystem   driveSystem   = new DriveSystem();
  private final Elevator      elevator      = new Elevator();
  private final FrontIntake   frontIntake   = new FrontIntake();
  private final GyroPID       gyroPID       = new GyroPID();
  private final Loader        loader        = new Loader();
  private final Shooter       shooter       = new Shooter();
  private final Spinner       spinner       = new Spinner();
  private final VisionPID     visionPID     = new VisionPID();

  // ----------------------------------------------------------------------------
  // Constructor
  public RobotContainer()
  {
    configureButtonBindings();

    driveSystem.setDefaultCommand(
       new DriveHuman(driveSystem,
                      () -> rightStick.getY(),
                      () -> leftStick.getY(),
                      () -> rightStick.getZ()
                      ));
    shooter.setDefaultCommand(new ShootDefaultActions(shooter, visionPID, elevator, spinner));
    loader.setDefaultCommand(new QueueManager(loader));
    frontIntake.setDefaultCommand(new FrontIntakeDriver(frontIntake, controller));
  }

  // ----------------------------------------------------------------------------
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
    new JoystickButton(rightStick, 4).toggleWhenPressed(new PrepareToShoot(shooter, visionPID));
    new JoystickButton(rightStick, 10).whenPressed(new GetColorData(spinner));

    new JoystickButton(controller, XboxController.Button.kB.value).whenPressed(new SpinnerControl(spinner));
    new JoystickButton(controller, XboxController.Button.kBumperLeft.value).toggleWhenPressed(new DriveElevator(elevator));

    //Toggling pneumatics
    new JoystickButton(rightStick, 1).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, shooter, Constants.IntakeMovementActions.TOGGLE_INTAKE_UP_DOWN));

    new JoystickButton(controller, XboxController.Button.kX.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, shooter, Constants.IntakeMovementActions.WOF_CONTACT_DISENGAGE));    
    new JoystickButton(controller, XboxController.Button.kY.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, shooter, Constants.IntakeMovementActions.WOF_UP_DOWN));
    new JoystickButton(controller, XboxController.Button.kBack.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, shooter, Constants.IntakeMovementActions.ELEVATOR_BOTTOM_CYLINDERS));
    new JoystickButton(controller, XboxController.Button.kStart.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, shooter, Constants.IntakeMovementActions.ELEVATOR_TOP_CYLINDERS));  
  }

  // ----------------------------------------------------------------------------
  // Set Autonomous routine based on Smart Dashboard choice.
  public Command getAutonomousCommand() 
  {
    // Set simple auton routine as default
    Command autonCommandChoice = new AutonSimple(driveSystem);

    if (SmartDashboard.getBoolean("Auton Stages",true))
        autonCommandChoice = new AutonStages(driveSystem, gyroPID, frontIntake, shooter, visionPID);
        
    return autonCommandChoice;
  }
}

