package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.SetHeading;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.command.CommandGroup;
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
public class LowBarHighGoal extends CommandGroup {

	public LowBarHighGoal() {
		addSequential(new DriveDistance(0.71, 175));
		addParallel(new MoveHood(() -> 0.2, 1));
		addSequential(new SetHeading(49));
		addSequential(new DriveDistance(0.71, 126));
		// Raise hood at 0.4 for 0.2 seconds
		addParallel(new Spool(4));
		addSequential(new WaitCommand(2));
		addSequential(new Shoot(5));
		addSequential(new PrintCommand("Shooting!"));
		addSequential(new WaitForChildren()); // wait for spool down
	}
}
