package com.team2383.robot.subsystems;

import org.strongback.components.Gyroscope;
import org.strongback.components.Solenoid;
import org.strongback.components.Switch;
import org.strongback.components.TalonSRX;
import org.strongback.drive.TankDrive;

import com.kauailabs.navx.frc.AHRS;
import com.team2383.robot.Constants;
import com.team2383.robot.components.SRXMagEncoder;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class Drivetrain extends Subsystem {
    public final TalonSRX leftFront, leftRear, rightFront, rightRear;
    public final AHRS navX;
    public final Solenoid shifter;
    public final TankDrive drive;
    public final Gyroscope leftEncoder, rightEncoder;

    public enum Gear {
        LOW, HIGH;
    }

    /**
     *
     * @param leftFront
     * @param leftRear
     * @param rightFront
     * @param rightRear
     * @param shifter
     * @param navX
     */
    public Drivetrain(TalonSRX leftFront, TalonSRX leftRear, TalonSRX rightFront, TalonSRX rightRear, Solenoid shifter,
                      AHRS navX) {
        super("Drive");
        this.leftFront = leftFront;
        this.leftRear = leftRear;
        this.rightFront = rightFront;
        this.rightRear = rightRear;
        this.navX = navX;

        leftRear.getWPILibCANTalon().changeControlMode(TalonControlMode.Follower);
        leftRear.getWPILibCANTalon().set(leftFront.getDeviceID());

        rightFront.getWPILibCANTalon().setInverted(true);
        // since rightRear is a slave, no need to invert it;
        rightRear.getWPILibCANTalon().changeControlMode(TalonControlMode.Follower);
        rightRear.getWPILibCANTalon().set(rightFront.getDeviceID());

        this.leftEncoder = new SRXMagEncoder(leftFront);
        this.rightEncoder = new SRXMagEncoder(rightRear);

        this.drive = new TankDrive(leftFront, rightFront);

        this.shifter = shifter;
    }

    public double getFeetPerSecond() {
        double avgDegreesPerSecond = (leftEncoder.getRate() + rightEncoder.getRate()) / 2.0;
        return avgDegreesPerSecond * Constants.driveFeetPerDegree;
    }

    public boolean tank(Switch shiftUp, Switch shiftDown, double leftSpeed, double rightSpeed) {
        if (shiftUp.isTriggered()) {
            shiftTo(Gear.HIGH);
        } else if (shiftDown.isTriggered()) {
            shiftTo(Gear.LOW);
        }

        drive.tank(leftSpeed, rightSpeed);
        return false;
    }

    public boolean arcade(Switch shiftUp, Switch shiftDown, double driveSpeed, double turnSpeed) {
        if (shiftUp.isTriggered()) {
            shiftTo(Gear.HIGH);
        } else if (shiftDown.isTriggered()) {
            shiftTo(Gear.LOW);
        }

        drive.arcade(driveSpeed, turnSpeed);
        return false;
    }

    public void shiftTo(Gear gear) {
        switch (gear) {
            case HIGH:
                shifter.extend();
                break;
            case LOW:
                shifter.retract();
                break;
        }
    }

    public void shift() {
        if (shifter.isRetracting()) {
            shifter.extend();
        } else {
            shifter.retract();
        }
    }

    public Gear getGear() {
        switch (shifter.getDirection()) {
            case EXTENDING:
                return Gear.HIGH;
            default:
            case RETRACTING:
                return Gear.LOW;
        }
    }
}
