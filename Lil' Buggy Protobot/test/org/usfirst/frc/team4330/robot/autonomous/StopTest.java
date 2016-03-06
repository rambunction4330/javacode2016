package org.usfirst.frc.team4330.robot.autonomous;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.DriveTrain;

public class StopTest {
	
	private DriveTrain driveTrain;
	private Stop testObject;
	
	@Before
	public void setUp() {
		driveTrain = mock(DriveTrain.class);
		testObject = new Stop(driveTrain);
	}
	
	@Test
	public void testStop() {
		testObject.initialize();
		testObject.execute();
		verify(driveTrain).stop();
		assertTrue(testObject.isFinished());
	}

}
