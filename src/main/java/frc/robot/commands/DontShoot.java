
package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSystem;
import frc.robot.subsystems.VisionPID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DontShoot extends CommandBase
{


    // Robot object referencess required for this action
    private final ShooterSystem  shooterSystem;
    private final VisionPID       visionPID;
    public double Distance, RPM;

    //-------------------------------------------------
    // Constructor:  Capture time and motor level for straight drive
    public DontShoot( ShooterSystem s, VisionPID v)
    {
        // Capture references to existing robot subsystems.  Define them as requirements.
        shooterSystem   = s;
        visionPID       = v;
        addRequirements(shooterSystem);
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
        shooterSystem.stop();
 //       visionPID.LEDoff();

 SmartDashboard.putString("DB/String 3", "x: "     + Double.toString(visionPID.getXValue()));
 SmartDashboard.putString("DB/String 4", "y: "    + Double.toString(visionPID.getYValue()));


    }
    
    //-------------------------------------------------
    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() 
    {
        return false;
    }
}