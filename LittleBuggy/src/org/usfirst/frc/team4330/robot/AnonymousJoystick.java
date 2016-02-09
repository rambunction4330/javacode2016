package org.usfirst.frc.team4330.robot;

import org.usfirst.frc.team4330.robot.canbus.LeddarDistanceSensor;

import edu.wpi.first.wpilibj.GenericHID;

public abstract class AnonymousJoystick extends GenericHID {
	
	
	// left is the true direction
	private LeddarDistanceSensor.LeddarDistanceSensorData leddar;
	private DriveTrain driver;
	
	
	public void turnToWall(boolean direction){
		// turn about 90ish degrees left or right depending on the direction given.
		if (direction){// left
	    	driver.turnCounter(0.2);
		}
		
		else {// reverse direction of true
			driver.turnClock(0.2);
		}
	}
	
	// checking method
//	public void aligntoLR(boolean direction){
//		if (direction) { // left
//			turnToWall(true);
//			
//			driver.stop();
//			
//		}
//		else {
//			turnToWall(false);
//		}
//	}
	
	
	public void moveToDistance(int distance){
		
		while (distance > leddar.getDistanceInCentimeters()) {
			driver.fixedDrive(0.2, 0.2);
		}
		driver.stop();
		
	}
	public void something(){
		
	}
	
}
