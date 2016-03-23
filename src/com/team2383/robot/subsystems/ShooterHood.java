package com.team2383.robot.subsystems;

import static com.team2383.robot.HAL.hoodHardStop;
import static com.team2383.robot.HAL.hoodMotor;

import java.util.function.DoubleUnaryOperator;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.HoldHood;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterHood extends Subsystem {

	public DoubleUnaryOperator mapToZero = (x) -> x - Constants.hoodReverseLimit;
	public DoubleUnaryOperator mapToRaw = (x) -> x + Constants.hoodReverseLimit;

	public ShooterHood() {
		hoodMotor.enableBrakeMode(true);
		hoodMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		hoodMotor.changeControlMode(TalonControlMode.Position);
		hoodMotor.setPID(Constants.hoodPositionP, Constants.hoodPositionI, Constants.hoodPositionD,
				Constants.hoodPositionF, Constants.hoodPositionIZone, 0, 0);
		hoodMotor.setReverseSoftLimit(Constants.hoodReverseLimit);
		hoodMotor.setForwardSoftLimit(Constants.hoodForwardLimit);
		hoodMotor.enableForwardSoftLimit(true);
		hoodMotor.enableReverseSoftLimit(true);
		hoodMotor.configPeakOutputVoltage(6.0, -6.0);
		hoodMotor.reverseOutput(false);
		hoodMotor.reverseSensor(false);
		SmartDashboard.putData("hoodMotor", hoodMotor);
	}

	public void moveAtSpeed(double speed) {
		hoodMotor.changeControlMode(TalonControlMode.PercentVbus);
		hoodMotor.set(speed);
	}

	public void holdPosition() {
		this.setRotations(this.getRotations());
	}

	public boolean isAtSetpoint() {
		return Math.abs(getRotations() - getSetpoint()) <= Constants.hoodRotationTolerance;
	}

	public void setRotations(double rotations) {
		hoodMotor.changeControlMode(TalonControlMode.Position);
		hoodMotor.setSetpoint(rotations);
	}

	public double getSetpoint() {
		return hoodMotor.getSetpoint();
	}

	public double getRotations() {
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
