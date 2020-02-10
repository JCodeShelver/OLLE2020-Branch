// FRC Team 3770 - BlitzCreek - OLLE 20
// Drive subsystem
// Manage motors for human driving and auton motion.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.*;  
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class DriveSystem extends SubsystemBase 
{
    // Declare/instantiate robot objects by calling constructors
    private TalonSRX leftMotor1,  leftMotor2;
    private TalonSRX rightMotor1, rightMotor2;
    private Counter leftEncoder, rightEncoder;

    public DriveSystem() 
    {
        // Declare/instantiate robot objects by calling constructors
        leftMotor1  = new TalonSRX(Constants.LEFT_MOTOR1_CAN_ID);   
        leftMotor2  = new TalonSRX(Constants.LEFT_MOTOR2_CAN_ID);    
        rightMotor1 = new TalonSRX(Constants.RIGHT_MOTOR1_CAN_ID);
        rightMotor2 = new TalonSRX(Constants.RIGHT_MOTOR2_CAN_ID); 

        // Instantiate encoders as simple counters
        leftEncoder  = new Counter(Constants.LT_ENCODER_DIGITAL_PORT);
        rightEncoder = new Counter(Constants.RT_ENCODER_DIGITAL_PORT);

        // Reverse rotation (polarity) of left motor set
        leftMotor1.setInverted(true);
        leftMotor2.setInverted(true);
    }
    
    // ----------------------------------------------------------------------------
    // Manage left and right drive wheels
    // Reversed drive allows backwards driving from a forward reference.
    public void drive(double inputL, double inputR)
    {
        leftMotor1.set(ControlMode.PercentOutput,inputL);
        leftMotor2.set(ControlMode.PercentOutput,inputL);
        rightMotor1.set(ControlMode.PercentOutput,inputR);
        rightMotor2.set(ControlMode.PercentOutput,inputR); 

        
    SmartDashboard.putString("DB/String 0", "Left: "     + Integer.toString(leftEncoder.get()));    
    SmartDashboard.putString("DB/String 1", "Right: "    + Integer.toString(rightEncoder.get()));
       
     } 

    // ----------------------------------------------------------------------------
    // Reset encoders to zero
    public void zeroEncoder()
    {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    // ----------------------------------------------------------------------------
    // Return distance traveled in inches
    public double getDistanceInches()
    {
        int aveTicks = (leftEncoder.get() + rightEncoder.get()) / 2;
        return aveTicks * Constants.INCHES_PER_TICK;
    }
}
