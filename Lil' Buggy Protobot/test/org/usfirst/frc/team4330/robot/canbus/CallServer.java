package org.usfirst.frc.team4330.robot.canbus;

import java.util.Date;
import java.util.Map;

import org.usfirst.frc.team4330.robot.raspberrypi.SensorDataRetriever;

public class CallServer {
	
	public static void main ( String[] args ) {
		try {
			SensorDataRetriever r = new SensorDataRetriever();
			r.startUp();
			long startTime = System.currentTimeMillis();
			for ( int i = 0; i < 1000000; i++ ) {
				Map<String,String> data = r.retrieveData();
				//System.out.println("Received data " + data + " at time " + new Date().toString());
				//Thread.sleep(20);
			}
			long endTime = System.currentTimeMillis();
			r.shutDown();
			System.out.println("Took " + (endTime - startTime) + " msec");
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

}
