package org.usfirst.frc.team4330.robot.autonomous;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class DriveStraightTest {
	
	private DriveTrain driveTrain;
	private Gyro gyro;
	private DriveStraight testObject;
	
	@Before
	public void setUp() {
		driveTrain = mock(DriveTrain.class);
		gyro = mock(Gyro.class);
	}
	
	@Test
	public void testDriveStraightForThreeFeetInForwardDirection() {
		testObject = new DriveStraight(driveTrain, gyro, 3, 30);
		
		double normalPower = DriveStraight.motorSpeed;
		double powerDelta = DriveStraight.motorDelta;
		double headingTolerence = DriveStraight.headingTolerance;
		
		when(gyro.getAngle()).thenReturn(30.0);
		testObject.initialize();
		testObject.execute();
		assertFalse(testObject.isFinished());
		verify(driveTrain).drive(normalPower, normalPower);
		
		// simulate a left turn correction
		when(gyro.getAngle()).thenReturn(30 + headingTolerence + 1);
		testObject.execute();
		assertFalse(testObject.isFinished());
		verify(driveTrain).drive(normalPower - powerDelta, normalPower + powerDelta);
		
		// simulate a right turn correction
		when(gyro.getAngle()).thenReturn(30 - headingTolerence - 1);
		testObject.execute();
		assertFalse(testObject.isFinished());
		verify(driveTrain).drive(normalPower + powerDelta, normalPower - powerDelta);
		
		when(gyro.getAngle()).thenReturn(30.0);
		// subtract out the three times execute has already been called
		int expectedNumberTimesToDrive = ((int) (3 * 50.0 / DriveStraight.speedActual)) - 3; 
		for ( int i = 0; i < expectedNumberTimesToDrive; i++ ) {
			testObject.execute();
			assertFalse(testObject.isFinished());
			verify(driveTrain, times(2 + i)).drive(normalPower, normalPower);
		}
		
		// expect this time to begin reverse power to stop quickly
		testObject.execute();
		verify(driveTrain).drive(-1 * normalPower, -1 * normalPower);
		assertFalse(testObject.isFinished());
		
		// should continue stopping for some time
		double stopTime = 3 * DriveStraight.stopTimeFactor;
		if ( stopTime > DriveStraight.maxStopTime ) {
			stopTime = DriveStraight.maxStopTime;
		}
		int expectedNumberTimesToReverse = (int)(stopTime * 50);
		for ( int i = 0; i < expectedNumberTimesToReverse; i++ ) {
			testObject.execute();
			verify(driveTrain, never()).stop();
			assertFalse(testObject.isFinished());
		}
		
		// expect this time to stop and say finished
		testObject.execute();
		verify(driveTrain).stop();
		assertTrue(testObject.isFinished());
	}
	
	@Test
	public void testDriveStraightForThreeFeetInReverse() {
		testObject = new DriveStraight(driveTrain, gyro, -3, 30);
		
		double normalPower = -1 * DriveStraight.motorSpeed;
		double powerDelta = DriveStraight.motorDelta;
		double headingTolerence = DriveStraight.headingTolerance;
		
		when(gyro.getAngle()).thenReturn(30.0);
		testObject.initialize();
		testObject.execute();
		assertFalse(testObject.isFinished());
		verify(driveTrain).drive(normalPower, normalPower);
		
		// simulate a left turn correction
		when(gyro.getAngle()).thenReturn(30 + headingTolerence + 1);
		testObject.execute();
		assertFalse(testObject.isFinished());
		verify(driveTrain).drive(normalPower - powerDelta, normalPower + powerDelta);
		
		// simulate a right turn correction
		when(gyro.getAngle()).thenReturn(30 - headingTolerence - 1);
		testObject.execute();
		assertFalse(testObject.isFinished());
		verify(driveTrain).drive(normalPower + powerDelta, normalPower - powerDelta);
		
		when(gyro.getAngle()).thenReturn(30.0);
		// subtract out the three times execute has already been called
		int expectedNumberTimesToDrive = ((int) (3 * 50.0 / DriveStraight.speedActual)) - 3; 
		for ( int i = 0; i < expectedNumberTimesToDrive; i++ ) {
			testObject.execute();
			assertFalse(testObject.isFinished());
			verify(driveTrain, times(2 + i)).drive(normalPower, normalPower);
		}
		
		// expect this time to begin reverse power to stop quickly
		testObject.execute();
		verify(driveTrain).drive(-1 * normalPower, -1 * normalPower);
		assertFalse(testObject.isFinished());
		
		// should continue stopping for some time
		double stopTime = 3 * DriveStraight.stopTimeFactor;
		if ( stopTime > DriveStraight.maxStopTime ) {
			stopTime = DriveStraight.maxStopTime;
		}
		int expectedNumberTimesToReverse = (int)(stopTime * 50);
		for ( int i = 0; i < expectedNumberTimesToReverse; i++ ) {
			testObject.execute();
			verify(driveTrain, never()).stop();
			assertFalse(testObject.isFinished());
		}
		
		// expect this time to stop and say finished
		testObject.execute();
		verify(driveTrain).stop();
		assertTrue(testObject.isFinished());
	}

}
