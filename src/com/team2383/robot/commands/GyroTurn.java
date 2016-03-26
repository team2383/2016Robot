package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroTurn extends PIDCommand {

	private double timeAtSetpoint;
	private double lastCheck;
	private final double tolerance;
	private final double wait;

	public GyroTurn(double angle) {
		this(Constants.driveHeadingMoveToVelocity, angle);
	}

	public GyroTurn(double velocity, double angle) {
		this(velocity, angle, Constants.driveHeadingMoveToTolerance);
	}

	public GyroTurn(double velocity, double angle, double tolerance) {
		this(velocity, angle, Constants.driveHeadingMoveToTolerance, Constants.pidSetpointWait);
	}

	public GyroTurn(double velocity, double angle, double tolerance, double wait) {
		super("Set Heading", Constants.driveHeadingMoveToP, Constants.driveHeadingMoveToI,
				Constants.driveHeadingMoveToD);
		requires(drivetrain);
		this.getPIDController().setInputRange(-180.0, 180.0);
		this.getPIDController().setOutputRange(-velocity, velocity);
		this.getPIDController().setContinuous();
		this.getPIDController().setSetpoint(angle);
		this.tolerance = tolerance;
		this.wait = wait;
		SmartDashboard.putData("SetHeading Controller", this.getPIDController());
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
		if (Math.abs(this.getPIDController().getError()) <= tolerance) {
			timeAtSetpoint += this.timeSinceInitialized() - lastCheck;
		} else {
			timeAtSetpoint = 0;
		}
		lastCheck = this.timeSinceInitialized();
		return timeAtSetpoint >= wait;
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
		if (this.timeSinceInitialized() > 0.1) {
			drivetrain.tank(-output, output);
		} else {
			System.out.println("Waiting for reset");
		}
	}

}
