package org.usfirst.frc.team4330.robot;


// ctrl + shift + o to automatically get imports
import org.usfirst.frc.team4330.robot.canbus.LeddarDistanceSensor;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
// TODO talk to drivers about all buttons needed

/**
 * 2016 final code!!
 * 
 * Author: Banananananananananananananananananananananamanda & sorta Patrick
 * 
 */
public class Robot extends IterativeRobot {
    RobotDrive myRobot;
    DriveTrain dT;
    Extremities extr;
    Joystick left, right, shooter;
    LeddarDistanceSensor leddar;
    AnonymousJoystick ajoy;

    /*
     * 
     */
    public Robot() {
    	dT = new DriveTrain();
    	extr = new Extremities();
    	left = new Joystick(RobotMap.JOYSTICK_ONE_LEFT);
    	right = new Joystick(RobotMap.JOYSTICK_TWO_RIGHT);
    	shooter = new Joystick(RobotMap.JOYSTICK_THREE);
    	leddar = new LeddarDistanceSensor();;
    	
    	System.out.println("right joystick goes in " + RobotMap.JOYSTICK_TWO_RIGHT + "; left goes in " + RobotMap.JOYSTICK_ONE_LEFT);

    }

    public void autonomousInit() {   
    	System.out.println("start");
    	leddar.startUp();
    	i=0;
    	j=2;
    	k=0;
    }
    
    int dist15 = -1;
    int dist0 = -1;
    int i = 0;
    int j = 2;
    int k = 0;
    boolean ninety = false;
    public void autonomousPeriodic() {
    	if (k == 0) {
    	for (LeddarDistanceSensor.LeddarDistanceSensorData info : leddar.getDistances()) {
    		if (info.getDistanceInCentimeters() < 120) {
    			if (info.getSegmentNumber() == 15 && i%5 == 0) {
    				dist15 = info.getDistanceInCentimeters();
    				System.out.println("Segment 15 = " + info.getDistanceInCentimeters());
    			}
    			if (info.getSegmentNumber() == 0 && j%5 == 0) {
    				dist0 = info.getDistanceInCentimeters();
    				System.out.println("Segment 0 = " + info.getDistanceInCentimeters());
    			}
    			if (Math.abs(dist15 - dist0) > 5 && dist0 != -1) {
    				System.out.println("90 Degrees baby");
    				ninety = true;

    			}
    			else {
    				ninety = false;
    			}
    			if (!ninety) {
    				ajoy.turnToWall(true);
    			}
    			else {
    				dT.stop();
    			
    			}
    			
    			i++;
    			j++;
    			k = 1;
    		}
    		ajoy.moveToDistance(1); // change later
    		
    	}
    	k = 1;
    	}
    	// assume we have successfully turned perpendicular(ish) to the wall
    	
    	/** now we move a set distance **/
    	
    	/** turn to face forward **/
    	
    	/** aim at low goal **/
    	
    	/** drive up and shoot **/
    	
    }
    
    @Override
    public void disabledInit() {
    	leddar.shutDown();
    }
    
    public void teleopInit() {
    	System.out.println("\n********** BUTTONS FOR DRIVERS *********");
    	System.out.println("LEFT joystick controls : ");
    	System.out.println("Reverse the Drive Direction: PRESS Trigger" );
    	System.out.println(" ");
    	System.out.println("RIGHT joystick controls : \n" + "PRESS Trigger to suck the ball in; PRESS " + RobotMap.INTAKE_BUTTON + " to push the ball out");
    	System.out.println("Scale Up (Release Scaling): " + RobotMap.SCALING_UPWARDS_BUTTON + "; Real In Scaling: " + RobotMap.SCALING_DOWNWARDS_BUTTON);
    	
    }
    int dmu = 0;
    public void teleopPeriodic() {
    	if (dmu % 3 == 2) {
    		System.out.println("No pressure dude");
    	}
    	else {System.out.println("Don't mess up");}
    	dmu++;
    	// left in first, right in second ???
    	extr.stopTake();
    	
    	// reverse driveTrain
        if (left.getTrigger())
       		dT.driveReversed();
        
        // press trigger
        if (right.getTrigger()) {
        	extr.inTakeSystem();
        	Timer.delay(0.5);
        	extr.stopTake();
        }
        // TODO change left to shooter
        
        // working as of Feb 8th
        if (right.getRawButton(RobotMap.INTAKE_BUTTON)) { // 5
        	extr.outTakeSystem();
        	Timer.delay(.2);
    		extr.pushBall();
    		Timer.delay(.2);
        	extr.stopTake();
        	// testing 
        	extr.pullButNotActuallyPullBall();
        }
        // fail-safe stoppers
        extr.stopTrekudesu();
        
        // hold 4 for reverse trex
        while (left.getRawButton(RobotMap.TREXARM_BACKWARDS_BUTTON)) { // 4
        	extr.runTrekudesuReverse();
        	dT.drive(left, right);
        }
        
        // hold 3 for trex
        while (left.getRawButton(RobotMap.TREXARM_FORWARDS_BUTTON)) { // 3
        	extr.runTrekudesu();
        	dT.drive(left, right);
        }
        
        dT.drive(left, right);
    }
    
    public void testInit() {
    }

    public void testPeriodic() {

    }
    
}
