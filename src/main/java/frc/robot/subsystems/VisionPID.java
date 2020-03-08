// FRC Team 3770 - BlitzCreek - OLLE 2020
// VisionPID Subsystem
// Manages the PID controller for Vision.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import frc.robot.Constants;


public class VisionPID extends PIDSubsystem 
{ 
	private NetworkTable table;
	private NetworkTableEntry tx, ty, ta;
	private NetworkTableInstance tableData;

	private double pidOutput, currentSetpoint;

	// --------------------------------------------------------------------------
	// Constructor
	public VisionPID() 
	{
		super(new PIDController(Constants.VISION_PID_P, Constants.VISION_PID_I, Constants.VISION_PID_D));   
		
		tableData = NetworkTableInstance.getDefault();
        table 	  = tableData.getTable("limelight");

		getController().setTolerance(Constants.VISION_X_PID_TOLERANCE);   // Degree tolerance for set point
		getController().setSetpoint(Constants.VISION_X_OFFSET);
	}

	// --------------------------------------------------------------------------
	// Toggles the Limelight's mode.
	public void cameraModeSwitch()
	{
		table.getEntry("camMode").setNumber(1 - (table.getEntry("camMode").getDouble(1)));
	}

	// --------------------------------------------------------------------------
	// Return the Limelight's X value.
	@Override
	public double getMeasurement() 
	{
		tx = table.getEntry("tx");
		double x = tx.getDouble(0.0);
		return x;
	}

	// --------------------------------------------------------------------------
	// Retrieve calculated PID output.
	public double getOutput()
	{
		return pidOutput;
	}

	// --------------------------------------------------------------------------
	// Retrieve current setpoint
	public double getSetpoint()
	{
		return currentSetpoint;
	}	

	// --------------------------------------------------------------------------
	// Return whether the target is in view or not.
	public boolean getTargetInView()
	{
		ta = table.getEntry("ta");
		double a = ta.getDouble(0.0);
		if (a > 0)
			return true;
		else
			return false;
	}

	// --------------------------------------------------------------------------
	// Return the Limelight's Vision variables.
	public void getVisionData()
	{
		tx = table.getEntry("tx");
		double x = tx.getDouble(0.0);

		ty = table.getEntry("ty");
		double y = ty.getDouble(0.0);
		
		ta = table.getEntry("ta");
		double a = ta.getDouble(0.0);

		SmartDashboard.putNumber("Vision X", x);
		SmartDashboard.putNumber("Vision Y", y);
		SmartDashboard.putNumber("Vision Area", a);
	}

	// --------------------------------------------------------------------------
	// Return the Limelight's Y value.
	public double getYValue()
	{
		return table.getEntry("ty").getDouble(-1.0);
	}
	
	// --------------------------------------------------------------------------
	// Turn the Limelight's LED's on.
	// Green LED's do turn on.  However, threshold and color changes for pipeline reset.
	public void LEDon()
	{
		table.getEntry("ledMode").setNumber(0);
	}

	// --------------------------------------------------------------------------
	// Turn the Limelight's LED's off.
	public void LEDoff()
	{
		table.getEntry("ledMode").setNumber(1);
	}

	// --------------------------------------------------------------------------
	// Consume output of PID controller using current set point.
	@Override
	public void useOutput(double output, double setpoint)
	{
		currentSetpoint  = setpoint;
		pidOutput        = output;
	}
}
