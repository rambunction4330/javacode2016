package org.usfirst.frc.team4330.robot.autonomous;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class CallbackToManagerTest {
	
	private Manager manager;
	private CallbackToManager testObject;
	
	@Before
	public void setUp() {
		manager = mock(Manager.class);
		testObject = new CallbackToManager(manager);
	}
	
	@Test
	public void testShoots() {
		testObject.initialize();
		testObject.execute();
		verify(manager).callback();
		assertTrue(testObject.isFinished());
	}

}
