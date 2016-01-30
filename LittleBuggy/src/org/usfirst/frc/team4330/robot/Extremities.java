package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;

public class Extremities {
	private Relay intake;
	private boolean take;
	
	public Extremities() {
		intake = new Relay(RobotMap.INTAKE_PORT);
	}
	
	public void inOutTake() {
		take = !take;
	}
	
	/**
	 * This will be the intake system for the robot.
	 * !take means outtake, so shooting the ball outwards.
	 */
	public void takeSystem() {
		if (!take)
			intake.setDirection(Direction.kReverse);
		else
			intake.setDirection(Direction.kForward);
	}
}
