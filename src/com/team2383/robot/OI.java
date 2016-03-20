package com.team2383.robot;

import static com.team2383.robot.HAL.arms;
import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.feeder;
import static com.team2383.robot.HAL.hoodTopLimit;

import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

import com.team2383.ninjaLib.DPadButton;
import com.team2383.ninjaLib.DPadButton.Direction;
import com.team2383.ninjaLib.LambdaButton;
import com.team2383.ninjaLib.Values;
import com.team2383.ninjaLib.WPILambdas;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.SetState;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.SpoolToRPM;
import com.team2383.robot.subsystems.Arms;
import com.team2383.robot.subsystems.Drivetrain.Gear;
import com.team2383.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.

	/* Sticks */
	public static Joystick left = new Joystick(0);
	public static Joystick right = new Joystick(1);

	private static DoubleUnaryOperator deadband = (x) -> {
		return Math.abs(x) > Constants.inputDeadband ? x : 0;
	};

	public static DoubleSupplier leftSpeed = () -> deadband.applyAsDouble(left.getY());
	public static DoubleSupplier rightSpeed = () -> deadband.applyAsDouble(right.getY());
	public static Button shiftDown = new JoystickButton(left, 6);
	public static Button shiftUp = new JoystickButton(right, 11);

	public static Joystick operator = new Joystick(2);

	public static DoubleSupplier hood = () -> deadband.applyAsDouble(operator.getY());;
	public static DoubleSupplier shooterSpeed = () -> {
		return Values.mapRange(-1.0, 1.0, 0, Constants.shooterMaxRPM).applyAsDouble(operator.getThrottle());
	};

	public static Button feedIn = new LambdaButton(() -> {
		return operator.getRawButton(7) || operator.getRawButton(8);
	});

	public static Button feedOut = new LambdaButton(() -> {
		return operator.getRawButton(11) || operator.getRawButton(12);
	});

	public static Button feedOutSlow = new LambdaButton(() -> {
		return operator.getRawButton(6);
	});

	public static Button extendArms = new DPadButton(operator, Direction.UP);
	public static Button retractArms = new DPadButton(operator, Direction.DOWN);

	public static Button shoot = new JoystickButton(operator, 1); // trigger
	public static Button spool = new JoystickButton(operator, 2); // thumb

	public static Button hoodPancake = new JoystickButton(operator, 3);

	public static Button alignBall = new LambdaButton(() -> {
		return operator.getRawButton(9) || operator.getRawButton(10);
	});

	public static Button moveHood = new LambdaButton(() -> {
		return hood.getAsDouble() != 0;
	});

	// use buttons
	public OI() {

		shiftDown.whenPressed(WPILambdas.createCommand(() -> {
			drivetrain.shiftTo(Gear.LOW);
			return true;
		}));

		shiftUp.whenPressed(WPILambdas.createCommand(() -> {
			drivetrain.shiftTo(Gear.HIGH);
			return true;
		}));

		feedIn.whileHeld(new SetState<Feeder.State>(feeder, Feeder.State.FEEDING, Feeder.State.STOPPED));
		feedOut.whileHeld(new SetState<Feeder.State>(feeder, Feeder.State.OUTFEEDING, Feeder.State.STOPPED));
		feedOutSlow.whileHeld(new SetState<Feeder.State>(feeder, Feeder.State.OUTFEEDINGSLOW, Feeder.State.STOPPED));

		extendArms.whileHeld(new SetState<Arms.State>(arms, Arms.State.EXTENDING, Arms.State.STOPPED));
		retractArms.whileHeld(new SetState<Arms.State>(arms, Arms.State.RETRACTING, Arms.State.STOPPED));

		/*
		 * if (dualCams != null && dualCams instanceof CameraFeeds) {
		 * switchCamera.whenPressed(WPILambdas.createCommand(() -> {
		 * dualCams.switchCam(); return true; })); }
		 */

		spool.whileHeld(new SpoolToRPM(shooterSpeed));
		shoot.whileHeld(new Shoot());

		alignBall.whileHeld(new SpoolToRPM(-2000));

		moveHood.whileHeld(new MoveHood(OI.hood));

		if (Constants.useMechanicalHoodPresets) {
			hoodPancake.toggleWhenActive(new ActuateHoodStop(hoodTopLimit));
		} else {
			// setup hood presets
		}
	}
}
