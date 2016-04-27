package com.team2383.robot.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.ActuateHoodStop;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.MoveArms;
import com.team2383.robot.commands.VisionShoot;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LowBarHighGoalFarVision extends CommandGroup {
	public LowBarHighGoalFarVision() {
		addSequential(new DriveDistance(0.85, 140, Gear.LOW, true));
		addSequential(new ActuateHoodStop(false));
		addSequential(new MoveArms(0.5, 1.0));
		addSequential(new GyroTurn(Constants.driveTurnVelocity, 38.5, 2));
		addSequential(new WaitCommand(0.3));
		addSequential(new VisionShoot());
	}
}
