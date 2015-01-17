package org.usfirst.frc.team766.robot.commands;

import org.usfirst.frc.team766.robot.UltrasonicSensor;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CalibrateUltrasonicSensor extends Command {

    public CalibrateUltrasonicSensor() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	UltrasonicSensor.getInstance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	boolean fooBar = false;
    	UltrasonicSensor.getInstance().getDistance(fooBar);
    	if(fooBar){
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
