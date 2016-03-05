package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class DriveStraight extends Command {
	private DriveTrain dT;
	private Gyro gyro;
	double distanceToDrive;
	double heading;
	double execCounter;
	private double speedActual = 3; // (Physical speed of the robot in feet per second)
	private boolean finished = false;
	private boolean stopping = false;
	private int stoppingCounter = 0;
	private static final double motorSpeed = 0.7;

	public DriveStraight(DriveTrain dT, Gyro gyro, double distanceToDrive, double heading) {
		this.dT = dT;
		this.gyro = gyro;
		this.distanceToDrive = distanceToDrive;
		this.heading = heading;
	}

	@Override
	protected void initialize() {
		execCounter = 0;
		finished = false;
		if (distanceToDrive < 0) {
			dT.drive(-1 * motorSpeed, -1 * motorSpeed);
		} else {
			dT.drive(motorSpeed, motorSpeed);
		}
	}

	@Override
	protected void execute() {
		if ( stopping ) {
			stoppingCounter--;
			if ( stoppingCounter <= 0 ) {
				dT.stop();
				finished = true;
			}
		} else {
			execCounter++;
			// TODO find out distance in 1 second
			/* seconds *//* distance in 1 second */
			double stopDistance = distanceToDrive * .5;
			final double maxStopDistance = 0.5;
			if ( stopDistance > maxStopDistance ) {
				stopDistance = maxStopDistance;
			}
			if ((execCounter * 20 / 1000) * speedActual > distanceToDrive - stopDistance) {
				if ( distanceToDrive > 0 ) {
					dT.drive(-1 * motorSpeed, -1 * motorSpeed);
				} else {
					dT.drive(motorSpeed, motorSpeed);
				}
				System.out.println("stopping");
				stopping = true;
				final double timePerFootStoppingFactor = 0.11;
				stoppingCounter = (int) (stopDistance * timePerFootStoppingFactor / 0.02);
			}
			
			if ( !stopping ) {
				// possibly apply steering correction
				double leftSpeed = motorSpeed;
				double rightSpeed = motorSpeed;
				if ( distanceToDrive < 0 ) {
					leftSpeed = -1 * leftSpeed;
					rightSpeed = -1 * rightSpeed;
				}
				double courseChange = HeadingCalculator.calculateCourseChange(gyro.getAngle(), heading);
				final double motorDelta = 0.1;
				if ( courseChange < -3 ) {
					// steer to the left more
					leftSpeed -= motorDelta;
					rightSpeed += motorDelta;
				} else if ( courseChange > 3 ) {
					// steer to the right more
					leftSpeed += motorDelta;
					rightSpeed -= motorDelta;
				}
				dT.drive(leftSpeed, rightSpeed);
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
