package com.team2383.robot.auto;

import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.SpoolToRPM;
import com.team2383.robot.commands.WaitForRPM;

import edu.wpi.first.wpilibj.command.PrintCommand;

public class ChevalDeFriseHighGoal extends ChevalDeFrise {
	public ChevalDeFriseHighGoal() {
		super();
		addParallel(new SpoolToRPM(4100, 6));
		addSequential(new GyroTurn(-6));
		addParallel(new PrintCommand("Shooting!"));
		addSequential(new WaitForRPM(0.1));
		addSequential(new Shoot(0.6));
	}
}
