package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

public class Arm {
	private SpeedController motorController1;
	private SpeedController motorController2;
	
	private static final double ARM_WHEEL_SPEED = .2;
	private static final double POWER_SPEED = 1.0;

	public Arm() {
		motorController1 = new Victor(RobotMap.TREXARM_PORT_ONE);
		motorController2 = new Victor(RobotMap.TREXARM_PORT_TWO);
	}

	public Arm(SpeedController motorController1, SpeedController motorController2) {
		this.motorController1 = motorController1;
		this.motorController2 = motorController2;
	}

	public void handleButtons(boolean raiseButton, boolean lowerButton, boolean powerButton) {
		double speed = 0;
		
		if (lowerButton && raiseButton)
			System.out.println("TREKUDESU");

		if (lowerButton) {
			speed = ARM_WHEEL_SPEED;
			if (powerButton)
				speed = POWER_SPEED;
		} else if (raiseButton) {
			speed = -1 * RobotMap.ARM_WHEEL_SPEED;
			if (powerButton)
				speed = -1 * POWER_SPEED;
		}
		
		motorController1.set(speed);
		motorController2.set(speed);

	}
}
