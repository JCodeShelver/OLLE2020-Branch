// FRC Team 3770 - BlitzCreek - OLLE 20
// Auto routine requiring multiple segments & turns in sequence
// Drive forward two seconds and stop

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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

public class AutonTargetAndShootSystem extends ParallelDeadlineGroup
{
    DriveSystem driveSystem;
    GyroPID gyroPID;

    public AutonTargetAndShootSystem(DriveSystem d, Shooter s, VisionPID v, int BallCount)
    {
        super(new AutonShooting(s, v, BallCount), new DriveAlignToTarget(d, v));           
    }
}
