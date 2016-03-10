package com.team2383.ninjaLib;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SequentialRunner extends CommandGroup {
	public SequentialRunner(Command... commands) {
		for (Command command : commands) {
			addSequential(command);
		}
	}
}
