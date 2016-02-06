package org.usfirst.frc.team4330.robot;


public class RobotMap {
	
	// TODO get the actual port numbers
	// TODO build the robot
	// SpeedControllers
	public static final int RIGHT_BACK_WHEEL = 4;
	public static final int LEFT_BACK_WHEEL = 1;
	public static final int RIGHT_FRONT_WHEEL = 2;
	public static final int LEFT_FRONT_WHEEL = 0;
	public static final int SCALAR_PORT = 6;
//	public static int MINI_RIGHT_WHEEL = 4;
//	public static int MINI_LEFT_WHEEL = 5;
	public static final double ARM_WHEEL_SPEED = 1;
	
	// Buttons
	public static final int REVERSE_DRIVE_BUTTON = 4;
	public static final int INTAKE_BUTTON = 5;
	public static final int REVERSE_INTAKE_BUTTON = 3;
	public static final int TREXARM_BACKWARDS_BUTTON = 2;
	public static final int SCALING_UPWARDS_BUTTON = 11;
	public static final int SCALING_DOWNWARDS_BUTTON = 12;
		
	// Relays
	public static final int TREXARM_PORT = 4; // confirmed 4
	public static final int SPIKE_PORT = 1; // confirmed
	public static final int INTAKE_PORT = 0; // confirmed
	
	// Joysticks
	public static final int JOYSTICK_ONE_LEFT = 1;
	public static final int JOYSTICK_TWO_RIGHT = 0;
	public static final int JOYSTICK_THREE = 2;
	
	// Encoders
	public static final int ENCODER_PORT_ONE = 0;
	public static final int ENCODER_PORT_TWO = 1;
}
