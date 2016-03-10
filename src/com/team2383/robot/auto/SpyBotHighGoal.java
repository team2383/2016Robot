package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.SetHeading;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/* Points: 30
 * 
 * Starts at the Spy Box, shoots, goes to low bar and goes back and forth through it to get 20 points
 * 
 */
public class SpyBotHighGoal extends CommandGroup {
	public SpyBotHighGoal() {
		addSequential(new DriveDistance(0.5, 36));
		addSequential(new SetHeading(0));
		// Raise hood at 0.4 for 0.2 seconds
		addParallel(new Spool());
		addSequential(new WaitForChildren());
		addSequential(new MoveHood(() -> 0.2, 1));
		addSequential(new Shoot());
		addSequential(new WaitCommand("Wait for shooter", 3));
		addParallel(new MoveHood(() -> -0.2, 1));
		addSequential(new DriveDistance(0.5, -28));
		addSequential(new SetHeading(90));
		
		// get 20 points by weakening low bar
		addSequential(new DriveDistance(0.5, 260));
		addSequential(new DriveDistance(0.5,-100));
		addSequential(new DriveDistance(0.5,100));
	}
}
