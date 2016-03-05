package org.usfirst.frc.team4330.robot.autonomous;

public class HeadingCalculator {
	
	/**
	 * 
	 * @param rawCurrentHeading
	 * @param desiredHeading
	 * @return the course change with -180 < value <= 180
	 */
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
		
		if ( val == -180 ) {
			val = 180;
		} else if ( val < -180 ) {
			val += 360;
		} else if ( val > 180 ) {
			val -= 360;
		}
		
		return val;
	}
	
	/**
	 * 
	 * @param raw
	 * @return raw value normalized so -180 < value <= 180
	 */
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
		
		if ( raw == -180 ) {
			raw = 180;
		}

		return raw;
	}

}
