
package org.usfirst.frc.team4330.robot;


import edu.wpi.first.wpilibj.*;

/**
 * 2016 final.
 */
public class Robot extends IterativeRobot {
    RobotDrive myRobot;
    DriveTrain driveTrain;
    Joystick stick1, stick2, stick3;

    public Robot() {
    	driveTrain = new DriveTrain();
    	stick1 = new Joystick(Map.JOYSTICK_ONE);
    	stick2 = new Joystick(Map.JOYSTICK_TWO);
    	stick3 = new Joystick(Map.JOYSTICK_THREE);
    }

    public void autonomousInit() {
    }
    
    public void autonomousPeriodic() {
    }

    public void teleopPeriodic() {
        myRobot.setSafetyEnabled(true);
        	if (stick3.getRawButton(4)) {
        		driveTrain.driveReversed();
            }
        	
            driveTrain.drive(stick1, stick2);
            
    }

    public void testPeriodic() {
    	
    }
}
