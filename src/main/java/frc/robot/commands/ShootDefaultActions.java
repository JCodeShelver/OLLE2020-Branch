
package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VisionPID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShootDefaultActions extends CommandBase
{
    // Robot object referencess required for this action
    private final Shooter    shooter;
    private final VisionPID  visionPID;
    public double Distance, RPM;

    //-------------------------------------------------
    // Constructor:  Capture time and motor level for straight drive
    public ShootDefaultActions( Shooter s, VisionPID v)
    {
        // Capture references to existing robot subsystems.  Define them as requirements.
        shooter  = s;
        visionPID       = v;
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
        shooter.stop();
        visionPID.LEDoff();

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