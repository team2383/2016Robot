/* Created Mon Feb 15 15:15:32 EST 2016 */
package com.team2383.robot.auto;

import org.strongback.command.CommandGroup;
import org.strongback.control.Controller;


import com.kauailabs.navx.frc.AHRS;
import com.team2383.robot.Robot;
import com.team2383.robot.auto.commands.Drive;
import com.team2383.robot.auto.commands.Hood;
import com.team2383.robot.auto.commands.RotateToRelativeAngle;
import com.team2383.robot.auto.commands.Shoot;

public class SpyBoxDeltaAuto extends CommandGroup{
	/*
	 * calls autonomous commands sequentially and simultaneously
	 * 
	 * This auto starts in the spy box, moves to shoot, and goes back and forth through the "Delta" Obstacle
	 * 
	 * Points: 20
	 */
	
	
	final AHRS ahrs;
	public SpyBoxDeltaAuto(Robot robot) {
		Controller controller = (Controller)robot.turnController.withTolerance(2.0).enable();
		ahrs = robot.ahrs;
        /*
         * 1) Move Hood to optimum angle
         * 2) Shoot
         * 3) Drive Forward
         * 4) turn 90 degrees left
         * 5) drive forward parallel to midway of the Delta obstacle
         * 6) turn toward Delta obstacle
         * 7) drive past obstacle
         * 8) move backward past obstacle into courtyard
         * 9) go back past obstacle into neutral zone
         * 10) turn so that our feeder is facing drivers
         */
		
		sequentially(new Hood(1.0,1.0),				
        			 new Shoot(1),
        			 new Drive(controller, () -> controller.withTarget(0), Robot.drive,12));
        
		simultaneously(new RotateToRelativeAngle(controller, -90, Robot.drive),
        			   new Hood(-1.0,1.0));
		
        sequentially(new Drive(controller, () -> controller.withTarget(-90) , Robot.drive, 60),
        			 new RotateToRelativeAngle(controller, 90, Robot.drive),
        			 new Drive(controller, () -> controller.withTarget(90) , Robot.drive, 144),
        			 new RotateToRelativeAngle(controller, 0, Robot.drive),
        			 new Drive(controller, () -> controller.withTarget(0) , Robot.drive, 48),
        			 new Drive(controller, () -> controller.withTarget(0) , Robot.drive, -48),
        			 new Drive(controller, () -> controller.withTarget(0) , Robot.drive, 60),
        			 new RotateToRelativeAngle(controller, -180, Robot.drive)
        		);
    }
}
