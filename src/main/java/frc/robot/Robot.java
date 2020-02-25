// FRC Team 3770 - BlitzCreek - OLLE 2020
// Robot Class
// Set up behavior and Cameras.
// Declares SmartDashboard variables.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot 
{
  private Command m_autonomousCommand;

  private RobotContainer robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() 
  {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    robotContainer = new RobotContainer();

    // Declare Smart Dashboard entrys on startup
      // Priority Variables
    SmartDashboard.putNumber("Gyro Angle", 0.0);
    SmartDashboard.putNumber("Shooter RPM", 0.0);
    SmartDashboard.putNumber("Distance from Target", 0.0);
    SmartDashboard.putBoolean("Ball In Shooter", false);
    SmartDashboard.putBoolean("End Game Enabled", false);
    SmartDashboard.putBoolean("Elevator Stage 1", false);
    SmartDashboard.putBoolean("Elevator Stages 2-3", false);
    SmartDashboard.putBoolean("Intake Pneumatic", false);
    SmartDashboard.putBoolean("Winch Inverted", false);
    
      // Lower Priority Variables
    SmartDashboard.putBoolean("Auton Stages", true);
    SmartDashboard.putBoolean("Assembly Pneumatic", false);
    SmartDashboard.putBoolean("Wheel Pneumatic", false);
    SmartDashboard.putNumber("Red", 0.0);
    SmartDashboard.putNumber("Green", 0.0);
    SmartDashboard.putNumber("Blue", 0.0);
    SmartDashboard.putNumber("Vision X", 0.0);
    SmartDashboard.putNumber("Vision Y", 0.0);
    SmartDashboard.putNumber("Vision Area", 0.0);


    // Initiate USB Cameras 1 and 2
    CameraServer.getInstance().startAutomaticCapture();
    CameraServer.getInstance().startAutomaticCapture();
  }

  @Override
  public void robotPeriodic() 
  {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() 
  {
    m_autonomousCommand = robotContainer.getAutonomousCommand();

    // Schedule the autonomous command (example)
    if (m_autonomousCommand != null) 
    {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() 
  {

  }

  @Override
  public void teleopInit() 
  {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) 
    {
      m_autonomousCommand.cancel();
    }
  }
}
