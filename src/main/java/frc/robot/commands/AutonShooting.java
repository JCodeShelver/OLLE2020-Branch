// FRC Team 3770 - BlitzCreek - OLLE 2020
// Autonomous Shooting Command
// Manages the Shooter mechanism during the
// Autonomous period.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VisionPID;

import frc.robot.Constants;

public class AutonShooting extends CommandBase
{
  // Robot object referencess required for this action
  private final Shooter   shooterSystem;
  private final VisionPID visionPID;

  public boolean RPMGood, XGood, ballInPlace;
  public double Distance, RPM;
  public int BallsShot, BallsToShoot;

  // ----------------------------------------------------------------------------
  // Constructor
  public AutonShooting(Shooter s, VisionPID v, int BallsToShootIn)
  {
    // Capture references to existing robot subsystems.  Define them as requirements.
    shooterSystem   = s;
    visionPID       = v;
    BallsToShoot    = BallsToShootIn;
    
    addRequirements(shooterSystem);
  }

  // ----------------------------------------------------------------------------
  // Initialization
  public void initialize() 
  {
    visionPID.enable();
    Distance  = 0;
    RPM       = 0;
    BallsShot = 0;
    RPMGood   = false;
    XGood     = false;
  }
  
  // ----------------------------------------------------------------------------
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

    if (Math.abs(visionPID.getOutput()) <= 0.05)
      XGood = true;
    else
      XGood = false;

    if (Math.abs(shooterSystem.getSetPoint() - shooterSystem.getRPM()) <= 100)
      RPMGood = true;
    else
      RPMGood = false;

    // Shooting booleans
    if (RPMGood && XGood && Constants.ballInShooter)
    {
      System.out.println("Firing");
      shooterSystem.shootBall();
    }
    else if (!shooterSystem.isShooterPistonDown() && !Constants.ballInShooter)
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
  
  // ----------------------------------------------------------------------------
  // Make this return true when this Command no longer needs to run execute()
  public boolean isFinished() 
  {
    return (BallsShot >= BallsToShoot);
  }

  // ----------------------------------------------------------------------------
  // Convert distance away to RPM
  private double distanceToRPMFormula(double d)
  {
    return -3700;
  }
  
  // ----------------------------------------------------------------------------
  // Convert Limelight's Y value to distance away
  private double yToDistanceFormula(double y)
  {
    //Actual target on test frame
    return 90.2 - 1.33 * y + 0.213 * y * y;
  }

  // ----------------------------------------------------------------------------
  //
  protected void end()      
  { 
    shooterSystem.stop();
  }

  // ----------------------------------------------------------------------------
  //
  protected void interrupted() 
  {
    shooterSystem.stop();
  }
}