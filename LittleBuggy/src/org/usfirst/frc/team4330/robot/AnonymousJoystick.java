package org.usfirst.frc.team4330.robot;

import org.usfirst.frc.team4330.robot.canbus.LeddarDistanceSensor;

import edu.wpi.first.wpilibj.GenericHID;

public abstract class AnonymousJoystick extends GenericHID {
	
	
	// left is the true direction
	private boolean direction;
	private LeddarDistanceSensor leddar;
	private DriveTrain driver;
	
	public AnonymousJoystick (boolean direction) {
		this.direction = direction;
	}
	
	public void turnToWall(){
		// turn about 90ish degrees left or right depending on the direction given.
		if (direction){// left
	    	for (LeddarDistanceSensor.LeddarDistanceSensorData info : leddar.getDistances()){
	    		
	    	}
		}
		
		else {
			
		}
	}
	public void aligntoLR(){
		if (direction) { // left
			
		}
		
		else {
			
		}
	}
	public void moveToDistance(double distance){
		
		= leddar.getDistances();
		if (distance > leddar.getDistances())
			info.getDistances();
		while ( )
				Dt.set(0.5);
	}
	public void something(){
		
	}
	
}
