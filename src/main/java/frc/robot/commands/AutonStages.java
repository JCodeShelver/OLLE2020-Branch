// FRC Team 3770 - BlitzCreek - OLLE 2020
// Auton Stages Command
// Auto routine requiring multiple segments & turns in sequence
// Drive forward two seconds and stop

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveSegment;
import frc.robot.commands.DriveTurn;

import frc.robot.subsystems.DriveSystem;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.FrontIntake;
import frc.robot.subsystems.GyroPID;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spinner;
import frc.robot.subsystems.VisionPID;

import frc.robot.Constants;

public class AutonStages extends SequentialCommandGroup 
{
    // --------------------------------------------------------------------------
    // Constructor
    public AutonStages(DriveSystem driveSystem, GyroPID gyroPID, FrontIntake intake, Shooter shooter, VisionPID visionPID, Spinner spinner, Elevator elevator)
    {
        gyroPID.resetGyro();
        
        addCommands(
            new AutonTargetAndShootSystem(driveSystem, shooter, visionPID, 3),
            new DriveSegment(driveSystem, gyroPID, 0.5, 10, 0.0)
            //new PneumaticManager(intake, spinner, elevator, Constants.IntakeMovementActions.TOGGLE_INTAKE_UP_DOWN),
            // new DriveTurn(driveSystem, gyroPID, 40),
            // new AutoPickUpBalls(driveSystem, gyroPID, intake, 0.25, 20, 0),
            // new DriveTurn(driveSystem, gyroPID, 45.0),
            // new AutonShooting(shooter, visionPID, 3)
        );

        /*
           new DriveSegment(driveSystem, gyroPID, 0.5, 44, 45.0), 
           new DriveSegment(driveSystem, gyroPID, 0.5, 111, -90.0)); */
    }
}
