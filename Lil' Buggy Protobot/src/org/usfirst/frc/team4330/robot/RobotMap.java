package org.usfirst.frc.team4330.robot;

public class RobotMap {

	// SpeedControllers
	public static final int RIGHT_BACK_WHEEL = 2;
	public static final int LEFT_BACK_WHEEL = 5;
	public static final int RIGHT_FRONT_WHEEL = 6;
	public static final int LEFT_FRONT_WHEEL = 3;
//	public static final int SCALAR_PORT = 4; // wench
	public static final int TREXARM_PORT_ONE = 7;
	public static final int TREXARM_PORT_TWO = 8;	
	public static final int INTAKE_PORT = 4;
	public static final int GYRO_PORT = 0;

	// Relays
	public static final int SPIKE_PORT = 3; // kicker

	// Speeds
	public static final double SCALING_MAXIMUM = .75;
	public static final double ARM_WHEEL_SPEED = .2;
	public static final double INTAKE_SPEED = .5;

	// Buttons
	public static final int REVERSE_DRIVE_BUTTON = 2;
	public static final int BALL_CONTROL_INTAKE_BUTTON = 6;
	public static final int BALL_CONTROL_SHOOT_BUTTON = 4;
	public static final int TREXARM_LOWER_BUTTON = 3;
	public static final int TREXARM_RAISE_BUTTON = 5;
//	public static final int SCALING_UPWARDS_BUTTON = 6;
//	public static final int SCALING_DOWNWARDS_BUTTON = 4;
	public static final int TREXARM_POWER_BUTTON = 2;

	// Joysticks
	public static final int JOYSTICK_ONE = 0;
	public static final int JOYSTICK_TWO = 1;
	public static final int JOYSTICK_THREE = 2;

	// Encoders
	public static final int ENCODER_PORT_ONE = 2;
	public static final int ENCODER_PORT_TWO = 3;
	public static final int ARM_ENCODER_PORT_ONE = 1; // confirmed goes forward
	public static final int ARM_ENCODER_PORT_TWO = 0;
	
	/*// Pneumatics
	public static final int COMPRESSOR = 0;
	public static final int KICKER_SOL = 2;*/
	
}
