package org.usfirst.frc.team4330.robot.autonomous;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.usfirst.frc.team4330.robot.BallControl;
import org.usfirst.frc.team4330.robot.DriveTrain;
import org.usfirst.frc.team4330.robot.SmartDashboardSetup;
import org.usfirst.frc.team4330.robot.raspberrypi.SensorDataRetriever;

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class ManagerTest {
	
	private DriveTrain driveTrain;
	private Gyro gyro;
	private SmartDashboardSetup smartDashboard;
	private SensorDataRetriever vision;
	private BallControl ballControl;
	private Scheduler scheduler;
	
	private Manager testObject;
	
	@Before
	public void setUp() {
		driveTrain = mock(DriveTrain.class);
		gyro = mock(Gyro.class);
		smartDashboard = mock(SmartDashboardSetup.class);
		vision = mock(SensorDataRetriever.class);
		ballControl = mock(BallControl.class);
		scheduler = mock(Scheduler.class);
		testObject = new Manager(driveTrain, gyro, smartDashboard, vision, ballControl, scheduler);
	}
	
	@Test
	public void testIsLeftTargetActive() {
		when(smartDashboard.getAutoPosition()).thenReturn(SmartDashboardSetup.one);
		assertTrue(testObject.isLeftTargetActive());
		when(smartDashboard.getAutoPosition()).thenReturn(SmartDashboardSetup.two);
		assertTrue(testObject.isLeftTargetActive());
		when(smartDashboard.getAutoPosition()).thenReturn(SmartDashboardSetup.three);
		assertTrue(testObject.isLeftTargetActive());
		when(smartDashboard.getAutoPosition()).thenReturn(SmartDashboardSetup.four);
		assertFalse(testObject.isLeftTargetActive());
		when(smartDashboard.getAutoPosition()).thenReturn(SmartDashboardSetup.five);
		assertFalse(testObject.isLeftTargetActive());
	}
	
	@Test
	public void testCalculateDirectionAndDistance() {
		double[] dirAndDistance = testObject.calculateDirectionAndDistance(5, 10, -5, 20);
		assertEquals(-45, dirAndDistance[0], 0.01);
		assertEquals(Math.sqrt(200), dirAndDistance[1], 0.01);
		
		dirAndDistance = testObject.calculateDirectionAndDistance(5, 10, 15, 20);
		assertEquals(45, dirAndDistance[0], 0.01);
		assertEquals(Math.sqrt(200), dirAndDistance[1], 0.01);
		
		dirAndDistance = testObject.calculateDirectionAndDistance(5, 10, 15, 30);
		assertEquals(26.565, dirAndDistance[0], 0.01);
		assertEquals(Math.sqrt(500), dirAndDistance[1], 0.01);
	}

}
