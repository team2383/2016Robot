package com.team2383.robot.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.UsePreset;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestAutoShoot extends CommandGroup {
	public TestAutoShoot() {
		addSequential(new UsePreset(Constants.Preset.courtyardMid, true));
		addSequential(new AutoShoot());
	}
}
