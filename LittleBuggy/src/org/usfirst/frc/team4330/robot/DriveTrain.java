package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.*;

public class DriveTrain {
//	private Joystick stick1, stick2;
	private SpeedController rFW, lFW, rBW, lBW;
	private SpeedController mRW, mLW;
	private boolean reverse;
	private RobotDrive myRobot;
	
	public DriveTrain() {
		reverse = false;
		
		rFW = new Talon(Map.RIGHT_FRONT_WHEEL);
		rBW = new Talon(Map.RIGHT_BACK_WHEEL);
		lFW = new Talon(Map.LEFT_FRONT_WHEEL);
		lBW = new Talon(Map.LEFT_BACK_WHEEL);
		
		mRW = new Talon(Map.MINI_RIGHT_WHEEL);
		mLW = new Talon(Map.MINI_LEFT_WHEEL);
	}
	
	public void drive(Joystick left, Joystick right) {
		if (reverse) {
			rFW.set(-right.getY());
			rBW.set(-right.getY());
			lFW.set(left.getY());
			lBW.set(left.getY());	
		}
		
		rFW.set(right.getY());
		rBW.set(right.getY());
		lFW.set(-left.getY());
		lBW.set(-left.getY());
	}
	
	public void driveReversed() {
		reverse = !reverse;
	}
	
	public void miniWheels(Joystick mini) {
		while (mini.getTrigger()) {
			mRW.set(-Map.MINI_WHEEL_SPEED);
			mLW.set(Map.MINI_WHEEL_SPEED);
		}
	}
}
