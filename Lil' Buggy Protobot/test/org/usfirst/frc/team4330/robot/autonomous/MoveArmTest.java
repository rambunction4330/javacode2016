package org.usfirst.frc.team4330.robot.autonomous;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.Arm;

public class MoveArmTest {
	
	private Arm arm;
	private MoveArm testObject;
	
	@Before
	public void setUp() {
		arm = mock(Arm.class);
	}
	
	@Test
	public void testRaise() {
		testObject = new MoveArm(arm, true);
		testObject.initialize();
		testObject.execute();
		verify(arm).autonomousArm(true, false, false, MoveArm.RAISE_TIME);
		assertTrue(testObject.isFinished());
	}
	
	@Test
	public void testLower() {
		testObject = new MoveArm(arm, false);
		testObject.initialize();
		testObject.execute();
		verify(arm).autonomousArm(false, true, false, MoveArm.LOWER_TIME);
		assertTrue(testObject.isFinished());
	}

}
