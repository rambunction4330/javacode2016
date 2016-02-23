package org.usfirst.frc.team4330.robot;

// ctrl + shift + o to automatically get imports

import org.usfirst.frc.team4330.robot.autonomous.Driver;
import org.usfirst.frc.team4330.robot.canbus.LeddarDistanceSensor;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 2016 final code!!
 * 
 * Author: Bananananananananananananananananananananamanda & sorta Patrick
 * pandanomium
 * 
 */
public class Robot extends IterativeRobot {
	DriveTrain hotbod;
	Arm trekudesu;
	BallControl ballControl;
	Joystick left, right, shooter;
	LeddarDistanceSensor leddar;
	AnonymousJoystick ajoy;
	Driver driver;

//	SendableChooser defenseChooser;
//	SendableChooser positionChooser;

	private int autoPosition;
	private String autoDefense;

	private String portcullis = "portcullis";
	private String chivalDeFrise = "chivalDeFrise";
	private String roughTerrain = "roughTerrain";
	private String moat = "moat";
	private String rampart = "rampart";
	private String rockWall = "rockWall";
	private String lowBar = "lowBar";
	private int one = 1;
	private int two = 2;
	private int three = 3;
	private int four = 4;
	private int five = 5;
	private int once;
	private int angle = 0;
	// private thread flag;
//	private thread flag = thread.OFF;
//
//	
//	private enum thread {
//		ARM, INTAKE, DRIVE, OFF;
//	}

	
	/*
     * 
     */
	public Robot() {
		System.out.println("has it failed yet??");
		
		driver = new Driver();
		hotbod = new DriveTrain();
		left = new Joystick(RobotMap.JOYSTICK_TWO);
		right = new Joystick(RobotMap.JOYSTICK_THREE);
		shooter = new Joystick(RobotMap.JOYSTICK_ONE);
		leddar = new LeddarDistanceSensor();
		trekudesu = new Arm(); // new Victor(RobotMap.TREXARM_PORT)
		ballControl = new BallControl(new Victor(RobotMap.INTAKE_PORT),
				new Relay(RobotMap.SPIKE_PORT, Direction.kBoth));
		// scaleraptor = new Scaling(new Victor(RobotMap.SCALAR_PORT),
		// shooter.getRawAxis(3));

//		defenseChooser = new SendableChooser();
//		defenseChooser.addDefault("Portcullis", portcullis);
//		defenseChooser.addObject("Chival de Frise (four moving trains)",
//				chivalDeFrise);
//		defenseChooser.addObject("Rough Terrain", roughTerrain);
//		defenseChooser.addObject("Moat", moat);
//		defenseChooser.addObject("Rampart (two non-moving trains)", rampart);
//		defenseChooser.addObject("Rock Wall", rockWall);
//		defenseChooser.addObject("Low Bar", lowBar);
//		SmartDashboard.putData("Autonomous Defense", defenseChooser);
//
//		positionChooser = new SendableChooser();
//		positionChooser.addDefault("One", one);
//		positionChooser.addObject("Two", two);
//		positionChooser.addObject("Three", three);
//		positionChooser.addObject("Four", four);
//		positionChooser.addObject("Five", five);
//		SmartDashboard.putData("Autonomous Position", positionChooser);
	}
	
//	public void changeState(thread change) {
//		justAName = change;
//	}

	public void autonomousInit() {
//		autoPosition = (int) positionChooser.getSelected();
//		autoDefense = (String) defenseChooser.getSelected();
		System.out.println("Position choice is " + autoPosition);
		System.out.println("Defense choice is " + autoDefense);

		System.out.println("*********************************");
		System.out.println("*********************************");
		System.out.println("******** May the evens be *******");
		System.out.println("****** ever in your favor! ******");
		System.out.println("*********************************");
		System.out.println("*********************************");

//		leddar.startUp();
		once = 0;

	}

	//

	public void autonomousPeriodic() {
//		/* step one: move forward */
//		if (once == 0 && flag != thread.OFF) {
//			/* step two: get over obstacle */
//			if (autoDefense.equals(portcullis)) {
//				flag = thread.ARM;
//				trekudesu.autonomousArm(false, true, false);
//				hotbod.forward(1000);
//				trekudesu.autonomousArm(true, false, true);
//				flag = thread.OFF;
//				
//			}
////			if (autoDefense.equals(chivalDeFrise)) {
////				// trex then ram
////			}
//			// drivers have trouble
//			if (autoDefense.equals(rampart)) {
//				hotbod.forward(1000);				
//				// ram 1 gb
//			}
//			if (autoDefense.equals(moat)) {
//				hotbod.forward(1000);
//				// ram 2.5 gb
//			}
//			if (autoDefense.equals(roughTerrain)) {
//				hotbod.forward(1000);
//				// ram 4 gb
//			}
//			if (autoDefense.equals(rockWall)) {
//				hotbod.forward(1000);
//				// ram 6 gb
//			}
//		}
		// threads so you don't keep doing other stuff while something is active
		// THIS IS AFTER YOU GET OVER THE F**KING OBSTACLE
		
//		driver.findAnfgle(angle);

		// if (autoDefense.equals(portcullis)) {
		//
		// } else if (autoDefense.equals(moat)) {
		//
		// }
		once = 1;

	}

