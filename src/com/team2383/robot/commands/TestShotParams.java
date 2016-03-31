package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterFlywheel;
import static com.team2383.robot.HAL.shooterHood;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestShotParams extends Command {

	public TestShotParams() {
		super("TestShotParams");
		requires(shooterHood);
		SmartDashboard.putNumber("SD TEST RPM", 0);
		SmartDashboard.putNumber("SD TEST HOOD", 0);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		shooterFlywheel.setRPM(SmartDashboard.getNumber("SD TEST RPM", 0));
		shooterHood.setRotations(SmartDashboard.getNumber("SD TEST HOOD", 0));
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
