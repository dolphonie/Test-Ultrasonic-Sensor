package org.usfirst.frc.team766.robot.commands;

import org.usfirst.frc.team766.robot.UltrasonicSensorPWM;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestTwoPWMs extends Command {

	public TestTwoPWMs() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		sensor1 = new UltrasonicSensorPWM(8);
		sensor2 = new UltrasonicSensorPWM(6);
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		counter++;
		if (counter >= 5) {
			sensor1.updateValue();
			double sensor1Distance = sensor1.getDistanceDouble();
			sensor2.updateValue();
			double sensor2Distance = sensor2.getDistanceDouble();
			pr("Sensor 1 Distance: " + sensor1Distance);
			pr("Sensor 2 Distance: " + sensor2Distance);
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

	private void pr(String line) {
		System.out.println("Test 2 Ultrasonics: " + line);
	}

	private int counter = 0;
	private UltrasonicSensorPWM sensor1, sensor2;
}
