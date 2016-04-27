package org.usfirst.frc.team4330.robot;

// ctrl + shift + o to automatically get imports

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.usfirst.frc.team4330.robot.autonomous.Manager;
import org.usfirst.frc.team4330.robot.canbus.LeddarDistanceSensor;
import org.usfirst.frc.team4330.robot.raspberrypi.SensorDataRetriever;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * 2016 final code!!
 * 
 * Author: Bananananananananananananananananananananamanda & sorta Patrick
 * pandanomium kinda Dasun
 */
public class Robot extends IterativeRobot {
	private Logger log = Logger.getLogger(Robot.class);

	Joystick leftJoystick, rightJoystick;

	DriveTrain driveTrain;
	Arm trekudesu;
	BallControl ballControl;
	LeddarDistanceSensor leddar;
	SensorDataRetriever vision;
	Gyro gyro;
	Manager manager;
	// Pneumatics pneu;
	// Compressor comp;
	// private long lastPeriodicTimeCalled;
	// private long warningCounter;

	SmartDashboardSetup smartDashboard;

	public Robot() {
		BasicConfigurator.configure();
		log.info("log4j Start Up");

		leftJoystick = new Joystick(RobotMap.JOYSTICK_ONE);
		rightJoystick = new Joystick(RobotMap.JOYSTICK_TWO);

		driveTrain = new DriveTrain();
		leddar = new LeddarDistanceSensor();
		trekudesu = new Arm();
		ballControl = new BallControl(new Victor(RobotMap.INTAKE_PORT),
				new Relay(RobotMap.SPIKE_PORT, Direction.kBoth));
		// comp = new Compressor(0);
		// comp.setClosedLoopControl(true);
		// Pneumatics pneumatics = new Pneumatics(comp, new
		// Solenoid(RobotMap.KICKER_SOL), new
		// DoubleSolenoid(RobotMap.BIGONE_SOLF, RobotMap.BIGONE_SOLB));
		// pneu = pneumatics;
		vision = new SensorDataRetriever();
		System.out.println("Calibrating gyro");
		gyro = new AnalogGyro(RobotMap.GYRO_PORT);
		System.out.println("Gyro calibrated");
		smartDashboard = new SmartDashboardSetup();
		manager = new Manager(driveTrain, gyro, smartDashboard, vision,
				ballControl, trekudesu, Scheduler.getInstance());

		// scaleraptor = new Scaling(new Victor(RobotMap.SCALAR_PORT));
	}

	public void autonomousInit() {
		try {
			try {
				vision.startUp();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Position choice is "
					+ smartDashboard.getAutoPosition());
			System.out.println("Defense choice is "
					+ smartDashboard.getAutoDefense());
			gyro.reset();
			manager.autonomousInit();

			// lastPeriodicTimeCalled = System.currentTimeMillis();
			// warningCounter = 0;
		} catch (Exception e) {
			log.error("Exception in Autonomous Init", e);
		}
	}

	public void autonomousPeriodic() {
		try {
			manager.autonomousPeriodic();
		} catch (Exception e) {
			log.error("Exception in Autonomous Periodic", e);
		}

		// warn if robot seems non responsive
		/*
		 * long time = System.currentTimeMillis(); if ( time -
		 * lastPeriodicTimeCalled > 25 && warningCounter % 50 == 1) {
		 * warningCounter++; System.out.println(
		 * "---WARNING--- Non responsive robot in autonomous phase with interval being "
		 * + (time - lastPeriodicTimeCalled) + " msec"); }
		 * lastPeriodicTimeCalled = time;
		 */
	}

