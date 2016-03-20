package com.team2383.robot;

import com.kauailabs.navx.frc.AHRS;
import com.team2383.robot.subsystems.Arms;
import com.team2383.robot.subsystems.Drivetrain;
import com.team2383.robot.subsystems.Feeder;
import com.team2383.robot.subsystems.ShooterFlywheel;
import com.team2383.robot.subsystems.ShooterHood;
import com.team2383.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.VictorSP;

public class HAL {
	/** CAN IDs **/
	public static CANTalon leftFront = new CANTalon(1);
	public static CANTalon leftRear = new CANTalon(2);
	public static CANTalon rightFront = new CANTalon(4);
	public static CANTalon rightRear = new CANTalon(5);

	public static CANTalon shooterMotor = new CANTalon(8);
	public static CANTalon hoodMotor = new CANTalon(9);
	public static CANTalon armMotor = new CANTalon(10);

	public static VictorSP feederMotor = new VictorSP(0);

	/** Solenoids **/
	public static DoubleSolenoid shifter = new DoubleSolenoid(1, 0);
	public static DoubleSolenoid hoodHardStop = new DoubleSolenoid(2, 3);

	/** Sensors **/

	public static PowerDistributionPanel PDP = new PowerDistributionPanel(0);
	public static AHRS navX = new AHRS(SPI.Port.kMXP);

	public static Arms arms = new Arms();
	public static Drivetrain drivetrain = new Drivetrain();
	public static Feeder feeder = new Feeder();
	public static ShooterFlywheel shooterFlywheel = new ShooterFlywheel();
	public static ShooterHood shooterHood = new ShooterHood();
	public static Vision vision = new Vision();
}
