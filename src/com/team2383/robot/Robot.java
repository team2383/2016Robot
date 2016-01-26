/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.Switch;
import org.strongback.components.TalonSRX;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private final String defaultAuto = "Default Auto";
	private final String secondAuto = "Second Auto";
	private TankDrive drive;
	private ContinuousRange leftSpeed, rightSpeed;

	private String autoSelected;

	private static SendableChooser chooser;

	@Override
	public void robotInit() {
		// Set up Strongback using its configurator. This is entirely optional,
		// but we're not using
		// events or data so it's better if we turn them off. All other defaults
		// are fine.
		Strongback.configure().recordNoEvents().recordNoData().initialize();

		CANTalon leftFront_WPI = new CANTalon(Config.LEFT_FRONT_MOTOR_PORT);
		CANTalon rightRear_WPI = new CANTalon(Config.RIGHT_REAR_MOTOR_PORT);

		TalonSRX leftFront = Hardware.Motors.talonSRX(leftFront_WPI);
		TalonSRX rightRear = Hardware.Motors.talonSRX(rightRear_WPI);

		leftFront_WPI.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightRear_WPI.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);

		TalonSRX leftRear = Hardware.Motors.talonSRX(Config.LEFT_REAR_MOTOR_PORT);
		TalonSRX rightFront = Hardware.Motors.talonSRX(Config.RIGHT_FRONT_MOTOR_PORT);

		Motor left = Motor.compose(leftFront, leftRear).invert();
		Motor right = Motor.compose(rightFront, rightRear);

		Motor feeder = Hardware.Motors.talon(Config.FEEDER_MOTOR_PORT);
		Motor climberPivot = Hardware.Motors.talon(Config.CLIMBER_MOTOR_PORT);

		Solenoid leftSolenoidShifter = Hardware.Solenoids.doubleSolenoid(Config.LEFT_EXTEND_SHIFTER_PORT,
				Config.LEFT_RETRACT_SHIFTER_PORT, Solenoid.Direction.STOPPED);
		Solenoid rightSolenoidShifter = Hardware.Solenoids.doubleSolenoid(Config.RIGHT_EXTEND_SHIFTER_PORT,
				Config.RIGHT_RETRACT_SHIFTER_PORT, Solenoid.Direction.STOPPED);

		drive = new TankDrive(left, right);

		FlightStick leftJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(Config.LEFT_JOYSTICK_PORT);
		FlightStick rightJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(Config.RIGHT_JOYSTICK_PORT);
		FlightStick operatorJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(Config.OPERATOR_JOYSTICK_PORT);
		leftSpeed = leftJoystick.getPitch();
		rightSpeed = rightJoystick.getPitch();

		chooser = new SendableChooser();
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("Second Auto", secondAuto);
		SmartDashboard.putData("Auto Choices", chooser);

		// joystick buttons
		Switch shiftUp = rightJoystick.getButton(3);
		Switch shiftDown = rightJoystick.getButton(4);
		Switch feedIn = leftJoystick.getButton(5);
		Switch feedOut = leftJoystick.getButton(6);
		Switch climberUp = operatorJoystick.getButton(4);
		Switch climberDown = operatorJoystick.getButton(5);

		// todo: add doubletap to strongback OI
		Switch climberExtend = operatorJoystick.getButton(6);

		SwitchReactor reactor = Strongback.switchReactor();

		// shifter
		reactor.onTriggered(shiftUp, () -> {
			leftSolenoidShifter.extend();
			rightSolenoidShifter.extend();
		});
		reactor.onTriggered(shiftDown, () -> {
			leftSolenoidShifter.retract();
			rightSolenoidShifter.retract();
		});

		// feeder
		reactor.whileTriggered(feedIn, () -> {
			feeder.setSpeed(0.5);
		});
		reactor.whileTriggered(feedOut, () -> {
			feeder.setSpeed(-0.5);
		});

		// climber
		reactor.whileTriggered(climberUp, () -> {
			climberPivot.setSpeed(1);
		});
		reactor.whileTriggered(climberDown, () -> {
			climberPivot.setSpeed(-1);
		});
	}

	@Override
	public void autonomousInit() {
		autoSelected = (String) chooser.getSelected();
		System.out.println("Auto Selected: " + autoSelected);
	}

	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case secondAuto:
			// secondAuto command here
			break;
		case defaultAuto:
			// defaultAuto command here
			break;
		}
	}

	@Override
	public void teleopInit() {
		// Start Strongback functions ...
		Strongback.restart();
	}

	@Override
	public void teleopPeriodic() {
		drive.tank(leftSpeed.read(), rightSpeed.read());
	}

	@Override
	public void disabledInit() {
		// Tell Strongback that the robot is disabled so it can flush and kill
		// commands.
		Strongback.disable();
	}

	@Override
	public void disabledPeriodic() {
	}
}
