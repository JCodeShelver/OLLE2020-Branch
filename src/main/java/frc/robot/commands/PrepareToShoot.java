// FRC Team 3770 - BlitzCreek - OLLE 2020
// Prepare to Shoot command
// Prepares shooter motor for shooting.
// Motor speed set using vision feedback.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.subsystems.Shooter;
import frc.robot.Constants;
import frc.robot.subsystems.VisionPID;

import frc.robot.Constants;

public class PrepareToShoot extends CommandBase
{
  // Robot object referencess required for this action
  private final Shooter   shooterSystem;
  private final VisionPID visionPID;

  public XboxController controller;

  public boolean ballInPlace, RPMGood, XGood;

  public double Distance, RPM;

  // --------------------------------------------------------------------------
  // Constructor:  Capture time and motor level for straight drive
  public PrepareToShoot(Shooter s, VisionPID v)
  {
    // Capture references to existing robot subsystems.  Define them as requirements.
    shooterSystem   = s;
    visionPID       = v;
    
    addRequirements(shooterSystem);
  }

  // --------------------------------------------------------------------------
  // Initialization
  public void initialize() 
  {
    visionPID.enable();

    RPMGood    = false;
    XGood      = false;
    
    Distance   = 0;
    RPM        = 0;

    controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);
  }
  
  // --------------------------------------------------------------------------
  // 
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

    if (Math.abs(visionPID.getOutput()) <= .05)
      XGood = true;
    else
      XGood = false;

    if (Math.abs(shooterSystem.getSetPoint() - shooterSystem.getRPM()) <= 100)
      RPMGood = true;
    else
      RPMGood = false;
  
    if (controller.getBumper(Hand.kRight))
    {
      System.out.println("Firing");
      shooterSystem.lowerShootingPiston();
    }
    else
    {
      System.out.println("Lowering");
      shooterSystem.shootBall();
    }
  }
  
  // --------------------------------------------------------------------------
  // 
  public boolean isFinished() 
  {
    return false;
  }
  
  // --------------------------------------------------------------------------
  // Convert distance away to RPM
  private double distanceToRPMFormula(double d)
  {
    return -3700;
  }
  
  // --------------------------------------------------------------------------
  // Convert Limelight's Y to distance away
  private double yToDistanceFormula(double y)
  {
    //Bar in robo Room
    //return 128-5.96*(y)+0.172*(y*y);

    //Actual target on test frame
    return 90.2 - 1.33*y + .213*y*y;
  }

  // --------------------------------------------------------------------------
  // 
  protected void end()      
  { 
    shooterSystem.stop();
  }

  // --------------------------------------------------------------------------
  //
  protected void interrupted() 
  {
    shooterSystem.stop();
  }
}