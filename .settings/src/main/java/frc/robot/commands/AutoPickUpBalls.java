// FRC Team 3770 - BlitzCreek - OLLE 2020
// Autonomous Pick-up Balls Command
// Autonomous routine requiring multiple
// segments & turns in sequence.

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

public class AutoPickUpBalls extends ParallelDeadlineGroup
{
    // --------------------------------------------------------------------------
    // Constructor
    public AutoPickUpBalls(DriveSystem d, GyroPID g, FrontIntake i)
    {
        super(new DriveSegment(d, g, 0.5, 80, 0.0), new AutonIntakeDriver(i, 0.75));           
    }
}
