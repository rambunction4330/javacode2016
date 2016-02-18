package org.usfirst.frc.team4330.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;

public class Scaling {
	
	private boolean isInitialized;

	private SpeedController motor;
	private Encoder encoder;
	private PIDController pid;

	private final static double P = .1;
	private final static double I = 0;
	private final static double D = 0;

	private final static int stowedPosition = 10;
	private final static int hookPosition = 720;
	private final static int robotRaisedPosition = 360;
	private final static double calibrationMotorSpeed = -0.05;
	
	private int desiredSetpoint = stowedPosition;
	private int currentSetpoint = stowedPosition;
	
	private final static int upDelta = 9;
	private final static int downDelta = -18;

	private double speedsensitivity;
	private Timer timer = new Timer();

	/**
	 * Coordinate system has zero height at completely lowered scaler position. Positive is pointing up.
	 * Motor speed is positive in up direction.  Encoder is reading in degrees of rotation from the zero position.
	 * @param motor
	 * @param speedsensitivity
	 */
	public Scaling(SpeedController motor, double speedsensitivity) {
		isInitialized = false;
		encoder = new Encoder(RobotMap.ENCODER_PORT_ONE,
				RobotMap.ENCODER_PORT_TWO);
		encoder.setPIDSourceType(PIDSourceType.kDisplacement);
		encoder.setDistancePerPulse(360.0 / 250);
		
		this.motor = motor;
		this.speedsensitivity = Math.abs(speedsensitivity * RobotMap.SCALING_MAXIMUM);
		pid = new PIDController(P, I, D, 0, encoder, motor);
		pid.setOutputRange(0, this.speedsensitivity);

		pid.disable();
	}
	
	public double getPosition() {
		return encoder.pidGet();
	}

	public void setSpeedSensitivity(double speedsenstivity) {
		this.speedsensitivity = speedsenstivity * RobotMap.SCALING_MAXIMUM;
		pid.setOutputRange(0, speedsensitivity);
	}
	
	public void handleButtons(boolean hookButton, boolean robotRaisedButton, boolean stowButton) {
		if ( !isInitialized ) {
			return;
		}
		
		if ( hookButton ) {
			// TODO add safety for last 20 seconds of teleop
			desiredSetpoint = hookPosition;
		} else if ( robotRaisedButton ) {
			// TODO add safety for last 20 seconds of teleop
			desiredSetpoint = robotRaisedPosition;
		} else if ( stowButton ) {
			desiredSetpoint = stowedPosition;
		}

	}
	
	public void initialize () {
		motor.set(calibrationMotorSpeed);
		timer.schedule(new DetermineIfCalibrationComplete(), 0, 100);
	}
	
	private class DetermineIfCalibrationComplete extends TimerTask {

		// initialize last position read to a random big number
		private double lastPositionRead = Double.MAX_VALUE;

		@Override
		public void run() {
			double currentPositionRead = encoder.pidGet();
			System.out.println("Scaler position last=" + lastPositionRead
					+ " current=" + currentPositionRead);
			final double angleTolerance = 2;
			if (Math.abs(lastPositionRead - currentPositionRead) < angleTolerance) {
				// we have reached the down position, so set the encoder position
				// to 0 degrees, deenergize the motor,
				// change to pid control of the motor
				encoder.reset();
				motor.set(0);
			
				pid.setSetpoint(currentSetpoint);
				pid.enable();
				
				isInitialized = true;
				
				// cancel this timer task since calibration has been completed
				cancel();
				System.out.println("Scaling calibration complete");
				
				timer.schedule(new AdjustCurrentSetpoint(), 1, 50);
				System.out.println("Scaling timer task scheduled");
			} else {
				// keep going
				lastPositionRead = currentPositionRead;
			}
		}
	}
	
	private class AdjustCurrentSetpoint extends TimerTask {
		@Override
		public void run() {
			
			int moveDelta = desiredSetpoint - currentSetpoint;
			if ( moveDelta == 0 ) {
				return;
			}
			
			boolean needToMoveUp = moveDelta > 0;
			if ( needToMoveUp ) {
				// want to move up
				if ( moveDelta > upDelta ) {
					currentSetpoint += upDelta;
				} else {
					currentSetpoint = desiredSetpoint;
				}
			} else {
				// want to move down
				if ( moveDelta < downDelta ) {
					currentSetpoint += downDelta;
				} else {
					currentSetpoint = desiredSetpoint;
				}
			}
			
			pid.setSetpoint(currentSetpoint);
		}
	}
	
}
