package org.usfirst.frc.team4330.robot.autonomous;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HeadingCalculatorTest {
	
	@Test
	public void testHeadingCalculator() {
		
		// test left turns
		assertEquals(-1, HeadingCalculator.calculateCourseChange(-179, 180), 0.1);
		assertEquals(-1, HeadingCalculator.calculateCourseChange(1, 0), 0.1);
		
		assertEquals(-2, HeadingCalculator.calculateCourseChange(1, -1), 0.1);
		assertEquals(-2, HeadingCalculator.calculateCourseChange(-179, 179), 0.1);
		assertEquals(-2, HeadingCalculator.calculateCourseChange(-180, 178), 0.1);
		assertEquals(-2, HeadingCalculator.calculateCourseChange(-178, -180), 0.1);
		assertEquals(-2, HeadingCalculator.calculateCourseChange(-178, 180), 0.1);
		assertEquals(-2, HeadingCalculator.calculateCourseChange(110, 108), 0.1);
		assertEquals(-2, HeadingCalculator.calculateCourseChange(-35, -37), 0.1);
		
		assertEquals(-5, HeadingCalculator.calculateCourseChange(45, 40), 0.1);
		assertEquals(-5, HeadingCalculator.calculateCourseChange(135, 130), 0.1);
		assertEquals(-5, HeadingCalculator.calculateCourseChange(-45, -50), 0.1);
		assertEquals(-5, HeadingCalculator.calculateCourseChange(-135, -140), 0.1);
		
		assertEquals(-45, HeadingCalculator.calculateCourseChange(0, -45), 0.1);
		assertEquals(-45, HeadingCalculator.calculateCourseChange(45, 0), 0.1);
		assertEquals(-45, HeadingCalculator.calculateCourseChange(90, 45), 0.1);
		assertEquals(-45, HeadingCalculator.calculateCourseChange(135, 90), 0.1);
		assertEquals(-45, HeadingCalculator.calculateCourseChange(180, 135), 0.1);
		assertEquals(-45, HeadingCalculator.calculateCourseChange(-180, 135), 0.1);
		assertEquals(-45, HeadingCalculator.calculateCourseChange(-135, 180), 0.1);
		assertEquals(-45, HeadingCalculator.calculateCourseChange(-90, -135), 0.1);
		assertEquals(-45, HeadingCalculator.calculateCourseChange(-45, -90), 0.1);
		assertEquals(-45, HeadingCalculator.calculateCourseChange(20, -25), 0.1);
		assertEquals(-45, HeadingCalculator.calculateCourseChange(-170, 145), 0.1);
		
		assertEquals(-90, HeadingCalculator.calculateCourseChange(0, -90), 0.1);
		assertEquals(-90, HeadingCalculator.calculateCourseChange(45, -45), 0.1);
		assertEquals(-90, HeadingCalculator.calculateCourseChange(90, 0), 0.1);
		assertEquals(-90, HeadingCalculator.calculateCourseChange(135, 45), 0.1);
		assertEquals(-90, HeadingCalculator.calculateCourseChange(180, 90), 0.1);
		assertEquals(-90, HeadingCalculator.calculateCourseChange(-180, 90), 0.1);
		assertEquals(-90, HeadingCalculator.calculateCourseChange(-135, 135), 0.1);
		assertEquals(-90, HeadingCalculator.calculateCourseChange(-90, 180), 0.1);
		assertEquals(-90, HeadingCalculator.calculateCourseChange(-45, -135), 0.1);
		
		assertEquals(-135, HeadingCalculator.calculateCourseChange(0, -135), 0.1);
		assertEquals(-135, HeadingCalculator.calculateCourseChange(-45, -180), 0.1);
		assertEquals(-135, HeadingCalculator.calculateCourseChange(-45, 180), 0.1);
		assertEquals(-135, HeadingCalculator.calculateCourseChange(-90, 135), 0.1);
		assertEquals(-135, HeadingCalculator.calculateCourseChange(-135, 90), 0.1);
		assertEquals(-135, HeadingCalculator.calculateCourseChange(-180, 45), 0.1);
		assertEquals(-135, HeadingCalculator.calculateCourseChange(180, 45), 0.1);
		assertEquals(-135, HeadingCalculator.calculateCourseChange(135, 0), 0.1);
		assertEquals(-135, HeadingCalculator.calculateCourseChange(90, -45), 0.1);
		assertEquals(-135, HeadingCalculator.calculateCourseChange(45, -90), 0.1);
		
		assertEquals(-179, HeadingCalculator.calculateCourseChange(0, -179), 0.1);
		assertEquals(-179, HeadingCalculator.calculateCourseChange(-1, -180), 0.1);
		assertEquals(-179, HeadingCalculator.calculateCourseChange(-1, 180), 0.1);
		assertEquals(-179, HeadingCalculator.calculateCourseChange(45, -134), 0.1);
		assertEquals(-179, HeadingCalculator.calculateCourseChange(90, -89), 0.1);
		assertEquals(-179, HeadingCalculator.calculateCourseChange(135, -44), 0.1);
		assertEquals(-179, HeadingCalculator.calculateCourseChange(-180, 1), 0.1);
		assertEquals(-179, HeadingCalculator.calculateCourseChange(180, 1), 0.1);
		assertEquals(-179, HeadingCalculator.calculateCourseChange(-135, 46), 0.1);
		assertEquals(-179, HeadingCalculator.calculateCourseChange(-90, 91), 0.1);
		assertEquals(-179, HeadingCalculator.calculateCourseChange(-45, 136), 0.1);
		
		// test right turns
		assertEquals(1, HeadingCalculator.calculateCourseChange(179, 180), 0.1);
		assertEquals(1, HeadingCalculator.calculateCourseChange(-1, 0), 0.1);
		
		assertEquals(2, HeadingCalculator.calculateCourseChange(179, -179), 0.1);
		assertEquals(2, HeadingCalculator.calculateCourseChange(178, 180), 0.1);
		assertEquals(2, HeadingCalculator.calculateCourseChange(178, -180), 0.1);
		assertEquals(2, HeadingCalculator.calculateCourseChange(180, -178), 0.1);
		assertEquals(2, HeadingCalculator.calculateCourseChange(-180, -178), 0.1);
		assertEquals(2, HeadingCalculator.calculateCourseChange(45, 47), 0.1);
		assertEquals(2, HeadingCalculator.calculateCourseChange(-20, -18), 0.1);
		
		assertEquals(5, HeadingCalculator.calculateCourseChange(45, 50), 0.1);
		assertEquals(5, HeadingCalculator.calculateCourseChange(135, 140), 0.1);
		assertEquals(5, HeadingCalculator.calculateCourseChange(-135, -130), 0.1);
		assertEquals(5, HeadingCalculator.calculateCourseChange(-45, -40), 0.1);
		
		assertEquals(45, HeadingCalculator.calculateCourseChange(0, 45), 0.1);
		assertEquals(45, HeadingCalculator.calculateCourseChange(45, 90), 0.1);
		assertEquals(45, HeadingCalculator.calculateCourseChange(90, 135), 0.1);
		assertEquals(45, HeadingCalculator.calculateCourseChange(135, 180), 0.1);
		assertEquals(45, HeadingCalculator.calculateCourseChange(135, -180), 0.1);
		assertEquals(45, HeadingCalculator.calculateCourseChange(180, -135), 0.1);
		assertEquals(45, HeadingCalculator.calculateCourseChange(-180, -135), 0.1);
		assertEquals(45, HeadingCalculator.calculateCourseChange(-135, -90), 0.1);
		assertEquals(45, HeadingCalculator.calculateCourseChange(-90, -45), 0.1);
		assertEquals(45, HeadingCalculator.calculateCourseChange(-45, 0), 0.1);
		assertEquals(45, HeadingCalculator.calculateCourseChange(-20, 25), 0.1);
		assertEquals(45, HeadingCalculator.calculateCourseChange(170, -145), 0.1);
		
		assertEquals(90, HeadingCalculator.calculateCourseChange(0, 90), 0.1);
		assertEquals(90, HeadingCalculator.calculateCourseChange(45, 135), 0.1);
		assertEquals(90, HeadingCalculator.calculateCourseChange(90, 180), 0.1);
		assertEquals(90, HeadingCalculator.calculateCourseChange(90, -180), 0.1);
		assertEquals(90, HeadingCalculator.calculateCourseChange(135, -135), 0.1);
		assertEquals(90, HeadingCalculator.calculateCourseChange(180, -90), 0.1);
		assertEquals(90, HeadingCalculator.calculateCourseChange(-180, -90), 0.1);
		assertEquals(90, HeadingCalculator.calculateCourseChange(-135, -45), 0.1);
		assertEquals(90, HeadingCalculator.calculateCourseChange(-90, 0), 0.1);
		assertEquals(90, HeadingCalculator.calculateCourseChange(-45, 45), 0.1);
		
		assertEquals(135, HeadingCalculator.calculateCourseChange(0, 135), 0.1);
		assertEquals(135, HeadingCalculator.calculateCourseChange(45, -180), 0.1);
		assertEquals(135, HeadingCalculator.calculateCourseChange(45, 180), 0.1);
		assertEquals(135, HeadingCalculator.calculateCourseChange(90, -135), 0.1);
		assertEquals(135, HeadingCalculator.calculateCourseChange(135, -90), 0.1);
		assertEquals(135, HeadingCalculator.calculateCourseChange(180, -45), 0.1);
		assertEquals(135, HeadingCalculator.calculateCourseChange(-180, -45), 0.1);
		assertEquals(135, HeadingCalculator.calculateCourseChange(-135, 0), 0.1);
		assertEquals(135, HeadingCalculator.calculateCourseChange(-90, 45), 0.1);
		assertEquals(135, HeadingCalculator.calculateCourseChange(-45, 90), 0.1);
		
		assertEquals(179, HeadingCalculator.calculateCourseChange(0, 179), 0.1);
		assertEquals(179, HeadingCalculator.calculateCourseChange(1, -180), 0.1);
		assertEquals(179, HeadingCalculator.calculateCourseChange(45, -136), 0.1);
		assertEquals(179, HeadingCalculator.calculateCourseChange(90, -91), 0.1);
		assertEquals(179, HeadingCalculator.calculateCourseChange(135, -46), 0.1);
		assertEquals(179, HeadingCalculator.calculateCourseChange(-180, -1), 0.1);
		assertEquals(179, HeadingCalculator.calculateCourseChange(180, -1), 0.1);
		assertEquals(179, HeadingCalculator.calculateCourseChange(-135, 44), 0.1);
		assertEquals(179, HeadingCalculator.calculateCourseChange(-90, 89), 0.1);
		assertEquals(179, HeadingCalculator.calculateCourseChange(-45, 134), 0.1);
		
		assertEquals(180, HeadingCalculator.calculateCourseChange(0, 180), 0.1);
		assertEquals(180, HeadingCalculator.calculateCourseChange(0, -180), 0.1);
		assertEquals(180, HeadingCalculator.calculateCourseChange(45, -135), 0.1);
		assertEquals(180, HeadingCalculator.calculateCourseChange(90, -90), 0.1);
		assertEquals(180, HeadingCalculator.calculateCourseChange(135, -45), 0.1);
		assertEquals(180, HeadingCalculator.calculateCourseChange(-180, 0), 0.1);
		assertEquals(180, HeadingCalculator.calculateCourseChange(180, 0), 0.1);
		assertEquals(180, HeadingCalculator.calculateCourseChange(-135, 45), 0.1);
		assertEquals(180, HeadingCalculator.calculateCourseChange(-90, 90), 0.1);
		assertEquals(180, HeadingCalculator.calculateCourseChange(-45, 135), 0.1);
		
	}
	
	@Test
	public void normalizeTest() {
		assertEquals(15, HeadingCalculator.normalize(15), 0.1);
		assertEquals(170, HeadingCalculator.normalize(-190), 0.1);
		assertEquals(0, HeadingCalculator.normalize(-720), 0.1);
		assertEquals(180, HeadingCalculator.normalize(180), 0.1);
		assertEquals(-170, HeadingCalculator.normalize(190), 0.1);
		assertEquals(0, HeadingCalculator.normalize(0), 0.1);
		assertEquals(180, HeadingCalculator.normalize(-180), 0.1);
		assertEquals(170, HeadingCalculator.normalize(-190), 0.1);
	}

}
