package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class Pneumatics {

	private static Pneumatics instance;

	// private Timer time;
	protected final Compressor comp;
	protected final Solenoid kicker;

	/**
	 * Use this method so that multiple instances of Pneumatics are not created.
	 * 
	 * @return the instance of the pneumatics method
	 */
	public static Pneumatics getInstance() {
		/*if (instance == null) {
			instance = new Pneumatics();
		}
		return instance;*/
		
	    return instance == null ? instance = new Pneumatics() : instance;
	}

	private Pneumatics() {
		comp = new Compressor(RobotMap.COMPRESSOR);
		comp.start();

		instance = this;

		kicker = new Solenoid(RobotMap.KICKER_SOL);
	}

	public void KickerOut() {
		kicker.set(true);
	}

	public void KickerIn() {
		kicker.set(false);
	}

	public void disabled() {
		comp.stop();
		kicker.set(false);
	}

	/*
	 * public void start() { time.reset(); time.start(); }
	 * 
	 * public double getTime() { return time.get(); }
	 */

}
