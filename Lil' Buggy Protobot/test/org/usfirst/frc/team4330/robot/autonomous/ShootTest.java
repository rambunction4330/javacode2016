package org.usfirst.frc.team4330.robot.autonomous;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.BallControl;

public class ShootTest {
	
	private BallControl ballControl;
	private Shoot testObject;
	
	@Before
	public void setUp() {
		ballControl = mock(BallControl.class);
		testObject = new Shoot(ballControl);
	}
	
	@Test
	public void testShoots() {
		testObject.initialize();
		testObject.execute();
		verify(ballControl).shoot(true);
		assertTrue(testObject.isFinished());
	}

}
