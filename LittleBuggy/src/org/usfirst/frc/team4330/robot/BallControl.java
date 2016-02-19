package org.usfirst.frc.team4330.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.SpeedController;

public class BallControl {

	private boolean isPerformingIntake;
	private boolean isPerformingShoot;
	private SpeedController blueWheels;
	private Relay kicker;
	private Timer timer = new Timer();

	public BallControl(SpeedController blueWheelsController, Relay kickerRelay) {
		this.blueWheels = blueWheelsController;
		this.kicker = kickerRelay;
		isPerformingIntake = false;
		isPerformingShoot = false;
		kicker.setDirection(Direction.kBoth);
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
		timer.schedule(new IntakeProcessComplete(), 500);
	}

	private void initiateShootProcess() {
		
		Thread shootThread = new Thread() {

			@Override
			public void run() {
				try {
					isPerformingShoot = true;
					
					// let blue wheels spin up
					System.out.println("Bye bye bally " + System.currentTimeMillis());
					spinOut();
					Thread.sleep(300);
					
					// kick ball
					System.out.println("Kicking ball forward " + System.currentTimeMillis());
					kicker.set(Value.kForward);
					
					Thread.sleep(100);
					// deenergize the relay
					kicker.stopMotor();
					
					
					Thread.sleep(400);
					System.out.println("Stopping blue wheels" + System.currentTimeMillis());
					blueWheels.set(0);
					
					kicker.set(Value.kReverse);
					Thread.sleep(100);
					
					// denergize the relay
					kicker.stopMotor();
					
					isPerformingShoot = false;
					
					
				} catch ( Exception e ) {
					System.err.println("Exception " + e.getMessage());
				}
			}
			
		};
		shootThread.start();
		
	}

	// spin the blue wheels so the ball is pulled in
	private void spinIn() {
		blueWheels.set(-RobotMap.INTAKE_SPEED);
	}

	// spin the blue wheels so the ball is pushed out
	private void spinOut() {
		blueWheels.set(RobotMap.INTAKE_SPEED);
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
