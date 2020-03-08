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
    |------------------------------------------------------------------------------------------------------------------------------------+
    |                                                   ___  _         ___                                                               |
    |                                                  / _ )(_)__  ___/ (_)__  ___ ____                                                  |
    |                                                 / _  / / _ \/ _  / / _ \/ _ `(_-<                                                  |
    |                                                /____/_/_//_/\_,_/_/_//_/\_, /___/                                                  |
    |                                                                        /___/                                                       |
    |--------------+-------------------+-----------------------+----------------------------+--------------------------------------------+
    |              |      Buttons      |     Left Joystick     |       Right Joystick       |                  Xbox Controller           |
    |--------------+-------------------+-----------------------+----------------------------+--------------------------------------------+
    |    ______    |1       /        A | NOT BOUND             | Front Intake Toggle      OP| NOT BOUND                                  |
    |   /_____//\  |-------------------+-----------------------+----------------------------+--------------------------------------------+
    |  |/     \//| |2       /        B | NOT BOUND             | NOT BOUND                  | Spinner Control                          OP|
    |  |       |/| |-------------------+-----------------------+----------------------------+--------------------------------------------+
    |  |   B   |/| |3       /        X | NOT BOUND             | Switch Camera Mode       OP| Spinner Contact Toggle                   OP|
    |  |       |/| |-------------------+-----------------------+----------------------------+--------------------------------------------+
    |  |       |/| |4       /        Y | NOT BOUND             | Prepare To Shoot        TOP| Spinner Assembly Toggle                  OP|
    |  |   U   |/| |-------------------+-----------------------+----------------------------+--------------------------------------------+
    |  |       |/| |5       /       LB | NOT BOUND             | Choke Shooter RPM        OH| Drive Elevator                          TOP|
    |  |       |/| |-------------------+-----------------------+----------------------------+--------------------------------------------+
    |  |   T   |/| |6       /       RB | NOT BOUND             | Align to Target         TOP| Stop Front Intake motors, Shoot Ball  OH/OH|
    |  |       |/| |-------------------+-----------------------+----------------------------+--------------------------------------------+
    |  |       |/| |7       /     Back | NOT BOUND             | NOT BOUND                  | NOT BOUND                                  |
    |  |   T   |/| |-------------------+-----------------------+----------------------------+--------------------------------------------+
    |  |       |/| |8       /    Start | NOT BOUND             | NOT BOUND                  | Elevator Top Toggle                      OP|
    |  |       |/| |-------------------+-----------------------+----------------------------+--------------------------------------------+
    |  |   O   |/| |9       /       LS | NOT BOUND             | NOT BOUND                  | NOT BOUND                                  |
    |  |       |/| |-------------------+-----------------------+----------------------------+--------------------------------------------+
    |  |       |/| |10      /       RS | NOT BOUND             | Get Color Data           OP| NOT BOUND                                  |
    |  |   N   |/| |-------------------+-----------------------+----------------------------+--------------------------------------------+
    |  |       |/| |11      /      N/A | NOT BOUND             | Half Drive Inputs        OH| NOT APPLICABLE                             |
    |  \\_____///  |-------------------+-----------------------+----------------------------+--------------------------------------------+
    |   ```````    |12      /      N/A | NOT BOUND             | NOT BOUND                  | NOT APPLICABLE                             |
    |==============+===================+=======================+============================+============================================+
    |==============+===========================+=================================+===========================+==========================================+
    |    _______   |           Axes            |            Continued            |         Continued         |         Continued                        |
    |   /_____//\  |---------------------------+---------------------------------+---------------------------+------------------------------------------+
    |  |/  T  \//| |1  /  X  /   Left X        | NOT BOUND                       | NOT BOUND                 | NOT BOUND                                |    
    |  |   R   |/| |---------------------------+---------------------------------+---------------------------+------------------------------------------+
    |  |   I   |/| |2  /  Y  /   Left Y        | D.Human, D.Target, Moving motor | D.Human                   | Elevator motors                          |
    |  |   G   |/| |---------------------------+---------------------------------+---------------------------+------------------------------------------+
    |  |   G   |/| |3  /  Z  /  LT & RT        | Shooter RPM Tweak               | D.Human                   | Front Intake motors (LT - / RT +)        |
    |  |   E   |/| |---------------------------+---------------------------------+---------------------------+------------------------------------------+
    |  |   R   |/| |4  /  Throttle  /  Right X | NOT BOUND                       | NOT BOUND                 | NOT BOUND                                |
    |  |   S   |/| |---------------------------+---------------------------------+---------------------------+------------------------------------------+
    |  |       |/| |5  /  Hat X  /  Right Y    | NOT BOUND                       | NOT BOUND                 | Winch motors                             |
    |  \\_____///  |---------------------------+---------------------------------+---------------------------+------------------------------------------+
    |    ``````    |6  /  Hat Y  /  D-Pad      | NOT BOUND                       | NOT BOUND                 | NOT BOUND                                |
    |---------------------------------------------------------------------------------------------------------------------------------------------------+
    */
    new JoystickButton(rightStick, 3).whenPressed(() -> visionPID.cameraModeSwitch());
    new JoystickButton(rightStick, 4).toggleWhenPressed(new PrepareToShoot(shooter, visionPID));
    new JoystickButton(rightStick, 6).toggleWhenPressed(new DriveAlignToTarget(driveSystem, visionPID));
    new JoystickButton(rightStick, 10).whenPressed(new GetColorData(spinner));

    new JoystickButton(controller, XboxController.Button.kB.value).whenPressed(new SpinnerControl(spinner));
    new JoystickButton(controller, XboxController.Button.kBumperLeft.value).toggleWhenPressed(new DriveElevator(elevator));

    //Toggling pneumatics
    new JoystickButton(rightStick, 1).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, Constants.IntakeMovementActions.TOGGLE_INTAKE_UP_DOWN));

    new JoystickButton(controller, XboxController.Button.kX.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, Constants.IntakeMovementActions.WOF_CONTACT_DISENGAGE));    
    new JoystickButton(controller, XboxController.Button.kY.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, Constants.IntakeMovementActions.WOF_UP_DOWN));
    // new JoystickButton(controller, XboxController.Button.kBack.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, shooter, Constants.IntakeMovementActions.ELEVATOR_BOTTOM_CYLINDERS));
    new JoystickButton(controller, XboxController.Button.kStart.value).whenPressed(new PneumaticManager(frontIntake, spinner, elevator, Constants.IntakeMovementActions.ELEVATOR_TOP_CYLINDERS));  
  }

  // ----------------------------------------------------------------------------
  // Set Autonomous routine based on Smart Dashboard choice.
  public Command getAutonomousCommand() 
  {
    // Set simple auton routine as default
    Command autonCommandChoice = new AutonSimple(driveSystem);

    if (SmartDashboard.getBoolean("Auton Stages", true))
        autonCommandChoice = new AutonStages(driveSystem, gyroPID, frontIntake, shooter, visionPID, spinner, elevator);

    return autonCommandChoice;
  }
}