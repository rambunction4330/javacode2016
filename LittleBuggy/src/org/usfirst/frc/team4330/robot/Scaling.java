package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

public class Scaling {

	private SpeedController moor;
	private Encoder encode;
	private PIDController pid;
	private boolean isUp;
	private boolean hasPulled;
	private boolean buttonSequenceComplete;

	private final static double P = .0125;
	private final static double I = 0;
	private final static double D = 0;

	private final static double goalHeight = 10;

	private boolean lastPressed = false;

	public Scaling(Victor moor) {
		encode = new Encoder(RobotMap.ENCODER_PORT_ONE,
				RobotMap.ENCODER_PORT_TWO);
		encode.setPIDSourceType(PIDSourceType.kDisplacement);
		
		// TODO determine linear distance in inches per degree of rotation
		final double inchesMovementPerDegree = 0.025;
		encode.setDistancePerPulse(inchesMovementPerDegree * 360.0 / 250);
		
		this.moor = moor;
		pid = new PIDController(P, I, D, 0, encode, moor);

		isUp = false;
		hasPulled = false;
		buttonSequenceComplete = false;

		pid.enable();
	}

	public void restart(boolean buttonPressed) {
		if ((!lastPressed && buttonPressed) && (hasPulled || isUp)) {
			// pid.enable();
			pid.setSetpoint(0);
			encode.reset();
			moor.set(0);

			isUp = false;
			hasPulled = false;
		}
		lastPressed = buttonPressed;
	}

	public void pullRobotUp() {
		if (!buttonSequenceComplete && (hasPulled || !isUp)) {
			return;
		} else {
			pid.setPID(P * 3, I, D);
			pid.setSetpoint(0);
			hasPulled = true;
		}
	}

	public void springAnHook() {
		if (!buttonSequenceComplete && (hasPulled || isUp))
			return;

		pid.setPID(P / 3, I, D);
		pid.setSetpoint(goalHeight);
		isUp = true;
	}
	
}
