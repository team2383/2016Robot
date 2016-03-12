package com.team2383.robot.subsystems;

import static com.team2383.robot.HAL.feederMotor;

import com.team2383.robot.commands.SetState.StatefulSubsystem;

public class Feeder extends StatefulSubsystem<Feeder.State> {

	public Feeder() {
		feederMotor.setInverted(false);
		feederMotor.setSafetyEnabled(false);
	}

	public enum State {
		FEEDING, OUTFEEDING, STOPPED, OUTFEEDINGSLOW
	}

	public void feedIn() {
		System.out.println("Feeding!");
		feederMotor.set(1);
	}

	public void feedOut() {
		feederMotor.set(-1);
	}

	public void feedOutSlow() {
		feederMotor.set(-0.6);
	}

	public void stop() {
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
		case OUTFEEDINGSLOW:
			feedOutSlow();
			break;
		}
	}

}
