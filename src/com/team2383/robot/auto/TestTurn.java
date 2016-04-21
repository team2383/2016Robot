package com.team2383.robot.auto;

import com.team2383.robot.commands.GyroTurn;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestTurn extends CommandGroup {
	public TestTurn() {
		addSequential(new GyroTurn(0, false));
	}
}
