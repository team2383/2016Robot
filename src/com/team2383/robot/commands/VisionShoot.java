package com.team2383.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class VisionShoot extends CommandGroup {
	public VisionShoot() {
		addParallel(new UseVisionPreset(false));
		addParallel(new SpoolToRPM());
		addSequential(new VisionTurn());
		addSequential(new WaitCommand(0.5));
		addSequential(new AutoShoot());
	}
}
