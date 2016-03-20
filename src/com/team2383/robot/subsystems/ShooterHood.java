package com.team2383.robot.subsystems;

import static com.team2383.robot.HAL.hoodHardStop;
import static com.team2383.robot.HAL.hoodMotor;

import java.util.function.DoubleUnaryOperator;

import com.team2383.ninjaLib.Values;
import com.team2383.robot.Constants;
import com.team2383.robot.commands.HoldHood;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterHood extends Subsystem {

	public DoubleUnaryOperator mapToAngle = Values.mapRange(Constants.hoodReverseLimit, Constants.hoodForwardLimit, 0,
			90);
	public DoubleUnaryOperator mapToNative = Values.mapRange(0, 90, Constants.hoodReverseLimit,
			Constants.hoodForwardLimit);

	public ShooterHood() {
		hoodMotor.enableBrakeMode(true);
		hoodMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		hoodMotor.changeControlMode(TalonControlMode.Position);
		hoodMotor.setPID(Constants.hoodPositionP, Constants.hoodPositionI, Constants.hoodPositionD,
				Constants.hoodPositionF, Constants.hoodPositionIZone, 0, 0);
		hoodMotor.setReverseSoftLimit(Constants.hoodReverseLimit);
		hoodMotor.setForwardSoftLimit(Constants.hoodForwardLimit);
		hoodMotor.configPeakOutputVoltage(6.0, -6.0);
		hoodMotor.reverseOutput(false);
		hoodMotor.reverseSensor(true);
		SmartDashboard.putData("hoodMotor", hoodMotor);
	}

	public void moveAtSpeed(double speed) {
		hoodMotor.changeControlMode(TalonControlMode.PercentVbus);
		hoodMotor.set(speed);
	}

	public void setSetpointAngle(double angle) {
		setSetpointNative(mapToNative.applyAsDouble(angle));
	}

	public void setSetpointNative(double rotations) {
		hoodMotor.changeControlMode(TalonControlMode.Position);
		hoodMotor.setSetpoint(rotations);
	}

	public void holdPosition() {
		this.setSetpointNative(this.getRotationsNative());
	}

	public boolean isAtSetpoint() {
		return hoodMotor.getError() <= Constants.hoodDegreeTolerance;
	}

	public double getSetpointAngle() {
		return mapToAngle.applyAsDouble(getSetpointNative());
	}

	public double getAngle() {
		return mapToAngle.applyAsDouble(getRotationsNative());
	}

	public double getSetpointNative() {
		return hoodMotor.getSetpoint();
	}

	public double getRotationsNative() {
		return hoodMotor.getPosition();
	}

	public void extendHardStop() {
		hoodHardStop.set(Value.kForward);
	}

	public void retractHardStop() {
		hoodHardStop.set(Value.kReverse);
	}

	public void stop() {
		hoodMotor.changeControlMode(TalonControlMode.PercentVbus);
		hoodMotor.set(0);
	}

	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new HoldHood());
	}
}
