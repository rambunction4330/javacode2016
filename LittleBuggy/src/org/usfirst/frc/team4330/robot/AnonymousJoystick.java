package org.usfirst.frc.team4330.robot;

import org.usfirst.frc.team4330.robot.canbus.LeddarDistanceSensor;

import edu.wpi.first.wpilibj.GenericHID;

public abstract class AnonymousJoystick extends GenericHID {
	
	
	// left is the true direction
	private LeddarDistanceSensor leddar;
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
	
	
//	public void aligntoLR(){
//		if (direction) { // left
//			
//		}
//		
//		else {
//			
//		}
//	}
//	public void moveToDistance(int distance){
//		
////		= leddar.getDistances();
////		if (distance > leddar.getDistances())
////			info.getDistances();
//		while (1 == 1 )//implement later
//			//not final
//				driver.fixedDrive(0.5, 0.5, -0.5, -0.5);
//		
//	}
//	public void something(){
//		
//	}
	
}
