package com.team2383.robot.auto;

import java.util.HashMap;

import com.team2383.robot.ActuateHoodStop;
import com.team2383.robot.HAL;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.SpoolToRPM;

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
		addParallel(new ActuateHoodStop(HAL.hoodTopLimit));
		addParallel(new SpoolToRPM(3250, getGlobalOption("Spool Time")));
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
