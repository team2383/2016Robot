package com.team2383.robot.auto;

import com.team2383.robot.Constants.Preset;
import com.team2383.robot.commands.SpoolToRPM;
import com.team2383.robot.commands.UsePreset;
import com.team2383.robot.commands.VisionShoot;

public class ChevalDeFriseHighGoal extends ChevalDeFrise {
	public ChevalDeFriseHighGoal() {
		super();
		addParallel(new SpoolToRPM(4100, 6));
		addSequential(new UsePreset(Preset.courtyardMid));
		addSequential(new VisionShoot());
	}
}
