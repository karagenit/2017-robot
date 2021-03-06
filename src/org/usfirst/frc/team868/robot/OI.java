package org.usfirst.frc.team868.robot;

import org.usfirst.frc.team868.robot.commands.auton.AutonChooser;
import org.usfirst.frc.team868.robot.commands.operator.CallJoystickTurretControl;
import org.usfirst.frc.team868.robot.commands.operator.ToggleGearAutoEject;
import org.usfirst.frc.team868.robot.commands.operator.ToggleShooting;
import org.usfirst.frc.team868.robot.commands.subsystems.*;
import org.usfirst.frc.team868.robot.commands.subsystems.turret.*;
import org.usfirst.frc.team868.robot.commands.util.ToggleFeederAndAgitator;
import org.usfirst.frc.team868.robot.commands.subsystems.gear.*;
import org.usfirst.frc.team868.robot.commands.subsystems.shooter.ShooterFlashlightCommand;
import org.usfirst.frc.team868.robot.commands.subsystems.shooter.ShooterIncrementSpeed;
import org.usfirst.frc.team868.robot.commands.subsystems.shooter.ShooterSetVoltageCommand;

import lib.hid.ControllerMap;
import lib.hid.DPadButton;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

//// CREATING BUTTONS
// One type of button is a joystick button which is any button on a joystick.
// You create one by telling it which joystick it's on and which button
// number it is.
// Joystick stick = new Joystick(port);
// Button button = new JoystickButton(stick, buttonNumber);

// There are a few additional built in buttons you can use. Additionally,
// by subclassing Button you can create custom triggers and bind those to
// commands the same as any other Button.

//// TRIGGERING COMMANDS WITH BUTTONS
// Once you have a button, it's trivial to bind it to a button in one of
// three ways:

// Start the command when the button is pressed and let it run the command
// until it is finished as determined by it's isFinished method.
// button.whenPressed(new ExampleCommand());

// Run the command while the button is being held down and interrupt it once
// the button is released.
// button.whileHeld(new ExampleCommand());

// Start the command when the button is released  and let it run the command
// until it is finished as determined by it's isFinished method.
// button.whenReleased(new ExampleCommand());
public class OI {
	
	static OI instance;
	private ControllerMap driver, operator;
	
	public OI() {
		driver = new ControllerMap(new Joystick(RobotMap.JoystickPort.DRIVER),
						ControllerMap.Type.XBOX_ONE);
		operator = new ControllerMap(new Joystick(RobotMap.JoystickPort.OPERATOR),
						ControllerMap.Type.XBOX_ONE);
	}
	
	void initialize() {
		setupDriver(driver);
		setupOperator(operator);
		
		initSmartDashboard();
	}
	
	public interface Controls {
		
		final int TOGGLE_PIXY_TURRET_TARGETING = ControllerMap.Key.B;
		final int TOGGLE_AGITATOR_AND_FEEDER = ControllerMap.Key.Y;
		final int TOGGLE_SHOOTER = ControllerMap.Key.X;
		final int TOGGLE_GEAR_EJECTOR = ControllerMap.Key.A;
		
		final int CALIBRATE = ControllerMap.Key.BACK;
		final int FREE_THE_BALL = ControllerMap.Key.START;
	
		final int TOGGLE_GEAR_FLASHLIGHT = ControllerMap.Key.LB;
		final int TOGGLE_SHOOTER_FLASHLIGHT = ControllerMap.Key.RB;

		final int CLIMB = ControllerMap.Key.RT;
		final int ADJUSTMENT_MULTIPLIER = ControllerMap.Key.LT;
		
		final int INCREASE_SHOOTER_SPEED = DPadButton.Direction.UP;
		final int DECREASE_SHOOTER_SPEED = DPadButton.Direction.DOWN;
		final int TOGGLE_GEAR_COLLECTOR = DPadButton.Direction.RIGHT;
		final int TOGGLE_GEAR_AUTO_EJECT = DPadButton.Direction.LEFT;
	}
	
	public void setupDriver(ControllerMap controller) {
		controller.clearButtons();
		controller.getButton(Controls.TOGGLE_GEAR_EJECTOR)
			.whenPressed(new GearEjectorToggleCommand());
	}
	
