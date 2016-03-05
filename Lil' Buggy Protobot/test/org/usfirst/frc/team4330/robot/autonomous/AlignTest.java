package org.usfirst.frc.team4330.robot.autonomous;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.DriveTrain;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class AlignTest {

	
	private RoughAlign testObject;
	private DriveTrain driveTrain;
	private Gyro gyro;
		
	@Before
	public void setUp() {
		driveTrain = mock(DriveTrain.class);
		gyro = mock(Gyro.class);
		testObject = new RoughAlign(driveTrain, gyro, 0) { 
			
		};
	}
	
	@Test
	public void assertRobotTurnsRightFromNeg45toNeg40() {	
		when(gyro.getAngle()).thenReturn(-45.0);
		testObject = new RoughAlign(driveTrain, gyro, -40);
		testObject.execute();
		assertFalse(testObject.isFinished());
		verify(driveTrain).autonomousTurnRight();
	}
	
	@Test
	public void assertRobotTurnsLeftFromNeg45toNeg50() {	
		when(gyro.getAngle()).thenReturn(-45.0);
		testObject = new RoughAlign(driveTrain, gyro, -50);
		testObject.execute();
		assertFalse(testObject.isFinished());
		verify(driveTrain).autonomousTurnLeft();
	}
	

}
