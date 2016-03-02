package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.PIDOutput;

public class MultiplePIDOutputs implements PIDOutput {
	private PIDOutput one;
	private PIDOutput two;
	
	public MultiplePIDOutputs(PIDOutput one, PIDOutput two) {
		this.one = one;
		this.two = two;
	}
	
	@Override
	public void pidWrite(double output) {
		one.pidWrite(output);
		two.pidWrite(output);
	}
	
}
