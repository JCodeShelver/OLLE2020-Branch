
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spinner;
import frc.robot.subsystems.VisionPID;

public class ShootDefaultActions extends CommandBase
{
    // Robot object referencess required for this action
    private final Shooter    shooter;
    private final VisionPID  visionPID;
    private final Elevator elevator;
    private final Spinner spinner;
    public double Distance, RPM;

    //-------------------------------------------------
    public ShootDefaultActions( Shooter s, VisionPID v, Elevator e, Spinner sp)
    {
        // Capture references to existing robot subsystems.  Define them as requirements.
        shooter     = s;
        visionPID   = v;
        elevator    = e;
        spinner     = sp;

        addRequirements(spinner);
        addRequirements(elevator);
        addRequirements(shooter);
        addRequirements(visionPID);
    }

    //-------------------------------------------------
    // Called just before this Command runs the first time
    public void initialize() 
    {
        
    }
    
    //-------------------------------------------------
    // Called repeatedly when this Command is scheduled to run
    public void execute() 
    {
        Constants.EndgameEnabled = false;
        shooter.updateBallInShooter();
        shooter.stop();
        visionPID.LEDoff();
        // spinner.motorOff();
        elevator.driveWinch(0.0);
        elevator.driveElevator(0.0);
    }
    
    //-------------------------------------------------
    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() 
    {
        return false;
    }
}