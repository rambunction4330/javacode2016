package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Align extends Command {
	private DriveTrain dT;
	private Gyro gyro;
	private double desiredBearing;
	private boolean finished = false;

	private double angleTolerance = .5;

	public Align(DriveTrain dT, Gyro gyro, double desiredAngle) {
		this.dT = dT;
		this.gyro = gyro;
		this.desiredBearing = desiredAngle;
	}

	@Override
	protected void initialize() {
		
	}

	/**
	 * 
	 * @return value between -180 and 180
	 */
	protected double angleCalculator() {
		double raw = getRaw();

		if (raw > 0) {
			raw = raw % 360;

			if (raw > 180) {
				raw -= 360;
			}
		} else {
			raw = raw % 360;

			if (raw < -180) {
				raw += 360;
			}
		}

		return raw;
	}
	
	protected double getRaw() {
		return gyro.getAngle();
	}

	@Override
	protected void execute() {
		double currentBearing = angleCalculator();
		double desiredp = desiredBearing;
		double currentp = currentBearing;
		if (currentp > 0 && desiredp < 0) {
			desiredp = desiredBearing + 360;
		} else if ( currentp < 0 && desiredp > 0 ) {
			if ( currentp > -90 ) {
				desiredp = desiredBearing + 360;
			}
			currentp = currentBearing + 360;
		}
		if (Math.abs(currentp - desiredp) < angleTolerance) {
			dT.drive(0, 0);
			System.out.println("Done.");
			finished = true;
		} else {
			double val = desiredp - currentp;
			boolean turnClockwise = (val > 0 && val < 180);	
			if (!turnClockwise) {
				System.out.println("Turning Left");
				dT.autonomousTurnLeft();
			} else {
				System.out.println("Turning Right");
				dT.autonomousTurnRight();
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
