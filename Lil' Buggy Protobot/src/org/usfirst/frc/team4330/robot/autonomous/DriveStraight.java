package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command {
	private DriveTrain dT;
	double distanceToDrive;
	double execCounter;
	private double speed = .5;
	private double speedActual = 3; // (Physical speed of the robot in feet per
									// second)
	private boolean finished = false;

	public DriveStraight(DriveTrain dT, double distanceToDrive) {
		this.dT = dT;
		this.distanceToDrive = distanceToDrive;
	}

	@Override
	protected void initialize() {
		execCounter = 0;
		finished = false;
		if (distanceToDrive < 0) {
			dT.drive(-1 * speed, -1 * speed);
		} else {
			dT.drive(speed, speed);
		}
	}

	@Override
	protected void execute() {
		execCounter++;
		// TODO find out distance in 1 second
		/* seconds *//* distance in 1 second */
		if ((execCounter * 20 / 1000) * speedActual > distanceToDrive) {
			System.out.println((execCounter * 20 / 1000) * speedActual);
			finished = true;
			dT.drive(0, 0);
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
