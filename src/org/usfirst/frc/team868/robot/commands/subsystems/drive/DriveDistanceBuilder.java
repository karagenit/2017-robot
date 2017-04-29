package org.usfirst.frc.team868.robot.commands.subsystems.drive;

import org.usfirst.frc.team868.robot.RobotMap;
import org.usfirst.frc.team868.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team868.robot.subsystems.GearEjectorSubsystem;
import org.usfirst.frc.team868.robot.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveDistanceBuilder extends TimedCommand {

	private DriveSubsystem drive;
	private GyroSubsystem gyro;
	private double endCount;
	private PIDController control;
	private double power = 0;
	private final double kp = .02, ki = 0, kd = .05, kf = 0;
	private double distanceCM;
	private boolean disableOnPlate = false;
	private double speed = RobotMap.Drive.MAX_AUTON_DRIVE_SPEED;
	
	/**
	 * Drives the given distance in centimeters using a PID controller.
	 * @param cm in centimeters
	 */
	public DriveDistanceBuilder(double cm, double timeout, boolean usePressurePlate, double speed) { //TODO could we instead use a trigger -> on plate press, run StopDriving command?
		super(timeout);
		distanceCM = cm;
		drive = DriveSubsystem.getInstance();
		requires(drive);
		gyro = GyroSubsystem.getInstance();
		control = new PIDController(kp , ki, kd, kf, new PIDSource(){
			public void setPIDSourceType(PIDSourceType pidSource) {}

			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			public double pidGet() {
				return drive.getAvgEncoders()*RobotMap.Drive.CM_PER_COUNT;
			}
		}, new PIDOutput(){
			public void pidWrite(double output) {
				power = output;
			}
		});
		control.setAbsoluteTolerance(4);
		disableOnPlate = usePressurePlate;
		this.speed = speed;
	}
	
	public static class Builder {
		private double distance = 0;
		private double timeout = 4;
		private double speed = RobotMap.Drive.MAX_AUTON_DRIVE_SPEED;
		private boolean usePlate = false;
		
		public Builder() {}
		
		public Builder setDistance(double cm) {
			distance = cm; return this;
		}
		
		public Builder setTimeout(double seconds) {
			timeout = seconds; return this;
		}
		
		public Builder setSpeed(double cps) {
			speed = cps; return this;
		}
		
		public Builder usePlate(boolean enablePlate) {
			usePlate = enablePlate; return this;
		}
		
		public DriveDistanceBuilder build() {
			return new DriveDistanceBuilder(distance, timeout, usePlate, speed);
		}
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	drive.resetEncoders();
		endCount = distanceCM;
		gyro.reset();
    	//SmartDashboard.putData("Drive distance PID", control);
    	control.setSetpoint(endCount);
    	control.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if(power > speed)
			power = speed;
		if(power < -speed)
			power = -speed;
		if(power < -.02 && power > -RobotMap.Drive.MIN_DRIVE_SPEED)
			power = -RobotMap.Drive.MIN_DRIVE_SPEED;
		if(power > .02 && power < RobotMap.Drive.MIN_DRIVE_SPEED)
			power = RobotMap.Drive.MIN_DRIVE_SPEED;
		double rPower = power, lPower = power;
		double rotation = gyro.getRotation();
		double multiplier = 1 - Math.abs(5*Math.sin(rotation*Math.PI/180));
    	if(rotation > 1){
    		if(power > 0)
    			lPower = lPower*multiplier;
    		else
    			rPower = rPower*multiplier;
    	}else if(rotation < -1){
    		if(power > 0)
    			rPower = rPower*multiplier;
    		else
    			lPower = lPower*multiplier;
    	}
		drive.setSpeed(.9*lPower, rPower);
		SmartDashboard.putNumber("Auton Driven Distance", drive.getAvgEncoders()*RobotMap.Drive.CM_PER_COUNT);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs(control.getError()) < 1 || (disableOnPlate && GearEjectorSubsystem.getInstance().isPlatePressed());
    }

    // Called once after isFinished returns true
    protected void end() {
    	control.disable();
    	drive.setSpeed(0);
    }
}
