package com.team2383.robot.auto;

import com.team2383.robot.commands.VisionShoot;

public class ChevalDeFriseHighGoal extends ChevalDeFrise {
	public ChevalDeFriseHighGoal() {
		super();
		addSequential(new VisionShoot());
	}
}
