package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

// TODO revisit once we have the encoder installed
public class Arm {

	private SpeedController motorController;
	private Encoder motorEncoder;

	public Arm(SpeedController motorController, Encoder motorEncoder) {
		this.motorController = motorController;
		this.motorEncoder = motorEncoder;
	}

	public void handleButtons(boolean raiseButton, boolean lowerButton) {
		double speed = 0;

		if (lowerButton && raiseButton)
			System.out.println("****ing calm down.");

		if (lowerButton) {
			speed = RobotMap.ARM_WHEEL_SPEED;
		} else if (raiseButton) {
			speed = -1 * RobotMap.ARM_WHEEL_SPEED;
		}

		motorController.set(speed);
	}

}
