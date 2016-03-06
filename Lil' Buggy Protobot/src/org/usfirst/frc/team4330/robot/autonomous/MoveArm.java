package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.Arm;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command {
	
	public static final double RAISE_TIME = 0.3;
	public static final double LOWER_TIME = 0.25;
	
	private Arm arm;
	private boolean raise;
	
	public MoveArm(Arm arm, boolean raise) {
		this.arm = arm;
		this.raise = raise;
	}

	@Override
	protected void initialize() {	
		if ( raise ) {
			arm.autonomousArm(raise, !raise, false, RAISE_TIME);
		} else {
			arm.autonomousArm(raise, !raise, false, LOWER_TIME);
		}
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}
	
	

}
