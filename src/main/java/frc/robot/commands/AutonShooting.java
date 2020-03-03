package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;
import frc.robot.subsystems.VisionPID;


public class AutonShooting extends CommandBase
{


    // Robot object referencess required for this action
    private final Shooter   shooterSystem;
    private final VisionPID visionPID;
    public double Distance, RPM;
    public int BallsShot, BallsToShoot;
    public boolean RPMGood;
    public boolean XGood, ballInPlace;

    //-------------------------------------------------
    // Constructor:  Capture time and motor level for straight drive
    public AutonShooting( Shooter s, VisionPID v, int BallsToShootIn)
    {
        // Capture references to existing robot subsystems.  Define them as requirements.
        shooterSystem   = s;
        visionPID       = v;
        BallsToShoot = BallsToShootIn;
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
        BallsShot = 0;
    }
    
    //-------------------------------------------------
    // Called repeatedly when this Command is scheduled to run
    public void execute() 
    {
        Distance = yToDistanceFormula(visionPID.getYValue());
        SmartDashboard.putNumber("Distance from Target", Distance);
        //RPM = distanceToRPMFormula(Distance);
        //shooterSystem.setSetPoint(RPM);
        shooterSystem.setSetPoint(3000);
        shooterSystem.spinToSetPoint();
        visionPID.LEDon();
        Constants.shooterSystemActive = true;
        shooterSystem.updateBallInShooter();

        if( Math.abs(visionPID.getOutput()) <= .05)
        XGood = true;
      else
        XGood = false;
  
      if(Math.abs(shooterSystem.getSetPoint()+shooterSystem.getRPM())<= 100)
        RPMGood = true;
      else
        RPMGood = false;

        System.out.println("XGood: " + XGood);
        System.out.println("RPMGood: " + RPMGood);

      //if (RPMGood == true && XGood == false && Constants.ballInShooter == true && controller.getBumper(Hand.kRight))


      //shooting booleans
      if(RPMGood == true && XGood == true && Constants.ballInShooter == true)
      {
        System.out.println("Firing");
        shooterSystem.shootBall();
      }
      else if(!shooterSystem.isShooterPistonDown())
      {
          BallsShot ++;
          shooterSystem.lowerShootingPiston();
      }
      else
      {
        System.out.println("Lowering");
        shooterSystem.lowerShootingPiston();
      }
      System.out.println("Balls Shot: " + BallsShot);

    }
    
    //-------------------------------------------------
    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() 
    {
        if(BallsShot >= BallsToShoot)
            return true;
        else
            return false;
    }

    private double yToDistanceFormula(double y)
    {
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