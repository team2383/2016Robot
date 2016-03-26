package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.vision;

import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionTurn extends PIDCommand {

	private double timeAtSetpoint;
	private double lastCheck;

	public VisionTurn() {
		super("VisionTurn", Constants.visionTurnP, Constants.visionTurnI, Constants.visionTurnD, .01);
		requires(drivetrain);
		this.getPIDController().setInputRange(-60.0, 60.0);
		this.getPIDController().setOutputRange(-1.0, 1.0);
		this.getPIDController().setSetpoint(0);
		SmartDashboard.putData("VisionTurn Controller", this.getPIDController());
	}

	@Override
	protected void initialize() {
		cancelIfNoTarget();
		drivetrain.enableBrake();
		drivetrain.shiftTo(Gear.LOW);
	}

	@Override
	protected void execute() {
		cancelIfNoTarget();
	}

	@Override
	protected boolean isFinished() {
		cancelIfNoTarget();
		if (Math.abs(this.getPIDController().getError()) <= Constants.visionAlignOffset) {
			timeAtSetpoint += this.timeSinceInitialized() - lastCheck;
		} else {
			timeAtSetpoint = 0;
		}
		lastCheck = this.timeSinceInitialized();
		return timeAtSetpoint >= Constants.pidSetpointWait;
	}

	@Override
	protected void end() {
		drivetrain.tank(0, 0);
	}

	@Override
	protected void interrupted() {
		drivetrain.tank(0, 0);
	}

	@Override
	protected double returnPIDInput() {
		return vision.getNearestTarget().getAzimuth();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (this.timeSinceInitialized() > 0.1) {
			drivetrain.tank(output, -output);
		} else {
			System.out.println("Waiting for reset");
		}
	}

	private void cancelIfNoTarget() {
		if (vision.getNearestTarget().getDistance() == 0.0) {
			if (getGroup() != null) {
				getGroup().cancel();
			} else {
				cancel();
			}
		}
	}

}
