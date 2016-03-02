package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;

public class Scaling {
	SpeedController motorController;
	
	private final static double LOW_POWER = .5;
	private final static double HIGH_POWER = 1.0;

	public Scaling(SpeedController motor) {
		this.motorController = motor;
	}
	
	public void handleButtons(boolean springUpButton, boolean liftRobotButton,
			boolean powerButton, int matchlimit) {
		if (Timer.getMatchTime() > (150 - matchlimit))
			return;
		
		double speed = 0;

		if (liftRobotButton && springUpButton)
			System.out.println("CALM DOWN");

		if (springUpButton) {
			speed = LOW_POWER;
			if (powerButton)
				speed = HIGH_POWER;
		} else if (liftRobotButton) {
			speed = -1 * LOW_POWER;
			if (powerButton)
				speed = -1 * HIGH_POWER;
		}

		motorController.set(speed);
	}	
}
