package com.team2383.robot.auto;

import com.team2383.robot.commands.MoveArms;
import com.team2383.robot.subsystems.Arms.State;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ExtendBullBar extends CommandGroup {
	public ExtendBullBar() {
		addSequential(new MoveArms(State.EXTENDING, 0.8));
		addSequential(new MoveArms(State.RETRACTING, 0.8));
	}
}
