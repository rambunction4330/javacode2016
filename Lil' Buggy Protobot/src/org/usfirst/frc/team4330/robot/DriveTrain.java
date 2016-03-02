package org.usfirst.frc.team4330.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class DriveTrain {
	private SpeedController rFW, lFW, rBW, lBW;
	boolean reverse;
	boolean lastPressed;
	Timer timer = new Timer();

	public DriveTrain() {
		reverse = false;
		lastPressed = false;

		rFW = new Jaguar(RobotMap.RIGHT_FRONT_WHEEL);
		rBW = new Jaguar(RobotMap.RIGHT_BACK_WHEEL);
		lFW = new Jaguar(RobotMap.LEFT_FRONT_WHEEL);
		lFW.setInverted(true);
		lBW = new Jaguar(RobotMap.LEFT_BACK_WHEEL);
		lBW.setInverted(true);
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

		if (reverse)
			drive(left.getY(), -right.getY());
		else
			drive(-left.getY(), right.getY());
	}
	
	
	public void drive(double left, double right){
			rFW.set(right);
			rBW.set(right);
			lFW.set(left);
			lBW.set(left);
	}
	public void driveToPosition(int x, int y) {

	}

	public void spin180() {

	}

	/**
	 * 
	 * @param milliseconds
	 *            the amount of rule to drive for
	 */
	public void forward(double speedForward, int startTime, int endTime) {
		timer.schedule(new timedDrive(speedForward), startTime);
		timer.schedule(new stopDrive(), endTime);
	}
	
	public void turn(double increasingSpeed, int stopTime, boolean shouldWe) {
		timer.schedule(new turn(increasingSpeed), 0);
		if (shouldWe)
		timer.schedule(new stopDrive(), stopTime);
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

	private class timedDrive extends TimerTask {
		double speed;

		private timedDrive(double speed) {
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

	/**
	 * positive is counterclockwise, negative is clockwise
	 */
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
