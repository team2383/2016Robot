
package com.team2383.robot;

import java.util.Arrays;

import com.team2383.robot.auto.BatterHighGoal;
import com.team2383.robot.auto.BatterLowGoal;
import com.team2383.robot.auto.CommandHolder;
import com.team2383.robot.auto.defenses.ChevalDeFrise;
import com.team2383.robot.auto.defenses.Moat;
import com.team2383.robot.auto.defenses.Portcullis;
import com.team2383.robot.auto.defenses.Ramparts;
import com.team2383.robot.auto.defenses.RockWall;
import com.team2383.robot.auto.defenses.RoughTerrain;
import com.team2383.robot.auto.defenses.TestDistance;
import com.team2383.robot.auto.defenses.TestTurn;
import com.team2383.robot.commands.GeneralPeriodic;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	Command defenseCommand, courtyardCommand;
	Command generalPeriodicCommand = new GeneralPeriodic();
	SendableChooser autoChooser, defenseChooser, positionChooser;

	@Override
	public void robotInit() {
		defenseChooser.addDefault("No auto", null);
		defenseChooser.addObject("Rough Terrain", new CommandHolder(RoughTerrain::new));
		defenseChooser.addObject("Rock Wall", new CommandHolder(RockWall::new));
		defenseChooser.addObject("Portcullis", new CommandHolder(Portcullis::new));
		defenseChooser.addObject("Cheval de Frise", new CommandHolder(ChevalDeFrise::new));
		defenseChooser.addObject("Moat", new CommandHolder(Moat::new));
		defenseChooser.addObject("Ramparts", new CommandHolder(Ramparts::new));

		// test commands
		defenseChooser.addObject("Test Distance", new CommandHolder(TestDistance::new));
		defenseChooser.addObject("Test Turn", new CommandHolder(TestTurn::new));

		autoChooser = new SendableChooser();
		autoChooser.addDefault("Just Cross", null);
		autoChooser.addObject("Batter High Goal", new CommandHolder(BatterHighGoal::new));
		autoChooser.addObject("Batter Low Goal", new CommandHolder(BatterLowGoal::new));

		SmartDashboard.putData("Auto mode", autoChooser);
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
		if (!generalPeriodicCommand.isRunning()) {
			generalPeriodicCommand.start();
		}

		try {
			autonomousCommand = (Command) autoChooser.getSelected().getClass().newInstance();
		} catch (Throwable e) {
			DriverStation.reportError(
					"ERROR instantiating autonomous " + e.toString() + " at " + Arrays.toString(e.getStackTrace()),
					false);
		}

		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.start();
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
