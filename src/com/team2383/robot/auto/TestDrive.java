package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestDrive extends CommandGroup {
	public TestDrive() {
		addSequential(new DriveDistance(1.0, 100, Gear.LOW, false, true));
	}
}
