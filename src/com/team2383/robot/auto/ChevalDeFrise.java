package com.team2383.robot.auto;

import com.team2383.robot.commands.ActuateHoodStop;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.MoveArms;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.SpoolToRPM;
import com.team2383.robot.commands.WaitForRPM;
import com.team2383.robot.subsystems.Arms.State;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ChevalDeFrise extends CommandGroup {
	private class RetractArms extends CommandGroup {
		RetractArms() {
			addSequential(new WaitCommand(0.5));
			addSequential(new MoveArms(-0.4, 5));
		}
	}

	private class Rev extends CommandGroup {
		Rev() {
			addSequential(new WaitCommand(3));
			addParallel(new SpoolToRPM(4100, 6));
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
		addSequential(new MoveArms(State.EXTENDING, 0.8));
		addSequential(new WaitCommand(0.3));
		addParallel(new RetractArms());
		addParallel(new Rev());
		addSequential(new DriveDistance(1.0, 70, Gear.LOW, false));
		addSequential(new GyroTurn(-6));
		addParallel(new PrintCommand("Shooting!"));
		addSequential(new WaitForRPM(0.1));
		addSequential(new Shoot(0.6));
	}
}
