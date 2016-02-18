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
	}

	public void performIntake(boolean buttonPressed) {
		if (buttonPressed) {
			if (isPerformingIntake || isPerformingShoot)
				return;
			initiateIntakeProcess();
		}
	}
	
	public void test() {
		kicker.set(Value.kForward);
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
		isPerformingShoot = true;
		System.out.println("Bye bye bally");
		spinOut();
		kicker.setDirection(Direction.kBoth);
		timer.schedule(new ShootProcessKickBall(), 300);
		timer.schedule(new ShootProcessComplete(), 400);
		timer.schedule(new ShootProcessReturnKicker(), 500);
		timer.schedule(new ShootProcessComplete(), 600);
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

	private class ShootProcessKickBall extends TimerTask {
		@Override
		public void run() {
//			kicker.setDirection(Direction.kForward);
			System.out.println("Kicking ball forward");
			kicker.set(Value.kForward);
		}
	}

	private class ShootProcessReturnKicker extends TimerTask {
		@Override
		public void run() {
//			System.out.println("Deenergize kicker");
//			kicker.set(Value.kOff);
////			kicker.stopMotor();
//			kicker.setDirection(Direction.kReverse);
			System.out.println("Returning kicker");
			kicker.set(Value.kReverse);
		}
	}

	private class ShootProcessComplete extends TimerTask {

		@Override
		public void run() {
			System.out.println("Stopping blue wheels");
			blueWheels.set(0);

			System.out.println("Deenergizing kicker");
			kicker.set(Value.kOff);
			isPerformingShoot = false;
		}
	}

}
