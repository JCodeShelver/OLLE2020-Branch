// FRC Team 3770 - BlitzCreek - OLLE 20
// Robot constant definition repository.

package frc.robot;

import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorMatch;

public final class Constants 
{
    // Declare constant values for ports
    public static  final int LEFT_STICK_USB_PORT       = 0;
    public static  final int RIGHT_STICK_USB_PORT      = 1;
    
    public static  final int RIGHT_MOTOR1_CAN_ID       = 4;
    public static  final int RIGHT_MOTOR2_CAN_ID       = 1;
    public static  final int LEFT_MOTOR1_CAN_ID        = 2;
    public static  final int LEFT_MOTOR2_CAN_ID        = 3;

    public static final int SHOOTER_MOTOR_CAN_ID       = 12;
    public static final int FEED_MOTOR_CAN_ID          = 20;

    public static  final int UTILITY_MOTOR_PORT        = 0;
    
    public static  final int POT_ANALOG_PORT           = 0;
    public static  final int SONAR_ANALOG_PORT         = 1;

    public static  final int PNEUMATICS_IN_PORT        = 7;
    public static  final int PNEUMATICS_OUT_PORT       = 4;
    
    public static  final int HEAVY_SWITCH_DIG_PORT     = 0;
    public static  final int LIGHT_SWITCH_DIG_PORT     = 1;

    public static  final int LT_ENCODER_DIGITAL_PORT   = 2;
    public static  final int RT_ENCODER_DIGITAL_PORT   = 4;

    public static  final int LIGHT_RELAY_PORT          = 0;

    public static  final Color COLOR_BLUE   = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static  final Color COLOR_GREEN  = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static  final Color COLOR_RED    = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static  final Color COLOR_YELLOW = ColorMatch.makeColor(0.361, 0.524, 0.113);

    public static  final double GYRO_PID_TOLERANCE     = 2.0;
    public static  final double GYRO_PID_P             = 0.01;
    public static  final double GYRO_PID_I             = 0.0;
    public static  final double GYRO_PID_D             = 0.0;

    public static  final double TURN_TIME_LIMIT        = 5.0;  // Stop turn after this time - as backup
    public static  final double ANGLE_TOLERANCE        = 3.0;  // Stop turn if within this number of degrees

    public static  final double INCHES_PER_TICK        = 0.052019;   // Conversion for drive encoder
    public static  final double DISTANCE_TOLERANCE     = 3.0;    // Absolute friving distance tolerance 
    public static  final double RAMP_UP_TIME           = 1.0;    // Time to ramp up speed in auton
    public static  final double RAMP_DOWN_DIST         = 24.0;   // Distance (inches) to target to decelerate

    public static  final double VISION_X_PID_TOLERANCE = 3.0;    // Absolute vision distance angle range
    public static  final double VISION_PID_P           = 0.06;
    public static  final double VISION_PID_I           = 0.03;
    public static  final double VISION_PID_D           = 0.0025;

    //PID_P = .0025, PID_I = 0.01, PID_D = 0.000175;
    public static final double SHOOTER_PID_P               = 0.003;
    public static final double SHOOTER_PID_I               = 0.01;
    public static final double SHOOTER_PID_D               = 0.000175;
    public static final double SHOOTER_TICKS_PER_RPM       = 6.837;

    public static final int COLOR_SAMPLE_NUMBER            = 5;   // Number samples taken for color sampling
    
    public static  final int SPINER_MOTOR_CAN_ID           = 5;
    public static  final double SPINNER_MOTOR_LEVEL        = 0.50; 

    public static final int LIFTER_CYLINDER1_INPORT        = 0;
    public static final int LIFTER_CYLINDER1_OUTPORT       = 1;
    public static final int LIFTER_CYLINDER2_INPORT        = 7;
    public static final int LIFTER_CYLINDER2_OUTPORT       = 8;

    public static final int INTAKE_SIDE_MOTOR_CAN_ID       = 99;
    public static final int INTAKE_FRONTBACK_MOTOR_CAN_ID  = 98;

    public static final int INTAKE_CYLINDER_INPORT         = 97;
    public static final int INTAKE_CYLINDER_OUTPORT        = 96;

    public static final int SPINNER_BIG_CYLINDER_INPORT     = 3;
    public static final int SPINNER_BIG_CYLINDER__OUTPORT   = 4;
    public static final int SPINNER_SMALL_CYLINDER_INPORT   = 5;
    public static final int SPINNER_SMALL_CYLINDER_OUTPORT  = 6;

    public static final int LOADER_TOP_MOTOR_CAN_ID         = 44;
    public static final int LOADER_BOTTOM_MOTOR_CAN_ID      = 55;

    public static final int LOADER_SWITCH_1_DIGITAL_PORT    = 1;
    public static final int LOADER_SWITCH_2_DIGITAL_PORT    = 2;
    public static final int LOADER_SWITCH_3_DIGITAL_PORT    = 3;
    public static final int LOADER_SWITCH_4_DIGITAL_PORT    = 4;

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    
    public static boolean shooterSystemActive;

}
