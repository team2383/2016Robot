package com.team2383.robot.subsystems;

//bring in HAL
import static com.team2383.robot.HAL.leftFront;
import static com.team2383.robot.HAL.leftRear;
import static com.team2383.robot.HAL.rightFront;
import static com.team2383.robot.HAL.rightRear;
import static com.team2383.robot.HAL.shifter;

import com.team2383.robot.Constants;
import com.team2383.robot.OI;
import com.team2383.robot.commands.TeleopDrive;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem implements PIDSource {
	private final RobotDrive robotDrive;

	public enum Gear {
		LOW, HIGH;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	public Drivetrain() {
		super("Drivetrain");

		leftFront.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		leftRear.changeControlMode(TalonControlMode.Follower);
		leftRear.set(leftFront.getDeviceID());

		rightRear.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightRear.reverseSensor(true);
		rightFront.changeControlMode(TalonControlMode.Follower);
		rightFront.set(rightRear.getDeviceID());

		this.robotDrive = new RobotDrive(leftFront, rightRear);
		robotDrive.setSafetyEnabled(false);
	}

	public void tank(double leftValue, double rightValue) {
		robotDrive.tankDrive(leftValue, rightValue);
	}

	public void arcade(double driveSpeed, double turnSpeed) {
		robotDrive.arcadeDrive(driveSpeed, turnSpeed);
	}

	public void shiftTo(Gear gear) {
		switch (gear) {
		default:
		case HIGH:
			enableBrake();
			shifter.set(Value.kForward);
			break;
		case LOW:
			disableBrake();
			shifter.set(Value.kReverse);
			break;
		}
	}

	public void shift() {
		if (getGear() == Gear.HIGH) {
			shiftTo(Gear.LOW);
		} else {
			shiftTo(Gear.HIGH);
		}
	}

	public void resetEncoders() {
		leftFront.setPosition(0);
		rightRear.setPosition(0);
	}

	public Gear getGear() {
		switch (shifter.get()) {
		case kForward:
			return Gear.HIGH;
		default:
		case kReverse:
			return Gear.LOW;
		}
	}

	public double getRotations() {
		return (leftFront.getPosition() + rightRear.getPosition()) / 2.0;
	}

	public double getInches() {
		return getRotations() * Constants.driveWheelCircumference;
	}

	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new TeleopDrive(OI.leftStick, OI.rightStick));
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return getInches();
	}

	public void setBrake(boolean brake) {
		leftFront.enableBrakeMode(brake);
		leftRear.enableBrakeMode(brake);
		rightFront.enableBrakeMode(brake);
		rightRear.enableBrakeMode(brake);
	}

	public void enableBrake() {
		setBrake(true);
	}

	public void disableBrake() {
		setBrake(false);
	}
}
