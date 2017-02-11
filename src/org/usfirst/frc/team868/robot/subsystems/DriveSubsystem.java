package org.usfirst.frc.team868.robot.subsystems;

import org.usfirst.frc.team868.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import randomUtils.RecordMotorMovement;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
	
	private static DriveSubsystem instance;
	private Spark leftMotor;
	private Spark rightMotor;
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private DriveSubsystem(){
		leftMotor = new Spark(RobotMap.Drive.LEFT_MOTOR);
		rightMotor = new Spark(RobotMap.Drive.RIGHT_MOTOR);
		leftMotor.setInverted(RobotMap.Drive.LEFT_IS_INVERTED);
		rightMotor.setInverted(RobotMap.Drive.RIGHT_IS_INVERTED);
		leftEncoder = new Encoder(RobotMap.Drive.ENCODER_L_A, RobotMap.Drive.ENCODER_L_B);
		rightEncoder = new Encoder(RobotMap.Drive.ENCODER_R_A, RobotMap.Drive.ENCODER_R_B);
		leftEncoder.setDistancePerPulse(RobotMap.Drive.CM_PER_COUNT);
		rightEncoder.setDistancePerPulse(RobotMap.Drive.CM_PER_COUNT);
	}
	
	/**
	 * Sets power to left motors
	 * @param speed 1 to -1
	 */
	public void setL(double speed){
		leftMotor.set(speed);
	}
	
	/**
	 * Sets power to right motors
	 * @param speed 1 to -1
	 */
	public void setR(double speed){
		rightMotor.set(speed);
	}
	
	/**
	 * Sets power to both motors
	 * @param speed 1 to -1
	 */
	public void setSpeed(double speed) {
		setR(speed);
		setL(speed);
	}
	
	/**
	 * Sets power to both motors 
	 * @param leftSpeed 1 to -1
	 * @param RightSpeed 1 to -1
	 */
	public void setSpeed(double leftSpeed, double rightSpeed) {
		setL(leftSpeed);
		setR(rightSpeed);
	}
		
	/**
	 * Gets speed of left drivetrain
	 * @return cm per second
	 */
	public double getLSpeed(){
		return leftEncoder.getRate();
	}
	
	/**
	 * Gets speed of right drivetrain
	 * @return cm per second
	 */
	public double getRSpeed(){
		return rightEncoder.getRate();
	}
	
	/**
	 * Gets average speed of drivetrain
	 * @return cm per second
	 */
	public double getAvgSpeed(){
		return (getRSpeed()+getLSpeed())/2;
	}
	
	/**
	 * Gets counts from right encoder
	 * @return counts
	 */
	public int getRightEncoder() {
		return rightEncoder.get();
	}
	
	/**
	 * Gets counts from left encoder
	 * @return counts
	 */
	public int getLeftEncoder() {
		return leftEncoder.get();
	}
	
	/**
	 * Gets distance traveled by right encoder
	 * @return cm
	 */
	public double getRightEncoderDistance() {
		return rightEncoder.getDistance();
	}
	
	/**
	 * Gets distance traveled by left encoder
	 * @return cm
	 */
	public double getLeftEncoderDistance() {
		return leftEncoder.getDistance();
	}
	
	/**
	 * Gets average encoder counts
	 * @return counts
	 */
	public int getAvgEncoders() {
		return (getRightEncoder() + getLeftEncoder()) / 2;
	}
	
	public void recordMotor(){
		RecordMotorMovement.getInstance().RecordMotors();
	}
	
	public static DriveSubsystem getInstance(){
		if(instance == null){
			instance = new DriveSubsystem();
		}
		return instance;
	}
	
	public void updateSD(){
		SmartDashboard.putNumber("Left Motor Speed", getLSpeed());
		SmartDashboard.putNumber("Right Motor Speed", getRSpeed());
//		SmartDashboard.putString("write/read for motor record", null);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

