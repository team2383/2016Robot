package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

import com.team2383.robot.Constants;

import edu.wpi.first.wpilibj.command.PIDCommand;

public class SetHeading extends PIDCommand {

	public SetHeading(double angle) {
		super("Set Heading", Constants.driveHeadingP, Constants.driveHeadingI, Constants.driveHeadingD);
		requires(drivetrain);
		this.getPIDController().setInputRange(-180.0, 180.0);
		this.getPIDController().setOutputRange(-0.5, 0.5);
		this.getPIDController().setContinuous();
		this.getPIDController().setAbsoluteTolerance(Constants.driveHeadingTolerance);
		this.getPIDController().setSetpoint(angle);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return this.getPIDController().onTarget();
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
