package org.usfirst.frc.team4330.robot.autonomous;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team4330.robot.Arm;
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
	private Arm arm;
	
	private double x;
	private double y;
	private boolean givingUp = false;
	private double shootAngle;
	private boolean testInitialized = false;
	
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
	
	private Timer timer = new Timer();
	private Command driveInCommand;

	public Manager(DriveTrain dT, Gyro gyro, SmartDashboardSetup smartDashboardSetup, SensorDataRetriever sensorDataRetriever, 
			BallControl ballControl, Arm arm, Scheduler scheduler) {
		this.driveTrain = dT;
		this.gyro = gyro;
		this.smartDashboardSetup = smartDashboardSetup;
		this.vision = sensorDataRetriever;
		this.ballControl = ballControl;
		this.arm = arm;
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
		
		testInitialized = false;
		

//		turnToHeading(30);
//		scheduler.add(new WaitCommand(5.0));
//		turnToHeading(60);
//		scheduler.add(new WaitCommand(5.0));
//		turnToHeading(90);

	}
	
	public void testPeriodic() {
		scheduler.run();
		if ( testInitialized ) {
		
			return;
		}
		gyro.calibrate();
		scheduler.add(new WaitCommand(5.0));
		System.out.println("Waited");
		scheduler.add(new DriveStraight(driveTrain, gyro, 3, 0));
		testInitialized = true;
	}
	
	public void disableInit() {
		timer.cancel();
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
			double newHeading = HeadingCalculator.normalize(gyro.getAngle() + relativeBearing);
			turnToHeading(newHeading);
			driveInCommand = new DriveStraight(driveTrain, gyro, 20, newHeading);
			scheduler.add(driveInCommand);
			
			timer.schedule(new DriveInMonitorTask(), 20, 20);
		}
	}
	
	private void loadCommandsToGetOverDefence() {
		String defense = smartDashboardSetup.autoDefense;
		switch(defense) {
		case SmartDashboardSetup.portcullis:
			loadCommandsForPortcullis();
			break;
		case SmartDashboardSetup.chivalDeFrise:
			loadCommandsForChevalDeFrise();
			break;
		case SmartDashboardSetup.moat:
			loadCommandsForMoat();
			break;
		case SmartDashboardSetup.rampart:
			loadCommandsForRamparts();
			break;
		case SmartDashboardSetup.rockWall:
			loadCommandsForRockWall();
			break;
		case SmartDashboardSetup.roughTerrain:
			loadCommandsForRoughTerrain();
			break;
		case SmartDashboardSetup.lowBar:
			loadCommandsForLowbar();
			break;
		default:
			givingUp = true;
			break;
		}
		
		scheduler.add(new CallbackToManager(this));
		
	}
	
	private void loadCommandsToGetToShoot() {
		scheduler.add(new WaitCommand(1));
		double newHeading = HeadingCalculator.normalize(shootAngle + 180);
		turnToHeading(newHeading);
		scheduler.add(new DriveStraight(driveTrain, gyro, -1 * distanceToDriveInReversePriorToShoot, newHeading));
		scheduler.add(new ShootCommand(ballControl));
	}
	
	private void turnToHeading(double heading) {
		scheduler.add(new RoughAlign(driveTrain, gyro, heading));
		scheduler.add(new FineAlign(driveTrain, gyro, heading));
	}
	
	private void loadCommandsToGetToLookingAtTarget() {
		double[] values = determineLookingAtTargetPositionAndHeading();
		double newX = values[0];
		double newY = values[1];
		double newHeading = values[2];
		
		double[] directionAndDistance = calculateDirectionAndDistance(x, y, newX, newY);
		double direction = directionAndDistance[0];
		double distance = directionAndDistance[1];
		
		turnToHeading(direction);
		scheduler.add(new DriveStraight(driveTrain, gyro, distance, direction));
		turnToHeading(newHeading);
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
		
		if ( isLeftTargetActive() ) {
			x = leftTargetApproachX;
			y = leftTargetApproachY;
			shootAngle = 60;
		} else {
			x = rightTargetApproachX;
			y = rightTargetApproachY;
			shootAngle = -60;
		}
		
		return new double[] { x, y, shootAngle };
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
	
	private void loadCommandsForLowbar() {
		scheduler.add(new DriveStraight(driveTrain, gyro, crossDefenseDistance, 0));
	}
	
	private void loadCommandsForRockWall() {
		scheduler.add(new RammingSpeed(driveTrain));
		scheduler.add(new WaitCommand(2));
		scheduler.add(new Stop(driveTrain));
	}
	
	private void loadCommandsForRoughTerrain() {
		scheduler.add(new RammingSpeed(driveTrain));
		scheduler.add(new WaitCommand(0.5));
		scheduler.add(new OscillatingRammingSpeed(driveTrain, 1.5));
		scheduler.add(new Stop(driveTrain));
	}
	
	private void loadCommandsForPortcullis() {
		scheduler.add(new MoveArm(arm, false));
		// give time for arm to move down before driving forward
		scheduler.add(new WaitCommand(0.2));
		scheduler.add(new DriveStraight(driveTrain, gyro, 3.5, 0));
		scheduler.add(new PowerArm(arm, true, 0.5));
		scheduler.add(new DriveStraight(driveTrain, gyro, 3.0, 0));
	}
	
	private void loadCommandsForChevalDeFrise() {
		scheduler.add(new DriveStraight(driveTrain, gyro, 3.5, 0));
		scheduler.add(new PowerArm(arm, false, 0.5));
		scheduler.add(new RammingSpeed(driveTrain));
		scheduler.add(new WaitCommand(2));
		scheduler.add(new Stop(driveTrain));
	}
	
	private void loadCommandsForMoat() {
		scheduler.add(new RammingSpeed(driveTrain));
		scheduler.add(new WaitCommand(2));
		scheduler.add(new Stop(driveTrain));
	}
	
	private void loadCommandsForRamparts() {
		scheduler.add(new RammingSpeed(driveTrain));
		scheduler.add(new WaitCommand(2));
		scheduler.add(new Stop(driveTrain));
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
