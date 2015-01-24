package org.usfirst.frc.team766.robot.commands;

import org.usfirst.frc.team766.robot.UltrasonicSensor;
import org.usfirst.frc.team766.robot.UltrasonicSensorPWM;
import org.usfirst.frc.team766.robot.UltrasonicSensorPWM.UltrasonicValuePWM;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestPWMUltrasonic extends Command {
	private static final int PORT = 8;

	public TestPWMUltrasonic() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		//sensor = new UltrasonicSensorPWM(PORT);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		counter++;
		if (counter >= 5) {
			sensor.updateValue();
			UltrasonicValuePWM distance = sensor.getDistance();
			if (distance.isNew) {
				System.out.println("Test Ultrasonic PWM: Distance to target: "
						+ distance.distance);
			}
			counter = 0;
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

	UltrasonicSensorPWM sensor;
	int counter = 0;
}
