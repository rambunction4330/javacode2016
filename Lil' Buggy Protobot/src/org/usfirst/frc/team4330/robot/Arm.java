package org.usfirst.frc.team4330.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

public class Arm {
	private SpeedController motorController1;
	private SpeedController motorController2;

	private static Arm instance;

	private Timer timer;

	private static final double ARM_WHEEL_SPEED = .3;
	private static final double POWER_SPEED = 1.0;

	public static Arm getInstance() {
		/*if (instance == null)
			instance = new Arm();
		return instance;*/

		return instance == null ? instance = new Arm() : instance;
	}

	private Arm() {
		instance = this;

		motorController1 = new Victor(RobotMap.TREXARM_PORT_ONE);
		motorController2 = new Victor(RobotMap.TREXARM_PORT_TWO);

		timer = new Timer();
	}

	public void handleButtons(boolean raiseButton, boolean lowerButton,
			boolean powerButton) {
		double speed = 0;

		if (lowerButton && raiseButton)
			System.out.println("CALM DOWN");

		if (lowerButton) {
			speed = ARM_WHEEL_SPEED;
			if (powerButton) {
				speed = POWER_SPEED;
			}
		} else if (raiseButton) {
			speed = -1 * RobotMap.ARM_WHEEL_SPEED * 1.15;
			if (powerButton)
				speed = -1 * POWER_SPEED;
		}

		motorController1.set(speed);
		motorController2.set(speed);

		// SmartDashboard.putDouble("arm 1", motorController1.get());
		// SmartDashboard.putDouble("arm 2", motorController2.get());
	}

	public void autonomousArm(boolean raise, boolean lower, boolean power,
			double time) {
		if (raise)
			timer.schedule(new AutonomousArmRaise(power, time), 0);
		if (lower)
			timer.schedule(new AutonomousArmLower(power, time), 0);
	}

	private class AutonomousArmLower extends TimerTask {
		private boolean power;
		private double time;

		public AutonomousArmLower(boolean power, double time) {
			this.power = power;
			this.time = time;
		}

		@Override
		public void run() {
			try {
				handleButtons(false, true, power);
				Thread.sleep((long) (time * 1000));
				handleButtons(false, false, power);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private class AutonomousArmRaise extends TimerTask {
		private boolean power;
		private double time;

		public AutonomousArmRaise(boolean power, double time) {
			this.power = power;
			this.time = time;
		}

		@Override
		public void run() {
			try {
				handleButtons(true, false, power);
				Thread.sleep((long) (time * 1000));
				handleButtons(false, false, power);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
