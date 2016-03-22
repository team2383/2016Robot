package com.team2383.robot.auto;

import com.team2383.robot.commands.ActuateHoodStop;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.SpoolToRPM;
import com.team2383.robot.commands.WaitForRPM;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;

public class PortcullisHighGoal extends Portcullis {
	private class Hood extends CommandGroup {
		Hood() {
			addSequential(new ActuateHoodStop(false));
			addSequential(new MoveHood(() -> 0.9, 0.25));
			addSequential(new ActuateHoodStop(true));
			addSequential(new MoveHood(() -> -0.55, 0.23));
		}
	}

	public PortcullisHighGoal() {
		super();
		addParallel(new Hood());
		addParallel(new SpoolToRPM(4100, 6));
		addSequential(new GyroTurn(-6));
		addParallel(new PrintCommand("Shooting!"));
		addSequential(new WaitForRPM(0.1));
		addSequential(new Shoot(0.6));
	}
}
