package org.usfirst.frc.team4330.robot;

// ctrl + shift + o to automatically get imports

import org.usfirst.frc.team4330.robot.canbus.LeddarDistanceSensor;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;

/**
 * 2016 final code!!
 * 
 * Author: Bananananananananananananananananananananamanda & sorta Patrick
 * pandanomium
 * 
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	DriveTrain dT;
	SmartArm arm;
	BallControl ballControl;
	Joystick left, right, shooter, xboxdrive;
	LeddarDistanceSensor leddar;
	AnonymousJoystick ajoy;
	Scaling scaling;

	/*
     * 
     */
	public Robot() {
		dT = new DriveTrain();
		left = new Joystick(RobotMap.JOYSTICK_ONE);
		right = new Joystick(RobotMap.JOYSTICK_THREE);
		shooter = new Joystick(RobotMap.JOYSTICK_TWO);
		leddar = new LeddarDistanceSensor();
		arm = new SmartArm();
		ballControl = new BallControl(new Victor(RobotMap.INTAKE_PORT),
				new Relay(RobotMap.SPIKE_PORT));
		scaling = new Scaling(new Victor(RobotMap.SCALAR_PORT), shooter.getRawAxis(3));
	}

	public void autonomousInit() {
		System.out.println("*********************************");
		System.out.println("*********************************");
		System.out.println("******** May the odds be ********");
		System.out.println("****** ever in your favor! ******");
		System.out.println("*********************************");
		System.out.println("*********************************");

		leddar.startUp();
		i = 0;
		j = 2;
		k = 0;
	}

	// Distance Comparator Variables
	int dist15 = -1;
	int dist0 = -1;
	int i = 0;
	int j = 2;
	// Autonomous Process Variables
	int k = 0; // Sequencer, what step the autonomous process is currently at.
	boolean ninety = false;
	int range = 270;
	int seg1 = 13;
	int seg2 = 2;

	public void autonomousPeriodic() {
		// Distance Comparator
		for (LeddarDistanceSensor.LeddarDistanceSensorData info : leddar
				.getDistances()) {
			if (info.getDistanceInCentimeters() < range) {
				if (info.getSegmentNumber() == seg1 && i % 5 == 0) {
					dist15 = info.getDistanceInCentimeters();
					System.out.println("Segment " + seg1 + " = "
							+ info.getDistanceInCentimeters());
				}
				if (info.getSegmentNumber() == seg2 && j % 5 == 0) {
					dist0 = info.getDistanceInCentimeters();
					System.out.println("Segment " + seg2 + " = "
							+ info.getDistanceInCentimeters());
				}

				if (Math.abs(dist15 - dist0) < 5 && dist0 != -1) {
					System.out.println("90 Degrees baby");
					ninety = true;
				}

				/**
				 * Shooting Process Starts Here (May wish to move to Autonomous
				 * Init() to clear space/processes for distance comparing.
				 **/

				else {
					ninety = false;
				}

				// Step 1. Turn towards the left boundary of the court.
				if (k == 0) {
					if (!ninety) {
						ajoy.turnToWall(true);
					} else {
						dT.stop();
						k = 1;
					}
					;
				}
				// Step 2. Move towards the wall, turn towards the north
				// boundary of the court, move towards the next wall.
				if (k == 1) {
					range = 520;
					seg1 = 8;
					seg2 = 7;
					// is facing left
					ajoy.moveToDistance(60);
					// is positioned at left
					// (placeholder)
					// turn towards north
					ajoy.moveToDistance(90);
					// is positioned at tower
					k = 2;
				}
				// Step 3. Shoot or something kek
				if (k == 2) {
					// turn 45 or whatever to goal

					// shoot
					/** aim at low goal **/

					/** drive up and shoot **/

				}
				/** Shooting Process Ends Here **/
				// Reverse the physical order (not the chronology) of the code
				// (put the if statements in order 2,1,0, so that it refreshes
				// sensor data for each step

				i++;
				j++;

			} // End of data in range check
		} // End of for loop
	}// End of Method

	@Override
	public void disabledInit() {

		leddar.shutDown();
	}

	public void teleopInit() {
		arm.initialize();
		
		System.out.println("\n****************************************");
		System.out.println("********** BUTTONS FOR DRIVERS *********");
		System.out.println("\nLEFT joystick controls :" + "\nPRESS "
				+ RobotMap.REVERSE_DRIVE_BUTTON
				+ "to reverse the direction of the robot." + "\nPRESS "
				+ RobotMap.BALL_CONTROL_INTAKE_BUTTON + "to suck the ball in"
				+ "\n");

		System.out.println("SHOOTER joystick controls : " + "\nPRESS "
				+ RobotMap.TREXARM_LOWER_BUTTON + " to lower Trekudesu."
				+ "\nPRESS " + RobotMap.TREXARM_RAISE_BUTTON
				+ " to raise Trekudesu." + "\nPRESS "
				+ RobotMap.BALL_CONTROL_SHOOT_BUTTON + " to push the ball out."
				+ "\nPRESS " + RobotMap.SCALING_UPWARDS_BUTTON
				+ " to release scaling mechanicism." + "\nPRESS "
				+ RobotMap.SCALING_DOWNWARDS_BUTTON
				+ "to reel in the scaling mechanicism." + "\n");
		System.out.println("****************************************");
		System.out.println("****************************************" + "\n");
	}
	
	int milliseconds = 0;

	public void teleopPeriodic() {
		
		// Amanda's work
		
		if (milliseconds % 10000 == 0)
			shooter.setRumble(RumbleType.kLeftRumble, 1);
		if (milliseconds % 10000 == 2000)
			shooter.setRumble(RumbleType.kLeftRumble, 0);

		ballControl.performIntake(left
				.getRawButton(RobotMap.BALL_CONTROL_INTAKE_BUTTON)); // 3

		ballControl.shoot(shooter
				.getRawButton(RobotMap.BALL_CONTROL_SHOOT_BUTTON)); // 4

		arm.handleButtons(shooter.getRawButton(RobotMap.TREXARM_RAISE_BUTTON), //
				shooter.getRawButton(RobotMap.TREXARM_LOWER_BUTTON)); //
		
		scaling.setSpeedSensitivity(shooter.getRawAxis(3));

		if (left.getIsXbox())
			dT.xboxDrive(left, left.getRawButton(RobotMap.REVERSE_DRIVE_BUTTON));
		else 
			dT.drive(left, right, left.getRawButton(RobotMap.REVERSE_DRIVE_BUTTON)); // 8
		
		milliseconds += 20;	
	}

	public void testInit() {
	}

	public void testPeriodic() {
	}

}
