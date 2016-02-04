// TODO deploy to robot;;

package org.usfirst.frc.team4330.robot;


// ctrl + shift + o to automatically get imports
import org.usfirst.frc.team4330.robot.canbus.LeddarDistanceSensor;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
// TODO one button to unwind spool & one to rewind
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

    /*
     * 
     */
    public Robot() {
    	dT = new DriveTrain();
    	extr = new Extremities();
    	left = new Joystick(RobotMap.JOYSTICK_ONE_LEFT);
    	right = new Joystick(RobotMap.JOYSTICK_TWO_RIGHT);
    	shooter = new Joystick(RobotMap.JOYSTICK_THREE);
    	leddar = new LeddarDistanceSensor();
    	
    	System.out.println("right joystick goes in " + RobotMap.JOYSTICK_TWO_RIGHT + "; left goes in " + RobotMap.JOYSTICK_ONE_LEFT);

    }

    public void autonomousInit() {   
    	System.out.println("start");
    	leddar.startUp();
    }
    
	int i = 0;

    public void autonomousPeriodic() {
    	i++;
    	
    	for (LeddarDistanceSensor.LeddarDistanceSensorData info : leddar.getDistances()) {
    		if (info.getSegmentNumber() == 0) {
    			
//        		System.out.println("leddar distances = " + info);
    			
    		}
    	}
    }
    
    @Override
    public void disabledInit() {
    	leddar.shutDown();
    }

    public void teleopPeriodic() {
        if (left.getRawButton(RobotMap.REVERSE_DRIVE_BUTTON))
       		dT.driveReversed();
        if (left.getRawButton(RobotMap.REVERSE_INTAKE_BUTTON))
        	extr.inOutTake();
        // TODO change left to shooter
        
        // pushBall() returns it to original state as well(hopefully)
        if (left.getRawButton(RobotMap.INTAKE_BUTTON)) {
        	extr.take = false;
        	extr.pushBall();
        	extr.takeSystem();
        }
        
        extr.stopTrexArm();
        
        while (left.getRawButton(RobotMap.TREXARM_BACKWARDS_BUTTON)) {
        	extr.runTrexArmReverse();
        	dT.drive(left, right);
        }
                
        while (left.getTrigger()) {
        	if (left.getRawButton(4))
            	extr.inOutTake();
        	
//        	extr.takeSystem();
        	extr.runTrexArm();
        	dT.drive(left, right);
        }
        
        dT.drive(left, right);
    }
    
    public void testInit() {
    	
    }

    public void testPeriodic() {
    	
    }
    
}