	public void teleopInit() {
		// comp.start();
		try {

			System.out.println("\n****************************************");
			System.out.println("********** BUTTONS FOR DRIVERS *********");
			System.out.println("\nLEFT joystick controls :" + "\nPRESS "
					+ RobotMap.REVERSE_DRIVE_BUTTON
					+ "to reverse the direction of the robot." + "\nPRESS "
					+ RobotMap.BALL_CONTROL_INTAKE_BUTTON
					+ "to suck the ball in" + "\nPRESS "
					+ RobotMap.BALL_CONTROL_SHOOT_BUTTON
					+ " to push the ball out." + "\n");

			System.out.println("RIGHT joystick controls : " + "\nPRESS "
					+ RobotMap.TREXARM_LOWER_BUTTON + " to lower Trekudesu."
					+ "\nPRESS " + RobotMap.TREXARM_RAISE_BUTTON
					+ " to raise Trekudesu."
					+ "\nPRESS Trigger to power Trekudesu." + "\n");
			System.out.println("****************************************");
			System.out.println("****************************************"
					+ "\n");

			// trying to do this to fix crash reported during match when
			// changing from autonomous to teleop
			// disabledInit();
			gyro.reset();

			/*
			 * lastPeriodicTimeCalled = System.currentTimeMillis();
			 * warningCounter = 0;
			 */
		} catch (Exception e) {
			log.error("Exception in Teleop Init", e);
		}

	}

	public void teleopPeriodic() {
		try {
			ballControl.performIntake(rightJoystick
					.getRawButton(RobotMap.BALL_CONTROL_INTAKE_BUTTON)); // 6

			ballControl.shoot(rightJoystick
					.getRawButton(RobotMap.BALL_CONTROL_SHOOT_BUTTON)); // 4

			trekudesu.handleButtons(
					leftJoystick.getRawButton(RobotMap.TREXARM_RAISE_BUTTON), // 5
					leftJoystick.getRawButton(RobotMap.TREXARM_LOWER_BUTTON), // 3
					leftJoystick.getTrigger());

			// RIP HER DREAMS, NOBODY WANTED THEM ANYWAYS

			// RIP weiner dog sometime - 2/22/16

			// d scaleraptor.handleButtons(
			// shooter.getRawButton(RobotMap.SCALING_UPWARDS_BUTTON), // 6
			// shooter.getRawButton(RobotMap.SCALING_DOWNWARDS_BUTTON), // 4
			// shooter.getTrigger(), 20);

			/*
			 * if (leftJoystick.getIsXbox()) hotbod.xboxDrive(leftJoystick,
			 * rightJoystick.getRawButton(RobotMap.REVERSE_DRIVE_BUTTON)); // 8
			 * else
			 */

			/*
			 * if (leftJoystick.getRawButton(9)) pneu.KickerOut(); if
			 * (leftJoystick.getRawButton(10)) pneu.KickerIn(); if
			 * (leftJoystick.getRawButton(7)) pneu.BigoneIn(); else if
			 * (leftJoystick.getRawButton(8)) pneu.BigoneOut(); else
			 * pneu.BigoneOff(); System.out.println("closed loop = " +
			 * pneu.comp.getClosedLoopControl());
			 */
			driveTrain.drive(leftJoystick, rightJoystick,
					rightJoystick.getRawButton(RobotMap.REVERSE_DRIVE_BUTTON)); // 8

			// warn if robot seems non responsive
			/*
			 * long time = System.currentTimeMillis(); if ( time -
			 * lastPeriodicTimeCalled > 25 && warningCounter % 50 == 1 ) {
			 * warningCounter++; System.out.println(
			 * "---WARNING--- Non responsive robot in teleop phase with interval being "
			 * + (time - lastPeriodicTimeCalled) + " msec"); }
			 * lastPeriodicTimeCalled = time;
			 */
		} catch (Exception e) {
			log.error("Exception in Teleop Periodic", e);
		}
	}

	public void testInit() {
		// manager.testInit();
		// gyro.calibrate();
		//
		// System.out.println("Defense is : " +
		// smartDashboard.getAutoDefense());
		// System.out.println("Position is : " +
		// smartDashboard.getAutoPosition());

	}

	public void testPeriodic() {
		// manager.testPeriodic();
		// System.out.println("Gyro reading is " + gyro.getAngle());
		// System.out.println("Vision angle" + vision.RELATIVE_BEARING);
		// hotbod.drive(.2, .2);
		// System.out.println("Left values: " + leftJoystick.getY() +
		// "; Right values: " + rightJoystick.getY());

		// pneu.checkStatus();

	}

	@Override
	public void disabledInit() {
		try {
			manager.disableInit();
		} catch (Exception e) {
			log.error("Exception in disabled init", e);
		}
		// pneu.disabled();
		// pneu.disabled();

	}

	@Override
	public void disabledPeriodic() {
	}

}
