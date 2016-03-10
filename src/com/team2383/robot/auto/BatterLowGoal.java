package com.team2383.robot.auto;

import java.util.HashMap;

import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class BatterLowGoal extends AutoCommand {
	public BatterLowGoal() {
		addParallel(new Spool(getOption("Spool Time")));
		addSequential(new WaitCommand(2));
		addSequential(new Shoot(getOption("Shoot Time")));
		addSequential(new PrintCommand("Shooting!"));
		addSequential(new WaitForChildren()); // wait for spool down
	}

	@Override
	public HashMap<String, Double> getOptionDefaults() {
		HashMap<String, Double> options = new HashMap<>();
		options.put("AutoLine to Pivot", -175.0);
		options.put("Pivot Angle", 49.0);
		options.put("Pivot To Batter", -126.0);
		options.put("Spool Time", 1.0);
		options.put("Shoot Time", 1.0);
		return options;
	}
}
