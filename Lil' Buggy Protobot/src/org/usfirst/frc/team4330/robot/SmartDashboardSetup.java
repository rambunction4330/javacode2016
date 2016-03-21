package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardSetup {
	private static SmartDashboardSetup instance;
	
	SendableChooser defenseChooser;
	SendableChooser positionChooser;
	SendableChooser goalChooser;

	public static final String portcullis = "portcullis";
	public static final String chivalDeFrise = "chivalDeFrise";
	public static final String roughTerrain = "roughTerrain";
	public static final String moat = "moat";
	public static final String rampart = "rampart";
	public static final String rockWall = "rockWall";
	public static final String lowBar = "lowBar";
	public static final String lowBar2 = "lowBar2";

	public static final int one = 1;
	public static final int two = 2;
	public static final int three = 3;
	public static final int four = 4;
	public static final int five = 5;
	public static final String none = "none";
	
	public static final String left = "left";
	public static final String right = "right";
	public static final String defult = "default";
	
	public static SmartDashboardSetup getInstance() {
		if (instance == null)
			instance = new SmartDashboardSetup();
		return instance;
	}
	
	private SmartDashboardSetup() {
		instance = this;
		
		defenseChooser = new SendableChooser();
		defenseChooser.addObject("No Autonomous", none);
		defenseChooser.addDefault("Low Bar", lowBar);
		defenseChooser.addObject("Portcullis", portcullis);
		defenseChooser.addObject("Chival de Frise (four moving planes)",
				chivalDeFrise);
		defenseChooser.addObject("Moat", moat);
		defenseChooser.addObject("Rampart (two non-moving planes)", rampart);
		defenseChooser.addObject("Rock Wall", rockWall);
		defenseChooser.addObject("Rough Terrain", roughTerrain);
		SmartDashboard.putData("Autonomous Defense", defenseChooser);

		positionChooser = new SendableChooser();
		positionChooser.addDefault("One", one);
		positionChooser.addObject("Two", two);
		positionChooser.addObject("Three", three);
		positionChooser.addObject("Four", four);
		positionChooser.addObject("Five", five);
		SmartDashboard.putData("Autonomous Position", positionChooser);
		
		goalChooser = new SendableChooser();
		goalChooser.addObject("No Goal", none);
		goalChooser.addDefault("Default", defult);
		goalChooser.addObject("Left Goal", left);
		goalChooser.addObject("Right Goal", right);
		SmartDashboard.putData("Goal Chooser", goalChooser);
	}
	
	public int getAutoPosition() {
		return (int) positionChooser.getSelected();
	}
	
	public String getAutoDefense() {
		return (String) defenseChooser.getSelected();
	}
	
	public String getGoalChoice() {
		return (String) goalChooser.getSelected();
	}
}
