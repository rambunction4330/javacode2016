package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class RammingSpeed extends Command{
	private DriveTrain dT;
	
	public RammingSpeed(DriveTrain dT) {
		this.dT = dT;
	}

	@Override
	protected void initialize() {
		dT.drive(1, 1);
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
