package com.team2383.robot.auto;

import org.strongback.command.ControllerCommand;
import org.strongback.control.Controller;
import org.strongback.drive.TankDrive;
import org.strongback.function.DoubleToDoubleFunction;

public class AutoDriveCommand extends ControllerCommand {

    private final Runnable periodic;
    private final DoubleToDoubleFunction leftApply;
    private final DoubleToDoubleFunction rightApply;
    private final TankDrive drive;

    protected AutoDriveCommand(Controller sharedController,
                               Runnable initializer,
                               DoubleToDoubleFunction leftApply,
                               DoubleToDoubleFunction rightApply,
                               TankDrive drivetrain) {
        super(sharedController, initializer, drivetrain);
        this.leftApply = leftApply;
        this.rightApply = rightApply;
        this.drive = drivetrain;
    }

    @Override
    public boolean execute() {
        double pidValue = controller.getValue();
        drive.tank(leftApply.applyAsDouble(pidValue), rightApply.applyAsDouble(pidValue));
        return controller.isWithinTolerance();
    }

}
