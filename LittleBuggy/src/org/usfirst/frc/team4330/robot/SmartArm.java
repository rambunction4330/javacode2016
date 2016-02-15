package org.usfirst.frc.team4330.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class SmartArm {

	// TODO tune the arm PID constants input is angular position in degrees and  
	// output is motor speed between -1 and 1
	private static final double P = 0.1;
	private static final double I = 0;
	private static final double D = 0;
	private boolean isInitialzied;

	// degrees with 0 at the raised hard stop and positive direction towards lowering
	private static final double RAISE_POSITION = 20;
	private static final double LOWER_POSITION = 3;

	private Encoder encoder;

	private SpeedController speedController;

	private PIDController positionController;

	private Timer timer = new Timer();


	public SmartArm() {
		speedController = new Talon(RobotMap.TREXARM_PORT);
		
		encoder = new Encoder(RobotMap.ARM_ENCODER_PORT_ONE,
				RobotMap.ARM_ENCODER_PORT_TWO);
		// want the encoder to report in angular position in degrees ( 360 degrees per revolution )
		// and the encoder being used has 250 cycles (1000 ticks) per revolution
		encoder.setPIDSourceType(PIDSourceType.kDisplacement);
		encoder.setDistancePerPulse(360.0 / 250);
		
		// don't enable the PID controller until the encoder has been calibrated
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
		}
		else if (raiseButton)
			raise();
	}

	public void initialize () {
		double speed = RobotMap.ARM_WHEEL_SPEED;
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

		// initialize last position read to a random big number
		private double lastPositionRead = 1000;

		@Override
		public void run() {
			double currentPositionRead = encoder.pidGet();
			System.out.println("Arm position last=" + lastPositionRead
					+ " current=" + currentPositionRead);
			final double angleTolerance = 1;
			if (Math.abs(lastPositionRead - currentPositionRead) < angleTolerance) {
				// we have reached the up position, so set the encoder position
				// to 0 degrees, deenergize the motor,
				// change to pid control of the motor
				encoder.reset();
				speedController.set(0);
				isInitialzied = true;
				positionController.enable();
				raise();
				// cancel this timer task since calibration has been completed
				cancel();
				System.out.println("Arm calibration complete");
			} else {
				// keep going
				lastPositionRead = currentPositionRead;
			}
		}

	}

}
