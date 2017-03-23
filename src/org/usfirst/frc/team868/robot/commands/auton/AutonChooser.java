package org.usfirst.frc.team868.robot.commands.auton;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutonChooser {

	private static AutonChooser instance;
	
	private static SendableChooser<StartingPoint> chooseStart;
	private static SendableChooser<DoThis> chooseDoThis;
	
	public static AutonChooser getInstance() {
		if(instance == null) instance = new AutonChooser();
		return instance;
	}
    private AutonChooser() { 
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	setupDashboard();
    }
    
    public static enum StartingPoint{
    	B1, B2, B3, R1, R2, R3
    }
    
    public static enum DoThis {
    	CROSS_BASE, DROP_GEAR, HOPPER, GEAR_TO_NEUTRAL, GEAR_TO_HOPPER
    }
    
    public static StartingPoint getStart() {
    	return ((StartingPoint) chooseStart.getSelected());
    }
    
    public static DoThis getDoThis() {
    	return ((DoThis) chooseDoThis.getSelected());
    }
    
    public void setupDashboard() {
    	chooseStart = new SendableChooser<StartingPoint>();
    		chooseStart.addObject("B1", StartingPoint.B1);
    		chooseStart.addObject("B2", StartingPoint.B2);
    		chooseStart.addObject("B3", StartingPoint.B3);
    		chooseStart.addObject("R1", StartingPoint.R1);
    		chooseStart.addObject("R2", StartingPoint.R2);
    		chooseStart.addObject("R3", StartingPoint.R3);
    		
    	chooseDoThis = new SendableChooser<DoThis>();
    		chooseDoThis.addObject("A: Cross the Baseline", DoThis.CROSS_BASE);
    		chooseDoThis.addObject("B: Drop off Gear", DoThis.DROP_GEAR);
    		chooseDoThis.addObject("C: Shoot Balls", DoThis.HOPPER);
    		chooseDoThis.addObject("D: Drop off Gear Then Go To Neutral", DoThis.GEAR_TO_NEUTRAL);
    		chooseDoThis.addObject("E: Drop Off Gear Then Hit Hopper", DoThis.GEAR_TO_HOPPER);
    	SmartDashboard.putData("Auton Start", chooseStart);
    	SmartDashboard.putData("Auton Mode", chooseDoThis);
    }

}