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
	private double timeWithoutTarget;

	public VisionTurn() {
		super("VisionTurn", Constants.visionTurnP, Constants.visionTurnI, Constants.visionTurnD, .01);
		requires(drivetrain);
		requires(vision); // since PID loop runs faster, we require vision to
							// get
							// faster updates in the PID loop
		this.getPIDController().setInputRange(-60.0, 60.0);
		this.getPIDController().setOutputRange(-0.7, 0.7);
		this.getPIDController().setSetpoint(0);
		SmartDashboard.putData("VisionTurn Controller", this.getPIDController());
	}

	@Override
	protected void initialize() {
		drivetrain.enableBrake();
		drivetrain.shiftTo(Gear.LOW);
	}

	@Override
	protected void execute() {
		cancelIfNoTarget();
	}

	@Override
	protected boolean isFinished() {
		if (Math.abs(this.getPIDController().getError()) <= Constants.visionTargetAzimuthThreshold) {
			timeAtSetpoint += this.timeSinceInitialized() - lastCheck;
		} else {
			timeAtSetpoint = 0;
		}
		lastCheck = this.timeSinceInitialized();
		return timeAtSetpoint >= Constants.pidSetpointWait;
	}

	@Override
	protected void end() {
		System.out.println("Vision Aligned Successfully");
		drivetrain.tank(0, 0);
	}

	@Override
	protected void interrupted() {
		drivetrain.tank(0, 0);
	}

	@Override
	protected double returnPIDInput() {
		return vision.update().getNearestTarget().getAzimuth();
	}

	@Override
	protected void usePIDOutput(double output) {
		drivetrain.tank(output, -output);
	}

	private void cancelIfNoTarget() {
		if (vision.getNearestTarget().getDistance() == 0.0) {
			timeWithoutTarget += this.timeSinceInitialized() - timeWithoutTarget;
			if (timeWithoutTarget >= Constants.visionAlignOffset) {
				if (getGroup() != null) {
					getGroup().cancel();
				} else {
					cancel();
				}
			}
		}
	}

}
