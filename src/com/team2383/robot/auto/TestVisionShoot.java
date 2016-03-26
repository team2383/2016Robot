package com.team2383.robot.auto;

import com.team2383.robot.commands.VisionShoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestVisionShoot extends CommandGroup {
	public TestVisionShoot() {
		addSequential(new VisionShoot());
	}
}
