package org.usfirst.frc.team4330.robot;

public class SmartDashboardSetup {
	SendableChooser defenseChooser;
	SendableChooser positionChooser;
	
	public int autoPosition;
	public String autoDefense;

	private String portcullis = "portcullis";
	private String chivalDeFrise = "chivalDeFrise";
	private String roughTerrain = "roughTerrain";
	private String moat = "moat";
	private String rampart = "rampart";
	private String rockWall = "rockWall";
	private String lowBar = "lowBar";
	private int one = 1;
	private int two = 2;
	private int three = 3;
	private int four = 4;
	private int five = 5;
	
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
		SmartDashboardSetup.putData("Autonomous Defense", defenseChooser);

		positionChooser = new SendableChooser();
		positionChooser.addDefault("One", one);
		positionChooser.addObject("Two", two);
		positionChooser.addObject("Three", three);
		positionChooser.addObject("Four", four);
		positionChooser.addObject("Five", five);
		SmartDashboardSetup.putData("Autonomous Position", positionChooser);
	}
	
	public void initiliaze() {
		 autoPosition = (int) positionChooser.getSelected();
		 autoDefense = (String) defenseChooser.getSelected();
	}
	
	public int getAutoPosition() {
		autoPosition = (int) positionChooser.getSelected();
		return autoPosition;
	}
	
	public String getAutoDefense() {
		autoDefense = (String) defenseChooser.getSelected();
		return autoDefense;
	}
}
