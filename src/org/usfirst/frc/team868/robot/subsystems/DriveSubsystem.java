package org.usfirst.frc.team868.robot.subsystems;

import org.usfirst.frc.team868.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
	
	private static DriveSubsystem instance;
	private CANTalon leftMotor;
	private CANTalon rightMotor;
	
	private DriveSubsystem(){
		leftMotor = new CANTalon(RobotMap.Drive.LEFT_MOTOR);
		rightMotor = new CANTalon(RobotMap.Drive.RIGHT_MOTOR);
	}
	
	public static DriveSubsystem getInstance(){
		if(instance == null){
			instance = new DriveSubsystem();
		}
		return instance;
	}
	
	public void setL(double speed){
		leftMotor.set(speed);
	}
	
	public void setR(double speed){
		rightMotor.set(speed);
	}
	
	public double getLSpeed(){
		return leftMotor.getSpeed();
	}
	
	public double getRSpeed(){
		return rightMotor.getSpeed();
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

