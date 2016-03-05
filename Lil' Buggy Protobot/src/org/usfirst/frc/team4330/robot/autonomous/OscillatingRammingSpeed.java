package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class OscillatingRammingSpeed extends Command {
	private DriveTrain dT;
	private boolean finished = false;
	private boolean leftFull = true;
	private int counter = 0;
	private static final int counterInterval = 15;
	private double runTime;
	
	public OscillatingRammingSpeed(DriveTrain dT, double runTime) {
		this.runTime = runTime;
		this.dT = dT;
	}
	
	@Override
	protected void initialize() {
		counter = 0;
		finished = false;
	}

	@Override
	protected void execute() {
		
		if (counter * 0.02  > runTime) {
			finished = true;
		} else {
			
			if (counter % counterInterval == 0) {
				leftFull = !leftFull;
			}
			
			double left = 1;
			double right = 1;
			final double delta = 0.2;
			
			if ( leftFull ) {
				right -= delta;
			} else {
				left -= delta;
			}
			
			dT.drive(left, right);
		}
		
		counter++;
			
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