	/*
	 * // Distance Comparator for (LeddarDistanceSensor.LeddarDistanceSensorData
	 * info : leddar .getDistances()) { if (info.getDistanceInCentimeters() <
	 * range) { if (info.getSegmentNumber() == seg1 && i % 5 == 0) { dist15 =
	 * info.getDistanceInCentimeters(); System.out.println("Segment " + seg1 +
	 * " = " + info.getDistanceInCentimeters()); } if (info.getSegmentNumber()
	 * == seg2 && j % 5 == 0) { dist0 = info.getDistanceInCentimeters();
	 * System.out.println("Segment " + seg2 + " = " +
	 * info.getDistanceInCentimeters()); }
	 * 
	 * if (Math.abs(dist15 - dist0) < 5 && dist0 != -1) {
	 * System.out.println("90 Degrees baby"); ninety = true; }
	 *//**
	 * Shooting Process Starts Here (May wish to move to Autonomous Init() to
	 * clear space/processes for distance comparing.
	 **/
	/*
	 * 
	 * else { ninety = false; }
	 * 
	 * // Step 1. Turn towards the left boundary of the court. if (k == 0) { if
	 * (!ninety) { ajoy.turnToWall(true); } else { herby.stop(); k = 1; } ; } //
	 * Step 2. Move towards the wall, turn towards the north // boundary of the
	 * court, move towards the next wall. if (k == 1) { range = 520; seg1 = 8;
	 * seg2 = 7; // is facing left ajoy.moveToDistance(60); // is positioned at
	 * left // (placeholder) // turn towards north ajoy.moveToDistance(90); //
	 * is positioned at tower k = 2; } // Step 3. Shoot or something kek if (k
	 * == 2) { // turn 45 or whatever to goal
	 * 
	 * // shoot
	 *//** aim at low goal **/
	/*

					*//** drive up and shoot **/
	/*
	 * 
	 * }
	 *//** Shooting Process Ends Here **/
	/*
	 * // Reverse the physical order (not the chronology) of the code // (put
	 * the if statements in order 2,1,0, so that it refreshes // sensor data for
	 * each step
	 * 
	 * i++; j++;
	 * 
	 * } // End of data in range check } // End of for loop
	 */// End of Method

	@Override
	public void disabledInit() {
		// trekudesu.disable();
//		leddar.shutDown();
		
		System.out.println("help");
	}

	public void teleopInit() {
		// trekudesu.initialize(false);
		// scaleraptor.initialize();

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
				+ " to raise Trekudesu."
				+ "\nPRESS Trigger to power Trekudesu." + "\nPRESS "
				+ RobotMap.BALL_CONTROL_SHOOT_BUTTON + " to push the ball out."
				+ "\nPRESS " + RobotMap.SCALING_UPWARDS_BUTTON
				+ " to release scaling mechanicism." + "\nPRESS "
				+ RobotMap.SCALING_DOWNWARDS_BUTTON + "to lift the robot up."
				+ "\n");
		System.out.println("****************************************");
		System.out.println("****************************************" + "\n");

	}

	public void teleopPeriodic() {

		ballControl.performIntake(right
				.getRawButton(RobotMap.BALL_CONTROL_INTAKE_BUTTON)); // 6

		ballControl.shoot(right
				.getRawButton(RobotMap.BALL_CONTROL_SHOOT_BUTTON)); // 4

		trekudesu.handleButtons(
				left.getRawButton(RobotMap.TREXARM_RAISE_BUTTON), // 5
				left.getRawButton(RobotMap.TREXARM_LOWER_BUTTON), // 3
				left.getTrigger());

		// RIP HER DREAMS, NOBODY WANTED THEM ANYWAYS

		// RIP weiner dog sometime - 2/22/16

		// scaleraptor.handleButtons(
		// shooter.getRawButton(RobotMap.SCALING_UPWARDS_BUTTON), // 6
		// shooter.getRawButton(RobotMap.SCALING_DOWNWARDS_BUTTON), // 4
		// shooter.getTrigger(), 20);

		if (left.getIsXbox())
			hotbod.xboxDrive(left,
					left.getRawButton(RobotMap.REVERSE_DRIVE_BUTTON));
		else
			hotbod.drive(left, right,
					left.getRawButton(RobotMap.REVERSE_DRIVE_BUTTON)); // 8

	}

	public void testInit() {
		// trekudesu.initialize(true);
	}

	public void testPeriodic() {
		// do not tuch except 4 year veterans
		// if (trekudesu.getPosition() != 0)
		// System.out.println("Smart Arm position is "
		// + trekudesu.getPosition());

		// ballControl.test();
	}

}
