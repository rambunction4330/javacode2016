package org.usfirst.frc.team4330.robot.autonomous;

import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class RoughAlign extends Command {
	protected DriveTrain dT;
	private Gyro gyro;
	private double desiredHeading;
	private boolean finished = false;
	private String name;
	
	public RoughAlign(DriveTrain dT, Gyro gyro, double desiredHeading) {
		this.dT = dT;
		this.gyro = gyro;
		this.desiredHeading = desiredHeading;
	}

	public RoughAlign(DriveTrain dT, Gyro gyro, double desiredHeading, String name) {
		this.dT = dT;
		this.gyro = gyro;
		this.desiredHeading = desiredHeading;
		this.name = name;
	}

	@Override
	protected void initialize() {
		
	}
	
	protected double getRaw() {
		return gyro.getAngle();
	}

	@Override
	protected void execute() {
		double turnAmount = HeadingCalculator.calculateCourseChange(getRaw(), desiredHeading);
		System.out.println(name + " turn Amount: " + turnAmount);
		if (Math.abs(turnAmount) < getTolerance()) {
			dT.drive(0, 0);
			System.out.println("Done.");
			finished = true;
		} else {
			if (turnAmount < 0) {
				System.out.println("Turning Left");
				dT.autonomousTurnLeft();
			} else {
				System.out.println("Turning Right");
				dT.autonomousTurnRight();
			}
		}
	}
	
	protected double getTolerance() {
		return 5;
	}
	
	protected void turnLeft() {
		dT.autonomousTurnLeft();
	}
	
	protected void turnRight() {
		dT.autonomousTurnRight();
	}

	@Override
	protected boolean isFinished() {
		return finished;
	}

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {

	}

}
