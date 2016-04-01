package com.team2383.robot.subsystems;

import static com.team2383.robot.HAL.shooterMotor;

import java.util.function.DoubleSupplier;

import com.team2383.robot.Constants;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterFlywheel extends Subsystem {

	private DoubleSupplier rpmSupplier = () -> 0;

	public ShooterFlywheel() {
		shooterMotor.enableBrakeMode(false);
		shooterMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		shooterMotor.changeControlMode(TalonControlMode.Speed);
		shooterMotor.reverseSensor(true);
		shooterMotor.configPeakOutputVoltage(12.0, -3.0);
		shooterMotor.setPID(Constants.shooterFlywheelP, Constants.shooterFlywheelI, Constants.shooterFlywheelD,
				Constants.shooterFlywheelF, Constants.shooterFlywheelIZone, 0, 0);
		shooterMotor.enable();
		SmartDashboard.putData("shooterMotor", shooterMotor);
	}

	/**
	 * call periodically to spool the shooter to a certain RPM BANG-BANG
	 * controller: Off if at or above setpoint, On otherwise.
	 *
	 * @param rpm
	 */
	public void spoolToSetpoint() {
		double setRPM = rpmSupplier.getAsDouble();
		shooterMotor.changeControlMode(TalonControlMode.Speed);
		shooterMotor.enable();
		shooterMotor.setSetpoint(setRPM);
		shooterMotor.enableBrakeMode(false);
	}

	public void stop() {
		shooterMotor.enableBrakeMode(true);
		shooterMotor.disable();
	}

	public void setRPM(double rpm) {
		setRPM(() -> rpm);
	}

	public void setRPM(DoubleSupplier rpmSupplier) {
		this.rpmSupplier = rpmSupplier;
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

	public double getSuppliedSetpoint() {
		return rpmSupplier.getAsDouble();
	}

	public double getSetpoint() {
		return shooterMotor.getSetpoint();
	}
}
