package org.usfirst.frc.team4330.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

public class DriveTrain {
	private SpeedController rFW, lFW, rBW, lBW;
	boolean reverse;
	boolean lastPressed;
	Timer timer = new Timer();

	public DriveTrain() {
		reverse = false;
		lastPressed = false;

		rFW = new Victor(RobotMap.RIGHT_FRONT_WHEEL);
		rBW = new Victor(RobotMap.RIGHT_BACK_WHEEL);
		lFW = new Victor(RobotMap.LEFT_FRONT_WHEEL);
		lBW = new Victor(RobotMap.LEFT_BACK_WHEEL);
	}

	/**
	 * Drives the robot forward or backwards if controller is xbox.
	 * 
	 * @param xboxdrive
	 *            the xbox controller
	 * @param currentlyPressed
	 *            true if reverse button is currently pressed, false otherwise
	 */
	public void xboxDrive(Joystick left, boolean currentlyPressed) {

		if (!lastPressed && currentlyPressed) {
			reverse = !reverse;
			if (reverse) {
				System.out.println("Robot is in reverse");
			} else {
				System.out.println("Rebot is in drive");
			}
		}

		lastPressed = currentlyPressed;

		if (reverse) {
			rFW.set(left.getRawAxis(5));
			rBW.set(left.getRawAxis(5));
			lFW.set(left.getRawAxis(1));
			lBW.set(left.getRawAxis(1));
		} else {
			rFW.set(-left.getRawAxis(5));
			rBW.set(-left.getRawAxis(5));
			lFW.set(-left.getRawAxis(1));
			lBW.set(-left.getRawAxis(1));
		}
	}

	/**
	 * Drives the robot forward or backwards.
	 * 
	 * @param left
	 *            the left joystick
	 * @param right
	 *            the right joystick
	 * @param currentlyPressed
	 *            true if reverse button is currently pressed, false otherwise
	 */
	public void drive(GenericHID left, GenericHID right,
			boolean currentlyPressed) {

		if (!lastPressed && currentlyPressed) {
			reverse = !reverse;
			if (reverse) {
				System.out.println("Robot is in reverse.");
			} else {
				System.out.println("Robot is in drive.");
			}
		}

		lastPressed = currentlyPressed;

		if (reverse) {
			rFW.set(-right.getY());
			rBW.set(-right.getY());
			lFW.set(left.getY());
			lBW.set(left.getY());
		} else {
			rFW.set(right.getY());
			rBW.set(right.getY());
			lFW.set(-left.getY());
			lBW.set(-left.getY());
		}
	}

	private class timedDrive extends TimerTask {
		@Override
		public void run() {
			rFW.set(-.2);
			rBW.set(-.2);
			lFW.set(.2);
			lBW.set(.2);
		}
	}

	private class timedBoost extends TimerTask {

		@Override
		public void run() {
			rFW.set(-.6);
			rBW.set(-.6);
			lFW.set(.6);
			lBW.set(.6);
		}
	}

	private class stopDrive extends TimerTask {
		@Override
		public void run() {
			rFW.set(0);
			rBW.set(0);
			lFW.set(0);
			lBW.set(0);
		}
	}

	public void driveToPosition(int x, int y) {

	}

	public void spin180() {

	}

	// try 2000 first
	public void forward(int milliseconds, boolean boost) {
		if (!boost)
			timer.schedule(new timedDrive(), 0);
		else {
			timer.schedule(new timedBoost(), 0);
		}
		timer.schedule(new stopDrive(), milliseconds);
	}

	public void stop() {
		rFW.set(0);
		rBW.set(0);
		lFW.set(0);
		lBW.set(0);
	}

	// all signs align for a single direction
	public void fixedDrive(double r, double l) {
		rFW.set(r);
		rBW.set(r);
		lFW.set(l);
		lBW.set(l);
	}

	// positive is counterclockwise, negative is clockwise
	private class turn extends TimerTask {
		double speed;

		private turn(double speed) {
			this.speed = speed;
		}

		@Override
		public void run() {
			rFW.set(speed);
			rBW.set(speed);
			lFW.set(speed);
			lBW.set(speed);
		}
	}

}
