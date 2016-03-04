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
	
	// TODO determine crossing defense distance
	private static final double crossDefenseDistance = 5;
	private static final double idealAngle = 20; 
	
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
		loadDefenseCommands();
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
			loadLookingAtTargetCommands();
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
	
	private void loadShootCommands() {
		double currentHeading = new Align(driveTrain, gyro, 0.0).angleCalculator();
		double newHeading = currentHeading + 180;
		if ( newHeading > 180 ) {
			newHeading -= 360;
		}
		scheduler.add(new WaitCommand(1));
		scheduler.add(new Align(driveTrain, gyro, newHeading));
		// TODO determine distance to drive
		scheduler.add(new DriveStraight(driveTrain, -5));
		scheduler.add(new ShootCommand(ballControl));
	}
	
	private void loadLookingAtTargetCommands() {
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
		
		// TODO determine positions
		if ( isLeftTargetActive() ) {
			x = 2;
			y = 15;
			heading = 60;
		} else {
			x = 25;
			y = 15;
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
	
	private void loadDefenseCommands() {
		String defense = smartDashboardSetup.autoDefense;
		switch(defense) {
		case SmartDashboardSetup.lowBar:
			loadLowbarCommands();
			break;
		default:
			givingUp = true;
			break;
		}
		
		scheduler.add(new CallbackToManager(this));
		
	}
	
	private void loadLowbarCommands() {
		// TODO measure how far to drive forward
		scheduler.add(new DriveStraight(driveTrain, 5));
	}
	
	private void setInitialPosition() {
		int startingPosition = smartDashboardSetup.getAutoPosition();
		
		// TODO measure these positions
		y = 15;
		switch(startingPosition) {
		case SmartDashboardSetup.one:
			x = 2;
			break;
		case SmartDashboardSetup.two:
			x = 4;
			break;
		case SmartDashboardSetup.three:
			x = 6;
			break;
		case SmartDashboardSetup.four:
			x = 8;
			break;
		case SmartDashboardSetup.five:
			x = 10;
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
				loadShootCommands();
				this.cancel();
				timer.cancel();
			}
			
		}

	}
	
}
