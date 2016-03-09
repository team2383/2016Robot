package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetHeading extends PIDCommand {

	public SetHeading(double angle) {
		this(Constants.driveHeadingMoveToVelocity, angle);
	}

	public SetHeading(double velocity, double angle) {
		super("Set Heading", Constants.driveHeadingMoveToP, Constants.driveHeadingMoveToI,
				Constants.driveHeadingMoveToD);
		requires(drivetrain);
		this.getPIDController().setInputRange(-180.0, 180.0);
		this.getPIDController().setOutputRange(-velocity, velocity);
		this.getPIDController().setContinuous();
		this.getPIDController().setAbsoluteTolerance(Constants.driveHeadingTolerance);
		this.getPIDController().setSetpoint(angle);
	}

	@Override
	protected void initialize() {
		navX.reset();
		drivetrain.enableBrake();
		drivetrain.shiftTo(Gear.LOW);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		SmartDashboard.putNumber("SetHeading error", this.getPIDController().getError());
		return Math.abs(this.getPIDController().getError()) <= Constants.driveHeadingTolerance;
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
		return navX.getYaw();
	}

	@Override
	protected void usePIDOutput(double output) {
		drivetrain.tank(-output, output);
	}

}
