package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class RammingSpeed extends DriveStraight{
	
	public RammingSpeed(DriveTrain dT, Gyro gyro, double distance, double heading) {
//		this.dT = dT;
		super(dT, gyro, distance, heading);
	}
	
	public RammingSpeed(DriveTrain dT, Gyro gyro, double distance) {
		super(dT, gyro, distance, 0);
	}

	@Override
	protected double getMotorSpeed() {
		return .9;
	}
	
	protected double getMotorDelta() {
		return .3;
	}
	
	
	
	
}
