/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.Solenoid.Direction;
import org.strongback.components.TalonSRX;
import org.strongback.hardware.Hardware;

import com.kauailabs.navx.frc.AHRS;
import com.team2383.robot.subsystems.Drivetrain;
import com.team2383.robot.subsystems.ShooterFeeder;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;

public class HAL {
    /** CAN IDs **/
    private static TalonSRX leftFront = Hardware.Motors.talonSRX(1);
    private static TalonSRX leftRear = Hardware.Motors.talonSRX(2);
    private static TalonSRX rightFront = Hardware.Motors.talonSRX(4);
    private static TalonSRX rightRear = Hardware.Motors.talonSRX(5);

    private static TalonSRX shooter = Hardware.Motors.talonSRX(8);
    private static TalonSRX hood = Hardware.Motors.talonSRX(9);

    private static Motor feeder = Hardware.Motors.victor(0).invert();

    /** Solenoids **/
    private static Solenoid shifter = Hardware.Solenoids.doubleSolenoid(0, 1, Direction.RETRACTING);

    /** Sensors **/

    private static PowerDistributionPanel PDP = new PowerDistributionPanel(0);
    private static AHRS navX = new AHRS(SPI.Port.kMXP);

    /** Subsystem Definitions **/

    public static Drivetrain drivetrain = new Drivetrain(leftFront, leftRear,
            rightFront, rightRear,
            shifter, navX);

    public static ShooterFeeder shooterFeeder = new ShooterFeeder(shooter, feeder, hood,
            PDP, 13);

    public static TalonSRX arms = Hardware.Motors.talonSRX(10);
}
