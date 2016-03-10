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

public class LowBarCourtyardHighGoal extends AutoCommand {

	public LowBarCourtyardHighGoal() {
		addSequential(new DriveDistance(0.71, 175));
		addParallel(new MoveHood(() -> 0.2, 1));
		addSequential(new SetHeading(49));
		// Shoot
		addParallel(new Spool(4));
		addSequential(new WaitCommand(2));
		addSequential(new Shoot(5));
		addSequential(new PrintCommand("Shooting!"));
		addSequential(new WaitForChildren()); // wait for spool down
	}

	@Override
	public HashMap<String, Double> getDefaultOptions() {
		// TODO Auto-generated method stub
		return null;
	}
}
