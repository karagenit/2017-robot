package org.usfirst.frc.team868.robot.commands.subsystems.gear;

import org.usfirst.frc.team868.robot.subsystems.GearEjectorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearEjectorOpenCommand extends Command {

private GearEjectorSubsystem ejector;
	
    public GearEjectorOpenCommand() {
        ejector = GearEjectorSubsystem.getInstance();
        requires(ejector);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	ejector.setGearEjectorOpen();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
