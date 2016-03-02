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
	Joystick leftJoystick, rightJoystick;

	DriveTrain hotbod;
	Arm trekudesu;
	BallControl ballControl;
	LeddarDistanceSensor leddar;
	Manager woman;
	SmartDashboardSetup smartDashboard;

	public Robot() {
		leftJoystick = new Joystick(RobotMap.JOYSTICK_ONE);
		rightJoystick = new Joystick(RobotMap.JOYSTICK_TWO);

		hotbod = new DriveTrain();
		leddar = new LeddarDistanceSensor();
		trekudesu = new Arm(); // new Victor(RobotMap.TREXARM_PORT)
		ballControl = new BallControl(new Victor(RobotMap.INTAKE_PORT),
				new Relay(RobotMap.SPIKE_PORT, Direction.kBoth));
		woman = new Manager(hotbod, new AnalogGyro(0, 0, 0));
		smartDashboard = new SmartDashboardSetup();
		
		// scaleraptor = new Scaling(new Victor(RobotMap.SCALAR_PORT));
	}

	public void autonomousInit() {
		smartDashboard.initiliaze();
		System.out.println("Position choice is " + smartDashboard.autoPosition);
		System.out.println("Defense choice is " + smartDashboard.autoDefense);
		
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

		ballControl.performIntake(rightJoystick
				.getRawButton(RobotMap.BALL_CONTROL_INTAKE_BUTTON)); // 6

		ballControl.shoot(rightJoystick
				.getRawButton(RobotMap.BALL_CONTROL_SHOOT_BUTTON)); // 4

		trekudesu.handleButtons(
				leftJoystick.getRawButton(RobotMap.TREXARM_RAISE_BUTTON), // 5
				leftJoystick.getRawButton(RobotMap.TREXARM_LOWER_BUTTON), // 3
				leftJoystick.getRawButton(RobotMap.TREXARM_POWER_BUTTON)); // 2

		// RIP HER DREAMS, NOBODY WANTED THEM ANYWAYS

		// RIP weiner dog sometime - 2/22/16

		// scaleraptor.handleButtons(
		// shooter.getRawButton(RobotMap.SCALING_UPWARDS_BUTTON), // 6
		// shooter.getRawButton(RobotMap.SCALING_DOWNWARDS_BUTTON), // 4
		// shooter.getTrigger(), 20);

		/*if (leftJoystick.getIsXbox())
			hotbod.xboxDrive(leftJoystick,
					rightJoystick.getRawButton(RobotMap.REVERSE_DRIVE_BUTTON)); // 8
		else
			*/
		hotbod.drive(leftJoystick, rightJoystick,
					rightJoystick.getRawButton(RobotMap.REVERSE_DRIVE_BUTTON)); // 8

	}

	public void testInit() {
		
	}

	public void testPeriodic() {
		System.out.println("Left values: " + leftJoystick.getY() + "; Right values: " + rightJoystick.getY());
	}

}
