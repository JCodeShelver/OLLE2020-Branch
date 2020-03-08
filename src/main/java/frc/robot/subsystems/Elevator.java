// FRC Team 3770 - BlitzCreek - OLLE 2020
// Lifter Subsystem
// Manage robot lifter for end game.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

import frc.robot.Constants;

public class Elevator extends SubsystemBase 
{ 
  private final DoubleSolenoid bottomCylinders, top2Cylinders;
  private final TalonSRX elevatorDriveMotor, winchDriveMotor;
  private boolean isDeployed1 = false, isDeployed2 = false;

  // ----------------------------------------------------------------------------
  // Constructor
  public Elevator()
  {
    bottomCylinders    = new DoubleSolenoid(Constants.PCM1, Constants.ELEVATOR_BOTTOM_CYLINDERS_OUTPORT, Constants.ELEVATOR_BOTTOM_CYLINDERS_INPORT);
    top2Cylinders      = new DoubleSolenoid(Constants.PCM1, Constants.ELEVATOR_TOP2_CYLINDERS_OUTPORT, Constants.ELEVATOR_TOP2_CYLINDERS_INPORT);

    elevatorDriveMotor = new TalonSRX(Constants.ELEVATOR_DRIVE_MOTOR_CAN_ID);
    winchDriveMotor    = new TalonSRX(Constants.WINCH_MOTOR_CAN_ID);
  }

  // ----------------------------------------------------------------------------
  // Return the state of the pneumatics for stage 1 of the Elevator mechanism.
  public boolean BottomDeployed() 
  {
    return isDeployed1;
  }

  // ----------------------------------------------------------------------------
  // Bar Crawler mechanism motor control.
  public void driveElevator(final double motorLevel)
  {
    elevatorDriveMotor.set(ControlMode.PercentOutput, motorLevel);
  }

  // ----------------------------------------------------------------------------
  // Winch mechanism motor control.
  public void driveWinch(final double winchInput)
  {
    winchDriveMotor.set(ControlMode.PercentOutput, winchInput);
  }

  // ----------------------------------------------------------------------------
  // Extend Stage 1 of the Elevator mechanism.
  public void extendBottomCylinders()
  {
    bottomCylinders.set(DoubleSolenoid.Value.kForward);
  }
  
  // ----------------------------------------------------------------------------
  // Extend Stages 2 and 3 of the Elevator mechanism.
  public void extendTop2Cylinders()
  {
    top2Cylinders.set(DoubleSolenoid.Value.kForward);
    isDeployed2 = true;
  }

  // ----------------------------------------------------------------------------
  // Retract Stage 1 of the Elevator mechanism.
  public void retractBottomCylinders()
  {
    bottomCylinders.set(DoubleSolenoid.Value.kReverse);
    isDeployed1 = false;
  }  

  // ----------------------------------------------------------------------------
  // Retract Stages 2 and 3 of the Elevator mechanism.
  public void retractTop2Cylinders() 
  {
    top2Cylinders.set(DoubleSolenoid.Value.kReverse);
    isDeployed2 = false;
  }

  // ----------------------------------------------------------------------------
  // Stop Stage 1 of the Elevator mechanism.
  public void stopBottomCylinders()
  {
    bottomCylinders.set(DoubleSolenoid.Value.kOff);
  }
  // ----------------------------------------------------------------------------
  // Return the state of the pneumatics for stages 2 and 3 of the Elevator mechanism.
  public boolean TopDeployed() 
  {
    return isDeployed2;
  }
}