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
    	
    }

    public void autonomousInit() {   
    	System.out.println("start");
    	leddar.startUp();
    	i=0;
    	j=2;
    	k=0;
    }
    
    //Distance Comparator Variables
    int dist15 = -1;
    int dist0 = -1;
    int i = 0;
    int j = 2;
    // Autonomous Process Variables
    int k = 0; // Sequencer, what step the autonomous process is currently at.
    boolean ninety = false;
    int range = 270;
	int seg1 = 13;
	int seg2 = 2;
	
    public void autonomousPeriodic() {
    	//Distance Comparator
    	for (LeddarDistanceSensor.LeddarDistanceSensorData info : leddar.getDistances()) {
    		if (info.getDistanceInCentimeters() < range) {
    			if (info.getSegmentNumber() == seg1 && i%5 == 0) {
    				dist15 = info.getDistanceInCentimeters();
    				System.out.println("Segment " + seg1 + " = " + info.getDistanceInCentimeters());
    			}
    			if (info.getSegmentNumber() == seg2 && j%5 == 0) {
    				dist0 = info.getDistanceInCentimeters();
    				System.out.println("Segment " + seg2 + " = " + info.getDistanceInCentimeters());
    			}	
    		
    			if (Math.abs(dist15 - dist0) < 5 && dist0 != -1) {
    				System.out.println("90 Degrees baby");
    				ninety = true;
    			}
    			
    			
    			/** Shooting Process Starts Here (May wish to move to Autonomous Init() to clear space/processes for distance comparing. **/
    			
    			else {
    				ninety = false;
    			}
    			
    			// Step 1. Turn towards the left boundary of the court.
    	    	if (k == 0) {
    	    		if (!ninety) {
    					ajoy.turnToWall(true);
    				}
    				else {
    					dT.stop();
    					k = 1;
    				}
    	    		;
    	    	}
    			// Step 2. Move towards the wall, turn towards the north boundary of the court, move towards the next wall.
    			if (k == 1){
    				range = 520;
    				seg1 = 8;
    				seg2 = 7;
    				// is facing left
    				ajoy.moveToDistance(60);
    				// is positioned at left
    				//(placeholder)
    				// turn towards north
    				ajoy.moveToDistance(90);
    				//is positioned at tower
    				k = 2;
    			}
    			// Step 3. Shoot or something kek	
    			if (k == 2){
    			//turn 45 or whatever to goal
    				
    				//shoot
    		    	/** aim at low goal **/
    		    	
    		    	/** drive up and shoot **/
    		    	
    			}   	
    											/** Shooting Process Ends Here **/
    						// Reverse the physical order (not the chronology) of the code (put the if statements in order 2,1,0, so that it refreshes sensor data for each step
    									
    			i++;
    			j++;
      	    
    		} // End of data in range check
    	} // End of for loop
    }// End of Method
    
    @Override
    public void disabledInit() {
    	leddar.shutDown();
    }
    
    public void teleopInit() {
    	System.out.println("\n********** BUTTONS FOR DRIVERS *********");
    	System.out.println("LEFT joystick controls : ");
    	System.out.println("Reverse the Drive Direction: PRESS " + RobotMap.REVERSE_DRIVE_BUTTON + "\n");
    	System.out.println("RIGHT joystick controls : \n" + "PRESS Trigger to suck the ball in; PRESS " + RobotMap.INTAKE_BUTTON + " to push the ball out");
    	System.out.println("Scale Up (Release Scaling): " + RobotMap.SCALING_UPWARDS_BUTTON + "; Reel In Scaling: " + RobotMap.SCALING_DOWNWARDS_BUTTON);
    	
    }
   // int dmu = 0;
    public void teleopPeriodic() {
//    	if (dmu % 3 == 2) {
//    		System.out.println("No pressure dude");
//    	}
//    	else {System.out.println("Don't mess up");}
    	//dmu++;
    	// left in first, right in second ???
    	extr.stopTake();
    	
    	// reverse driveTrain
        if (left.getTrigger()) {
       		dT.driveReversed();
       		System.out.println("Drive train reversed.");
        }
        
        // press trigger
        if (right.getTrigger()) {
        	System.out.println("Intake commence!");
        	extr.inTakeSystem();
        	Timer.delay(0.5);
        	extr.stopTake();
        }
        // TODO change left to shooter
        
        // working as of Feb 8th
        if (right.getRawButton(RobotMap.INTAKE_BUTTON)) { // 5
        	System.out.println("Bye bye bally");
        	extr.outTakeSystem();
        	Timer.delay(.2);
    		extr.pushBall();
    		Timer.delay(.2);
        	extr.stopTake();
        	// testing 
        	extr.pullButNotActuallyPullBall();
        	System.out.println("\nGoal!!");
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
