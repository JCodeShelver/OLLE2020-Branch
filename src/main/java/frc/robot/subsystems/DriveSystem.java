// FRC Team 3770 - BlitzCreek - OLLE 20
// Drive subsystem
// Manage motors for human driving and auton motion.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.*;  
import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveSystem extends SubsystemBase 
{
    // Declare/instantiate robot objects by calling constructors

    private TalonSRX encoderReading;
    private CANSparkMax leftMotor1, leftMotor2;
    private CANSparkMax rightMotor1, rightMotor2;
    double adjustedL, adjustedR, adjustedY, adjustedX;

    public DriveSystem() 
    {
        // Declare/instantiate robot objects by calling constructors
        leftMotor1  = new CANSparkMax(Constants.LEFT_MOTOR1_CAN_ID, MotorType.kBrushless);   
        leftMotor2  = new CANSparkMax(Constants.LEFT_MOTOR2_CAN_ID, MotorType.kBrushless);    
        rightMotor1 = new CANSparkMax(Constants.RIGHT_MOTOR1_CAN_ID, MotorType.kBrushless);
        rightMotor2 = new CANSparkMax(Constants.RIGHT_MOTOR2_CAN_ID, MotorType.kBrushless);

        //encoder reading motor
        encoderReading = new TalonSRX(Constants.MOVING_MOTOR_CAN_ID);


        // Reverse rotation (polarity) of left motor set
        rightMotor1.setInverted(false);
        rightMotor2.setInverted(false);
        leftMotor1.setInverted(true);
        leftMotor2.setInverted(true);
    }
    
    // ----------------------------------------------------------------------------
    // Manage left and right drive wheels
    // Reversed drive allows backwards driving from a forward reference.
    public void Quadraticdrive(double inputL, double inputR)
    {
        if(inputL<0)
            adjustedL = -inputL * inputL;
        else
            adjustedL = inputL*inputL;

        if(inputR <0)
            adjustedR = -inputR*inputR;
        else
            adjustedR = inputR*inputR;

            adjustedR /= 4;

        leftMotor1.set(adjustedL);
        leftMotor2.set(adjustedL);
        rightMotor1.set(adjustedR);
        rightMotor2.set(adjustedR); 
     } 

     public void ArcadeDrive(double rightY, double rightX)
     {
    if(rightY<0)
        adjustedY = -rightY * rightY;
    else
        adjustedY = rightY*rightY;        

    if(rightX<0)
        adjustedX = -rightX * rightX;
    else
        adjustedX = rightX*rightX;

        leftMotor1.set(adjustedY + adjustedX);
        leftMotor2.set(adjustedY + adjustedX);
        rightMotor1.set(adjustedY - adjustedX);
        rightMotor2.set(adjustedY - adjustedX);
     }

     public void drive(double inL, double inR)
     {
             
         leftMotor1.set(inL);
         leftMotor2.set(inL);
         rightMotor1.set(inR);
         rightMotor2.set(inR); 
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
