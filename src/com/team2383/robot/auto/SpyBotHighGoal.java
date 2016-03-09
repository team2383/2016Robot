package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.SetHeading;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 * SpyBot with ball - Drives forward - Pivots ~ -120 degrees - Drives onto
 * batter - Raise hood, spool, shoot - Drive off Batter - Pivot -60 - Lower Hood
 * - Drive through low bar Drives under low bar Pivots ~30 degrees Drives to
 * Batter Reverse Raises Hood Fire
 *
 * @author Matthew Alonso
 *
 */
public class SpyBotHighGoal extends CommandGroup {
	public SpyBotHighGoal() {
		addSequential(new DriveDistance(0.5, 30));
		addSequential(new SetHeading(-120));
		addSequential(new DriveDistance(0.5, 67));
		// Raise hood at 0.4 for 0.2 seconds
		addParallel(new Spool());
		addSequential(new WaitForChildren());
		addSequential(new MoveHood(() -> 0.4, 0.2));
		addSequential(new Shoot());
		addSequential(new WaitCommand("Wait for shooter", 3));
		addParallel(new MoveHood(() -> 0.4, 0.2));
		addSequential(new DriveDistance(0.5, -67));
		addSequential(new SetHeading(-60));
		addSequential(new DriveDistance(0.5, -100));
	}
}
