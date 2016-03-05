package org.usfirst.frc.team4330.robot;

// ctrl + shift + o to automatically get imports

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
 * pandanomium
 * 
 */
public class Robot extends IterativeRobot {
	Joystick leftJoystick, rightJoystick;

	DriveTrain driveTrain;
	Arm trekudesu;
	BallControl ballControl;
	LeddarDistanceSensor leddar;
	SensorDataRetriever vision;
	Gyro gyro;
	Manager manager;

	SmartDashboardSetup smartDashboard;

	public Robot() {
		leftJoystick = new Joystick(RobotMap.JOYSTICK_ONE);
		rightJoystick = new Joystick(RobotMap.JOYSTICK_TWO);

		driveTrain = new DriveTrain();
		leddar = new LeddarDistanceSensor();
		trekudesu = new Arm(); // new Victor(RobotMap.TREXARM_PORT)
		ballControl = new BallControl(new Victor(RobotMap.INTAKE_PORT),
				new Relay(RobotMap.SPIKE_PORT, Direction.kBoth));
		vision = new SensorDataRetriever();
		gyro = new AnalogGyro(0, 0, 0);
		manager = new Manager(driveTrain, gyro, smartDashboard, vision, ballControl, trekudesu, Scheduler.getInstance());
		smartDashboard = new SmartDashboardSetup();
		// scaleraptor = new Scaling(new Victor(RobotMap.SCALAR_PORT));
	}

	public void autonomousInit() {
		smartDashboard.initiliaze();
		System.out.println("Position choice is " + smartDashboard.autoPosition);
		System.out.println("Defense choice is " + smartDashboard.autoDefense);
		manager.autonomousInit();
	}

	public void autonomousPeriodic() {		
		manager.autonomousPeriodic();
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
		driveTrain.drive(leftJoystick, rightJoystick,
					rightJoystick.getRawButton(RobotMap.REVERSE_DRIVE_BUTTON)); // 8

	}

	public void testInit() {
		manager.testInit();
	}

	public void testPeriodic() {
		manager.testPeriodic();
//		hotbod.drive(.2, .2);
//		System.out.println("Left values: " + leftJoystick.getY() + "; Right values: " + rightJoystick.getY());
	}

	@Override
	public void disabledInit() {
		manager.disableInit();
	}

	@Override
	public void disabledPeriodic() {
	}
	
	

}
