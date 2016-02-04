package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Extremities {
	private Relay m_intake;
	boolean take;
	private SpeedController m_trexarm;
	private Relay spoke;
	
	public Extremities() {
		m_intake = new Relay(RobotMap.INTAKE_PORT);
		m_trexarm = new Talon(RobotMap.TREXARM_PORT);
		spoke = new Relay(RobotMap.SPIKE_PORT);
	}
	
	public void inOutTake() {
		take = !take;
	}
	
	public void pushBall() {
		if (!take) {
			spoke.set(Value.kForward);
			Timer.delay(0.2);
			spoke.set(Value.kReverse);
			Timer.delay(0.2);
			spoke.stopMotor();
		}
		else return;
	}
	
	/**
	 * This will be the intake system for the robot.
	 * !take means outtake, so shooting the ball outwards.
	 */
	public void takeSystem() {
		if (!take)
			m_intake.setDirection(Direction.kReverse);
		else
			m_intake.setDirection(Direction.kForward);
	}
	
	/**
	 * Runs the Trex Arm down(??)
	 */
	public void runTrexArm() {
		m_trexarm.set(RobotMap.ARM_WHEEL_SPEED);
	}
	
	/**
	 * Runs the Trex Arm up (??)
	 */
	public void runTrexArmReverse() {
		m_trexarm.set(-RobotMap.ARM_WHEEL_SPEED);
	}
	
	/**
	 * Stops the Trex Arm u genius
	 */
	public void stopTrexArm() {
		m_trexarm.set(0);
	}
}
