package org.usfirst.frc.team4330.robot.autonomous;

import edu.wpi.first.wpilibj.command.Command;

public class CallbackToManager extends Command {
	
	private Manager manager;
	
	public CallbackToManager(Manager manager) {
		this.manager = manager;
	}

	@Override
	protected void execute() {
		manager.callback();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {	
	}
	
	

}
