package org.usfirst.frc.team4330.robot.autonomous;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.DriveTrain;

public class RammingSpeedTest {
	
	private DriveTrain driveTrain;
	private RammingSpeed testObject;
	
	@Before
	public void setUp() {
		driveTrain = mock(DriveTrain.class);
		testObject = new RammingSpeed(driveTrain);
	}
	
	@Test
	public void testRammingSpeed() {
		testObject.initialize();
		testObject.execute();
		verify(driveTrain).drive(1, 1);
		assertTrue(testObject.isFinished());
	}

}
