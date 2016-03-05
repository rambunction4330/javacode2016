package org.usfirst.frc.team4330.robot.autonomous;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HeadingCalculatorTest {
	
	@Test
	public void testHeadingCalculator() {
		
		// test left turns
		assertEquals(-90, HeadingCalculator.calculateCourseChange(45, -45), 0.1);
		assertEquals(-90, HeadingCalculator.calculateCourseChange(-45, -135), 0.1);
		assertEquals(-90, HeadingCalculator.calculateCourseChange(-135, 135), 0.1);
		assertEquals(-90, HeadingCalculator.calculateCourseChange(135, 45), 0.1);
		
		assertEquals(-5, HeadingCalculator.calculateCourseChange(45, 40), 0.1);
		assertEquals(-5, HeadingCalculator.calculateCourseChange(135, 130), 0.1);
		assertEquals(-5, HeadingCalculator.calculateCourseChange(-45, -50), 0.1);
		assertEquals(-5, HeadingCalculator.calculateCourseChange(-135, -140), 0.1);
		
		assertEquals(-1, HeadingCalculator.calculateCourseChange(-179, 180), 0.1);
		assertEquals(-1, HeadingCalculator.calculateCourseChange(1, 0), 0.1);
		
		// test right turns
		assertEquals(90, HeadingCalculator.calculateCourseChange(45, 135), 0.1);
		assertEquals(90, HeadingCalculator.calculateCourseChange(135, -135), 0.1);
		assertEquals(90, HeadingCalculator.calculateCourseChange(-135, -45), 0.1);
		assertEquals(90, HeadingCalculator.calculateCourseChange(-45, 45), 0.1);
		
		assertEquals(175, HeadingCalculator.calculateCourseChange(50, -135), 0.1);
		assertEquals(5, HeadingCalculator.calculateCourseChange(45, 50), 0.1);
		assertEquals(5, HeadingCalculator.calculateCourseChange(135, 140), 0.1);
		assertEquals(5, HeadingCalculator.calculateCourseChange(-135, -130), 0.1);
		assertEquals(5, HeadingCalculator.calculateCourseChange(-45, -40), 0.1);
		
		assertEquals(1, HeadingCalculator.calculateCourseChange(179, 180), 0.1);
		assertEquals(1, HeadingCalculator.calculateCourseChange(-1, 0), 0.1);
		assertEquals(2, HeadingCalculator.calculateCourseChange(179, -179), 0.1);
	}
	
	@Test
	public void angleCalculatorTest() {
		assertEquals(15, HeadingCalculator.normalize(15), 0.1);
		assertEquals(170, HeadingCalculator.normalize(-190), 0.1);
		assertEquals(0, HeadingCalculator.normalize(-720), 0.1);
		assertEquals(180, HeadingCalculator.normalize(180), 0.1);
		assertEquals(-170, HeadingCalculator.normalize(190), 0.1);
		assertEquals(0, HeadingCalculator.normalize(0), 0.1);
		assertEquals(-180, HeadingCalculator.normalize(-180), 0.1);
		assertEquals(170, HeadingCalculator.normalize(-190), 0.1);
	}

}
