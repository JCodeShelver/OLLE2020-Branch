// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to shoot ball
// Requires access to shooter system.  Performs one ball shot cycle.

package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VisionPID;
import frc.robot.Constants;
import frc.robot.subsystems.Loader;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootBall extends CommandBase 
{
  private final Shooter shooter;   // Reference to shooter system object 
  private final VisionPID visionPID;
  private final Loader loader;
  public boolean RPMGood;
  public boolean XGood, ballInPlace;
  public XboxController controller;


  // ----------------------------------------------------------------------------
  public ShootBall(Shooter s, VisionPID v, Loader l) 
  {
    shooter = s;
    visionPID = v;
    loader = l;
  }

  // ----------------------------------------------------------------------------
  // Initiate shooting
  @Override
  public void initialize() 
  { 
    visionPID.enable();
    RPMGood = false;
    XGood = false;
    controller = new XboxController(Constants.XBOX_CONTROLLER_USB_PORT);
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

    if(Math.abs(shooter.getSetPoint()-shooter.getRPM())<= 100)
      RPMGood = true;
    else
      RPMGood = false;

    if (RPMGood == true && XGood == true && Constants.BallInShooter == true && controller.getBumper(Hand.kLeft))
      shooter.shootBall();
    else
      shooter.lowerShootingPiston();
  }

  // ----------------------------------------------------------------------------
  @Override
  public boolean isFinished() 
  {
    return false;
  }
}
