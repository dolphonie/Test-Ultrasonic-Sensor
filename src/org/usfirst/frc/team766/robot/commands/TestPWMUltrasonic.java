package org.usfirst.frc.team766.robot.commands;

import org.usfirst.frc.team766.robot.ultrasonic.UltrasonicSensorPWM;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestPWMUltrasonic extends Command {
	private static final int PORT = 8;

	public TestPWMUltrasonic() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		sensor = new UltrasonicSensorPWM(PORT);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		counter++;
		if (counter >= 5) {
				System.out.println("Test Ultrasonic PWM: Distance to target: "
						+ sensor.getDistance());			
			counter = 0;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		sensor.free();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

	UltrasonicSensorPWM sensor;
	int counter = 0;
}
