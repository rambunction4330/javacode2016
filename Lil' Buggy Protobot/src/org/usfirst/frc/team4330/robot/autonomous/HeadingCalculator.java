package org.usfirst.frc.team4330.robot.autonomous;

public class HeadingCalculator {
	
	public static double calculateCourseChange ( double rawCurrentHeading, double desiredHeading ) {
		double normalizedCurrentHeading = normalize(rawCurrentHeading);
		
		double desiredp = desiredHeading;
		double currentp = normalizedCurrentHeading;
		if (currentp > 0 && desiredp < 0) {
			desiredp = desiredHeading + 360;
		} else if ( currentp < 0 && desiredp > 0 ) {
			if ( currentp > -90 ) {
				desiredp = desiredHeading + 360;
			}
			currentp = normalizedCurrentHeading + 360;
		}

		double val = desiredp - currentp;
		boolean turnRight = (val > 0 && val < 180);	
		if ( turnRight ) {
			if ( val < 0 ) {
				val += 360;
			}		
		} else {
			if ( val > 0 ) {
				val -= 360;
			}
		}
		
		return val;
	}
	
	public static double normalize(double raw) {

		if (raw > 0) {
			raw = raw % 360;

			if (raw > 180) {
				raw -= 360;
			}
		} else {
			raw = raw % 360;

			if (raw < -180) {
				raw += 360;
			}
		}

		return raw;
	}

}
