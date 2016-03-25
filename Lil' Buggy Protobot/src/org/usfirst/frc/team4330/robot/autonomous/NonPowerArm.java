package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.Arm;

import edu.wpi.first.wpilibj.command.Command;

public class NonPowerArm extends Command {
	
	private Arm arm;
	private boolean raise;
	private double time;
	
	public NonPowerArm (Arm arm, boolean raise, double time) {
		this.arm = arm;
		this.raise = raise;
		this.time = time;
	}

	@Override
	protected void initialize() {
		arm.autonomousArm(raise, !raise, false, time);
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
