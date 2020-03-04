// FRC Team 3770 - BlitzCreek - OLLE 2020
// GyroPID Subsystem
// Manages gyroscope mechanism and PID 
// control with gyroscope.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.SPI;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;

public class GyroPID extends PIDSubsystem 
{ 
	private AHRS gyro;

	private double currentSetpoint, pidOutput;

	// --------------------------------------------------------------------------
	// Constructor
	public GyroPID() 
	{
		super(new PIDController(Constants.GYRO_PID_P, Constants.GYRO_PID_I, Constants.GYRO_PID_D));   

        // Initialize gyroscope
        try {
			gyro = new AHRS(SPI.Port.kMXP);
			resetGyro();
		} catch (RuntimeException ex) 
		{
            System.out.println("Error instantiating navX MXP:  " + ex.getMessage());
        }

		getController().setTolerance(Constants.GYRO_PID_TOLERANCE);   // Degree tolerance for set point
	}

	// --------------------------------------------------------------------------
	// Returns Yaw measurement of Gyroscope in degrees.
	@Override
	public double getMeasurement() 
	{
		return gyro.getAngle(); 
	}
	
	// --------------------------------------------------------------------------
	// Retrieves the calculated PID output.
	public double getOutput()
	{
		return pidOutput;
	}
	
	// --------------------------------------------------------------------------
	// Retrieves the current setpoint.
	public double getSetpoint()
	{
		return currentSetpoint;
	}		
	
	// --------------------------------------------------------------------------
	// Sets the Gyroscope's Yaw reading to 0 degrees.
	public void resetGyro()
	{	
		gyro.reset();
		gyro.zeroYaw();
	}
	
	// --------------------------------------------------------------------------
	// Adjusts the Proportion value of the PID controller.
	public void setPvalue(double newP)
	{
		getController().setP(newP);
	}

	// --------------------------------------------------------------------------
	// Consumes output of PID controller using current set point.
	@Override
	public void useOutput(double output, double setpoint)
	{
		currentSetpoint  = setpoint;
		pidOutput        = output;
	}
}	
