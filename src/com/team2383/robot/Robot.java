
package com.team2383.robot;

import com.team2383.ninjaLib.SequentialRunner;
import com.team2383.robot.auto.BatterHighGoal;
import com.team2383.robot.auto.BatterLowGoal;
import com.team2383.robot.auto.CommandHolder;
import com.team2383.robot.auto.defenses.Moat;
import com.team2383.robot.auto.defenses.Ramparts;
import com.team2383.robot.auto.defenses.RockWall;
import com.team2383.robot.auto.defenses.RoughTerrain;
import com.team2383.robot.auto.positions.Center;
import com.team2383.robot.auto.positions.LowBarPosition;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GeneralPeriodic;
import com.team2383.robot.commands.SetHeading;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	Command autonomousCommand, defenseCommand, courtyardCommand, positionCommand;
	Command generalPeriodicCommand = new GeneralPeriodic();
	SendableChooser courtyardChooser, defenseChooser, positionChooser;

	@Override
	public void robotInit() {
		defenseChooser.addDefault("No auto", null);
		defenseChooser.addObject("Rough Terrain", new CommandHolder(RoughTerrain::new));
		defenseChooser.addObject("Rock Wall", new CommandHolder(RockWall::new));
		defenseChooser.addObject("Moat", new CommandHolder(Moat::new));
		defenseChooser.addObject("Ramparts", new CommandHolder(Ramparts::new));

		// test commands
		defenseChooser.addObject("Test Distance",
				new CommandHolder(() -> new DriveDistance(0.66, 50, Gear.LOW, false)));
		defenseChooser.addObject("Test Turn", new CommandHolder(() -> new SetHeading(0.66, 90)));

		courtyardChooser = new SendableChooser();
		courtyardChooser.addDefault("Just Cross", null);
		courtyardChooser.addObject("Batter High Goal", new CommandHolder(BatterHighGoal::new));
		courtyardChooser.addObject("Batter Low Goal", new CommandHolder(BatterLowGoal::new));

		positionChooser = new SendableChooser();
		positionChooser.addDefault("Low Bar", new CommandHolder(LowBarPosition::new));
		positionChooser.addObject("Center", new CommandHolder(Center::new));

		SmartDashboard.putData("Auto mode", courtyardChooser);
	}

	@Override
	public void disabledInit() {
		if (!generalPeriodicCommand.isRunning()) {
			generalPeriodicCommand.start();
		}
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

		// 21.18 in TalonSRX software manual
		HAL.leftFront.enableBrakeMode(false);
		HAL.leftRear.enableBrakeMode(false);
		HAL.rightFront.enableBrakeMode(false);
		HAL.rightRear.enableBrakeMode(false);
		HAL.shooterMotor.enableBrakeMode(false);
		HAL.hoodMotor.enableBrakeMode(true);
		HAL.armMotor.enableBrakeMode(true);
	}

	@Override
	public void autonomousInit() {
		defenseCommand = ((CommandHolder) defenseChooser.getSelected()).get();
		courtyardCommand = ((CommandHolder) courtyardChooser.getSelected()).get();
		positionCommand = ((CommandHolder) courtyardChooser.getSelected()).get();
		if (defenseCommand != null && courtyardCommand != null && positionCommand != null) {
			autonomousCommand = new SequentialRunner(defenseCommand, courtyardCommand, positionCommand);
			autonomousCommand.start();
		}

		if (!generalPeriodicCommand.isRunning()) {
			generalPeriodicCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (!generalPeriodicCommand.isRunning()) {
			generalPeriodicCommand.start();
		}
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
