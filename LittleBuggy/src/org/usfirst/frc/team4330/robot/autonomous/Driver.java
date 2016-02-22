package org.usfirst.frc.team4330.robot.autonomous;

import edu.wpi.first.wpilibj.GenericHID;

public class Driver extends GenericHID {

	@Override
	public double getX(Hand hand) {
		return 0;
	}

	@Override
	public double getY(Hand hand) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getZ(Hand hand) {
		return 0;
	}

	@Override
	public double getTwist() {
		return 0;
	}

	@Override
	public double getThrottle() {
		return 0;
	}

	@Override
	public double getRawAxis(int which) {
		return 0;
	}

	@Override
	public boolean getTrigger(Hand hand) {
		return false;
	}

	@Override
	public boolean getTop(Hand hand) {
		return false;
	}

	@Override
	public boolean getBumper(Hand hand) {
		return false;
	}

	@Override
	public boolean getRawButton(int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPOV(int pov) {
		return 0;
	}

	
}
