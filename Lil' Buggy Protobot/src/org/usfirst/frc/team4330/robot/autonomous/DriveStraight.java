package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class DriveStraight extends Command {
	
	public static final double speedActual = 5; // (Physical speed of the robot in feet per second)
	public static final double timeToGetToFullSpeed = 0.5;
	public static final double motorSpeed = 0.6;  // base amount of motor speed when driving forward or reverse
	public static final double motorDelta = 0.1;  // amount to add/subtract to steer robot to maintain heading
	public static final double headingTolerance = .5;  // in degrees
	
	public static final double stopTimeFactor = 0.05;  // distance to drive * this factor = stopping time in seconds
	public static final double maxStopTime = 0.2;  // cap of stopping time in seconds
	
	private DriveTrain dT;
	private Gyro gyro;
	double distanceToDrive;
	double distanceTravelled = 0;
	double heading;
	double execCounter;
	private boolean finished = false;
	private boolean stopping = false;
	private int stoppingCounter = 0;
	private double speed = 0;

	public DriveStraight(DriveTrain dT, Gyro gyro, double distanceToDrive, double heading) {
		this.dT = dT;
		this.gyro = gyro;
		this.distanceToDrive = distanceToDrive;
		this.heading = heading;
		speed = 0;
	}

	protected double getMotorSpeed() {
		return motorSpeed;
	}
	
	protected double getMotorDelta() {
		return motorDelta;
	}
	
	@Override
	protected void initialize() {
		execCounter = 0;
		finished = false;
		speed = 0;
		distanceTravelled = 0;
	}

	@Override
	protected void execute() {
		if ( stopping ) {
			if ( stoppingCounter <= 0 ) {
				dT.stop();
				finished = true;
			}
			stoppingCounter--;
		} else {
			double stopTime = Math.abs(distanceToDrive) * stopTimeFactor;
			if ( stopTime > maxStopTime ) {
				stopTime = maxStopTime;
			}
			updateSpeedAndDistanceTravelled();
			// multiplying by 0.02 because each counter value represents 20 milliseconds of time
			if (distanceTravelled >= Math.abs(distanceToDrive)) {
				if ( distanceToDrive > 0 ) {
					dT.drive(-1 * getMotorSpeed(), -1 * getMotorSpeed());
				} else {
					dT.drive(getMotorSpeed(), getMotorSpeed());
				}
				System.out.println("stopping");
				stopping = true;
				// multiplying by 50 because need 50 counter steps per second
				stoppingCounter = (int) (stopTime * 50 );
			}
			
			if ( !stopping ) {
				// possibly apply steering correction
				double leftSpeed = getMotorSpeed();
				double rightSpeed = getMotorSpeed();
				if ( distanceToDrive < 0 ) {
					leftSpeed = -1 * leftSpeed;
					rightSpeed = -1 * rightSpeed;
				}
				double courseChange = HeadingCalculator.calculateCourseChange(gyro.getAngle(), heading);
				if ( courseChange < -1 * headingTolerance ) {
					// steer to the left more
					leftSpeed -= getMotorDelta();
					rightSpeed += getMotorDelta();
				} else if ( courseChange > headingTolerance ) {
					// steer to the right more
					leftSpeed += getMotorDelta();
					rightSpeed -= getMotorDelta();
				}
				dT.drive(leftSpeed, rightSpeed);
			}
			execCounter++;
		}
	}
	
	private void updateSpeedAndDistanceTravelled() {
		if ( speed < speedActual ) {
			speed += (0.02 * speedActual) / timeToGetToFullSpeed;
		}
		distanceTravelled += 0.02 * speed;
	}
	
	private double getSpeed() {
		return speed;
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
