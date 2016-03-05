package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command {
	private DriveTrain dT;
	double distanceToDrive;
	double execCounter;
	private double speedActual = 3; // (Physical speed of the robot in feet per second)
	private boolean finished = false;
	private boolean stopping = false;
	private int stoppingCounter = 0;

	public DriveStraight(DriveTrain dT, double distanceToDrive) {
		this.dT = dT;
		this.distanceToDrive = distanceToDrive;
	}

	@Override
	protected void initialize() {
		execCounter = 0;
		finished = false;
		if (distanceToDrive < 0) {
			dT.autonomousDriveReverse();
		} else {
			dT.autonomousDriveForward();
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
			double coastDistance = distanceToDrive * .5;
			final double maxCoastDistance = 3;
			if ( coastDistance > maxCoastDistance ) {
				coastDistance = maxCoastDistance;
			}
			if ((execCounter * 20 / 1000) * speedActual > distanceToDrive - coastDistance) {
				if ( distanceToDrive > 0 ) {
					dT.autonomousDriveReverse();
				} else {
					dT.autonomousDriveForward();
				}
				System.out.println("stopping");
				stopping = true;
				final double timePerFootStoppingFactor = 0.11;
				stoppingCounter = (int) (coastDistance * timePerFootStoppingFactor / 0.02);
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
