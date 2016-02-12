package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;
import java.util.Timer;
import java.util.TimerTask;

public class BallControl {
	
	// the time that the blue wheels will spin in during the intake process
	private static int SPIN_IN_DELAY = 500;
	
	private boolean isPerformingIntake;
	
	private SpeedController blueWheels;
	
	private Relay kicker;
	
	private Timer timer = new Timer();
	
	public BallControl ( SpeedController blueWheelsController, Relay kickerRelay ) {
		blueWheels = blueWheelsController;
		kicker = kickerRelay;
		isPerformingIntake = false;
	}
	
	public void performIntake() {
		if ( isPerformingIntake ) {
			return;
		}
		initiateIntakeProcess();
	}
	
	public void shoot() {
//		System.out.println("Bye bye bally");
//		extr.outTakeSystem();
//		Timer.delay(.2);
//		extr.pushBall();
//		Timer.delay(.2);
//		extr.stopTake();
//		// testing 
//		extr.pullButNotActuallyPullBall();
//		System.out.println("\nGoal!!");
	}
	
	private void initiateIntakeProcess() {
		isPerformingIntake = true;
		System.out.println("\nIntake commence!");
		spinIn();
		timer.schedule(new CompleteIntakeProcess(), SPIN_IN_DELAY);
	}
	
	// spin the blue wheels so the ball is pulled in
	private void spinIn( ) {
		blueWheels.set(-RobotMap.INTAKE_SPEED);
	}
	
	// spin the blue wheels so the ball is pushed out
	private void spinOut() {
		blueWheels.set(RobotMap.INTAKE_SPEED);
	}
	
	private class CompleteIntakeProcess extends TimerTask {

		@Override
		public void run() {
			System.out.println("Stopping blue wheels");
			blueWheels.set(0);
			isPerformingIntake = false;
		}
		
	}
	

}
