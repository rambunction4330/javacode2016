package org.usfirst.frc.team4330.robot.autonomous;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.DriveTrain;
import org.mockito.Mockito;

public class AlignTest {

	
	private Align testObject;
	double speed = 0.8;
	
	private double rawValue = 0;
	private double angle = 45;
		
	@Before
	public void setUp() {
		testObject = new Align(null, null, 0) { 
			
			@Override
			protected double getRaw() {
				return rawValue;
			}
		};
	}
	
	@Test
	public void angleCalculatorTest() {
		rawValue = 15;
		Assert.assertEquals(15, testObject.angleCalculator(), .1);
		rawValue = -190;
		Assert.assertEquals(170, testObject.angleCalculator(), .1);
		rawValue = -720;
		Assert.assertEquals(0, testObject.angleCalculator(), .1);
		rawValue = 180;
		Assert.assertEquals(180, testObject.angleCalculator(), .1);
		rawValue = 190;
		Assert.assertEquals(-170, testObject.angleCalculator(), .1);
		rawValue = 0;
		Assert.assertEquals(0, testObject.angleCalculator(), .1);
		rawValue = -180;
		Assert.assertEquals(-180, testObject.angleCalculator(), .1);
		rawValue = -190;
		Assert.assertEquals(170, testObject.angleCalculator(), .1);
	}
	
	@Test
	public void assertRobotTurnsLeftFrom45ToNeg45() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, -45) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(-speed, speed);
		angle = -45;
		testObject.execute();
		Mockito.verify(mockDt).drive(0,0);
		Assert.assertTrue(testObject.isFinished());		
	}
	
	@Test
	public void assertRobotTurnsLeftFromNeg45ToNeg135() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, -135) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = -45;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(-speed, speed);
	}
	
	@Test
	public void assertRobotTurnsLeftFromNeg135To135() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, 135) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = -135;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(-speed, speed);
	}
	
	@Test
	public void assertRobotTurnsLeftFrom135To45() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, 45) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = 135;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(-speed, speed);
	}
	
	@Test
	public void assertRobotTurnsRightFrom45To135() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, 135) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = 45;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(speed, -speed);
	}
	
	@Test
	public void assertRobotTurnsRightFrom135ToNeg135() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, -135) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = 135;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(speed, -speed);
	}
	
	@Test
	public void assertRobotTurnsRightFromNeg135ToNeg45() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, -45) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = -135;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(speed, -speed);
	}
	
	@Test
	public void assertRobotTurnsRightFromNeg45To45() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, 45) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = -45;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(speed, -speed);
	}
	
	@Test
	public void assertRobotTurnsRightFrom50ToNeg135() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, -135) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = 50;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(speed, -speed);
	}
	
	@Test
	public void assertRobotTurnsRightFrom45To50() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, 50) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = 45;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(speed, -speed);
	}
	
	@Test
	public void assertRobotTurnsLeftFrom45to40() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, 40) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = 45;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(-speed, speed);
	}
	@Test
	public void assertRobotTurnsRightFrom135to140() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, 140) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = 135;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(speed, -speed);
	}
	@Test
	public void assertRobotTurnsLeftFrom135to130() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, 130) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = 135;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(-speed, speed);
	}
	
	@Test
	public void assertRobotTurnsRightFromNeg45toNeg40() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, -40) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = -45;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(speed, -speed);
	}
	
	@Test
	public void assertRobotTurnsLeftFromNeg45toNeg50() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, -50) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = -45;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(-speed, speed);
	}
	
	@Test
	public void assertRobotTurnsLeftFromNeg135toNeg140() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, -140) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = -135;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(-speed, speed);
	}

	@Test
	public void assertRobotTurnsRightFromNeg135toNeg130() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, -130) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = -135;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(speed, -speed);
	}

	@Test
	public void assertRobotTurnsLeftFromNeg179to180() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, 180) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = -179;
		testObject.execute();
		Mockito.verify(mockDt).drive(-speed, speed);
		Assert.assertFalse(testObject.isFinished());
		angle = -179.6;
		testObject.execute();
		Assert.assertTrue(testObject.isFinished());
		Mockito.verify(mockDt).drive(0, 0);
	}
	
	@Test
	public void assertRobotTurnsRightFrom179to180() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, 180) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = 179;
		testObject.execute();
		Assert.assertFalse(testObject.isFinished());
		Mockito.verify(mockDt).drive(speed, -speed);
	}
	
	public void assertRobotTurnsLeftFrom1to0() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, 0) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = 1;
		testObject.execute();
		Mockito.verify(mockDt).drive(-speed, speed);
		angle = 0.1;
		testObject.execute();
		Assert.assertTrue(testObject.isFinished());
		Mockito.verify(mockDt).drive(0, 0);
	}
	
public void assertRobotTurnsRightFromNeg1to0() {
		
		DriveTrain mockDt = Mockito.mock(DriveTrain.class);
		
		testObject = new Align(mockDt, null, 0) {

			@Override
			protected double angleCalculator() {
				// TODO Auto-generated method stub
				return angle;
			} 
		};
		
		angle = -1;
		testObject.execute();
		Mockito.verify(mockDt).drive(speed, -speed);
		angle = -0.1;
		testObject.execute();
		Assert.assertTrue(testObject.isFinished());
		Mockito.verify(mockDt).drive(0, 0);
	}

}
