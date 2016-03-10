
package com.team2383.robot;

import com.team2383.robot.auto.AutoCommand;
import com.team2383.robot.auto.LowBar;
import com.team2383.robot.auto.LowBarBatterHighGoal;
import com.team2383.robot.auto.LowBarBatterLowGoal;
import com.team2383.robot.auto.LowBarCourtyardHighGoal;
import com.team2383.robot.auto.Reach;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	Command autonomousCommand;
	SendableChooser chooser;

	@Override
	public void robotInit() {
		chooser = new SendableChooser();
		chooser.addDefault("Low Bar + Batter High Goal", new LowBarBatterHighGoal());
		chooser.addObject("Low Bar + Batter Low Goal", new LowBarBatterLowGoal());
		chooser.addObject("Low Bar + Courtyard Low Goal", new LowBarCourtyardHighGoal());
		chooser.addObject("Damage Low Bar", new LowBar());
		chooser.addObject("Reach Any Defense", new Reach());
		SmartDashboard.putData("Auto mode", chooser);
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		// if autonomousCommand has options, check dashboard for updates;
		if (autonomousCommand != null && autonomousCommand instanceof AutoCommand) {
			((AutoCommand) autonomousCommand).update();
		}

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
		autonomousCommand = (Command) chooser.getSelected();

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
