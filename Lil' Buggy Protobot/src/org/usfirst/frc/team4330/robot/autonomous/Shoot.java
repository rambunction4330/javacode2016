package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.BallControl;

import edu.wpi.first.wpilibj.command.Command;

public class Shoot extends Command {
	
	private BallControl ballControl;
	
	public Shoot ( BallControl ballControl ) {
		this.ballControl = ballControl;
	}

	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		ballControl.shoot(true);
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
