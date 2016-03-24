package com.team2383.robot.auto;

import com.team2383.robot.commands.MoveArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ExtendBullBar extends CommandGroup {
	public ExtendBullBar() {
		addSequential(new MoveArms(1.0, .8));
		addSequential(new MoveArms(-1.0, .8));
	}
}
