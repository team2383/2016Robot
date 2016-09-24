package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.team2383.ninjaLib.CheesyDriveHelper;
import com.team2383.robot.HAL;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopDrive extends Command {
	private final DoubleSupplier rightStick;
	private final DoubleSupplier leftStick;
	private final BooleanSupplier toggleAutoShift;
	private final BooleanSupplier lowGear;
	private final BooleanSupplier highGear;
	private final CheesyDriveHelper cdh;
	private boolean autoShift;
	private boolean dirty;
	private Gear gear;

	public TeleopDrive(DoubleSupplier leftStick, DoubleSupplier rightStick, BooleanSupplier toggleAutoShift, BooleanSupplier lowGear,  BooleanSupplier highGear) {
		super("Teleop Drive");
		requires(drivetrain);
		this.leftStick = leftStick;
		this.rightStick = rightStick;
		this.toggleAutoShift = toggleAutoShift;
		this.lowGear = lowGear;
		this.highGear = highGear;
		this.autoShift = false;
		this.gear = Gear.LOW;
		this.dirty = false;
		this.cdh = new CheesyDriveHelper();
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		if (this.toggleAutoShift.getAsBoolean() && !dirty) {
			autoShift = !autoShift;
			dirty = true;
		} else if (!this.toggleAutoShift.getAsBoolean()) {
			dirty = false;
		}
		if (this.autoShift) {
			if(Math.abs(drivetrain.getSpeed()) > 3.0 && Math.abs(rightStick.getAsDouble()) < 0.2) {
				gear = Gear.HIGH;
			} else if (Math.abs(drivetrain.getSpeed()) < 2.0 && Math.abs(rightStick.getAsDouble()) < 0.2) {
				gear = Gear.LOW;
			}
		} else {
			if (lowGear.getAsBoolean()) {
				gear = Gear.LOW;
			}
			if (highGear.getAsBoolean()) {
				gear = Gear.HIGH;
			}
		}
		drivetrain.shiftTo(gear);
		cdh.cheesyDrive(leftStick.getAsDouble(), rightStick.getAsDouble(), gear == Gear.HIGH);
		SmartDashboard.putBoolean("Automatic Shifting", autoShift);
		SmartDashboard.putBoolean("Dirty Automatic Shifting", dirty);
		SmartDashboard.putBoolean("Toggle Automatic Shifting", this.toggleAutoShift.getAsBoolean());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drivetrain.tank(0, 0);
	}

	@Override
	protected void interrupted() {
		drivetrain.tank(0, 0);
	}
}
