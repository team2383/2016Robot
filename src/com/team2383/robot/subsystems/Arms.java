package com.team2383.robot.subsystems;

import static com.team2383.robot.HAL.armMotor;

import com.team2383.robot.commands.SetState.StatefulSubsystem;

public class Arms extends StatefulSubsystem<Arms.State> {

	public enum State {
		EXTENDING, RETRACTING, STOPPED
	}

	protected void set(double speed) {
		armMotor.set(speed);
	}

	private void up() {
		armMotor.set(1.0);
	}

	private void down() {
		armMotor.set(-1.0);
	}

	private void stop() {
		armMotor.set(0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setState(State state) {
		switch (state) {
		case EXTENDING:
			up();
			break;
		case RETRACTING:
			down();
			break;
		default:
		case STOPPED:
			stop();
			break;
		}
	}
}
