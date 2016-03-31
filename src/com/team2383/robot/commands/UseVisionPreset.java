package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterFlywheel;
import static com.team2383.robot.HAL.shooterHood;
import static com.team2383.robot.HAL.vision;

import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Vision.LookupTable.Entry;
import com.team2383.robot.subsystems.Vision.Target;

import edu.wpi.first.wpilibj.command.Command;

public class UseVisionPreset extends Command {
	private boolean foundValid = false;
	private double timeWithoutTarget = 0.0;

	public UseVisionPreset() {
		super("UseVisionPreset");
		requires(shooterHood);
	}

	@Override
	protected void initialize() {
		foundValid = false;
		timeWithoutTarget = 0.0;
	}

	@Override
	protected void execute() {
		Target target = vision.getNearestTarget();
		if (target.getDistance() != 0) {
			Entry entry = vision.getNearestEntryForTarget(target);
			shooterFlywheel.setRPM(entry.getFlywheelRPM());
			shooterHood.setRotations(entry.getHoodRotations());
			foundValid = true;
		} else {
			timeWithoutTarget += this.timeSinceInitialized() - timeWithoutTarget;
			if (timeWithoutTarget >= Constants.visionAlignOffset) {
				if (getGroup() != null) {
					getGroup().cancel();
				} else {
					cancel();
				}
			}
		}

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return foundValid;
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
