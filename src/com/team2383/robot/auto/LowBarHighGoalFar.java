package com.team2383.robot.auto;

import com.team2383.robot.commands.ActuateHoodStop;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.SpoolToRPM;
import com.team2383.robot.commands.WaitForRPM;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class LowBarHighGoalFar extends CommandGroup {
	public LowBarHighGoalFar() {
		// addParallel(new ExtendBullBar());
		addSequential(new DriveDistance(1.0, 209, Gear.LOW, true));
		addSequential(new ActuateHoodStop(false));
		addSequential(new GyroTurn(60));
		addSequential(new MoveHood(() -> 0.9, 0.25));
		addSequential(new ActuateHoodStop(true));
		addSequential(new MoveHood(() -> -0.55, 0.29));
		// Raise hood at 0.4 for 0.2 seconds
		addParallel(new SpoolToRPM(4000, 3.3));
		addParallel(new PrintCommand("Shooting!"));
		addSequential(new WaitForRPM(0.1));
		addSequential(new Shoot(0.6));
		addSequential(new WaitForChildren()); // wait for spool down
	}
}
