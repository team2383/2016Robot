/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import java.util.concurrent.TimeUnit;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.Switch;
import org.strongback.components.TalonSRX;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.components.ui.Gamepad;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private final String defaultAuto = "Default Auto";
	private final String secondAuto = "Second Auto";

	private String autoSelected;

	private static SendableChooser chooser;

	@Override
	public void robotInit() {
		// Set up Strongback using its configurator. This is entirely optional,
		// but we're not using
		// events or data so it's better if we turn them off. All other defaults
		// are fine.
		Strongback.configure().recordCommands().useExecutionPeriod(25, TimeUnit.MILLISECONDS).initialize();

		TalonSRX leftFront = Config.TalonSRXs.LEFT_FRONT.get();
		TalonSRX leftRear = Config.TalonSRXs.LEFT_REAR.get();
		TalonSRX rightFront = Config.TalonSRXs.RIGHT_FRONT.get();
		TalonSRX rightRear = Config.TalonSRXs.RIGHT_REAR.get();

		Motor left = Motor.compose(leftFront, leftRear);
		Motor right = Motor.compose(rightFront, rightRear).invert();

		Motor climberPivot = Config.TalonSRXs.CLIMBER.get();
		Motor shooter = Config.TalonSRXs.CLIMBER.get();

		Motor hood = Config.PWMMotors.HOOD.get();
		Motor feeder = Config.PWMMotors.FEEDER.get();

		edu.wpi.first.wpilibj.Solenoid kicker = new edu.wpi.first.wpilibj.Solenoid(0);

		FlightStick leftJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(Config.LEFT_JOYSTICK_PORT);
		FlightStick rightJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(Config.RIGHT_JOYSTICK_PORT);
		Gamepad operatorJoystick = Hardware.HumanInterfaceDevices.logitechF310(Config.OPERATOR_JOYSTICK_PORT);

		Solenoid shifter = Config.DoubleSolenoids.SHIFTER.get();
		Solenoid leftClimber = Config.SingleSolenoids.LEFT_CLIMBER.get();
		Solenoid rightClimber = Config.SingleSolenoids.RIGHT_CLIMBER.get();

		TankDrive drive = new TankDrive(left, right);

		ContinuousRange leftSpeed = leftJoystick.getPitch();
		ContinuousRange rightSpeed = rightJoystick.getPitch();

		chooser = new SendableChooser();
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("Second Auto", secondAuto);
		SmartDashboard.putData("Auto Choices", chooser);

		// joystick buttons
		Switch shiftUp = rightJoystick.getTrigger();
		Switch shiftDown = leftJoystick.getTrigger();

		Switch feedIn = leftJoystick.getButton(5);
		Switch feedOut = leftJoystick.getButton(6);

		Switch hoodUp = operatorJoystick.getLeftStick();
		Switch hoodDown = operatorJoystick.getRightStick();
		Switch shoot = operatorJoystick.getLeftBumper();
		Switch climberUp = operatorJoystick.getB();
		Switch climberDown = operatorJoystick.getX();
		Switch climberRetract = operatorJoystick.getA();
		Switch climberExtend = operatorJoystick.getY();
		Switch kickBall = operatorJoystick.getRightBumper();

		SwitchReactor reactor = Strongback.switchReactor();

		reactor.whileTriggered(Switch.alwaysTriggered(), () -> drive.tank(leftSpeed.read(), rightSpeed.read()));

		// shooter
		reactor.onTriggered(shoot, () -> {
			shooter.setSpeed(1.0);
		});
		reactor.onUntriggered(shoot, shooter::stop);
		reactor.onTriggered(kickBall, () -> {
			kicker.set(true);
			feeder.setSpeed(1);
		});
		reactor.onUntriggered(kickBall, () -> {
			kicker.set(false);
			feeder.stop();
		});

		// shifter
		reactor.onTriggered(shiftUp, () -> {
			shifter.retract();
		});
		reactor.onTriggered(shiftDown, () -> {
			shifter.extend();
		});

		// hood
		reactor.onTriggered(hoodUp, () -> {
			hood.setSpeed(.4);
		});
		reactor.onTriggered(hoodDown, () -> {
			hood.setSpeed(-.4);
		});
		reactor.onUntriggered(hoodUp, hood::stop);
		reactor.onUntriggered(hoodDown, hood::stop);

		// feeder
		reactor.onTriggered(feedIn, () -> {
			feeder.setSpeed(1);
		});
		reactor.onTriggered(feedOut, () -> {
			feeder.setSpeed(-1);
		});
		reactor.onUntriggered(feedIn, feeder::stop);
		reactor.onUntriggered(feedOut, feeder::stop);

		// climber pivot
		reactor.onTriggered(climberUp, () -> {
			climberPivot.setSpeed(1);
		});
		reactor.onTriggered(climberDown, () -> {
			climberPivot.setSpeed(-1);
		});
		reactor.onUntriggered(climberUp, climberPivot::stop);
		reactor.onUntriggered(climberDown, climberPivot::stop);

		// climber
		reactor.onTriggered(climberRetract, () -> {
			leftClimber.retract();
			rightClimber.retract();
		});
		reactor.onTriggered(climberExtend, () -> {
			leftClimber.extend();
			rightClimber.extend();
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
	public void disabledInit() {
		// Tell Strongback that the robot is disabled so it can flush and kill
		// commands.
		Strongback.disable();
	}
}
