package org.usfirst.frc.team4330.robot;


public class RobotMap {
	
	// TODO get the actual port numbers
	
	// SpeedControllers
	public static final int RIGHT_BACK_WHEEL = 3; // 4
	public static final int LEFT_BACK_WHEEL = 1;
	public static final int RIGHT_FRONT_WHEEL = 2;
	public static final int LEFT_FRONT_WHEEL = 0;
	public static final int SCALAR_PORT = 5;
	public static final double ARM_WHEEL_SPEED = 0.3;
	public static final double INTAKE_SPEED = 1;
	
	// Buttons
	public static final int REVERSE_DRIVE_BUTTON = 8;
	public static final int BALL_CONTROL_INTAKE_BUTTON = 6;
	public static final int BALL_CONTROL_SHOOT_BUTTON = 4;
	public static final int TREXARM_LOWER_BUTTON = 3;
	public static final int TREXARM_RAISE_BUTTON = 5;
	public static final int SCALING_UPWARDS_BUTTON = 11;
	public static final int SCALING_DOWNWARDS_BUTTON = 12;
		
	// Relays
	public static final int TREXARM_PORT = 4; // confirmed 4
	public static final int SPIKE_PORT = 1; // confirmed
	public static final int INTAKE_PORT = 6; // confirmed
	
	// Joysticks
	public static final int JOYSTICK_ONE_LEFT = 1;
	public static final int JOYSTICK_TWO_RIGHT = 0;
	public static final int JOYSTICK_THREE = 2;
	
	// Encoders
	public static final int ENCODER_PORT_ONE = 0;
	public static final int ENCODER_PORT_TWO = 1;
	public static final int ARM_ENCODER_PORT_ONE = 2;
	public static final int ARM_ENCODER_PORT_TWO = 3;
}
