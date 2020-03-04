// FRC Team 3770 - BlitzCreek - OLLE 2020
// Drive Straight Command
// Command that controls DriveSystem subsystem and uses
// encoder measure to drive a given distance in inches.
// Note:  FORWARD drive is NEGATIVE

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.subsystems.DriveSystem;
import frc.robot.subsystems.GyroPID;

import frc.robot.Constants;


public class DriveSegment extends CommandBase
{
    // Robot object referencess required for this action
    private final DriveSystem   driveSystem;  
    private final GyroPID       gyroPID; 

    private Timer segmentDriveTimer;

    // For adjusting left/right motors for angle correction
    private double angleMotorAdjust, left, powerLevel, percentage, right, targetAngle, targetDistance;   

    // --------------------------------------------------------------------------
    // Constructor:  Capture time and motor level for straight drive
    public DriveSegment(DriveSystem d, GyroPID g, double power, double distance, double angle) 
    {
        // Capture references to existing robot subsystems.  Define them as requirements.
        driveSystem   = d;   
        gyroPID       = g;

        addRequirements(driveSystem, gyroPID);
        
        targetDistance    = distance;   
        powerLevel        = -power; // Negative = forward?
        targetAngle       = angle;
        segmentDriveTimer = new Timer();

        driveSystem.zeroEncoder();
        gyroPID.enable();
        
        // Start clock for this action
        segmentDriveTimer.reset(); 
        segmentDriveTimer.start();
        
        gyroPID.setSetpoint(targetAngle);
    }
    
    // --------------------------------------------------------------------------
    // Periodic action.  Initial angle set.  Motors ramp up, drive, ramp down.  
    // Distance managed by encoder PID and gyro maintains straight drive.  
    public void execute() 
    {
        double inches = driveSystem.getDistanceInches();
        SmartDashboard.putNumber("Gyro Angle", gyroPID.getMeasurement());

        if (Math.abs(targetDistance - inches) > Constants.DISTANCE_TOLERANCE) 
        {
            angleMotorAdjust = gyroPID.getOutput();     // Get gyro value

            // Adjust motor level to keep robot moving straight
            left             = powerLevel - angleMotorAdjust;
            right            = powerLevel + angleMotorAdjust;
            
            if (segmentDriveTimer.get() < Constants.RAMP_UP_TIME)               // Handle gradual ramp down
            {
                percentage = segmentDriveTimer.get() / Constants.RAMP_UP_TIME;
                left       = percentage * left;
                right      = percentage * right;
            } 
            else if (targetDistance - inches <= Constants.RAMP_DOWN_DIST)       // Handle gradual ramp down
            {
                percentage = (targetDistance - inches) / Constants.RAMP_DOWN_DIST;
                left       = percentage * left;
                right      = percentage * right;
            } 
        }
    }
    
    // --------------------------------------------------------------------------
    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() 
    {
        // Continue driving until encoder measure terminates action 
        if (driveSystem.getDistanceInches() < targetDistance - 5) 
        {
            driveSystem.drive(left, right);  // Set drive motors to target level
            return false;
        }
        else
        {
            driveSystem.drive(0.0, 0.0);
            gyroPID.disable();
            return true;
        }
    }
}