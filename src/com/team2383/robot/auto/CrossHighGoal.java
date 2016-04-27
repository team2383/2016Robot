package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.MoveArms;
import com.team2383.robot.commands.VisionShoot;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class CrossHighGoal extends CommandGroup {
	public CrossHighGoal() {
		addSequential(new DriveDistance(1.0, -167, 6, 0.01, Gear.LOW, false));
		addParallel(new MoveArms(0.5, 1.0));
		addSequential(new GyroTurn(1.0, 179.9, 5));
		addSequential(new WaitCommand(0.7));
		addSequential(new VisionShoot());
	}
}
