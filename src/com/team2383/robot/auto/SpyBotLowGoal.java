package com.team2383.robot.auto;

import java.util.HashMap;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.SetHeading;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;


import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class SpyBotLowGoal extends AutoCommand{
	public SpyBotLowGoal() {
		addSequential(new DriveDistance(0.5, getOption("SpyBox to Batter")));
		addSequential(new SetHeading(0));
		// Raise hood at 0.4 for 0.2 seconds
		addParallel(new Spool(getOption("Spool Time")));
		addSequential(new WaitForChildren());
		addSequential(new MoveHood(() -> 0.2, 1));
		addSequential(new Shoot(getOption("Shoot Time")));
		addSequential(new WaitCommand("Wait for shooter", 3));
		addParallel(new MoveHood(() -> -0.2, 1));
		addSequential(new DriveDistance(0.5, getOption("Batter to SpyBox")));
		addSequential(new SetHeading(getOption("Pivot Angle")));
		
		// get 20 points by weakening low bar
		addSequential(new DriveDistance(0.5, getOption("Wall past Low Bar")));
		addSequential(new DriveDistance(0.5, getOption("Through Low Bar")));
		addSequential(new DriveDistance(0.5, -getOption("Through Low Bar")));
	}
	
	@Override
	public HashMap<String, Double> getDefaultOptions() {
		HashMap<String, Double> options = new HashMap<>();
		options.put("SpyBox to Batter", -36.0);
		options.put("Batter to SpyBox", 28.0);
		options.put("Spool Time", 1.0);
		options.put("Shoot Time", 1.0);
		options.put("Pivot Angle", 90.0);
		options.put("Wall past Low Bar", -180.0);
		options.put("Through Low Bar", 48.0);
		return options;
	}
}
