package com.team2383.robot.subsystems;

import org.strongback.components.Gyroscope;
import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.TalonSRX;
import org.strongback.drive.TankDrive;

import com.kauailabs.navx.frc.AHRS;
import com.team2383.robot.Constants;
import com.team2383.robot.components.SRXMagEncoder;

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

        this.leftEncoder = new SRXMagEncoder(leftFront);
        this.rightEncoder = new SRXMagEncoder(rightRear);

        this.drive = new TankDrive(Motor.compose(leftFront, leftRear), Motor.compose(rightFront, rightRear).invert());

        this.shifter = shifter;
    }

    public double getFeetPerSecond() {
        double avgDegreesPerSecond = (leftEncoder.getRate() + rightEncoder.getRate()) / 2.0;
        return avgDegreesPerSecond * Constants.driveFeetPerDegree;
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
