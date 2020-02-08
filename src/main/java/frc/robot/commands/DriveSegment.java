// FRC Team 3770 - BlitzCreek - OLLE 20
// Example: DriveStraight
// Command that will control DriveSystem subsystem and use
// encoder measure to drive a given distance in inches.
// Note:  FORWARD drive is NEGATIVE

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.DriveSystem;
import frc.robot.subsystems.GyroPID;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveSegment extends CommandBase
{
    // Robot object referencess required for this action
    private final DriveSystem   driveSystem;  
    private final GyroPID       gyroPID; 

    // Class member variables
    private double targetDistance;
    private double powerLevel;
    private Timer segmentDriveTimer;
 
    private double angleMotorAdjust;  // For adjusting left/right motors for angle correction
    
    // Utility variables
    private double left, right, percentage, targetAngle;   

    //-------------------------------------------------
    // Constructor:  Capture time and motor level for straight drive
    public DriveSegment( DriveSystem d, GyroPID g, double power, double distance, double angle) 
    {
        // Capture references to existing robot subsystems.  Define them as requirements.
        driveSystem   = d;   
        gyroPID       = g;  
        addRequirements(driveSystem);
        addRequirements(gyroPID);
        
        targetDistance = distance;   
        powerLevel     = -power; // Negative = forward?
        targetAngle    = angle;

        segmentDriveTimer = new Timer();

        driveSystem.zeroEncoder();

        gyroPID.enable();
        
        // Start clock for this action
        segmentDriveTimer.reset(); 
        segmentDriveTimer.start();
        
        gyroPID.setSetpoint(targetAngle);
    }
    
    //-------------------------------------------------
    // Periodic action.  Initial angle set.  Motors ramp up, drive, ramp down.  
    // Distance managed by encoder PID and gyro maintains straight drive.  
    public void execute() 
    {
        double inches = driveSystem.getDistanceInches();
        SmartDashboard.putString("DB/String 2", "Gyro: "     + Double.toString(gyroPID.getMeasurement()));
        if (Math.abs(targetDistance - inches) > Constants.DISTANCE_TOLERANCE) 
        {
            angleMotorAdjust = gyroPID.getOutput();     // Get gyro value
            System.out.println(angleMotorAdjust);
            left  = powerLevel - angleMotorAdjust;           // Adjust motor level to keep robot moving straight
            right = powerLevel + angleMotorAdjust;
            
            if (segmentDriveTimer.get() < Constants.RAMP_UP_TIME)               // Handle gradual ramp down
            {
                percentage = segmentDriveTimer.get() / Constants.RAMP_UP_TIME;
                left = percentage * left;
                right = percentage * right;
            } 
            else if (targetDistance - inches <= Constants.RAMP_DOWN_DIST)       // Handle gradual ramp down
            {
                percentage = (targetDistance - inches) / Constants.RAMP_DOWN_DIST;
                left = percentage * left;
                right = percentage * right;
            } 
        }
    }
    
    //-------------------------------------------------
    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() 
    {
        // Continue driving until encoder measure terminates action 
        if ( driveSystem.getDistanceInches() < targetDistance-5) 
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

