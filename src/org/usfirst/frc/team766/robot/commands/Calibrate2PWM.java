package org.usfirst.frc.team766.robot.commands;

import org.usfirst.frc.team766.robot.ultrasonic.UltrasonicInfo;
import org.usfirst.frc.team766.robot.ultrasonic.UltrasonicPWMReader;

import edu.wpi.first.wpilibj.command.Command;

public class Calibrate2PWM extends Command{
	private static final int SAMPLES_TO_AVERAGE = 50;
	private static final boolean PRINT_EVERY_VALUE = true;

	public Calibrate2PWM() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		resetValues();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		UltrasonicInfo data = UltrasonicPWMReader.getInstance().getDistance();
		if (data.isNew()) {
			double distance1 = data.getDistance1();
			double distance2 = data.getDistance2();
			if (PRINT_EVERY_VALUE) {
				pr(counter + "th Attempt");
				pr("Sensor 1 Distance to Target: " + distance1);
				pr("Sensor 2 Distance to Target: " + distance2);
				pr("Angle: "+data.getAngle());
			}
			distances[0][counter] = distance1;
			distances[1][counter] = distance2;
			for (int i = 0; i < min.length; i++) {
				if (distances[i][counter] > max[i]) {
					max[i] = distances[i][counter];
				}
				if (distances[i][counter] < min[i]) {
					min[i] = distances[i][counter];
				}
			}
			counter++;

			if (counter >= SAMPLES_TO_AVERAGE) {
				double means[] = new double[2];
				for (int i = 0; i < distances.length; i++) {
					means[i] = 0;
					for (double curValue : distances[i]) {
						means[i]+= curValue;
					}
					means[i]/= SAMPLES_TO_AVERAGE;
					double standardDev = 0;
					for (double curValue : distances[i]) {
						curValue -= means[i];
						curValue *= curValue;
						standardDev += curValue;
					}
					standardDev /= SAMPLES_TO_AVERAGE;
					standardDev = Math.sqrt(standardDev);
					pr("Sensor " + i + ": ");
					pr("	Mean: " + means[i]);
					pr("	Standard Deviation: " + standardDev);
					pr("	Minimum Distance: " + min[i]);
					pr("	Maximum Distance: " + max[i]);
				}
				pr("Average Angle: "+UltrasonicPWMReader.calculateAngle(means[0], means[1]));
				isFinished = true;
			}
		}
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
		end();
	}

	private void pr(String line) {
		System.out.println("Calibrate 2 Ultrasonics: " + line);
	}

	private void resetValues() {
		counter = 0;
		isFinished = false;
		for (int i = 0; i < min.length; i++) {
			min[i] = Double.MAX_VALUE;
			max[i] = Double.MIN_VALUE;
		}
	}

	private boolean isFinished = false;
	private double[] min = new double[2];
	private double[] max = new double[2];
	private double[][] distances = new double[2][SAMPLES_TO_AVERAGE];
	private int counter;
}
