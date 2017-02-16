package org.usfirst.frc.team868.robot.commands.auton;

import org.usfirst.frc.team868.robot.commands.AutonChooser.StartingPoint;
import org.usfirst.frc.team868.robot.commands.subsystems.drive.DriveDistanceCommand;
import org.usfirst.frc.team868.robot.commands.subsystems.drive.RotateUsingColorPixy;
import org.usfirst.frc.team868.robot.commands.subsystems.drive.TurnToAngle;
import org.usfirst.frc.team868.robot.commands.subsystems.gear.GearReleaseCommand;
import org.usfirst.frc.team868.robot.subsystems.ColorPixySubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CrossBaselineCommandGroup extends CommandGroup {

    public CrossBaselineCommandGroup(StartingPoint selected) {
    	switch(selected) {
    		case B1:
    			addSequential(new DriveDistanceCommand(250));
    			break;
    		case B2:
    			addSequential(new TurnToAngle(-45));
    			addSequential(new DriveDistanceCommand(180));
    			break;
    		case B3:
    			addSequential(new DriveDistanceCommand(250));
    			break;
    		case R1:
    			addSequential(new DriveDistanceCommand(250));
    			break;
    		case R2:
    			addSequential(new TurnToAngle(-45));
    			addSequential(new DriveDistanceCommand(180));
    			break;
    		case R3:
    			addSequential(new DriveDistanceCommand(250));
    			break;
    		default:
    			break;
    	}
    }
}

