package com.team2383.robot.auto;

import com.team2383.robot.commands.ActuateHoodStop;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.VisionShoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

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
		addSequential(new VisionShoot());
	}
}
