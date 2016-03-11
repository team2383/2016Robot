package com.team2383.robot.auto;

import java.util.HashMap;

import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class BatterLowGoal extends AutoCommand {
	public BatterLowGoal() {
		addParallel(new Spool(getGlobalOption("Spool Time")));
		addSequential(new WaitCommand(2));
		addSequential(new Shoot(getGlobalOption("Shoot Time")));
		addSequential(new PrintCommand("Shooting!"));
		addSequential(new WaitForChildren()); // wait for spool down
	}

	@Override
	public HashMap<String, Double> getGlobalOptionDefaults() {
		HashMap<String, Double> globalOptions = new HashMap<>();
		globalOptions.put("Spool Time", 2.0);
		globalOptions.put("Shoot Time", 1.0);
		return globalOptions;
	}
}
