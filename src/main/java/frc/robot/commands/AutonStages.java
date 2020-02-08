// FRC Team 3770 - BlitzCreek - OLLE 20
// Auto routine requiring multiple segments & turns in sequence
// Drive forward two seconds and stop

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.GyroPID;
import frc.robot.subsystems.DriveSystem;
import frc.robot.commands.DriveSegment;
import frc.robot.commands.DriveTurn;

public class AutonStages extends SequentialCommandGroup 
{
    public AutonStages(DriveSystem driveSystem, GyroPID gyroPID)
    {
        gyroPID.resetGyro();
        addCommands(

           new DriveSegment(driveSystem,gyroPID,0.5,80,0.0),
           
           new DriveTurn(driveSystem,gyroPID,45.0)
        );

        /*

           new DriveSegment(driveSystem,gyroPID,0.5,44,45.0),
           
           new DriveSegment(driveSystem,gyroPID,0.5,111,-90.0) ); */
    }
}
