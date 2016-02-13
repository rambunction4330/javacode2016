package org.usfirst.frc.team4330.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class ArmWithEncoder {

	private static final double P = 0.5;
	private static final double I = 0;
	private static final double D = 0;
	private boolean isInitialzied;
<<<<<<< HEAD

	private static final double RAISE_POSITION = 3;
	private static final double LOWER_POSITION = 20;

=======
	
	private static final double RAISE_POSITION = 20;
	private static final double LOWER_POSITION = 3;
	
>>>>>>> c4c0f39c7da7d562a3d701b3b9170c9514c9d773
	private Encoder encoder;

	private SpeedController speedController;

	private PIDController positionController;

	private Timer timer = new Timer();

	public ArmWithEncoder() {
		speedController = new Talon(RobotMap.TREXARM_PORT);
		encoder = new Encoder(RobotMap.ARM_ENCODER_PORT_ONE,
				RobotMap.ARM_ENCODER_PORT_TWO);
		positionController = new PIDController(P, I, D, 0, encoder,
				speedController);
		positionController.disable();
		isInitialzied = false;
	}

	public void handleButtons(boolean raiseButton, boolean lowerButton) {
		if (!isInitialzied) {
			return;
		}

		if (lowerButton) {
			lower();
		} else if (raiseButton) {
			raise();
		}

	}
<<<<<<< HEAD

	public void initialize() {
		double speed = -1 * RobotMap.ARM_WHEEL_SPEED;
=======
	
	
	public void initialize () {
		double speed = RobotMap.ARM_WHEEL_SPEED;
>>>>>>> c4c0f39c7da7d562a3d701b3b9170c9514c9d773
		speedController.set(speed);
		timer.schedule(new DetermineIfCalibrationComplete(), 0, 100);
	}

	private void raise() {
		positionController.setSetpoint(RAISE_POSITION);
	}

	private void lower() {
		positionController.setSetpoint(LOWER_POSITION);
	}

	private class DetermineIfCalibrationComplete extends TimerTask {

		private double lastPositionRead = 1000;

		@Override
		public void run() {
			double positionRead = encoder.pidGet();
			System.out.println("Arm position last=" + lastPositionRead
					+ " current=" + positionRead);
			final double angleTolerance = 1;
			if (Math.abs(lastPositionRead - positionRead) < angleTolerance) {
				// we have reached the up position, so set the encoder position
				// to 0 degrees, deenergize the motor,
				// change to pid control of the motor
				encoder.reset();
				speedController.set(0);
				isInitialzied = true;
				positionController.enable();
				raise();
				this.cancel();
				System.out.println("Arm calibration complete");
			} else {
				// keep going
				lastPositionRead = positionRead;
			}
		}

	}

}
