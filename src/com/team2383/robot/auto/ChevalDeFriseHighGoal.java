package com.team2383.robot.auto;

import com.team2383.robot.commands.MoveArms;
import com.team2383.robot.commands.VisionShoot;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class ChevalDeFriseHighGoal extends ChevalDeFrise {
	public ChevalDeFriseHighGoal() {
		super();
		addSequential(new MoveArms(0.5, 1.0));
		addSequential(new WaitCommand(0.3));
		addSequential(new VisionShoot());
	}
}
