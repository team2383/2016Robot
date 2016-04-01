package com.team2383.robot.auto;

import com.team2383.robot.Constants;
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
	private class ExtendHood extends CommandGroup {
		public ExtendHood() {
			addSequential(new MoveHood(() -> 0.9, 0.25));
			addSequential(new ActuateHoodStop(true));
			addSequential(new MoveHood(() -> -0.55, 0.29));
		}
	}

	public LowBarHighGoalFar() {
		// addParallel(new ExtendBullBar());
		addSequential(new DriveDistance(1.0, 209, Gear.LOW, true));
		addSequential(new ActuateHoodStop(false));
		addParallel(new ExtendHood());
		addParallel(new SpoolToRPM(4000));
		addSequential(new GyroTurn(55.5));
		addParallel(new PrintCommand("Shooting!"));
		addSequential(new WaitForRPM(Constants.shooterRPMWaitTime));
		addSequential(new Shoot(Constants.shooterFollowThruTime));
		addSequential(new WaitForChildren()); // wait for spool down
	}
}
