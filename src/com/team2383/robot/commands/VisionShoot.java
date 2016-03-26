package com.team2383.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class VisionShoot extends CommandGroup {
	public VisionShoot() {
		addSequential(new VisionTurn());
		addSequential(new AutoShoot());
	}
}
