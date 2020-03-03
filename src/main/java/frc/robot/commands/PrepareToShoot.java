// FRC Team 3770 - BlitzCreek - OLLE 20
// PrepareToShoot command
// Prepares shooter motor for shooting.  Motor speed set using vision feedback.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;
import frc.robot.subsystems.VisionPID;


public class PrepareToShoot extends CommandBase
{


    // Robot object referencess required for this action
    private final Shooter   shooterSystem;
    private final VisionPID visionPID;
    public double Distance, RPM;
    public boolean RPMGood;
    public boolean XGood, ballInPlace;
    public XboxController controller;

    //-------------------------------------------------
    // Constructor:  Capture time and motor level for straight drive
    public PrepareToShoot( Shooter s, VisionPID v)
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
        RPMGood = false;
        XGood = false;
        controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);
    }
    
    //-------------------------------------------------
    // Called repeatedly when this Command is scheduled to run
    public void execute() 
    {
        Distance = yToDistanceFormula(visionPID.getYValue());
        SmartDashboard.putNumber("Distance from Target", Distance);
        RPM = distanceToRPMFormula(Distance);
        shooterSystem.setSetPoint(RPM);
        shooterSystem.spinToSetPoint();
        visionPID.LEDon();
        Constants.shooterSystemActive = true;
        shooterSystem.updateBallInShooter();

        if( Math.abs(visionPID.getOutput()) <= .05)
        XGood = true;
      else
        XGood = false;
  
      if(Math.abs(shooterSystem.getSetPoint()-shooterSystem.getRPM())<= 100)
        RPMGood = true;
      else
        RPMGood = false;
  
      //if (RPMGood == true && XGood == false && Constants.ballInShooter == true && controller.getBumper(Hand.kRight))
  
      if(controller.getBumper(Hand.kRight))
      {
        System.out.println("Firing");
        shooterSystem.shootBall();
      }
      else
      {
        System.out.println("Lowering");
        shooterSystem.lowerShootingPiston();
      }
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
        return -3700;
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