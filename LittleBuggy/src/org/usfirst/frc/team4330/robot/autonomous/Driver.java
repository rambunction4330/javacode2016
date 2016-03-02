package org.usfirst.frc.team4330.robot.autonomous;

import edu.wpi.first.wpilibj.AnalogGyro;

public class Driver {
	private State autonomousSteps;
//	private double currentAngle;

	private static enum State {
		Initial, DrivenOverTheDefense, DrivingToMidPosition, MidPosition, Turning, DrivingToGoal, Shooting;
	}
	
	
	AnalogGyro gyro;

	public Driver() {
		
		gyro = new AnalogGyro(0, 0, 0);
		autonomousSteps = State.Initial;
	}

	public void intial() {
		if (autonomousSteps != State.Initial)
			return;
		if (autonomousSteps != State.DrivenOverTheDefense) {
			
		autonomousSteps = State.DrivenOverTheDefense;
		overDefense();
		}
	}

	public void overDefense() {
		
		autonomousSteps = State.DrivingToMidPosition;
	}
	
//	WE'RE NOT SHOOTING IF WE CAN'T EVEN GO OVER THE F***ING DEFENCES IN THE FU**ING TELEOP MODE BECAUSE NONE OF OUR "DRIVERS" HAVE ACTUALLY DRIVEN MORE THAN A COUPLE OF HOURS :<
	public void findAngle(int angle) {
		System.out.println("Gyro angle is " + gyro.getAngle());
		
		if (gyro.getAngle() > angle-1 && gyro.getAngle() < angle+1) {
			autonomousSteps = State.DrivingToMidPosition;
		} else if (gyro.getAngle() < -61) {
		} else {
		}
	}
}