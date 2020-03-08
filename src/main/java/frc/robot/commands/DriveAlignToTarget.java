// FRC Team 3770 - BlitzCreek - OLLE 2020
// Drive Align-to-Target Command
// Command that can control the DriveSystem 
// subsystem and use encoder measure to 
// pivot turn to a desired angle measure.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Joystick;

import frc.robot.subsystems.DriveSystem;
import frc.robot.subsystems.VisionPID;

import frc.robot.Constants;

public class DriveAlignToTarget extends CommandBase
{
    // Robot object referencess required for this action
    private final DriveSystem   driveSystem;  
    private final VisionPID     visionPID;

    private final Joystick leftStick   = new Joystick(Constants.LEFT_STICK_USB_PORT);
    private final Joystick rightStick  = new Joystick(Constants.RIGHT_STICK_USB_PORT);

    private boolean doneTuring, doneTurning;
    
    // For adjusting left/right motors for angle correction
    private double angleRotateMotorAdjust, idleTurnSpeed;
    
    // --------------------------------------------------------------------------
    // Constructor:  Capture time and motor level for straight drive
    public DriveAlignToTarget(DriveSystem d, VisionPID v) 
    {
        // Capture references to existing robot subsystems.  Define them as requirements.
        driveSystem   = d;   
        visionPID     = v;  

        addRequirements(driveSystem);
    }

    // --------------------------------------------------------------------------
    // Initialization
    public void initialize() 
    {
        doneTurning   = false;
        idleTurnSpeed = 0.4;
        
        visionPID.enable();
    }
    
    // --------------------------------------------------------------------------
    // During periodic action, robot controls drive system and
    // Spins until target acquired.
    public void execute() 
    {
        visionPID.LEDon();
        double xVisionTarget = visionPID.getMeasurement();
        System.out.println("XVISIONTARGET: " + xVisionTarget);
        
        {
            angleRotateMotorAdjust = visionPID.getOutput();
            System.out.println("PID Output: " + angleRotateMotorAdjust);
            
            // Adjust left/right motor sets to PID output.  Rotate
            // as needed toward target angle
            double left  = (+angleRotateMotorAdjust * 0.5) + rightStick.getY();
            double right = (-angleRotateMotorAdjust * 0.5) + rightStick.getY();

            driveSystem.drive(left, right);
        }
    }

    // --------------------------------------------------------------------------
    //
    public void interupted()
    {

    }

    // --------------------------------------------------------------------------
    //
    @Override
    public void end(boolean interrupted)
    {
        driveSystem.drive(0.0, 0.0);
        visionPID.LEDoff();
    }
    
    // --------------------------------------------------------------------------
    // Routine stops when target in view and within range.
    // Vision system returns positive x-value when in view.
    // Then, stop when absolute error within tolerance.
    public boolean isFinished() 
    {
        return false;
    }
}
