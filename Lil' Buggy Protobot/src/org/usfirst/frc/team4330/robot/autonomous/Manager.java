package org.usfirst.frc.team4330.robot.autonomous;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team4330.robot.Arm;
import org.usfirst.frc.team4330.robot.BallControl;
import org.usfirst.frc.team4330.robot.DriveTrain;
import org.usfirst.frc.team4330.robot.SmartDashboardSetup;
import org.usfirst.frc.team4330.robot.raspberrypi.SensorDataRetriever;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Manager {

	private AutonomousState state = AutonomousState.Initial;

	Scheduler scheduler;
	private List<Command> commands = new ArrayList<Command>();
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
	private double gyroanglestart;

	// autoline is at 1'2"
	private static final double autoLineY = 1 + 2 / 12;

	// TODO determine crossing defense distance
	// defenses start at 7'2" and are 4'2" deep so end and target ending at 1'
	// past defense
	private static final double crossDefenseDistance = 7 + 2 / 12 + 4 + 2 / 12
			+ 1 - autoLineY;

	// confirm defense width is 4' 5"
	private static final double defenseWidth = 4 + 5 / 12;
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

	private static final double idealAngle = 0;

	// TODO determine the distance to drive in reverse after rotating 180
	// degrees prior to shooting
	private static final double distanceToDriveInReversePriorToShoot = 2;

	private Timer timer = new Timer();
	private DriveStraight driveInCommand;

	public Manager(DriveTrain dT, Gyro gyro,
			SmartDashboardSetup smartDashboardSetup,
			SensorDataRetriever sensorDataRetriever, BallControl ballControl,
			Arm arm, Scheduler scheduler) {
		this.driveTrain = dT;
		this.gyro = gyro;
		this.smartDashboardSetup = smartDashboardSetup;
		this.vision = sensorDataRetriever;
		this.ballControl = ballControl;
		this.arm = arm;
		this.scheduler = scheduler;
		gyroanglestart = gyro.getAngle();
	}

	public void autonomousInit() {
		// start actual autonomous program
		gyro.reset();
		timer = new Timer();
		scheduler.enable();
		setInitialPosition();
		loadCommandsToGetOverDefence();
	}

	public void autonomousPeriodic() {
		scheduler.run();
	}

	public void testInit() {
		scheduler.enable();
		testInitialized = false;

	}

	public void testPeriodic() {

		scheduler.run();
		System.out.println("Gyro " + gyro.getAngle());
		if (testInitialized) {

			return;
		}
		testInitialized = true;
		commands.clear();
		// gyro.calibrate();
		// commands.add(new WaitCommand(5.0));
		commands.add(new DriveStraight(driveTrain, gyro, 9, 0));
		// commands.add(new RoughAlign(driveTrain, gyro, -90));
		// commands.add(new FineAlign(driveTrain, gyro, -90));
		scheduleCommands();

		/*
		 * System.out.println("Initializing"); gyro.calibrate();
		 * System.out.println("Calibration complete");
		 * 
		 * testInitialized = true; commands.clear();
		 * loadCommandsForRoughTerrain(); //commands.add(new WaitCommand(5.0));
		 * //commands.add(new RoughAlign(driveTrain, gyro, -90));
		 * //commands.add(new DriveStraight(driveTrain, gyro, 5, 20));
		 * scheduleCommands();
		 */
	}

	public void disableInit() {
		timer.cancel();
		scheduler.disable();
	}

	private void loadCommandsToGetOverDefence() {
		String defense = smartDashboardSetup.getAutoDefense();
		switch (defense) {
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

		// TODO uncomment this once defense testing is complete
		// TODO recomment if in testing area
		
		if (smartDashboardSetup.getGoalChoice() == smartDashboardSetup.turn)
			turnToHeading(180);
		else if (smartDashboardSetup.getGoalChoice() != smartDashboardSetup.none)
			commands.add(new CallbackToManager(this));
		
		scheduleCommands();

	}

	private void loadCommandsToGetToLookingAtTarget() {
		double[] values = determineLookingAtTargetPositionAndHeading();
		double newX = values[0];
		double newY = values[1];
		double newHeading = values[2];

		double[] directionAndDistance = calculateDirectionAndDistance(x, y,
				newX, newY);
		double direction = directionAndDistance[0];
		double distance = directionAndDistance[1];

		turnToHeading(direction);
		commands.add(new DriveStraight(driveTrain, gyro, distance, direction));
		turnToHeading(newHeading);
//		commands.add(new WaitCommand(1));
		System.out.println("got to callback");
		commands.add(new CallbackToManager(this));
		scheduleCommands();
	}

	private void loadCommandsToShoot() {
		String relativeBearingStr = vision.retrieveData().get(
				SensorDataRetriever.RELATIVE_BEARING);
		if (relativeBearingStr != null) {
			System.out.println("doing shooting");
			double relativeBearing = Double.parseDouble(relativeBearingStr);
			double newHeading = HeadingCalculator.normalize(gyro.getAngle()
					+ relativeBearing);
			turnToHeading(newHeading);
			driveInCommand = new DriveStraight(driveTrain, gyro, 20, newHeading);
			commands.add(driveInCommand);
			double turnHeading = HeadingCalculator.normalize(newHeading + 190);
			turnToHeading(turnHeading);
//			commands.add(new DriveStraight(driveTrain, gyro, -1
//					* distanceToDriveInReversePriorToShoot, turnHeading));
			commands.add(new Shoot(ballControl));
			scheduleCommands();
			timer.schedule(new DriveInMonitorTask(), 20, 20);
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void callback() {
		if (givingUp) {
			return;
		}

		switch (state) {
		case Initial:
			y += crossDefenseDistance;
			state = AutonomousState.CrossedDefense;
			loadCommandsToGetToLookingAtTarget();
			break;
		case CrossedDefense:
			state = AutonomousState.LookingAtTarget;
			loadCommandsToShoot();
			break;
		default:
			throw new RuntimeException("Unexpected Manager state " + state);
		}
	}

	private void turnToHeading(double heading) {
		commands.add(new RoughAlign(driveTrain, gyro, heading));
		commands.add(new FineAlign(driveTrain, gyro, heading));
	}

	protected double[] calculateDirectionAndDistance(double currentX,
			double currentY, double desiredX, double desiredY) {

		double deltaX = desiredX - currentX;
		double deltaY = desiredY - currentY;

		double newDirection = Math.atan(Math.abs(deltaX) / Math.abs(deltaY))
				* 180 / Math.PI;
		if (deltaX < 0) {
			newDirection = -1 * newDirection;
		}

		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		return new double[] { newDirection, distance };
	}

	private double[] determineLookingAtTargetPositionAndHeading() {

		double x = 0;
		double y = 0;

		if (isLeftTargetActive()) {
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
		// int startingPosition = smartDashboardSetup.getAutoPosition();
		String goalChoice = smartDashboardSetup.getGoalChoice();
		boolean result = true;

		switch (goalChoice) {
		case SmartDashboardSetup.defult:
			result = getDefaultLeftTargetActive();
			break;
		case SmartDashboardSetup.right:
			result = false;
			break;
		case SmartDashboardSetup.left:
			result = true;
			break;
		}

		return result;
	}

	private boolean getDefaultLeftTargetActive() {
		int startingPosition = smartDashboardSetup.getAutoPosition();

		switch (startingPosition) {
		case SmartDashboardSetup.four:
		case SmartDashboardSetup.five:
			return false;
		}

		return true;
	}

	private void loadCommandsForLowbar() {
		commands.add(new DriveStraight(driveTrain, gyro,
				crossDefenseDistance + 3, gyroanglestart));
	}

	private void loadCommandsForLowbar2() {
		commands.add(new DriveStraight(driveTrain, gyro,
				crossDefenseDistance + 3, 0));
		commands.add(new DriveStraight(driveTrain, gyro,
				-crossDefenseDistance - 3, 0));
		commands.add(new DriveStraight(driveTrain, gyro,
				crossDefenseDistance + 3, 0));
	}

	private void loadCommandsForRockWall() {
		commands.add(new DriveStraight(driveTrain, gyro, crossDefenseDistance-3, 0));
		commands.add(new WaitCommand(.3));
		commands.add(new DriveStraight(driveTrain, gyro, 5, 0));
		// commands.add(new WaitCommand(2));
		// commands.add(new Stop(driveTrain));
	}

	private void loadCommandsForRoughTerrain() {
		commands.add(new RammingSpeed(driveTrain, gyro, 12));
		// commands.add(new WaitCommand(0.5));
		// commands.add(new RammingSpeed(driveTrain, gyro, 1.5));
		// commands.add(new Stop(driveTrain));
	}

	private void loadCommandsForPortcullis() {
		commands.add(new MoveArm(arm, false));
		// give time for arm to move down before driving forward
		commands.add(new WaitCommand(0.3));
		commands.add(new DriveStraight(driveTrain, gyro, 5.8, 0));
		commands.add(new PowerArm(arm, true, 1));
		commands.add(new DriveStraight(driveTrain, gyro, .5, 0));
		commands.add(new WaitCommand(.2));
		commands.add(new RammingSpeed(driveTrain, gyro, 4));
		commands.add(new DriveStraight(driveTrain, gyro, 3, 0));
	}

	private void loadCommandsForChevalDeFrise() {
		commands.add(new DriveStraight(driveTrain, gyro, 4.4, gyroanglestart));
		commands.add(new PowerArm(arm, false, .7));
		commands.add(new WaitCommand(.15));
		commands.add(new RammingSpeed(driveTrain, gyro, 10));
		commands.add(new WaitCommand(.3));
		commands.add(new NonPowerArm(arm, true, .5));
	}

	private void loadCommandsForMoat() {
		commands.add(new DriveStraight(driveTrain, gyro, 5));
		commands.add(new RammingSpeed(driveTrain, gyro, 15));
		// commands.add(new WaitCommand(2));
		// commands.add(new Stop(driveTrain));
	}

	private void loadCommandsForRamparts() {
		commands.add(new RammingSpeed(driveTrain, gyro, 15));
		turnToHeading(0);
		// commands.add(new WaitCommand(2));
		// commands.add(new Stop(driveTrain));
	}

	private void setInitialPosition() {
		int startingPosition = smartDashboardSetup.getAutoPosition();

		y = autoLineY;
		switch (startingPosition) {
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
			throw new RuntimeException("Starting position " + startingPosition
					+ " is not expected");
		}
	}

	private void scheduleCommands() {
		if (commands.isEmpty()) {
			return;
		}
		CommandGroup group = new CommandGroup();
		for (Command command : commands) {
			group.addSequential(command);
		}
		scheduler.add(group);
		commands.clear();
	}

	private class DriveInMonitorTask extends TimerTask {

		@Override
		public void run() {

			String verticalAngleString = vision.retrieveData().get(
					SensorDataRetriever.VERTICAL_ANGLE);
			boolean done = false;
			if (verticalAngleString == null) {
				done = true;
			} else {
				double verticalAngle = Double.parseDouble(verticalAngleString);
				if (verticalAngle > idealAngle) {
					done = true;
				}
			}

			if (done) {
				driveInCommand.stop();
				this.cancel();
				timer.cancel();
			}

		}

	}

}
