package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.FrontIntake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutonIntakeDriver extends CommandBase 
{
    private final FrontIntake frontIntake;   
    private double input;

  // ----------------------------------------------------------------------------
  public AutonIntakeDriver(FrontIntake i, double in) 
  {
    frontIntake = i;
    input = in;
    addRequirements(frontIntake);
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
    
    if(frontIntake.isOut())
    {
      System.out.println(input);
      frontIntake.driveIntakeMotors(input);
    }
    else
      frontIntake.driveIntakeMotors(0.0);


  }

  // ----------------------------------------------------------------------------
  // 
  @Override
  public boolean isFinished() 
  {
    return false;
  }

}