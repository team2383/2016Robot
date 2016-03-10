package com.team2383.robot.auto;

import java.util.HashMap;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.SetHeading;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class LowBarBatterLowGoal extends AutoCommand{
	public LowBarBatterLowGoal() {
		addSequential(new DriveDistance(0.71, getOption("AutoLine to Pivot")));
		addParallel(new MoveHood(() -> 0.2, 1));
		addSequential(new SetHeading(getOption("Pivot Angle")));
		addSequential(new DriveDistance(0.71, getOption("Pivot To Batter")));
		// Raise hood at 0.4 for 0.2 seconds
		addParallel(new Spool(getOption("Spool Time")));
		addSequential(new WaitCommand(2));
		addSequential(new Shoot(getOption("Shoot Time")));
		addSequential(new PrintCommand("Shooting!"));
		addSequential(new WaitForChildren()); // wait for spool down
	}
	
	@Override
	public HashMap<String, Double> getDefaultOptions() {
		HashMap<String, Double> options = new HashMap<>();
		options.put("AutoLine to Pivot", -175.0);
		options.put("Pivot Angle", 49.0);
		options.put("Pivot To Batter", -126.0);
		options.put("Spool Time", 1.0);
		options.put("Shoot Time", 1.0);
		return options;
	}
}
