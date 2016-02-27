package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

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
		gyro.calibrate();
		sche.add(new WaitCommand(5.0));
		sche.add(new Align(dT, gyro, -180));
//		sche.add(new DriveStraight(dT, 5 * 5 / 3));
	}
	
	public void checkXY() {
		
	}
}
