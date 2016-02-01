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

    }

    public void autonomousInit() {   
    	System.out.println("start");
    	leddar.startUp();
    }
    
	int i = 0;

    public void autonomousPeriodic() {
    	i++;
    	
    	if (i%50 == 0)
    		System.out.println("leddar distances = " + leddar.getDistances());
    }
    
    @Override
    public void disabledInit() {
    	leddar.shutDown();
    }

    public void teleopPeriodic() {
        if (shooter.getRawButton(11))
       		dT.driveReversed();
        if (shooter.getRawButton(12))
        	extr.inOutTake();
                
        while (shooter.getTrigger()) {
        	if (shooter.getRawButton(12))
            	extr.inOutTake();
        	
        	extr.takeSystem();
        	dT.drive(left, right);
        }
        
        dT.drive(left, right);
        
    }

    public void testPeriodic() {
    	
    }
    
}
