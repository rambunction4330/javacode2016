package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
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
	private SpeedController scalar;
	private Encoder notHereYet;
	private PIDController here;
	
	public Extremities() {
		m_intake = new Relay(RobotMap.INTAKE_PORT);
		m_trekudesu = new Talon(RobotMap.TREXARM_PORT);
		spoke = new Relay(RobotMap.SPIKE_PORT);
		scalar = new Talon(RobotMap.SCALAR_PORT);
        notHereYet = new Encoder(RobotMap.ENCODER_PORT_ONE, RobotMap.ENCODER_PORT_TWO, true, CounterBase.EncodingType.k1X);
        here = new PIDController(.125, .001, .0, notHereYet, scalar);
	}
	
	public void inOutTake() {
		take = !take;
	}
	
	/**
	 * Pushes the boulder out of the intake nest
	 * then returns to former position.
	 */
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
	
	public void quickTest() {
		m_intake.setDirection(Direction.kForward);
		spoke.setDirection(Direction.kForward);
		Timer.delay(.5);
		m_intake.stopMotor();;
		spoke.stopMotor();;
	}
}
