package org.usfirst.frc.team766.robot.commands;

import org.usfirst.frc.team766.robot.Robot;
import org.usfirst.frc.team766.robot.ultrasonic.UltrasonicSensor;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestUltrasonic extends Command {

	public TestUltrasonic() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.exampleSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		UltrasonicSensor.getInstance();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		foo++;
		if (foo == 5) {
			System.out.println("Test Ultrasonic: Distance to target: "
					+ UltrasonicSensor.getInstance().getDistanceDouble());
			foo = 0;
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
		end();
	}

	int foo = 0;
}
