package org.usfirst.frc.team766.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team766.robot.ultrasonic.UltrasonicSensor;
import org.usfirst.frc.team766.robot.ultrasonic.UltrasonicSensor.UltrasonicValue;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CalibrateUltrasonicSensor extends Command {
	private static final boolean PRINT = true;

	public CalibrateUltrasonicSensor() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		UltrasonicSensor.getInstance();
		setValues();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		UltrasonicValue currentValue = UltrasonicSensor.getInstance()
				.getDistance();
		if (currentValue.isNew) {
			pr("Distance to target: "
					+ new String(new Double(currentValue.distance).toString()));
			distances.add(currentValue.distance);
			pr("Array Size: " + distances.size());
			if (currentValue.distance > max) {
				max = currentValue.distance;
			}
			if (currentValue.distance < min) {
				min = currentValue.distance;
			}

			if (distances.size() >= 100) {
				double mean = 0;
				for (double curValue : distances) {
					mean += curValue;
				}
				mean /= distances.size();
				double standardDev = 0;
				for (double curValue : distances) {
					curValue -= mean;
					curValue *= curValue;
					standardDev += curValue;
				}
				standardDev /= distances.size();
				standardDev = Math.sqrt(standardDev);
				pr("Mean: " + mean);
				pr("Standard Deviation: " + standardDev);
				pr("Minimum Distance: " + min);
				pr("Maximum Distance: " + max);
				setValues();
				isFinished = true;
			}
		}
	}

	
	private void setValues(){
		min = Double.MAX_VALUE;
		max = Double.MIN_VALUE;
		distances.clear();
		isFinished = false;
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isFinished;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

	private void pr(String printData) {
		if (PRINT)
			System.out.println("Calibrate Ultrasonic: " + printData);
	}

	double min;
	double max;
	boolean isFinished = false;
	ArrayList<Double> distances = new ArrayList<Double>();
}
