package org.usfirst.frc.team4330.robot.autonomous;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.DriveTrain;

public class OscillatingRammingSpeedTest {
	
	private DriveTrain driveTrain;
	private OscillatingRammingSpeed testObject;
	
	@Before
	public void setUp() {
		driveTrain = mock(DriveTrain.class);
		testObject = new OscillatingRammingSpeed(driveTrain, 4 * OscillatingRammingSpeed.counterInterval * 0.02 - 0.01);
	}
	
	@Test
	public void testOscillatingRammingSpeed() {
		testObject.initialize();
		double delta = OscillatingRammingSpeed.delta;
		int interval = OscillatingRammingSpeed.counterInterval;
		
		// right power
		for ( int i = 0; i < interval; i++ ) {
			testObject.execute();
			verify(driveTrain, times(i+1)).drive(1 - delta, 1);
			assertFalse(testObject.isFinished());
		}
		
		// left power
		for ( int i = 0; i < interval; i++ ) {
			testObject.execute();
			verify(driveTrain, times(i+1)).drive(1, 1 - delta);
			assertFalse(testObject.isFinished());
		}
		
		// right power
		for ( int i = 0; i < interval; i++ ) {
			testObject.execute();
			verify(driveTrain, times(interval + i+1)).drive(1 - delta, 1);
			assertFalse(testObject.isFinished());
		}
		
		// left power
		for ( int i = 0; i < interval; i++ ) {
			testObject.execute();
			verify(driveTrain, times(interval + i+1)).drive(1, 1 - delta);
			assertFalse(testObject.isFinished());
		}
		
		// should now stop due to time
		testObject.execute();
		verify(driveTrain, never()).stop();
		assertTrue(testObject.isFinished());	
	}

}
