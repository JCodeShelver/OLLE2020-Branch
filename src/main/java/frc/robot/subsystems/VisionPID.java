// FRC Team 3770 - BlitzCreek - OLLE 20
// VisionPID subsystem
// Manaages PID control for vision

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionPID extends PIDSubsystem 
{ 
	NetworkTableInstance tableData;

	private double pidOutput, currentSetpoint;

	NetworkTable table;
	NetworkTableEntry tx, ta, ty;

	public VisionPID() 
	{

		super( new PIDController(Constants.VISION_PID_P, Constants.VISION_PID_I, Constants.VISION_PID_D));   
		
		tableData = NetworkTableInstance.getDefault();
        table = tableData.getTable("limelight");

		getController().setTolerance(Constants.VISION_X_PID_TOLERANCE);   // Degree tolerance for set point
		getController().setSetpoint(5);
	}

	// Primary action to get current vision x-coordinate measure.  Defined as abstract and there
	// mandated.  Automatically called periodically.
	@Override
	public double getMeasurement() 
	{
		tx = table.getEntry("tx");
		double x = tx.getDouble(0.0);
		return x;
	}

	// Consumes output of PID controller using current set point.  Automatically called
	// periodically.
	
	@Override
	public void useOutput(double output, double setpoint)
	{
		pidOutput        = output;
		currentSetpoint  = setpoint;
	}

	// Getter action to retrieve calculated PID output.
	public double getOutput()
	{
		return pidOutput;
	}

	// Getter action to retrieve current setpoint
	public double getSetpoint()
	{
		return currentSetpoint;
	}	

	public double getXValue()
	{
		return table.getEntry("tx").getDouble(0.0);
	}

	public double getYValue()
	{
		return table.getEntry("ty").getDouble(0.0);
	}

	public boolean getTargetInView()
	{
		ta = table.getEntry("ta");
		double a = ta.getDouble(0.0);
		if (a > 0){
			return true;
		}else{
			return false;
		}
	}

	// Green LEDs do turn on.  However, threshold and color changes for pipeline reset.
	public void LEDon()
	{
		table.getEntry("ledMode").setNumber(0);
	}

	public void LEDoff()
	{
		table.getEntry("ledMode").setNumber(1);
	}

	public void getVisionData()
	{
		ta = table.getEntry("ta");
		double a = ta.getDouble(0.0);

		ty = table.getEntry("ty");
		double y = ty.getDouble(0.0);

		tx = table.getEntry("tx");
		double x = tx.getDouble(0.0);

		SmartDashboard.putNumber("Vision X", x);
		SmartDashboard.putNumber("Vision Y", y);
		SmartDashboard.putNumber("Vision Area", a);
	}

}
