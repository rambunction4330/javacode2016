//package org.usfirst.frc.team4330.robot;
//
//import edu.wpi.first.wpilibj.Compressor;
//import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
//
//public class Pneumatics {
//
////	private static Pneumatics instance;
//
//	// private Timer time;
//	public Compressor comp;
//	private final Solenoid kicker;
//	private final DoubleSolenoid bigone;
//
//	
//	
//	
//	/**
//	 * Use this method so that multiple instances of Pneumatics are not created.
//	 * @param solenoid2 
//	 * 
//	 * @return the instance of the pneumatics method
//	 */
//	/*public static Pneumatics getInstance() {
//		if (instance == null) {
//			instance = new Pneumatics();
//		}
//		return instance;
//		
//	    return instance == null ? instance = new Pneumatics(new Compressor(RobotMap.COMPRESSOR), new Solenoid(RobotMap.KICKER_SOL)) : instance;
//	}*/
//
//	public Pneumatics(Compressor compressor, Solenoid solenoid, DoubleSolenoid solenoid2) {
//		comp = compressor;
//		comp.start();
//		
//	//	instance = this;
//		bigone = solenoid2;
//		kicker = solenoid;
//	}
//	
//	public void BigoneIn() {
//		bigone.set(Value.kForward);
//	}
//	
//	public void BigoneOut() {
//		bigone.set(Value.kReverse);
//	}
//	public void BigoneOff() {
//		bigone.set(Value.kOff);
//	}
//	
//	
//	public void KickerIn() {
//		kicker.set(true);
//	}
//
//	public void KickerOut() {
//		kicker.set(false);
//	}
//
//	public void disabled() {
//		comp.stop();
////		kicker.set();
//	}
//	
////	public void checkStatus() {
////		if (comp.getPressureSwitchValue()) comp.stop();
////		else comp.start();
////	}
//	
//	/*
//	 * public void start() { time.reset(); time.start(); }
//	 * 
//	 * public double getTime() { return time.get(); }
//	 */
//
//}