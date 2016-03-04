package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command {
	private DriveTrain dT;
	double distanceToDrive;
	double execCounter;
	private double speedActual = 3; // (Physical speed of the robot in feet per second)
	private boolean finished = false;
	private boolean coasting = false;
	private int coastingCounter = 0;

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
		if ( coasting ) {
			coastingCounter--;
			if ( coastingCounter <= 0 ) {
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
				dT.stop();
				System.out.println("coasting");
				coasting = true;
				final double timePerFootCoastingFactor = 0.33;
				coastingCounter = (int) (coastDistance * timePerFootCoastingFactor / 0.02);
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
