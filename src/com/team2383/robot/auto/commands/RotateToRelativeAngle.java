/* Created Sat Feb 13 18:12:51 EST 2016 */
package com.team2383.robot.auto.commands;

import org.strongback.command.ControllerCommand;
import org.strongback.control.Controller;
import org.strongback.drive.TankDrive;
import org.strongback.function.DoubleToDoubleFunction;

import com.kauailabs.navx.frc.AHRS;


public class RotateToRelativeAngle extends ControllerCommand {

    private final DoubleToDoubleFunction leftApply;
    private final DoubleToDoubleFunction rightApply;
    private final TankDrive drive;
    private AHRS ahrs;
    private Runnable initializer;
    
	public RotateToRelativeAngle(Controller sharedController, Runnable initializer, TankDrive drivetrain) {
		super(sharedController, initializer, drivetrain);
		this.leftApply = (x) -> x;
		this.rightApply = (x) -> -x;
		this.drive = drivetrain;
	}

    public RotateToRelativeAngle(Controller sharedController,
                               double setPoint,
                               DoubleToDoubleFunction leftApply,
                               DoubleToDoubleFunction rightApply,
                               TankDrive drivetrain) {
        super(sharedController, ()->{}, drivetrain);
        this.initializer = () -> { ahrs.reset(); controller.withTarget(setPoint); };
        this.leftApply = leftApply;
        this.rightApply = rightApply;
        this.drive = drivetrain;
    }

    @Override
    public boolean execute() {
    	ahrs.reset();
        double pidValue = controller.getValue();
        drive.tank(leftApply.applyAsDouble(pidValue), rightApply.applyAsDouble(pidValue));
        return controller.isWithinTolerance();
    }

}
