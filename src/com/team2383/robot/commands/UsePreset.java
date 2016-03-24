package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterFlywheel;
import static com.team2383.robot.HAL.shooterHood;

import com.team2383.robot.Constants.Preset;
import com.team2383.robot.Constants.ShooterPreset;

import edu.wpi.first.wpilibj.command.Command;

public class UsePreset extends Command {

	private final ShooterPreset preset;

	public UsePreset(Preset preset) {
		super("UsePreset");
		requires(shooterHood);
		this.preset = preset.get();
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		shooterFlywheel.setRPM(preset.shooterRPM);
		shooterHood.setRotations(preset.hoodRotations);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
