package org.usfirst.frc.team868.robot.commands.operator;

import org.usfirst.frc.team868.robot.commands.subsystems.shooter.ShooterSetSpeed;
import org.usfirst.frc.team868.robot.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleShooting extends Command {

    private ShooterSubsystem shooter;

	public ToggleShooting() {
    	shooter = ShooterSubsystem.getInstance();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shooter.toggleShooting();
    	if(shooter.isRunning()){
    		new ShooterSetSpeed(69).start();
    	}else{
    		new ShooterSetSpeed(0).start();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
