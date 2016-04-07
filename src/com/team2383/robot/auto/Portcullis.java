package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.MoveArms;
import com.team2383.robot.subsystems.Arms.State;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Portcullis extends CommandGroup {
	private class RetractArms extends CommandGroup {
		RetractArms() {
			addSequential(new WaitCommand(0.3));
			addSequential(new MoveArms(-0.4, 5));
		}
	}

	public Portcullis() {
		addSequential(new DriveDistance(0.72, 54, Gear.LOW, false));
		addSequential(new MoveArms(State.EXTENDING, 0.8));
		addParallel(new RetractArms());
		addSequential(new DriveDistance(0.72, 70, Gear.LOW, false));
	}
}
