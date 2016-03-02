package org.usfirst.frc.team4330.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartArm {

	// maybe good
	// TODO raise D to lower overshoot after lifting portcullis
	private static final double P = 0.02; // .003
	private static final double I = 0.00025; // .000125
	private static final double D = 0.08; // 0.0
	private State smartArmState;
	private boolean debugging;

	private static enum State {
		Ready, Initial, Calibrating;
	}

	// degrees with 0 at the raised hard stop and positive direction towards
	// lowering
	private static final double RAISE_POSITION = 30;
	private static final double LOWER_POSITION = 160;

	private double desiredSetpoint = 0;
	private double currentSetpoint = 0;

	private final static double deltaSpeed = 1.2;

	private Encoder encoder;

	private SpeedController speedController1;
	private SpeedController speedController2;

	private MultiplePIDOutputs outputs;

	private PIDController positionController;

	private Timer timer = new Timer();

	public SmartArm() {
		speedController1 = new Victor(RobotMap.TREXARM_PORT_ONE);
		speedController2 = new Victor(RobotMap.TREXARM_PORT_TWO);
		
		encoder = new Encoder(RobotMap.ARM_ENCODER_PORT_ONE,
				RobotMap.ARM_ENCODER_PORT_TWO, true);
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

		speedController1.setInverted(false);
		speedController2.setInverted(false);
		speedController1.stopMotor();
		speedController2.stopMotor();

		outputs = new MultiplePIDOutputs(speedController1, speedController2);

		positionController = new PIDController(P, I, D, 0, encoder, outputs,
				.01);
		// don't enable the PID controller until the encoder has been calibrated
		positionController.reset();

		// want the encoder to report in angular position in degrees ( 360
		// degrees per revolution )
		// and the encoder being used has 250 cycles (1000 ticks) per revolution
		encoder.setPIDSourceType(PIDSourceType.kDisplacement);
		encoder.setDistancePerPulse(360.0 / 250);

		double speed = -1 * RobotMap.ARM_WHEEL_SPEED;
		outputs.pidWrite(speed);

		timer = new Timer();

		if (debugging)
			timer.schedule(new Position(), 0, 10);

		timer.schedule(new DetermineIfCalibrationComplete(), 200, 200);
	}

	public void initialize(boolean debugging) {
		this.debugging = debugging;

		smartArmState = State.Initial;

		// positionController.setAbsoluteTolerance(5);
	}

	public void disable() {
		if (outputs != null) {
			outputs.pidWrite(0);
		}
		if (positionController != null) {
			positionController.disable();
		}
		if (timer != null) {
			timer.cancel();
		}
	}

	public double getPosition() {
		return encoder.pidGet();
	}

	private void raise() {
		// positionController.setSetpoint(RAISE_POSITION);
		desiredSetpoint = RAISE_POSITION;
	}

	private void lower() {
		// positionController.setSetpoint(LOWER_POSITION);
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
				outputs.pidWrite(0);
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
			SmartDashboard.putNumber("amanda motor", speedController1.get());
			SmartDashboard.putNumber("amanda motor 2", speedController2.get());

			// System.out.println("Actual Position is " + encoder.pidGet());
		}
	}

	private class AdjustCurrentSetpoint extends TimerTask {
		@Override
		public void run() {
			double moveDelta = desiredSetpoint - currentSetpoint;
			System.out.println("movedelta: " + moveDelta);
			// if (moveDelta == 0) {
			// return;
			// }

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