package com.team2383.robot.commands;

import com.team2383.robot.HAL;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class VisionShoot extends CommandGroup {
	private Gear gear;

	public VisionShoot() {
		addParallel(new UseVisionPreset(false));
		addParallel(new SpoolToRPM());
		addSequential(new VisionTurn());
		addSequential(new AutoShoot());
	}

	@Override
	public void initialize() {
		this.gear = HAL.drivetrain.getGear();
	}

	@Override
	public void end() {
		HAL.drivetrain.shiftTo(gear);
	}

	@Override
	public void interrupted() {
		end();
	}
}
