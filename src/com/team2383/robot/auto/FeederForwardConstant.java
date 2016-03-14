package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class FeederForwardConstant extends CommandGroup {
	public FeederForwardConstant() {
		addSequential(new WaitCommand(5));
		addSequential(new DriveDistance(1.0, -160, Gear.LOW, false));
	}
}
