package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class Stop extends Command {
	
	private DriveTrain dt;
	
	public Stop ( DriveTrain dt) {
		this.dt = dt;
	}

	@Override
	protected void initialize() {
		dt.stop();	
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
