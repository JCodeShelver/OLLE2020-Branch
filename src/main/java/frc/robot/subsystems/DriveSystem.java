// FRC Team 3770 - BlitzCreek - OLLE 20
// Drive subsystem
// Manage motors for human driving and auton motion.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.*;  
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveSystem extends SubsystemBase 
{
    // Declare/instantiate robot objects by calling constructors
    //private TalonSRX leftMotor1,  leftMotor2;
    //private TalonSRX rightMotor1, rightMotor2;
    private TalonSRX encoderReading;
    private CANSparkMax leftMotor1, leftMotor2;
    private CANSparkMax rightMotor1, rightMotor2;

    public DriveSystem() 
    {
        // Declare/instantiate robot objects by calling constructors
        leftMotor1  = new CANSparkMax(Constants.LEFT_MOTOR1_CAN_ID, MotorType.kBrushless);   
        leftMotor2  = new CANSparkMax(Constants.LEFT_MOTOR2_CAN_ID, MotorType.kBrushless);    
        rightMotor1 = new CANSparkMax(Constants.RIGHT_MOTOR1_CAN_ID, MotorType.kBrushless);
        rightMotor2 = new CANSparkMax(Constants.RIGHT_MOTOR2_CAN_ID, MotorType.kBrushless);
        encoderReading = new TalonSRX(Constants.LOADER_MOTOR_CAN_ID);

        // Instantiate encoders as simple counters
        //leftEncoder  = new Counter(Constants.LT_ENCODER_DIGITAL_PORT);
        //rightEncoder = new Counter(Constants.RT_ENCODER_DIGITAL_PORT);

        // Reverse rotation (polarity) of left motor set
        leftMotor1.setInverted(true);
        leftMotor2.setInverted(true);
    }
    
    // ----------------------------------------------------------------------------
    // Manage left and right drive wheels
    // Reversed drive allows backwards driving from a forward reference.
    public void drive(double inputL, double inputR)
    {
        leftMotor1.set(inputL);
        leftMotor2.set(inputL);
        rightMotor1.set(inputR);
        rightMotor2.set(inputR); 
     } 
     
    // ----------------------------------------------------------------------------
    // Reset encoders to zero
    public void zeroEncoder()
    {
        encoderReading.setSelectedSensorPosition(0);   
    }

    // ----------------------------------------------------------------------------
    // Return distance traveled in inches
    public double getDistanceInches()
    {
        return encoderReading.getSelectedSensorPosition()/Constants.INCHES_PER_TICK;
    }
}
