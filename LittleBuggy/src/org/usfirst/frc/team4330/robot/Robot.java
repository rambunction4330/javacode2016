package org.usfirst.frc.team4330.robot;


// ctrl + shift + o to automatically get imports
import org.usfirst.frc.team4330.robot.canbus.LeddarDistanceSensor;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
// TODO talk to drivers about all buttons needed
import edu.wpi.first.wpilibj.Timer;

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
    }
    

    public void autonomousPeriodic() {
    	
    	for (LeddarDistanceSensor.LeddarDistanceSensorData info : leddar.getDistances()) {
    		if (info.getDistanceInCentimeters() < 152) {
    			
    			System.out.println("leddar distances = " + info.getDistanceInCentimeters());
    			
    		}
    	}
    }
    
    @Override
    public void disabledInit() {
    	leddar.shutDown();
    }
    
    public void teleopInit() {
    	System.out.println("\n********** BUTTONS FOR DRIVERS *********");
    	System.out.println("Reverse the Drive Direction: " + RobotMap.REVERSE_DRIVE_BUTTON);
    	System.out.println("Suck the Boulder In: " + RobotMap.REVERSE_INTAKE_BUTTON + "; Push Ball Out: " + RobotMap.INTAKE_BUTTON);
    	System.out.println("Scale Up (Release Scaling): " + RobotMap.SCALING_UPWARDS_BUTTON + "; Real In Scaling: " + RobotMap.SCALING_DOWNWARDS_BUTTON);
    	System.out.println();
    }
    
    public void teleopPeriodic() {
    	extr.stopTake();
    	
    	// reverse driveTrain
        if (left.getRawButton(RobotMap.REVERSE_DRIVE_BUTTON)) // 4
       		dT.driveReversed();
        
        // nothing yet
        while (left.getRawButton(RobotMap.REVERSE_INTAKE_BUTTON)) { // 3
        	extr.outTakeSystem();
        }
        // TODO change left to shooter
        
        // pushBall() returns it to original state as well (hopefully)
        while (right.getTrigger()) {
        	extr.inTakeSystem();
        	if (right.getRawButton(RobotMap.INTAKE_BUTTON)) // 5
        		extr.pushBall();
        }
        extr.stopTake();
        
        extr.stopTrekudesu();
        // hold 2 for reverse trex
        while (left.getRawButton(RobotMap.TREXARM_BACKWARDS_BUTTON)) { // 2
        	extr.runTrekudesuReverse();
        	dT.drive(left, right);
        }
        
        // hold trigger for trex
        while (left.getTrigger()) {
        	if (left.getRawButton(4))
//            	extr.takeSystem();
        	
//        	extr.takeSystem();
        	extr.runTrekudesu();
        	dT.drive(left, right);
        }
        
        dT.drive(left, right);
    }
    
    public void testInit() {
    }

    public void testPeriodic() {
    	if (left.getRawButton(4))
    		extr.quickTest();
    }
    
}
