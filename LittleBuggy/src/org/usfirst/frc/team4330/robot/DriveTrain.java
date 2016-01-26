package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class DriveTrain {
	private SpeedController rFW, lFW, rBW, lBW;
	private SpeedController miniMotor;
	private boolean reverse;
	
	public DriveTrain() {
		reverse = false;
		
		rFW = new Talon(RobotMap.RIGHT_FRONT_WHEEL);
		rBW = new Talon(RobotMap.RIGHT_BACK_WHEEL);
		lFW = new Talon(RobotMap.LEFT_FRONT_WHEEL);
		lBW = new Talon(RobotMap.LEFT_BACK_WHEEL);
		
		miniMotor = new Talon(RobotMap.MINI_MOTOR_PORT);
		
//		mRW = new Talon(Map.MINI_RIGHT_WHEEL);
//		mLW = new Talon(Map.MINI_LEFT_WHEEL);
	}
	
	public void drive(Joystick left, Joystick right) {
		if (reverse) {
			rFW.set(-right.getY());
			rBW.set(right.getY());
			lFW.set(left.getY());
			lBW.set(left.getY());	
		}
		
		rFW.set(right.getY());
		rBW.set(-right.getY());
		lFW.set(-left.getY());
		lBW.set(-left.getY());
	}
	
	public void driveReversed() {
		reverse = !reverse;
	}
	
	/*
	public void miniWheels(Joystick mini) {
		while (mini.getTrigger()) {
			mRW.set(-Map.MINI_WHEEL_SPEED);
			mLW.set(Map.MINI_WHEEL_SPEED);
		}
	}
	*/
	
	public void frontMinis() {
		if (reverse) {
			miniMotor.set(-RobotMap.MINI_WHEEL_SPEED);
		}
		
		miniMotor.set(RobotMap.MINI_WHEEL_SPEED);
	}
}
