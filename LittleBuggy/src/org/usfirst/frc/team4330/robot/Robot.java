
package org.usfirst.frc.team4330.robot;


// ctrl + shift + o to automatically get imports
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;


/**
 * 2016 final code!!
 * 
 * Author: Mandie
 * 
 */
public class Robot extends IterativeRobot {
    RobotDrive myRobot;
    DriveTrain dT;
    Joystick left, right, shooter;

    /*
     * 
     */
    public Robot() {
    	dT = new DriveTrain();
    	left = new Joystick(RobotMap.JOYSTICK_ONE_LEFT);
    	right = new Joystick(RobotMap.JOYSTICK_TWO_RIGHT);
    	shooter = new Joystick(RobotMap.JOYSTICK_THREE);
    }

    public void autonomousInit() {
    	dT.drive(left, right);
    }
    
    public void autonomousPeriodic() {
    }

    public void teleopPeriodic() {
//        myRobot.setSafetyEnabled(true);
        if (shooter.getRawButton(11)) {
       		dT.driveReversed();
        }
        	
        dT.drive(left, right);
        
        while (shooter.getTrigger()) {
        	dT.frontMinis();
        	dT.drive(left, right);
        }
    }

    public void testPeriodic() {
    	
    }
}
