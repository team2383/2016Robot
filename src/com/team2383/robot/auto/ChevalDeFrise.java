package com.team2383.robot.auto;

import com.team2383.robot.commands.ActuateHoodStop;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.MoveArms;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.subsystems.Arms.State;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ChevalDeFrise extends CommandGroup {
	private class Arms extends CommandGroup {
		Arms() {
			addSequential(new MoveArms(State.EXTENDING, 0.8));
			addSequential(new WaitCommand(1.0));
			addSequential(new MoveArms(-0.4, 5));
		}
	}

	private class Hood extends CommandGroup {
		Hood() {
			addSequential(new ActuateHoodStop(false));
			addSequential(new MoveHood(() -> 0.9, 0.25));
			addSequential(new ActuateHoodStop(true));
			addSequential(new MoveHood(() -> -0.55, 0.23));
		}
	}

	public ChevalDeFrise() {
		addParallel(new Hood());
		addSequential(new DriveDistance(0.62, 50, Gear.LOW, false));
		addParallel(new Arms());
		addSequential(new WaitCommand(0.5));
		addSequential(new DriveDistance(1.0, 70, Gear.LOW, false));
	}
}
