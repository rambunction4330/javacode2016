package org.usfirst.frc.team4330.robot.autonomous;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team4330.robot.BallControl;
import org.usfirst.frc.team4330.robot.DriveTrain;
import org.usfirst.frc.team4330.robot.SmartDashboardSetup;
import org.usfirst.frc.team4330.robot.raspberrypi.SensorDataRetriever;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Manager {
	
	private AutonomousState state = AutonomousState.Initial;
	
	Scheduler scheduler;
	private DriveTrain driveTrain;
	private Gyro gyro;
	private SmartDashboardSetup smartDashboardSetup;
	private SensorDataRetriever vision;
	private BallControl ballControl;
	
	private double x;
	private double y;
	private boolean givingUp = false;
	
	// autoline is at 1'2"
	private static final double autoLineY = 1 + 2/12;
	
	// TODO determine crossing defense distance
	// defenses start at 7'2" and are 4'2" deep so end and target ending at 1' past defense
	private static final double crossDefenseDistance = 7 + 2/12 + 4 + 2/12 + 1 - autoLineY;
	
	// confirm defense width is 4' 5"
	private static final double defenseWidth = 4 + 5/12;
	private static final double startingPosOneX = 0.5 * defenseWidth;
	private static final double startingPosTwoX = 1.5 * defenseWidth;
	private static final double startingPosThreeX = 2.5 * defenseWidth;
	private static final double startingPosFourX = 3.5 * defenseWidth;
	private static final double startingPosFiveX = 4.5 * defenseWidth;
	
	// determine approach positions
	private static final double leftTargetApproachX = 5.63;
	private static final double leftTargetApproachY = 22.05;
	private static final double rightTargetApproachX = 21.22;
	private static final double rightTargetApproachY = 23.05;
	
	// TODO determine idea angle to stop for camera system
	private static final double idealAngle = 20; 
		
	// TODO determine the distance to drive in reverse after rotating 180 degrees prior to shooting
	private static final double distanceToDriveInReversePriorToShoot = 2;
	
	private Timer timer;
	private Command driveInCommand;

	public Manager(DriveTrain dT, Gyro gyro, SmartDashboardSetup smartDashboardSetup, SensorDataRetriever sensorDataRetriever, 
			BallControl ballControl, Scheduler scheduler) {
		this.driveTrain = dT;
		this.gyro = gyro;
		this.smartDashboardSetup = smartDashboardSetup;
		this.vision = sensorDataRetriever;
		this.ballControl = ballControl;
		this.scheduler = scheduler;
	}
	
	public void autonomousInit() {	
		// start actual autonomous program
		timer = new Timer();
		scheduler.enable();
		setInitialPosition();
		loadCommandsToGetOverDefence();
	}

	public void autonomousPeriodic() {
		scheduler.run();
	}
	
	public void testInit() {
	 	gyro.calibrate();
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 30));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 60));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 90));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 120));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 150));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 180));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, -150));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, -120));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, -90));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, -60));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, -30));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 0));
		
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, -30));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, -60));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, -90));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, -120));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, -150));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 180));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 150));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 120));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 90));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 60));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 30));
		scheduler.add(new WaitCommand(5.0));
		scheduler.add(new Align(driveTrain, gyro, 0));
	}
	
	public void disableInit() {
		timer.cancel();
		timer = null;
		scheduler.disable();
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void callback() {
		if ( givingUp ) {
			return;
		}
		
		switch(state) {
		case Initial:	
			y += crossDefenseDistance;
			state = AutonomousState.CrossedDefense;
			loadCommandsToGetToLookingAtTarget();
			break;
		case CrossedDefense:
			state = AutonomousState.LookingAtTarget;
			performVisionControlledDriveIn();
			break;
		default:
			throw new RuntimeException("Unexpected Manager state " + state);
		}
	}
	
	private void performVisionControlledDriveIn() {
		String relativeBearingStr = vision.retrieveData().get(SensorDataRetriever.RELATIVE_BEARING);
		if ( relativeBearingStr != null ) {
			double relativeBearing = Double.parseDouble(relativeBearingStr);
			double heading = new Align(driveTrain, gyro, 0.0).angleCalculator();
			double newHeading = heading + relativeBearing;
			scheduler.add(new Align(driveTrain, gyro, newHeading));
			driveInCommand = new DriveStraight(driveTrain, 20);
			scheduler.add(driveInCommand);
			
			timer.schedule(new DriveInMonitorTask(), 20, 20);
		}
	}
	
	private void loadCommandsToGetOverDefence() {
		String defense = smartDashboardSetup.autoDefense;
		switch(defense) {
		case SmartDashboardSetup.lowBar:
			loadCommandsForLowbarDefense();
			break;
		default:
			givingUp = true;
			break;
		}
		
		scheduler.add(new CallbackToManager(this));
		
	}
	
	private void loadCommandsToGetToShoot() {
		double currentHeading = new Align(driveTrain, gyro, 0.0).angleCalculator();
		double newHeading = currentHeading + 180;
		if ( newHeading > 180 ) {
			newHeading -= 360;
		}
		scheduler.add(new Align(driveTrain, gyro, newHeading));
		scheduler.add(new DriveStraight(driveTrain, -1 * distanceToDriveInReversePriorToShoot));
		scheduler.add(new ShootCommand(ballControl));
	}
	
	private void loadCommandsToGetToLookingAtTarget() {
		double[] values = determineLookingAtTargetPositionAndHeading();
		double newX = values[0];
		double newY = values[1];
		double newHeading = values[2];
		
		double[] directionAndDistance = calculateDirectionAndDistance(x, y, newX, newY);
		double direction = directionAndDistance[0];
		double distance = directionAndDistance[1];
		
		scheduler.add(new Align(driveTrain, gyro, direction));
		scheduler.add(new DriveStraight(driveTrain, distance));
		scheduler.add(new Align(driveTrain, gyro, newHeading));
		scheduler.add(new WaitCommand(1));
		scheduler.add(new CallbackToManager(this));
	}
	
	protected double[] calculateDirectionAndDistance(double currentX, double currentY, double desiredX, double desiredY) {
		
		double deltaX = desiredX - currentX;
		double deltaY = desiredY - currentY;
		
		double newDirection = Math.atan(Math.abs(deltaX) / Math.abs(deltaY)) * 180 / Math.PI;
		if ( deltaX < 0) {
			newDirection = -1 * newDirection;
		}
		
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		return new double[] { newDirection, distance };
	}
	
	private double[] determineLookingAtTargetPositionAndHeading() {
		
		double x = 0;
		double y = 0;
		double heading = 0;
		
		if ( isLeftTargetActive() ) {
			x = leftTargetApproachX;
			y = leftTargetApproachY;
			heading = 60;
		} else {
			x = rightTargetApproachX;
			y = rightTargetApproachY;
			heading = -60;
		}
		
		return new double[] { x, y, heading };
	}
	
	protected boolean isLeftTargetActive() {
		int startingPosition = smartDashboardSetup.getAutoPosition();
		boolean result = true;
		switch ( startingPosition ) {
			case SmartDashboardSetup.four:
			case SmartDashboardSetup.five:
				result = false;
				break;
		}
		return result;
	}
	
	private void loadCommandsForLowbarDefense() {
		scheduler.add(new DriveStraight(driveTrain, crossDefenseDistance));
	}
	
	private void setInitialPosition() {
		int startingPosition = smartDashboardSetup.getAutoPosition();
		
		y = autoLineY;
		switch(startingPosition) {
		case SmartDashboardSetup.one:
			x = startingPosOneX;
			break;
		case SmartDashboardSetup.two:
			x = startingPosTwoX;
			break;
		case SmartDashboardSetup.three:
			x = startingPosThreeX;
			break;
		case SmartDashboardSetup.four:
			x = startingPosFourX;
			break;
		case SmartDashboardSetup.five:
			x = startingPosFiveX;
			break;
		default:
			throw new RuntimeException("Starting position " + startingPosition + " is not expected");
		}
	}
	
	private class DriveInMonitorTask extends TimerTask {

		@Override
		public void run() {
			
			String verticalAngleString = vision.retrieveData().get(SensorDataRetriever.VERTICAL_ANGLE);
			boolean done = false;
			if ( verticalAngleString == null ) {
				done = true;
			} else {
				double verticalAngle = Double.parseDouble(verticalAngleString);
				if ( verticalAngle > idealAngle ) {
					done = true;
				}
			}
			
			if ( done ) {	
				driveInCommand.cancel();
				driveTrain.stop();
				loadCommandsToGetToShoot();
				this.cancel();
				timer.cancel();
			}
			
		}

	}
	
}
