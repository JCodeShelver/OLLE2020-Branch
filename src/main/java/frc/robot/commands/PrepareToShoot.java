// FRC Team 3770 - BlitzCreek - OLLE 20
// PrepareToShoot command
// Prepares shooter motor for shooting.  Motor speed set using vision feedback.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSystem;
import frc.robot.subsystems.VisionPID;


public class PrepareToShoot extends CommandBase
{


    // Robot object referencess required for this action
    private final ShooterSystem  shooterSystem;
    private final VisionPID       visionPID;
    public double Distance, RPM;

    //-------------------------------------------------
    // Constructor:  Capture time and motor level for straight drive
    public PrepareToShoot( ShooterSystem s, VisionPID v)
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
        visionPID.enable();
        Distance = 0;
        RPM = 0;
    }
    
    //-------------------------------------------------
    // Called repeatedly when this Command is scheduled to run
    public void execute() 
    {
        Distance = yToDistanceFormula(visionPID.getYValue());
        System.out.println("visionPID Y: " + visionPID.getYValue());
        System.out.println("Distnace: " + Distance); 
        RPM = distanceToRPMFormula(Distance);
        shooterSystem.setSetPoint(RPM);
        shooterSystem.spinToSetPoint();
        visionPID.LEDon();
    }
    
    //-------------------------------------------------
    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() 
    {
        return false;
    }

    private double yToDistanceFormula(double y)
    {
        //Bar in robo Room
        //return 128-5.96*(y)+0.172*(y*y);

        //Actual target on test frame
        return 90.2 - 1.33*y + .213*y*y;
    }
    private double distanceToRPMFormula(double d)
    {
        return (d/12)*397;
    }

    //-------------------------------------------------
    protected void end()      
    { 
        shooterSystem.stop();
    }
    protected void interrupted() 
    {
        shooterSystem.stop();
     }
}