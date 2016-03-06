package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Will apply oscillating power to the drive train but WILL NOT stop drive train after the time expires
 * since we want the client to decide if need to continue with full power or stop
 *
 */
public class OscillatingRammingSpeed extends Command {
	private DriveTrain dT;
	private boolean finished = false;
	private boolean leftFull = true;
	private int counter = 0;
	// 15 means switch every 300 milliseconds
	public static final int counterInterval = 15;
	public static final double delta = 0.2;
	private double runTime;
	
	/**
	 * 
	 * @param dT
	 * @param runTime in seconds
	 */
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
		
		// multiplying by 0.02 because each counter is worth 20 milliseconds of time
		if (counter * 0.02  >= runTime) {
			finished = true;
		} else {
			
			if (counter % counterInterval == 0) {
				leftFull = !leftFull;
			}
			
			double left = 1;
			double right = 1;
			
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
