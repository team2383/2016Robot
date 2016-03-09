package com.team2383.robot.subsystems;

import static com.team2383.robot.HAL.feederMotor;

import com.team2383.robot.commands.SetState.StatefulSubsystem;

public class Feeder extends StatefulSubsystem<Feeder.State> {

	public enum State {
		FEEDING, OUTFEEDING, STOPPED
	}

	private void feedIn() {
		feederMotor.set(1);
	}

	private void feedOut() {
		feederMotor.set(-1);
	}

	private void stop() {
		feederMotor.set(0);
	}

	@Override
	protected void initDefaultCommand() {
	}

	@Override
	public void setState(State state) {
		switch (state) {
		case FEEDING:
			feedIn();
			break;
		case OUTFEEDING:
			feedOut();
			break;
		default:
		case STOPPED:
			stop();
			break;
		}
	}

}
