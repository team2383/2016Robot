package com.team2383.robot.auto;

import java.util.HashMap;

import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 * Starts in Neutral Zone with ball. Drives under low bar Pivots ~30 degrees
 * Drives to Batter Reverse Raises Hood Fire
 *
 * @author Matthew Alonso
 *
 */
public class BatterHighGoal extends AutoCommand {

	public BatterHighGoal() {
		// Raise hood at 0.4 for 0.2 seconds
		addParallel(new Spool(getOption("Spool Time")));
		addSequential(new WaitCommand(2));
		addSequential(new Shoot(getOption("Shoot Time")));
		addSequential(new PrintCommand("Shooting!"));
		addSequential(new WaitForChildren()); // wait for spool down
	}

	@Override
	public HashMap<String, Double> getOptionDefaults() {
		HashMap<String, Double> options = new HashMap<>();
		options.put("AutoLine to Pivot", 175.0);
		options.put("Pivot To Batter", 126.0);
		return options;
	}

	@Override
	public HashMap<String, Double> getGlobalOptionDefaults() {
		HashMap<String, Double> globalOptions = new HashMap<>();
		globalOptions.put("Spool Time", 2.0);
		globalOptions.put("Shoot Time", 1.0);
		return globalOptions;
	}

}
