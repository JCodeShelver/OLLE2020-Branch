// FRC Team 3770 - BlitzCreek - OLLE 2020
// Spinner Subsystem
// Controlls the spinner mechanism.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;  
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import frc.robot.Constants;

public class Spinner extends SubsystemBase 
{ 
  private final ColorMatch m_colorMatcher;
  private final ColorSensorV3 m_colorSensor;
  private final DoubleSolenoid assemblyCylinder, wheelCylinder;
  private final I2C.Port i2cPort;
  private final TalonSRX spinnerMotor;
  
  private boolean engaged = false, systemUp = false;
  private String recentColorSequence;

  // ----------------------------------------------------------------------------
  // Constructor
  public Spinner() 
  {
    assemblyCylinder   = new DoubleSolenoid(Constants.PCM0, Constants.SPINNER_ASSEMBLY_CYLINDER_OUTPORT,Constants.SPINNER_ASSEMBLY_CYLINDER_INPORT);
    wheelCylinder      = new DoubleSolenoid(Constants.PCM0, Constants.SPINNER_WHEEL_CYLINDER_OUTPORT,Constants.SPINNER_WHEEL_CYLINDER_INPORT);
    
    spinnerMotor       = new TalonSRX(Constants.SPINNER_MOTOR_CAN_ID); 

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
  
  // ----------------------------------------------------------------------------
  // Extend the assembly pneumatic of the Spinner mechanism.
  public void assemblyCylinderExtend() 
  {
    assemblyCylinder.set(DoubleSolenoid.Value.kForward);
    systemUp = true;
  }
  
  // ----------------------------------------------------------------------------
  // Retract the assembly pneumatic of the Spinner mechanism.
  public void assemblyCylinderRetract() 
  {
    assemblyCylinder.set(DoubleSolenoid.Value.kReverse);
    systemUp = false;
  }
  
  // ----------------------------------------------------------------------------
  // Return the state of the wheel pneumatic of the Spinner mechanism.
  public boolean engaged()
  {
    return engaged;
  }
  
  // ----------------------------------------------------------------------------
  // Return the character associated with the color nearest to the sensor's reading.
  public char getAdjustedSensorColor() 
  {
    char colorCode;
    Color detectedColor = m_colorSensor.getColor();
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
  
  // ----------------------------------------------------------------------------
  // Return the current color that has been detected repetitively for the number 
  // of required samples. If a color is found: return  'B', 'G', 'R', 'Y',
  // otherwise return 'X'.
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
  
  // ----------------------------------------------------------------------------
  // Retrieve the target color from FMS. If the FMS character exists (length > 0),
  // return it, otherwise return 'X'
  public char getGameDataColor()
  { 
    String gameData =  DriverStation.getInstance().getGameSpecificMessage().toUpperCase();
    if (gameData.length() > 0)
      return gameData.charAt(0);
    else
      return 'X';
  }
  
  // ----------------------------------------------------------------------------
  // Update the Smart Dashboard's Red, Green, and Blue variables.
  public void getSensorColorRGB() 
  {
    Color detectedColor = m_colorSensor.getColor();
    SmartDashboard.putNumber("Red" ,   detectedColor.red); 
    SmartDashboard.putNumber("Green" , detectedColor.green); 
    SmartDashboard.putNumber("Blue" ,  detectedColor.blue); 
  }
  
  // ----------------------------------------------------------------------------
  // Take the FMS-dictated color and target the color 90 degrees, or two places
  // from, it.
  //     Robot
  //       |
  //       R
  //   G   |   Y
  //  B----+----B <--- Field Scanner.
  //   Y   |   G
  //       R
  public char getTargetColor()
  {
    char FMSColor = getGameDataColor();
    switch (FMSColor)
    {
      case 'B':
        return 'R';
      case 'G':
        return 'Y';
      case 'R':
        return 'B';
      case 'Y':
        return 'G';
      default:
        return 'X';
    }
  }
  
  // ----------------------------------------------------------------------------
  // Build the initial color sampling string with all 'X' values to designate that
  // no color has been yet found.
  public void initiateColorSampler()
  {
    recentColorSequence = new String("");
    for (int i = 0; i < Constants.COLOR_SAMPLE_NUMBER; i++)
    {
      recentColorSequence += 'X';
    }
  }

  // ----------------------------------------------------------------------------
  // Return true when current color matches target color
  public boolean isSensorOnTargetColor(String mode)
  {
    switch (mode) 
    {
      case "RtnCtrl":
        if (getCurrentSampledColor() == 'B')
          return true;
        else
          return false;
      case "PosCtrl":
        if (getCurrentSampledColor() == getTargetColor() && getCurrentSampledColor() != 'X')
          return true;
        else
          return false;
      default:
        if (getCurrentSampledColor() == mode.toUpperCase().charAt(0) && getCurrentSampledColor() != 'X')
          return true;
        else
          return false;
    }
  }

  // ----------------------------------------------------------------------------
  // Turn the Spinner mechanism motor off.
  public void motorOff()
  {
    spinnerMotor.set(ControlMode.PercentOutput, 0.0); 
  }
  
  // ----------------------------------------------------------------------------
  // Turn the Spinner mechanism motor on.
  public void motorOn()
  {
    spinnerMotor.set(ControlMode.PercentOutput, Constants.SPINNER_MOTOR_LEVEL); 
  }

  // ----------------------------------------------------------------------------
  // Build a substring containing the most recent n colors that are detected by 
  // the color sensor.  Colors are stored in a string with the newest color added to 
  // "Front" (index zero) and the least recent falling off the end of the string.
  public void sampleRecentColors()
  {
    char newestColor = getAdjustedSensorColor();                   // Get instantaneous color sampled
    recentColorSequence = newestColor + recentColorSequence;      // Append new color to front of string
    
    // If String exceeds desired length, let last char fall off
    if (recentColorSequence.length() > Constants.COLOR_SAMPLE_NUMBER)
      recentColorSequence = recentColorSequence.substring(0, Constants.COLOR_SAMPLE_NUMBER);
  }

  // ----------------------------------------------------------------------------
  // Return the state of the assembly pneumatic of the Spinner mechanism.
  public boolean systemUp()
  {
    return systemUp;
  }

  // ----------------------------------------------------------------------------
  // Extend the wheel pneumatic of the Spinner mechanism.
  public void wheelCylinderExtend() 
  {
    wheelCylinder.set(DoubleSolenoid.Value.kForward);
    engaged = false;
  }
  
  // ----------------------------------------------------------------------------
  // Retract the wheel pneumatic of the Spinner mechanism.
  public void wheelCylinderRetract() 
  {
    wheelCylinder.set(DoubleSolenoid.Value.kReverse);
    engaged = true;
  }
}