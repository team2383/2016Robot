package com.team2383.robot.commands;

import static com.team2383.robot.HAL.feeder;
import static com.team2383.robot.HAL.shooterFlywheel;

import com.team2383.robot.Constants;
import com.team2383.robot.HAL;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Spool shooter flywheel
 */
public class AutoShoot extends Command {
	double timeAtSetpoint;
	double lastCheck;
	double shootStartTime;
	boolean setShootStart;
	boolean done;

	public AutoShoot() {
		super("Auto Shoot");
		requires(shooterFlywheel);
		SmartDashboard.putBoolean("spooling?", false);
	}

	@Override
	protected void initialize() {
		timeAtSetpoint = 0.0;
		lastCheck = 0.0;
		shootStartTime = 0.0;
		done = false;
		setShootStart = false;
	}

	@Override
	protected void execute() {
		SmartDashboard.putBoolean("spooling?", true);
		shooterFlywheel.spoolToSetpoint();

		if (readyToShoot() && !setShootStart) {
			// shooter has reached rpm
			// so record when we started so we know when
			// to stop running the feeder
			this.shootStartTime = this.timeSinceInitialized();
			this.setShootStart = true;
		}

		if (setShootStart && this.shootStartTime != 0.0) {
			// we set a shootStartTime
			// so we know we are readyToShoot
			// so we should run the feeder to shoot
			feeder.feedIn();

			// once feeder has been running for 0.6 seconds
			// we are done and can stop the feeder and scommand
			if (this.timeSinceInitialized() - shootStartTime > Constants.shooterFollowThruTime) {
				feeder.stop();
				done = true;
			}
		} else {
			// we havent set a start or it was invalid so reset
			this.shootStartTime = 0.0;
			this.setShootStart = false;
		}
	}

	@Override
	protected boolean isFinished() {
		return done;
	}

	@Override
	protected void end() {
		SmartDashboard.putBoolean("spooling?", false);
		shooterFlywheel.stop();
		feeder.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}

	private boolean readyToShoot() {
		if (HAL.shooterFlywheel.isAtSetpoint()) {
			timeAtSetpoint += this.timeSinceInitialized() - lastCheck;
		} else {
			timeAtSetpoint = 0;
		}
		lastCheck = this.timeSinceInitialized();
		return timeAtSetpoint >= Constants.shooterRPMWaitTime;
	}
}
