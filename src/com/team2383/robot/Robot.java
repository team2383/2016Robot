
package com.team2383.robot;

import com.team2383.robot.auto.FeederForwardConstant;
import com.team2383.robot.auto.LowBarConstant;
import com.team2383.robot.auto.LowBarHighGoalConstant;
import com.team2383.robot.auto.LowBarHighGoalFarConstant;
import com.team2383.robot.auto.LowBarHighGoalWallConstant;
import com.team2383.robot.auto.TestDrive;
import com.team2383.robot.auto.TestTurn;
import com.team2383.robot.commands.GeneralPeriodic;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	Command autoCommand;
	Command generalPeriodicCommand = new GeneralPeriodic();
	SendableChooser autoChooser;

	@SuppressWarnings("unused")
	@Override
	public void robotInit() {
		HAL hal = new HAL();
		Constants constants = new Constants();
		OI oi = new OI();

		autoChooser = new SendableChooser();
		autoChooser.addDefault("No auto", null);
		autoChooser.addObject("Low Bar", new LowBarConstant());
		autoChooser.addObject("Low Bar + High Goal", new LowBarHighGoalConstant());
		autoChooser.addObject("Low Bar + High Goal Far", new LowBarHighGoalFarConstant());
		autoChooser.addObject("Low Bar + High Goal Wall", new LowBarHighGoalWallConstant());
		autoChooser.addObject("TestDrive", new TestDrive());
		autoChooser.addObject("TestTurn", new TestTurn());
		autoChooser.addObject("All other defenses", new FeederForwardConstant());

		SmartDashboard.putData("Auto Chooser", autoChooser);
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
		autoCommand = (Command) autoChooser.getSelected();
		if (autoCommand != null) {
			autoCommand.start();
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
		if (autoCommand != null) {
			autoCommand.cancel();
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
