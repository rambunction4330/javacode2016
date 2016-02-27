package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Align extends Command {
	private DriveTrain dT;
	private Gyro gyro;
	private double desiredBearing;
	private double currentBearing;
	private boolean finished = false;

	private static final double SPEED = .8;

	private double angleTolerance = .5;

	public Align(DriveTrain dT, Gyro gyro, double desiredAngle) {
		this.dT = dT;
		this.gyro = gyro;
		this.desiredBearing = desiredAngle;
	}

	@Override
	protected void initialize() {
		currentBearing = angleCalculator();
	}

	private double angleCalculator() {
		double raw = gyro.getAngle();

		if (raw > 0) {
			raw = raw % 360;

			if (raw > 180) {
				raw = 360 - raw;
			}
		} else {
			raw = raw % 360;

			if (raw < -180) {
				raw = 360 + raw;
			}
		}

		return raw;
	}

	@Override
	protected void execute() {
		currentBearing = angleCalculator();
		System.out.println("Angle is " + gyro.getAngle());

		if (Math.abs(currentBearing - desiredBearing) < angleTolerance) {
			dT.drive(0, 0);
			System.out.println("Done.");
			finished = true;
		} else {
			double desiredp = desiredBearing;
			double currentp = currentBearing;
			if (desiredBearing < 0)
				desiredp = desiredBearing + 360;
			if (currentBearing < 0)
				currentp = currentBearing + 360;
			
			double val = desiredp - currentp;
			boolean turnRight = (val > 0 && val < 180);	
			if (!turnRight) {
				System.out.println("Turning left.");
				dT.drive(-SPEED, -SPEED);
			} else {
				System.out.println("Turning right.");
				dT.drive(SPEED, SPEED);
			}
		}
	}

	@Override
	protected boolean isFinished() {
		return finished;
	}

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {

	}

}
