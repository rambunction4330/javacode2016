package org.usfirst.frc.team4330.robot;

// ctrl + shift + o to automatically get imports

import org.usfirst.frc.team4330.robot.autonomous.Manager;
import org.usfirst.frc.team4330.robot.canbus.LeddarDistanceSensor;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.interfaces.Gyro;
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
	Joystick left, right;
	LeddarDistanceSensor leddar;
	AnonymousJoystick ajoy;
	Gyro dudero;
	Manager woman;

	SendableChooser defenseChooser;
	SendableChooser positionChooser;

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

	public Robot() {
		hotbod = new DriveTrain();
		left = new Joystick(RobotMap.JOYSTICK_ONE);
		right = new Joystick(RobotMap.JOYSTICK_TWO);
		leddar = new LeddarDistanceSensor();
		trekudesu = new Arm(); // new Victor(RobotMap.TREXARM_PORT)
		ballControl = new BallControl(new Victor(RobotMap.INTAKE_PORT),
				new Relay(RobotMap.SPIKE_PORT, Direction.kBoth));
		dudero = new AnalogGyro(0, 0, 0);
		woman = new Manager(hotbod, dudero);
		
		// scaleraptor = new Scaling(new Victor(RobotMap.SCALAR_PORT));

		defenseChooser = new SendableChooser();
		defenseChooser.addDefault("Portcullis", portcullis);
		defenseChooser.addObject("Chival de Frise (four moving trains)",
				chivalDeFrise);
		defenseChooser.addObject("Rough Terrain", roughTerrain);
		defenseChooser.addObject("Moat", moat);
		defenseChooser.addObject("Rampart (two non-moving trains)", rampart);
		defenseChooser.addObject("Rock Wall", rockWall);
		defenseChooser.addObject("Low Bar", lowBar);
		SmartDashboard.putData("Autonomous Defense", defenseChooser);

		positionChooser = new SendableChooser();
		positionChooser.addDefault("One", one);
		positionChooser.addObject("Two", two);
		positionChooser.addObject("Three", three);
		positionChooser.addObject("Four", four);
		positionChooser.addObject("Five", five);
		SmartDashboard.putData("Autonomous Position", positionChooser);
	}

	public void autonomousInit() {
		// autoPosition = (int) positionChooser.getSelected();
		// autoDefense = (String) defenseChooser.getSelected();
		System.out.println("Position choice is " + autoPosition);
		System.out.println("Defense choice is " + autoDefense);

		System.out.println("*********************************");
		System.out.println("*********************************");
		System.out.println("******** May the evens be *******");
		System.out.println("****** ever in your favor! ******");
		System.out.println("*********************************");
		System.out.println("*********************************");

		woman.initialize();
	}

	//

	public void autonomousPeriodic() {
		woman.runSchedule();

		// /* step one: move forward */
		// if (once == 0 && flag == thread.OFF) {
		// // hotbod.forward(1000, .2);
		//
		// /* step two: get over obstacle */
		// if (autoDefense.equals(portcullis)) {
		// flag = thread.ARM;
		// // trekudesu.autonomousArm(false, true, false);
		// // hotbod.forward(1000, .2);
		// // trekudesu.autonomousArm(true, false, true);
		// flag = thread.OFF;
		// }
		// /*
		// * if (autoDefense.equals(chivalDeFrise)) { // trex then ram }
		// */
		// // drivers have trouble
		// // TODO choose rampart in Dashboard
		// // TODO initialize autoDefense
		// if (autoDefense.equals(rampart)) {
		// flag = thread.DRIVE;
		// hotbod.forward(0.5, 0, 499);
		// hotbod.forward(0.8, 500, 1000);
		// flag = thread.OFF;
		// // ram 1 gb`
		// }
		// if (autoDefense.equals(moat)) {
		// // hotbod.forward(1000, .2);
		// // ram 2.5 gb
		// }
		// if (autoDefense.equals(roughTerrain)) {
		// // hotbod.forward(1000, .2);
		// // ram 4 gb
		// }
		// if (autoDefense.equals(rockWall)) {
		// // hotbod.forward(1000, .2);
		// // ram 6 gb
		// }
		// }
		// // threads so you don't keep doing other stuff while something is
		// // active
		// driver.findAngle(angle);
		// System.out.println("You should not be seeing this unless the robot has finished attempting to cross an obstacle \n \n \n");
		// System.out.println("DISABLE");
		//
		// once = 1;
		//
		// // } else {
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
				+ "\nPRESS " + RobotMap.BALL_CONTROL_SHOOT_BUTTON
				+ " to push the ball out." + "\n");

		System.out.println("RIGHT joystick controls : " + "\nPRESS "
				+ RobotMap.TREXARM_LOWER_BUTTON + " to lower Trekudesu."
				+ "\nPRESS " + RobotMap.TREXARM_RAISE_BUTTON
				+ " to raise Trekudesu."
				+ "\nPRESS Trigger to power Trekudesu." + "\n");
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

	}

}
