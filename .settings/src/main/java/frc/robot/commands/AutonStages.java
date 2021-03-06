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
import frc.robot.subsystems.FrontIntake;
import frc.robot.subsystems.GyroPID;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VisionPID;

public class AutonStages extends SequentialCommandGroup 
{
    // --------------------------------------------------------------------------
    // Constuctor
    public AutonStages(DriveSystem driveSystem, GyroPID gyroPID, FrontIntake intake, Shooter shooter, VisionPID visionPID)
    {
        gyroPID.resetGyro();
        
        addCommands(
            new AutonShooting(shooter, visionPID, 3),
            new DriveSegment(driveSystem, gyroPID, -0.5, 40, 0.0),
            new AutoPickUpBalls(driveSystem, gyroPID, intake),
            new DriveTurn(driveSystem, gyroPID, 45.0),
            new AutonShooting(shooter, visionPID, 3)
        );

        /*
           new DriveSegment(driveSystem,gyroPID,0.5,44,45.0), 
           new DriveSegment(driveSystem,gyroPID,0.5,111,-90.0) ); */
    }
}
