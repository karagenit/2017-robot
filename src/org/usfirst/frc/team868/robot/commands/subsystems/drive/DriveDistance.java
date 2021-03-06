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
public class DriveDistance extends TimedCommand {

	private DriveSubsystem drive;
	private GyroSubsystem gyro;
	private double endCount;
	private PIDController control;
	private double power = 0;
	private final double kp = .02, ki = 0, kd = .05, kf = 0;
	private double distanceCM;
	private boolean disableOnPlate = false;
	private double offsetAdjustor = .9;
	private boolean DEBUG = true;
	private int onTargetCounts = 0;
	
	/**
	 * Drives the given distance in centimeters using a PID controller.
	 * @param cm in centimeters
	 */
	public DriveDistance(double cm, double timeout, boolean usePressurePlate) { //TODO could we instead use a trigger -> on plate press, run StopDriving command?
		super(timeout);
		if(DEBUG){
			SmartDashboard.putNumber("Auton offset multiplier", 
					SmartDashboard.getNumber("Auton offset multiplier", 0.9));
		}
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
		control.setAbsoluteTolerance(2); //TODO update builder
		disableOnPlate = usePressurePlate;
	}
	//TODO should we be using a builder?
	public DriveDistance(double cm) {
		this(cm, 4, false);
	}
	
	public DriveDistance(double cm, boolean usePressurePlate) {
		this(cm, 4, usePressurePlate);
	}
	
	public DriveDistance(double cm, double timeout) {
		this(cm, timeout, false);
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(DEBUG){
    		offsetAdjustor = SmartDashboard.getNumber("Auton offset multiplier", offsetAdjustor);
    	}
    	drive.resetEncoders();
		endCount = distanceCM;
		gyro.reset();
    	//SmartDashboard.putData("Drive distance PID", control);
    	control.setSetpoint(endCount);
    	control.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if(power > RobotMap.Drive.MAX_AUTON_DRIVE_SPEED)
			power = RobotMap.Drive.MAX_AUTON_DRIVE_SPEED;
		if(power < -RobotMap.Drive.MAX_AUTON_DRIVE_SPEED)
			power = -RobotMap.Drive.MAX_AUTON_DRIVE_SPEED;
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
		drive.setSpeed(offsetAdjustor*lPower, rPower);
		SmartDashboard.putNumber("Auton Driven Distance", drive.getAvgEncoders()*RobotMap.Drive.CM_PER_COUNT);
		
		if(control.onTarget()) {
			onTargetCounts++;
		} else {
			onTargetCounts = 0;
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return super.isFinished() || 
    			onTargetCounts > 3 || 
    			(disableOnPlate && GearEjectorSubsystem.getInstance().isPlatePressed());
    }

    // Called once after isFinished returns true
    protected void end() {
    	control.disable();
    	drive.setSpeed(0);
    }
}
