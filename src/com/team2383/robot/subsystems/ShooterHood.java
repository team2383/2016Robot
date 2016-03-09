package com.team2383.robot.subsystems;

import static com.team2383.robot.HAL.hoodMotor;

import com.team2383.robot.Constants;
import com.team2383.robot.OI;
import com.team2383.robot.commands.MoveHood;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterHood extends Subsystem {

	public ShooterHood() {
		hoodMotor.enableBrakeMode(true);
		hoodMotor.changeControlMode(TalonControlMode.Position);
		hoodMotor.setPIDSourceType(PIDSourceType.kDisplacement);
		hoodMotor.setPID(Constants.hoodPositionP, Constants.hoodPositionI, Constants.hoodPositionD);
		hoodMotor.reverseOutput(false);
		/*
		 * hoodMotor.reverseSensor(false);
		 * hoodMotor.setForwardSoftLimit(Constants.hoodForwardLimit);
		 * hoodMotor.enableForwardSoftLimit(false);
		 */
	}

	public void moveAtSpeed(double speed) {
		hoodMotor.changeControlMode(TalonControlMode.PercentVbus);
		hoodMotor.set(speed);
	}

	public void setAngle(double angle) {
		hoodMotor.changeControlMode(TalonControlMode.Position);
		hoodMotor.setSetpoint(angle);
	}

	public void stop() {
		hoodMotor.changeControlMode(TalonControlMode.PercentVbus);
		hoodMotor.set(0);
	}

	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new MoveHood(OI.hood));
	}
}
