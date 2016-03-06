package org.usfirst.frc.team4330.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardSetup {
	SendableChooser defenseChooser;
	SendableChooser positionChooser;

	public static final String portcullis = "portcullis";
	public static final String chivalDeFrise = "chivalDeFrise";
	public static final String roughTerrain = "roughTerrain";
	public static final String moat = "moat";
	public static final String rampart = "rampart";
	public static final String rockWall = "rockWall";
	public static final String lowBar = "lowBar";
	public static final int one = 1;
	public static final int two = 2;
	public static final int three = 3;
	public static final int four = 4;
	public static final int five = 5;
	
	public SmartDashboardSetup() {
		defenseChooser = new SendableChooser();
		defenseChooser.addDefault("Portcullis", portcullis);
		defenseChooser.addObject("Chival de Frise (four moving trains)",
				chivalDeFrise);
		defenseChooser.addObject("Rough Terrain", roughTerrain);
		defenseChooser.addObject("Moat", moat);
		defenseChooser.addObject("Rampart (two non-moving trains)", rampart);
		defenseChooser.addObject("Rock Wall", rockWall);
		defenseChooser.addObject("Low Bar", lowBar);
		SmartDashboard.putData("Autonomous Defense", defenseChooser);

		positionChooser = new SendableChooser();
		positionChooser.addDefault("One", one);
		positionChooser.addObject("Two", two);
		positionChooser.addObject("Three", three);
		positionChooser.addObject("Four", four);
		positionChooser.addObject("Five", five);
		SmartDashboard.putData("Autonomous Position", positionChooser);
	}
	
	public int getAutoPosition() {
		return (int) positionChooser.getSelected();
	}
	
	public String getAutoDefense() {
		return (String) defenseChooser.getSelected();
	}
}
