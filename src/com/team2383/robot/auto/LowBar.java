package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBar extends CommandGroup {
	public LowBar() {
		addSequential(new DriveDistance(0.8, 150, Gear.LOW, false));
	}
}
