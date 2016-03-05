package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class FineAlign extends RoughAlign {
	
	public FineAlign(DriveTrain driveTrain, Gyro gyro, double desiredAngle) {
		super(driveTrain, gyro, desiredAngle);
	}

	@Override
	protected double getTolerence() {
		return 2;
	}

	@Override
	protected void turnLeft() {
		dT.autonomousTurnLeftSlow();
	}

	@Override
	protected void turnRight() {
		dT.autonomousTurnRightSlow();
	}
	
	

}
