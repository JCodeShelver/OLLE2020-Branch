// FRC Team 3770 - BlitzCreek - OLLE 2020
// Shooter (Default Actions) Command
// Manages shooter mechanism when not
// explicitly used.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spinner;
import frc.robot.subsystems.VisionPID;

import frc.robot.Constants;

public class ShootDefaultActions extends CommandBase
{
    // Robot object referencess required for this action
    private final Shooter    shooter;
    private final VisionPID  visionPID;
    private final Elevator   elevator;
    private final Spinner    spinner;
    public double            Distance, RPM;

    // --------------------------------------------------------------------------
    // Constructor
    public ShootDefaultActions(Shooter s, VisionPID v, Elevator e, Spinner sp)
    {
        // Capture references to existing robot subsystems.  Define them as requirements.
        shooter     = s;
        visionPID   = v;
        elevator    = e;
        spinner     = sp;

        addRequirements(spinner, elevator, shooter, visionPID);
    }

    // --------------------------------------------------------------------------
    // Initialization
    public void initialize() 
    {
        
    }
    
    // --------------------------------------------------------------------------
    // 
    public void execute() 
    {
        Constants.EndgameEnabled      = false;
        Constants.shooterSystemActive = false;

        elevator.driveWinch(0.0);
        elevator.driveElevator(0.0);

        shooter.updateBallInShooter();
        shooter.stop();
        visionPID.LEDoff();
        spinner.motorOff();
    }
    
    // --------------------------------------------------------------------------
    // 
    public boolean isFinished() 
    {
        return false;
    }
}