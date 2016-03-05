package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.Arm;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command {
	
	private Arm arm;
	private boolean raise;
	
	public MoveArm(Arm arm, boolean raise) {
		this.arm = arm;
		this.raise = raise;
	}

	@Override
	protected void initialize() {	
		arm.autonomousArm(raise, !raise, false, 0.3);
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
