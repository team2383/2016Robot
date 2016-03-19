package com.team2383.robot.subsystems;

import static com.team2383.robot.HAL.hoodMotor;

import java.util.function.DoubleUnaryOperator;

import com.team2383.ninjaLib.Values;
import com.team2383.robot.Constants;
import com.team2383.robot.OI;
import com.team2383.robot.commands.MoveHood;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterHood extends Subsystem {

	public DoubleUnaryOperator mapToAngle = Values.mapRange(Constants.hoodReverseLimit, Constants.hoodForwardLimit, 0,
			90);
	public DoubleUnaryOperator mapToNative = Values.mapRange(Constants.hoodReverseLimit, Constants.hoodForwardLimit, 0,
			90);

	public ShooterHood() {
		hoodMotor.enableBrakeMode(true);
		hoodMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		hoodMotor.changeControlMode(TalonControlMode.Position);
		hoodMotor.setPID(Constants.hoodPositionP, Constants.hoodPositionI, Constants.hoodPositionD,
				Constants.hoodPositionF, Constants.hoodPositionIZone, 0, 0);
		hoodMotor.reverseOutput(false);
		hoodMotor.reverseSensor(false);
	}

	public void moveAtSpeed(double speed) {
		hoodMotor.changeControlMode(TalonControlMode.PercentVbus);
		hoodMotor.set(speed);
	}

	public void setSetpoint(double angle) {
		hoodMotor.changeControlMode(TalonControlMode.Position);
		hoodMotor.setSetpoint(angle);
	}

	public void holdPosition(double angle) {
		this.setSetpoint(this.getSetpoint());
	}

	public boolean isAtSetpoint() {
		return hoodMotor.getError() <= Constants.hoodDegreeTolerance;
	}

	public double getSetpoint() {
		return hoodMotor.getSetpoint();
	}

	public double getRotations() {
		return hoodMotor.getPosition();
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
