// FRC Team 3770 - BlitzCreek - OLLE 2020
// Spinner subsystem
// Manage spinner motor and color sensor

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.can.*;  
import com.ctre.phoenix.motorcontrol.*;

public class Spinner extends SubsystemBase 
{ 
  private  I2C.Port i2cPort;
  private  ColorSensorV3 m_colorSensor;
  private  ColorMatch m_colorMatcher;

  private TalonSRX spinnerMotor;

  private DoubleSolenoid assemblyCylinder;
  private DoubleSolenoid wheelCylinder;

  private String recentColorSequence;

  private boolean systemUp = false, engaged = false;
  // -----------------------------------------------------
  // Constructor
  public Spinner() 
  {
    spinnerMotor = new TalonSRX(Constants.SPINER_MOTOR_CAN_ID); 

    assemblyCylinder   = new DoubleSolenoid(Constants.PCM0, Constants.SPINNER_ASSEMBLY_CYLINDER_OUTPORT,Constants.SPINNER_ASSEMBLY_CYLINDER_INPORT);
    wheelCylinder      = new DoubleSolenoid(Constants.PCM0, Constants.SPINNER_WHEEL_CYLINDER_OUTPORT,Constants.SPINNER_WHEEL_CYLINDER_INPORT);

    // Prepare color sensor
    i2cPort = I2C.Port.kOnboard;
    m_colorSensor = new ColorSensorV3(i2cPort);
    m_colorMatcher = new ColorMatch();

    // Make pre-defined color matching constants available
    m_colorMatcher.addColorMatch(Constants.COLOR_BLUE);
    m_colorMatcher.addColorMatch(Constants.COLOR_GREEN);
    m_colorMatcher.addColorMatch(Constants.COLOR_RED);
    m_colorMatcher.addColorMatch(Constants.COLOR_YELLOW); 
  }
  
  
  // -----------------------------------------------------
  // Take one sample from color sensor and return raw color code
  public void getSensorColor() 
  {
    Color detectedColor = m_colorSensor.getColor();
    SmartDashboard.putNumber("Red" , detectedColor.red); 
    SmartDashboard.putNumber("Green" , detectedColor.green); 
    SmartDashboard.putNumber("Blue" , detectedColor.blue); 
  }
  
  // -----------------------------------------------------
  // Take one sample from color sensor.  Map color values to
  // char codes and return.  Match constants tuned to colors
  public char getAdjustedSensorColor() 
  {
    Color detectedColor = m_colorSensor.getColor();

    char colorCode;
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    if (match.color == Constants.COLOR_BLUE)
      colorCode = 'B';
    else if (match.color == Constants.COLOR_RED)
      colorCode = 'R';
    else if (match.color == Constants.COLOR_GREEN)
      colorCode = 'G';
    else if (match.color == Constants.COLOR_YELLOW)
      colorCode = 'Y';
    else
      colorCode = ' ';

    return colorCode;
  }
  
  // -----------------------------------------------------
  // Manage spinner motor - basic on and off
  public void motorOn()
  {
    spinnerMotor.set(ControlMode.PercentOutput,Constants.SPINNER_MOTOR_LEVEL); 
  }

  public void motorOff()
  {
    spinnerMotor.set(ControlMode.PercentOutput,0.0); 
  }

  // -----------------------------------------------------
  // Manage wheel pneumatic unit
  public void wheelCylinderExtend() 
  {
    wheelCylinder.set(DoubleSolenoid.Value.kForward);
    engaged = false;
  }

  public void wheelCylinderRetract() 
  {
    wheelCylinder.set(DoubleSolenoid.Value.kReverse);
    engaged = true;
  }

  public boolean engaged()
  {
    return engaged;
  }

  // -----------------------------------------------------
  // Manage assembly pneumatic unit
  public void assemblyCylinderExtend() 
  {
    assemblyCylinder.set(DoubleSolenoid.Value.kForward);
    systemUp = true;
  }

  public void assemblyCylinderRetract() 
  {
    assemblyCylinder.set(DoubleSolenoid.Value.kReverse);
    systemUp = false;
  }

  public boolean systemUp()
  {
    return systemUp;
  }

  
  // -----------------------------------------------------  
  // Build initial color sampling string with all 'X' values
  // to designate no color yet found.
  public void initiateColorSampler()
  {
    recentColorSequence = new String("");
    for (int i = 0; i < Constants.COLOR_SAMPLE_NUMBER; i++)
    {
      recentColorSequence += 'X';
    }
  }
  
  // -----------------------------------------------------
  // Method builds a substring containing the most recent n colors
  // detected by color sensor.  Colors are stored in a string with
  // the newest color added to "Front" (index zero) and the least recent
  // falling off the end of the string.  Should be called periodically.
  public void sampleRecentColors()
  {
    char newestColor = getAdjustedSensorColor();                          // Get instantaneous color sampled
    recentColorSequence = newestColor + recentColorSequence;      // Append new color to front of string
    
    // If String exceeds desired length, let last char fall off
    if (recentColorSequence.length() > Constants.COLOR_SAMPLE_NUMBER)
    {
       recentColorSequence = recentColorSequence.substring(0,Constants.COLOR_SAMPLE_NUMBER);
    }    
  }
 
  // -----------------------------------------------------
  // Returns current color that has been detected repetitively for
  // number of required samples (Constant: COLOR_SAMPLE_NUMBER ).
  // If color found:  returns  'B', 'G', 'R', 'Y'
  // If code inconsistent or not conclusive:  returns 'X'
  public char getCurrentSampledColor()
  {  
    // Check for all chars matching in sample string.  Compare all to
    // the first character in string.  If all match, set flag to mark it so.
    boolean charsMatch = true;
    char frontCharColorCode = recentColorSequence.charAt(0);
    for (int i = 1; i < Constants.COLOR_SAMPLE_NUMBER; i++)
    {
       if (recentColorSequence.charAt(i) != frontCharColorCode)
          charsMatch = false;
    }
    
    // If all sample characters match identically and none are 'X',
    // set return value to color code.
    char returnValue = 'X';
    char frontCharColor = recentColorSequence.charAt(0);
    if (charsMatch && frontCharColor != 'X')
       returnValue = frontCharColor;    
    return returnValue;
  }
  
  // -----------------------------------------------------
  // Retrieve current color from field system
  // If field system string exists (length > 0):  'B', 'G', 'R', 'Y'
  // Otherwise:  return 'X'
  public char getGameDataColor()
  { 
    String gameData =  DriverStation.getInstance().getGameSpecificMessage().toUpperCase();
    if (gameData.length() > 0)
        return gameData.charAt(0);
    else
        return 'X';
  }
  
  // ----------------------------------------------------------------------------
  // Return true when current color matches target color
  public boolean isSensorOnTargetColor(char mode)
  {
    switch (mode) 
    {
      case 'R':
        if (getCurrentSampledColor() == getGameDataColor() && getCurrentSampledColor() != 'X')
          return true;
        else
          return false;
      default:
        if (getCurrentSampledColor() == mode && getCurrentSampledColor() != 'X')
          return true;
        else
          return false;
    }
  }
}