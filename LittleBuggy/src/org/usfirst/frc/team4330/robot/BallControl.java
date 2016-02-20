package org.usfirst.frc.team4330.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.SpeedController;

/*
 * confirmed working as of 2/18
 */
public class BallControl {

	private boolean isPerformingIntake;
	private boolean isPerformingShoot;
	private SpeedController blueWheels;
	private Relay kicker;
	private Timer timer = new Timer();

	private static final double inSpeed = -1;
	private static final double outSpeed = .5;

	public BallControl(SpeedController blueWheelsController, Relay kickerRelay) {
		this.blueWheels = blueWheelsController;
		this.kicker = kickerRelay;
		isPerformingIntake = false;
		isPerformingShoot = false;
	}

	public void performIntake(boolean buttonPressed) {
		if (buttonPressed) {
			if (isPerformingIntake || isPerformingShoot)
				return;
			initiateIntakeProcess();
		}
	}

	public void test() {
		kicker.set(Value.kReverse);
	}

	public void shoot(boolean buttonPressed) {
		if (buttonPressed) {
			if (isPerformingIntake || isPerformingShoot)
				return;

			initiateShootProcess();
		}
	}

	private void initiateIntakeProcess() {
		isPerformingIntake = true;
		System.out.println("\nIntake commence!");
		spinIn();
		timer.schedule(new IntakeProcessComplete(), 2000);
	}

	private void initiateShootProcess() {

		Thread shootThread = new Thread() {

			@Override
			public void run() {
				try {
					isPerformingShoot = true;

					// let blue wheels spin up
					System.out.println("Bye bye bally "
							+ System.currentTimeMillis());
					spinOut();
					Thread.sleep(600);

					// kick ball
					System.out.println("Kicking ball forward "
							+ System.currentTimeMillis());
					kicker.set(Value.kForward);

					Thread.sleep(120);
					// deenergize the relay
					kicker.stopMotor();

					Thread.sleep(100);

					kicker.set(Value.kReverse);

					Thread.sleep(20);

					// denergize the relay
					kicker.stopMotor();
					System.out.println("Stopping blue wheels"
							+ System.currentTimeMillis());
					blueWheels.set(0);

					isPerformingShoot = false;

				} catch (Exception e) {
					System.err.println("Exception " + e.getMessage());
				}
			}

		};
		shootThread.start();

	}

	// spin the blue wheels so the ball is pulled in
	private void spinIn() {
		blueWheels.set(inSpeed);
	}

	// spin the blue wheels so the ball is pushed out
	private void spinOut() {
		blueWheels.set(outSpeed);
	}

	private class IntakeProcessComplete extends TimerTask {

		@Override
		public void run() {
			System.out.println("Stopping blue wheels");
			blueWheels.set(0);
			isPerformingIntake = false;
		}
	}

}
