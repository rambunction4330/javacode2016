package org.usfirst.frc.team4330.robot.autonomous;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class FineAlignTest {
	
	private DriveTrain driveTrain;
	private Gyro gyro;
	private FineAlign testObject;
	
	@Before
	public void setUp() {
		driveTrain = mock(DriveTrain.class);
		gyro = mock(Gyro.class);
		testObject = new FineAlign(driveTrain, gyro, 30);
	}
	
	@Test
	public void testTurnRightToHeading() {
		when(gyro.getAngle()).thenReturn(25.0);
		testObject.initialize();
		testObject.execute();
		verify(driveTrain).autonomousTurnRightSlow();
		assertFalse(testObject.isFinished());
		
		// simulate moving a little to the right but not yet there
		when(gyro.getAngle()).thenReturn(27.0);
		testObject.execute();
		verify(driveTrain, times(2)).autonomousTurnRightSlow();
		assertFalse(testObject.isFinished());
		
		// simulate arriving in the tolerance
		when(gyro.getAngle()).thenReturn(29.0);
		testObject.execute();
		verify(driveTrain, times(2)).autonomousTurnRightSlow();
		verify(driveTrain).stop();
		assertTrue(testObject.isFinished());
		verify(driveTrain, never()).autonomousTurnLeftSlow();
	}
	
	@Test
	public void testTurnLeftToHeading() {
		when(gyro.getAngle()).thenReturn(35.0);
		testObject.initialize();
		testObject.execute();
		verify(driveTrain).autonomousTurnLeftSlow();
		assertFalse(testObject.isFinished());
		
		// simulate moving a little to the left but not yet there
		when(gyro.getAngle()).thenReturn(33.0);
		testObject.execute();
		verify(driveTrain, times(2)).autonomousTurnLeftSlow();
		assertFalse(testObject.isFinished());
		
		// simulate arriving in the tolerance
		when(gyro.getAngle()).thenReturn(31.0);
		testObject.execute();
		verify(driveTrain, times(2)).autonomousTurnLeftSlow();
		verify(driveTrain).stop();
		assertTrue(testObject.isFinished());
		verify(driveTrain, never()).autonomousTurnRightSlow();
	}

}
