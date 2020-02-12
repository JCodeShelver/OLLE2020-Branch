// FRC Team 3770 - BlitzCreek - OLLE 20
// Command to 

package frc.robot.commands;

import frc.robot.subsystems.Lifter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DeployLifter extends CommandBase 
{
  private final Lifter lifter;   

  // ----------------------------------------------------------------------------
  public DeployLifter(Lifter l) 
  {
    lifter = l;
  }

  // ----------------------------------------------------------------------------
  // Initization
  @Override
  public void initialize() 
  { 
     
  }

  // ----------------------------------------------------------------------------
  //  
  @Override
  public void execute() 
  {
    lifter.extendBottomCylinders();
    lifter.extendeMiddleCylinder();
    lifter.extendTopCylinder();
  }

  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return true;
  }

}