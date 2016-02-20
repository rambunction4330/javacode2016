package org.usfirst.frc.team4330.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartArm {

	// maybe good
	private static final double P = 0.02; // .003
	private static final double I = 0.00025; // .000125
	private static final double D = 0.0; // 0.0
	private State smartArmState;
	private boolean debugging;

	private static enum State {
		Ready, Initial, Calibrating;
	}

	// degrees with 0 at the raised hard stop and positive direction towards
	// lowering
	private static final double RAISE_POSITION = 70;
	private static final double LOWER_POSITION = 200; // 192

	private double desiredSetpoint = 0;
	private double currentSetpoint = 0;

	private final static double deltaSpeed = 1.2;

	private Encoder encoder;

	private SpeedController speedController;

	private PIDController positionController;

	private Timer timer = new Timer();

	public SmartArm() {
		speedController = new Talon(RobotMap.TREXARM_PORT);

		encoder = new Encoder(RobotMap.ARM_ENCODER_PORT_ONE,
				RobotMap.ARM_ENCODER_PORT_TWO);

		positionController = new PIDController(P, I, D, 0, encoder,
				speedController, .01);
	}

	public void handleButtons(boolean raiseButton, boolean lowerButton) {
		if (smartArmState != State.Ready) {
			if (smartArmState != State.Calibrating) {
				calibrate();
			}
		} else {
			if (lowerButton)
				lower();
			else if (raiseButton)
				raise();

		}
	}

	private void calibrate() {
		smartArmState = State.Calibrating;
		double speed = -1 * RobotMap.ARM_WHEEL_SPEED;
		speedController.set(speed);

		if (debugging)
			timer.schedule(new Position(), 0, 10);

		timer.schedule(new DetermineIfCalibrationComplete(), 200, 200);
	}

	public void initialize(boolean debugging) {
		this.debugging = debugging;

		speedController.setInverted(false);

		smartArmState = State.Initial;
		timer = new Timer();

		// don't enable the PID controller until the encoder has been calibrated
		positionController.disable();
		// positionController.setAbsoluteTolerance(5);

		// want the encoder to report in angular position in degrees ( 360
		// degrees per revolution )
		// and the encoder being used has 250 cycles (1000 ticks) per revolution
		encoder.setPIDSourceType(PIDSourceType.kDisplacement);
		encoder.setDistancePerPulse(1.28 * 360.0 / 250);
	}

	public void disable() {
		speedController.stopMotor();
		positionController.disable();
		timer.cancel();
	}

	public double getPosition() {
		return encoder.pidGet();
	}

	private void raise() {
//		positionController.setSetpoint(RAISE_POSITION);
		desiredSetpoint = RAISE_POSITION;
	}

	private void lower() {
//		positionController.setSetpoint(LOWER_POSITION);
		desiredSetpoint = LOWER_POSITION;
	}

	private class DetermineIfCalibrationComplete extends TimerTask {

		// initialize last position read to a random big number
		private double lastPositionRead = Double.MAX_VALUE;

		@Override
		public void run() {
			double currentPositionRead = encoder.pidGet();
			System.out.println("Arm position last=" + lastPositionRead
					+ " current=" + currentPositionRead);
			final double angleTolerance = .1;
			System.out.println("Difference is "
					+ Math.abs(lastPositionRead - currentPositionRead)
					+ "; current time: " + System.currentTimeMillis());
			if (Math.abs(lastPositionRead - currentPositionRead) < angleTolerance) {
				// we have reached the up position, so set the encoder position
				// to 0 degrees, deenergize the motor, change to pid control of
				// the motor
				speedController.set(0);
				encoder.reset();

				smartArmState = State.Ready;

				// cancel this timer task since calibration has been completed
				cancel();
				System.out.println("Arm calibration complete.");
				
				positionController.enable();
				raise();
				timer.schedule(new AdjustCurrentSetpoint(), 10, 10);
			} else {
				// keep going
				lastPositionRead = currentPositionRead;
			}
		}
	}

	private class Position extends TimerTask {

		@Override
		public void run() {
			encoder.pidGet();

			SmartDashboard.putNumber("amanda encoder", encoder.pidGet());
			SmartDashboard.putNumber("amanda motor", speedController.get());
			// System.out.println("Actual Position is " + encoder.pidGet());
		}
	}

	private class AdjustCurrentSetpoint extends TimerTask {
		@Override
		public void run() {
			double moveDelta = desiredSetpoint - currentSetpoint;
			System.out.println("movedelta: " + moveDelta);
//			if (moveDelta == 0) {
//				return;
//			}

			boolean needToMoveUp = moveDelta > 0;
			if (needToMoveUp) {
				// want to forward
				if (moveDelta > deltaSpeed) {
					currentSetpoint += deltaSpeed;
				} else {
					currentSetpoint = desiredSetpoint;
				}
			} else {
				// want to backwards
				if (moveDelta < -1 * deltaSpeed) {
					currentSetpoint -= deltaSpeed;
				} else {
					currentSetpoint = desiredSetpoint;
				}
			}
			
			System.out.println("Current Setpoint is " + currentSetpoint);
			SmartDashboard.putNumber("pid current Setpoint", currentSetpoint);
			SmartDashboard.putNumber("pid desired Setpoint", desiredSetpoint);

			positionController.setSetpoint(currentSetpoint);
		}
	}
}