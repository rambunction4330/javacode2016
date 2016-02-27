package org.usfirst.frc.team4330.robot;

import java.util.Timer;
import java.util.TimerTask;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

public class Arm {
	private SpeedController motorController1;
	private SpeedController motorController2;

//	private State armState;
//
//	private static enum State {
//		Ready, Initial, Calibrating;
//	}
//
//	private Encoder encoder;
//
	Timer timer = new Timer();

	private static final double ARM_WHEEL_SPEED = .2;
	private static final double POWER_SPEED = 1.0;

	public Arm() {
		motorController1 = new Victor(RobotMap.TREXARM_PORT_ONE);
		motorController2 = new Victor(RobotMap.TREXARM_PORT_TWO);
//		encoder = new Encoder(RobotMap.ENCODER_PORT_ONE,
//				RobotMap.ENCODER_PORT_TWO);
//
//		armState = State.Initial;
	}

	private void calibrate() {
//		armState = State.Calibrating;

		motorController1.setInverted(false);
		motorController2.setInverted(false);
		motorController1.stopMotor();
		motorController2.stopMotor();

//		encoder.setPIDSourceType(PIDSourceType.kDisplacement);
//		encoder.setDistancePerPulse(360.0 / 250);

		double speed = -.5 * ARM_WHEEL_SPEED;
		motorController1.set(speed);
		motorController2.set(speed);
		
//		timer = new Timer();

//		timer.schedule(new DetermineIfCalibrationComplete(), 200, 200);
	}
/*
	private class DetermineIfCalibrationComplete extends TimerTask {

		// initialize last position read to a random big number
		private double lastPositionRead = Double.MAX_VALUE;

		@Override
		public void run() {
			double currentPositionRead = encoder.pidGet();
			final double angleTolerance = .1;
			if (Math.abs(lastPositionRead - currentPositionRead) < angleTolerance) {
				// we have reached the up position, so set the encoder position
				// to 0 degrees, de-energize the motor, change to pid control of
				// the motor
				encoder.reset();

				armState = State.Ready;

				// cancel this timer task since calibration has been completed
				cancel();
			} else {
				// keep going
				lastPositionRead = currentPositionRead;
			}
		}
	}*/

	public void handleButtons(boolean raiseButton, boolean lowerButton,
			boolean powerButton) {
//		if (armState != State.Ready) {
//			if (armState != State.Calibrating) {
//				calibrate();
//			}
//		} else {

			double speed = 0;

			if (lowerButton && raiseButton)
				System.out.println("CALM DOWN");

			if (lowerButton) {
				speed = ARM_WHEEL_SPEED;
				if (powerButton) {
					speed = POWER_SPEED;
				}
			} else if (raiseButton) {
				speed = -1 * RobotMap.ARM_WHEEL_SPEED;
				if (powerButton )
					speed = -1 * POWER_SPEED;
			}

			motorController1.set(speed);
			motorController2.set(speed);
//		}
	}

	public void autonomousArm(boolean raise, boolean lower, boolean power) {
		if (raise)
			timer.schedule(new autonomousArmRaise(power), 0);
		if (lower)
			timer.schedule(new autonomousArmLower(power), 0);
	}

	private class autonomousArmLower extends TimerTask {
		private boolean power;

		public autonomousArmLower(boolean power) {
			this.power = power;
		}

		@Override
		public void run() {
			try {
				handleButtons(false, true, power);
				Thread.sleep(300);
				handleButtons(false, false, power);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private class autonomousArmRaise extends TimerTask {
		private boolean power;

		public autonomousArmRaise(boolean power) {
			this.power = power;
		}

		@Override
		public void run() {
			try {
				handleButtons(true, false, power);
				Thread.sleep(300);
				handleButtons(false, false, power);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
