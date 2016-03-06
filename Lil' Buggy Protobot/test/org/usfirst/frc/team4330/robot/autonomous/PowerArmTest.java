package org.usfirst.frc.team4330.robot.autonomous;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.Arm;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class PowerArmTest {
	
	private Arm arm;
	private PowerArm testObject;
	
	@Before
	public void setUp() {
		arm = mock(Arm.class);
	}
	
	@Test
	public void testPowerRaise() {
		testObject = new PowerArm(arm, true, 0.3);
		testObject.initialize();
		testObject.execute();
		verify(arm).autonomousArm(true, false, true, 0.3);
		assertTrue(testObject.isFinished());
	}
	
	@Test
	public void testPowerLower() {
		testObject = new PowerArm(arm, false, 0.5);
		testObject.initialize();
		testObject.execute();
		verify(arm).autonomousArm(false, true, true, 0.5);
		assertTrue(testObject.isFinished());
	}

}
