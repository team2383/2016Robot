package com.team2383.robot.subsystems;

import static com.team2383.robot.HAL.shooterMotor;

import com.team2383.robot.Constants;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterFlywheel extends Subsystem {

	private final double setpoint = Constants.shooterMinRPM;

	public ShooterFlywheel() {
		shooterMotor.enableBrakeMode(false);
		shooterMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		shooterMotor.changeControlMode(TalonControlMode.Speed);
		shooterMotor.reverseSensor(true);
		shooterMotor.configPeakOutputVoltage(12.0, 0.0);
		shooterMotor.setPID(Constants.shooterFlywheelP, Constants.shooterFlywheelI, Constants.shooterFlywheelD,
				Constants.shooterFlywheelF, Constants.shooterFlywheelIZone, 0, 0);
		shooterMotor.enable();
	}

	/**
	 * call periodically to spool the shooter to a certain RPM BANG-BANG
	 * controller: Off if at or above setpoint, On otherwise.
	 *
	 * @param rpm
	 */
	public void spoolToSetpoint(double setpoint) {
		shooterMotor.enableBrakeMode(false);
		shooterMotor.changeControlMode(TalonControlMode.Speed);
		shooterMotor.setSetpoint(setpoint);
	}

	public void set(double speed) {
		shooterMotor.enableBrakeMode(false);
		shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
		shooterMotor.set(speed);
	}

	public void spool() {
		set(1);
	}

	public void stop() {
		shooterMotor.enableBrakeMode(true);
		set(0);
	}

	public void unspool() {
		set(-1);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

	public boolean isAtSetpoint() {
		return Math.abs(shooterMotor.getError()) < Constants.shooterRPMTolerance;
	}

	public double getRPM() {
		return shooterMotor.getSpeed();
	}

	public double getSetpoint() {
		return shooterMotor.getSetpoint();
	}
}
