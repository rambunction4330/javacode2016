package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class DriveTrain {
	private SpeedController rFW, lFW, rBW, lBW;
	boolean reverse;
	boolean lastPressed;

	public DriveTrain() {
		reverse = false;
		lastPressed = false;

		rFW = new Talon(RobotMap.RIGHT_FRONT_WHEEL);
		rBW = new Talon(RobotMap.RIGHT_BACK_WHEEL);
		lFW = new Talon(RobotMap.LEFT_FRONT_WHEEL);
		lBW = new Talon(RobotMap.LEFT_BACK_WHEEL);
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
	public void drive(Joystick left, Joystick right, boolean currentlyPressed) {

		if (!lastPressed && currentlyPressed) {
			reverse = !reverse;
			if (reverse)
				System.out.println("Robot is in reverse");
			else
				System.out.println("Rebot is in drive");
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

	public void turnCounter(double speed) {
		rFW.set(speed);
		rBW.set(speed);
		lFW.set(speed);
		lBW.set(speed);
	}

	public void turnClock(double speed) {
		rFW.set(-speed);
		rBW.set(-speed);
		lFW.set(-speed);
		lBW.set(-speed);
	}

}
