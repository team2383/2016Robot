package com.team2383.robot.auto;

import com.team2383.robot.Constants.Preset;
import com.team2383.robot.commands.ActuateHoodStop;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.UsePreset;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class LowBarHighGoalFarReturn extends LowBarHighGoalFar {
	public LowBarHighGoalFarReturn() {
		super();
		addSequential(new ActuateHoodStop(false));
		addSequential(new WaitCommand(0.1));
		addSequential(new UsePreset(Preset.closed));
		addSequential(new GyroTurn(115));
		addSequential(new DriveDistance(1.0, 200, Gear.LOW, true));
	}
}
