package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterFlywheel;
import static com.team2383.robot.HAL.shooterHood;
import static com.team2383.robot.HAL.vision;

import com.team2383.robot.subsystems.Vision.LookupTable.Entry;
import com.team2383.robot.subsystems.Vision.Target;

import edu.wpi.first.wpilibj.command.Command;

public class UseVisionPreset extends Command {
	private boolean foundValid = false;
	private final boolean finish;

	public UseVisionPreset(boolean finish) {
		super("UseVisionPreset");
		requires(shooterHood);
		this.finish = false;
	}

	public UseVisionPreset() {
		super("UseVisionPreset");
		requires(shooterHood);
		this.finish = true;
	}

	@Override
	protected void initialize() {
		foundValid = false;
	}

	@Override
	protected void execute() {
		Target target = vision.getNearestTarget();
		if (target.getDistance() != 0.0) {
			Entry entry = vision.getNearestEntryForTarget(target);
			shooterFlywheel.setRPM(entry.getFlywheelRPM());
			shooterHood.setRotations(entry.getHoodRotations());
			foundValid = true;
		}
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return finish && foundValid;
	}

	@Override
	protected void end() {
		foundValid = false;
	}

	@Override
	protected void interrupted() {
		foundValid = false;
	}

}