	public void setupOperator(ControllerMap controller) {
		controller.clearButtons();
		
		// TURRET
		controller.getButton(Controls.TOGGLE_PIXY_TURRET_TARGETING)
			.whenPressed(new CallJoystickTurretControl());
		controller.getButton(Controls.CALIBRATE)
			.whenPressed(new CalibrateTurret());
		controller.getButton(Controls.FREE_THE_BALL)
			.whenPressed(new AgitatorFreeCommand(RobotMap.Feeder.SHAKE_TIME));
		controller.getButton(Controls.TOGGLE_AGITATOR_AND_FEEDER)
			.whenPressed(new ToggleFeederAndAgitator());
				
		// GEAR
		controller.getButton(Controls.TOGGLE_GEAR_COLLECTOR)
			.whenPressed(new GearCollectorToggleCommand());
		controller.getButton(Controls.TOGGLE_GEAR_EJECTOR)
			.whenPressed(new GearEjectorToggleCommand());
		controller.getButton(Controls.TOGGLE_GEAR_AUTO_EJECT)
			.whenPressed(new ToggleGearAutoEject());
		
		//FLASHLIGHTS
		controller.getButton(Controls.TOGGLE_GEAR_FLASHLIGHT)
			.whenPressed(new GearFlashlightCommand());
		controller.getButton(Controls.TOGGLE_SHOOTER_FLASHLIGHT)
			.whenPressed(new ShooterFlashlightCommand());
		
		//SHOOTER
		controller.getButton(Controls.TOGGLE_SHOOTER)
			.whenPressed(new ToggleShooting());
		controller.getButton(Controls.INCREASE_SHOOTER_SPEED)
			.whenPressed(new ShooterIncrementSpeed(2));
		controller.getButton(Controls.DECREASE_SHOOTER_SPEED)
			.whenPressed(new ShooterIncrementSpeed(-2));
	}
	
	public ControllerMap getDriver() {
		return driver;
	}
	
	public ControllerMap getOperator() {
		return operator;
	}

	public void initSmartDashboard() {
		AutonChooser.setupDashboard();
		
		SmartDashboard.putData("Shooter 6V", new ShooterSetVoltageCommand(6));
		SmartDashboard.putData("Shooter 5.8V", new ShooterSetVoltageCommand(5.8));
		SmartDashboard.putData("Shooter 5.6V", new ShooterSetVoltageCommand(5.6));
		SmartDashboard.putData("Shooter 5.4V", new ShooterSetVoltageCommand(5.4));
		SmartDashboard.putData("Shooter 5.2V", new ShooterSetVoltageCommand(5.2));
		SmartDashboard.putData("Shooter 5V", new ShooterSetVoltageCommand(5));

//		SmartDashboard.putData("save file", new RecordMotorMovementHelper("saveFile", "testing#1.txt"));
//		SmartDashboard.putData("loadFile(dont press)", new RecordMotorMovementHelper("readFile", "testing#1.txt"));
//		SmartDashboard.putData("record motors start", new RecordMotorMovementHelper("record", "testing#1.txt"));
		
//		SmartDashboard.putData("Rotate 180", new RotateAngle(180));
//		SmartDashboard.putData("Rotate 90", new RotateAngle(90));
//		SmartDashboard.putData("Rotate 0", new RotateAngle(0));
//		SmartDashboard.putData("Drive forward 2m", new DriveDistance(200));
    	
//    	SmartDashboard.putData("Agitator ON", new AgitatorTestingCommand(State.FORWARD));
//    	SmartDashboard.putData("Agitator OFF", new AgitatorTestingCommand(State.OFF));
//    	SmartDashboard.putData("Feeder ON", new FeederTestingCommand(State.FORWARD));
//    	SmartDashboard.putData("Feeder OFF", new FeederTestingCommand(State.OFF));
//    	SmartDashboard.putData("Shooter STOP", new ShooterCommandVoltage(0));
	}

	public static OI getInstance() {
		if(instance == null) {
			instance = new OI();
		}
		return instance;
	}
}
