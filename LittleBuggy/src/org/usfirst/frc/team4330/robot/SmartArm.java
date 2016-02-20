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

	// TODO tune the arm PID constants input is angular position in degrees and
	// output is motor speed between -1 and 1
	private static final double P = 0.016;
	private static final double I = 0;
	private static final double D = 0.0;
	private boolean isInitialized;

	private double zeroposition = 0;

	// degrees with 0 at the raised hard stop and positive direction towards
	// lowering
	private static final double RAISE_POSITION = 30;
	private static final double LOWER_POSITION = 50;

	private Encoder encoder;

	private SpeedController speedController;

	private PIDController positionController;

	private Timer timer = new Timer();

	public SmartArm() {
		speedController = new Talon(RobotMap.TREXARM_PORT);

		encoder = new Encoder(RobotMap.ARM_ENCODER_PORT_ONE,
				RobotMap.ARM_ENCODER_PORT_TWO);
		// want the encoder to report in angular position in degrees ( 360
		// degrees per revolution )
		// and the encoder being used has 250 cycles (1000 ticks) per revolution
		encoder.setPIDSourceType(PIDSourceType.kDisplacement);
		encoder.setDistancePerPulse(1.28 * 360.0 / 250);

		// don't enable the PID controller until the encoder has been calibrated
		positionController = new PIDController(P, I, D, 0, encoder,
				speedController, .01);
		positionController.disable();

		isInitialized = false;
	}

	public void handleButtons(boolean raiseButton, boolean lowerButton) {
		if (!isInitialized)
			return;

		if (lowerButton)
			lower();
		else if (raiseButton)
			raise();
	}

	public void initialize() {
		double speed = -1 * RobotMap.ARM_WHEEL_SPEED;
		speedController.set(speed);
		timer.schedule(new Position(), 0, 10);
		timer.schedule(new DetermineIfCalibrationComplete(), 200, 200);
	}

	public double getPosition() {
		return encoder.pidGet() - zeroposition;
	}

	private void raise() {
		positionController.setSetpoint(RAISE_POSITION + zeroposition);
	}

	private void lower() {
		positionController.setSetpoint(LOWER_POSITION + zeroposition);
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

				zeroposition = encoder.pidGet();
				System.out.println("zeroposition = " + zeroposition);

				isInitialized = true;
				positionController.enable();
				raise();
				// cancel this timer task since calibration has been completed
				cancel();
				System.out.println("Arm calibration complete.");
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
}