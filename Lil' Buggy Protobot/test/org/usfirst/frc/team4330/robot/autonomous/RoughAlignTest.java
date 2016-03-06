package org.usfirst.frc.team4330.robot.autonomous;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class RoughAlignTest {
	
	private DriveTrain driveTrain;
	private Gyro gyro;
	private RoughAlign testObject;
	
	@Before
	public void setUp() {
		driveTrain = mock(DriveTrain.class);
		gyro = mock(Gyro.class);
		testObject = new RoughAlign(driveTrain, gyro, 30);
	}
	
	@Test
	public void testTurnRightToHeading() {
		when(gyro.getAngle()).thenReturn(10.0);
		testObject.initialize();
		testObject.execute();
		verify(driveTrain).autonomousTurnRight();
		assertFalse(testObject.isFinished());
		
		// simulate moving a little to the right but not yet there
		when(gyro.getAngle()).thenReturn(20.0);
		testObject.execute();
		verify(driveTrain, times(2)).autonomousTurnRight();
		assertFalse(testObject.isFinished());
		
		// simulate arriving in the tolerance
		when(gyro.getAngle()).thenReturn(26.0);
		testObject.execute();
		verify(driveTrain, times(2)).autonomousTurnRight();
		verify(driveTrain).stop();
		assertTrue(testObject.isFinished());
		verify(driveTrain, never()).autonomousTurnLeft();
	}
	
	@Test
	public void testTurnLeftToHeading() {
		when(gyro.getAngle()).thenReturn(50.0);
		testObject.initialize();
		testObject.execute();
		verify(driveTrain).autonomousTurnLeft();
		assertFalse(testObject.isFinished());
		
		// simulate moving a little to the left but not yet there
		when(gyro.getAngle()).thenReturn(40.0);
		testObject.execute();
		verify(driveTrain, times(2)).autonomousTurnLeft();
		assertFalse(testObject.isFinished());
		
		// simulate arriving in the tolerance
		when(gyro.getAngle()).thenReturn(34.0);
		testObject.execute();
		verify(driveTrain, times(2)).autonomousTurnLeft();
		verify(driveTrain).stop();
		assertTrue(testObject.isFinished());
		verify(driveTrain, never()).autonomousTurnRight();
	}

}
