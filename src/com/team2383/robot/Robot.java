
package com.team2383.robot;

import com.team2383.robot.auto.ChevalDeFrise;
import com.team2383.robot.auto.ChevalDeFriseHighGoal;
import com.team2383.robot.auto.CrossDefense;
import com.team2383.robot.auto.CrossShoot;
import com.team2383.robot.auto.LowBar;
import com.team2383.robot.auto.LowBarHighGoal;
import com.team2383.robot.auto.LowBarHighGoalFar;
import com.team2383.robot.auto.LowBarHighGoalFarReturn;
import com.team2383.robot.auto.Portcullis;
import com.team2383.robot.auto.PortcullisHighGoal;
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
		autoChooser.addObject("Low Bar", new LowBar());
		autoChooser.addObject("Moat / Rock Wall / Rough Terrain/ Ramparts", new CrossDefense());
		autoChooser.addObject("Moat / Rock Wall / Rough Terrain/ Ramparts + High Goal (Vision)", new CrossShoot());
		autoChooser.addObject("Cheval De Frise", new ChevalDeFrise());
		autoChooser.addObject("Portcullis", new Portcullis());
		autoChooser.addObject("Cheval De Frise + High Goal", new ChevalDeFriseHighGoal());
		autoChooser.addObject("Portcullis +  High Goal", new PortcullisHighGoal());
		autoChooser.addObject("Low Bar + High Goal", new LowBarHighGoal());
		autoChooser.addObject("Low Bar + High Goal Far", new LowBarHighGoalFar());
		autoChooser.addObject("Low Bar + High Goal Far + Return", new LowBarHighGoalFarReturn());
		autoChooser.addObject("Low Bar + High Goal Wall (BBQ Auto)", new LowBarHighGoalFar());
		autoChooser.addObject("TestDrive (100in)", new TestDrive());
		autoChooser.addObject("TestTurn (90degrees)", new TestTurn());
		autoChooser.addObject("TestAutoShoot (Courtyard mid)", new TestTurn());
		autoChooser.addObject("TestVisionShoot (set preset in teleop)", new TestTurn());

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
