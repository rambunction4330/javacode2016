package org.usfirst.frc.team4330.robot;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Pneumatics{

	private static Pneumatics instance;

	private Timer time;
	protected final Compressor comp;
	protected final Solenoid kicker;

	public static Pneumatics getInstance() {
		if (instance == null) {
			instance = new Pneumatics();
		}
		return instance;
	}

	public Pneumatics(Compressor comp, Solenoid kicker) {
		this.comp = comp;
		comp.start();

		this.kicker = kicker;
		time = new Timer();
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

	/*public void start() {
		time.reset();
		time.start();
	}

	public double getTime() {
		return time.get();
	}*/
	
}
