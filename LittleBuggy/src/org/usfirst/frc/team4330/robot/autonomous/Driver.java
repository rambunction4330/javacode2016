package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.AnalogGyro;

public class Driver {
	private State autonomousSteps;
	private double currentAngle;
	DriveTrain dt;

	private static enum State {
		Initial, DrivenOverTheDefense, DrivingToMidPosition, MidPosition, Turning, DrivingToGoal, Shooting;
	}

	AnalogGyro gyro;

	public Driver() {
		gyro = new AnalogGyro(0, 0, 0);
		dt = new DriveTrain();
		autonomousSteps = State.Initial;
	}

	public void intial() {
		if (autonomousSteps != State.Initial)
			return;
		if (autonomousSteps != State.DrivenOverTheDefense) {
		dt.forward(1000);
		autonomousSteps = State.DrivenOverTheDefense;
		overDefense();
		}
	}

	public void overDefense() {
		
		dt.fixedDrive(1, -1);
		autonomousSteps = State.DrivingToMidPosition;
	}
//	WE'RE NOT SHOOTING IF WE CAN'T EVEN GO OVER THE F***ING DEFENCES IN THE FU**ING TELEOP MODE BECAUSE NONE OF OUR "DRIVERS" HAVE ACTUALLY DRIVEN MORE THAN A COUPLE OF HOURS :<
	public void findAngle() {
		System.out.println("Gyro angle is " + gyro.getAngle());
		
		if (gyro.getAngle() > -61 && gyro.getAngle() < -59) {
			dt.fixedDrive(0, 0);
			autonomousSteps = State.DrivingToMidPosition;
		} else if (gyro.getAngle() < -61) {
			dt.fixedDrive(-.3, -.3);
		} else {
			dt.fixedDrive(.3, .3);
		}
	}
}