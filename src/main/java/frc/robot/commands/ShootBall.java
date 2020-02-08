// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to shoot ball
// Requires access to shooter system.  Performs one ball shot cycle.

package frc.robot.commands;

import frc.robot.subsystems.ShooterSystem;
import frc.robot.subsystems.VisionPID;
import edu.wpi.first.wpilibj2.command.CommandBase;



public class ShootBall extends CommandBase 
{
  private final ShooterSystem shooterSystem;   // Reference to shooter system object 
  private final VisionPID visionPID;
  public boolean RPMGood;
  public boolean XGood;


  // ----------------------------------------------------------------------------
  public ShootBall(ShooterSystem s, VisionPID v) 
  {
    shooterSystem = s;
    visionPID = v;
  }

  // ----------------------------------------------------------------------------
  // Initiate shooting by starting action timer
  @Override
  public void initialize() 
  { 
    visionPID.enable();
    RPMGood = false;
    XGood = false;

  }

  // ----------------------------------------------------------------------------
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if( Math.abs(visionPID.getOutput()) <= .05)
      XGood = true;
    else
      XGood = false;


    if(Math.abs(shooterSystem.getSetPoint()-shooterSystem.getRPM())<= 100)
      RPMGood = true;
    else
      RPMGood = false;



    if (RPMGood == true && XGood == true)
      shooterSystem.feedInBall();
    else
      shooterSystem.stopBallFeed();
  }

  // ----------------------------------------------------------------------------
  // Return true when timer reaches designated target time.
  @Override
  public boolean isFinished() 
  {
    return false;
  }
}
