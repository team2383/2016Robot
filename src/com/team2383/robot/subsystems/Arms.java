package com.team2383.robot.subsystems;

import static com.team2383.robot.HAL.armMotor;

import com.team2383.robot.commands.SetState.StatefulSubsystem;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;

public class Arms extends StatefulSubsystem<Arms.State> {

	public Arms() {
		armMotor.setInverted(true);
		armMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		armMotor.configPeakOutputVoltage(2, -2);
	}

	public enum State {
		EXTENDING(0.5), RETRACTING(-0.5), STOPPED(0);

		private final double speed;

		State(double speed) {
			this.speed = speed;
		}

		public double getSpeed() {
			return speed;
		}
	}

	public void set(double speed) {
		armMotor.set(speed);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setState(State state) {
		set(state.getSpeed());
	}
}
