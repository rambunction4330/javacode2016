package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;
import org.usfirst.frc.team4330.robot.Robot;

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Manager {
	Scheduler sche;
	private DriveTrain dT;
	private Gyro gyro;
	

	public Manager(DriveTrain dT, Gyro gyro) {
		this.dT = dT;
		sche = Scheduler.getInstance();
		this.gyro = gyro;
		
	}

	public void runSchedule() {
		sche.run();
	}
	
	public void initialize() {
		// TODO set gyro position to 0 at beginning
		/*if (Robot.autoDefense.equals("lowBar")) {
			sche.add(new DriveStraight(dT, 8));
		}
		if (Robot.autoDefense.equals("roughTerrain")) {
			sche.add(new DriveStraight(dT, 1));
			sche.add(new Align(dT, gyro, 30));
			sche.add(new DriveStraight(dT, 1));
			sche.add(new Align(dT, gyro, -30));
			sche.add(new DriveStraight(dT, 3)); // 30 degrees at 3 = 3sqrt(3)/2 total = 8?
			sche.add(new Align(dT, gyro, 0));
		}*/
		
//		 testing stuff
	 	gyro.calibrate();
		sche.add(new WaitCommand(5.0));
		sche.add(new Align(dT, gyro, -180));
		sche.add(new DriveStraight(dT, 5));
		sche.add(new Align(dT, gyro, 180));
		sche.add(new DriveStraight(dT, 5));
		
//		sche.add(new DriveStraight(dT, 5));
//		sche.add(new Align(dT, gyro, 0));
		}
	
}
