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
	private SpeedController m_trekudesu;
	private Relay spoke;
	
	public Extremities() {
		m_intake = new Relay(RobotMap.INTAKE_PORT);
		m_trekudesu = new Talon(RobotMap.TREXARM_PORT);
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
	 * Runs the T-rex Arm down(??)
	 */
	public void runTrekudesu() {
		m_trekudesu.set(RobotMap.ARM_WHEEL_SPEED);
	}
	
	/**
	 * Runs the T-rex Arm up (??)
	 */
	public void runTrekudesuReverse() {
		m_trekudesu.set(-RobotMap.ARM_WHEEL_SPEED);
	}
	
	/**
	 * Stops the T-rex Arm u genius
	 * It's pronounced (tee-reh-ku-desu)
	 */
	public void stopTrekudesu() {
		m_trekudesu.set(0);
	}
}
