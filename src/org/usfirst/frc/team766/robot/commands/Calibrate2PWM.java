package org.usfirst.frc.team766.robot.commands;

import org.usfirst.frc.team766.robot.UltrasonicSensorPWM;

import edu.wpi.first.wpilibj.command.Command;

public class Calibrate2PWM extends Command implements Runnable {
	private static final int SAMPLES_TO_AVERAGE = 50;
	private static final boolean PRINT_EVERY_VALUE = true;

	public Calibrate2PWM() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		sensor1 = new UltrasonicSensorPWM(8);
		sensor2 = new UltrasonicSensorPWM(6);
		resetValues();
		dataCollector = new Thread(this);
		dataCollector.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Calibrate2PWMValues data = getDistance();
		if (data.isNew) {
			double distance1 = data.sensorValues[0];
			double distance2 = data.sensorValues[1];
			if (PRINT_EVERY_VALUE) {
				pr(counter + "th Attempt");
				pr("Sensor 1 Distance to Target: " + distance1);
				pr("Sensor 2 Distance to Target: " + distance2);
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
				for (int i = 0; i < distances.length; i++) {
					double mean = 0;
					for (double curValue : distances[i]) {
						mean += curValue;
					}
					mean /= SAMPLES_TO_AVERAGE;
					double standardDev = 0;
					for (double curValue : distances[i]) {
						curValue -= mean;
						curValue *= curValue;
						standardDev += curValue;
					}
					standardDev /= SAMPLES_TO_AVERAGE;
					standardDev = Math.sqrt(standardDev);
					pr("Sensor " + i + ": ");
					pr("	Mean: " + mean);
					pr("	Standard Deviation: " + standardDev);
					pr("	Minimum Distance: " + min[i]);
					pr("	Maximum Distance: " + max[i]);
				}
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
		sensor1.free();
		sensor2.free();
		dataCollector.interrupt();
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
		isNew = false;
	}

	private synchronized double[] getDistanceDouble() {
		return sensorValues;
	}

	private synchronized Calibrate2PWMValues getDistance() {
		boolean n = isNew;
		isNew = false;
		return new Calibrate2PWMValues(sensorValues, n);
	}

	private synchronized void setDistance(double d1, double d2) {
		sensorValues[0] = d1;
		sensorValues[1] = d2;
	}

	public void run() {
		setDistance(sensor1.getDistance(), sensor2.getDistance());
		isNew = true;
	}

	public class Calibrate2PWMValues {
		public Calibrate2PWMValues(double[] sensorValues, boolean isNew) {
			this.sensorValues = sensorValues;
			this.isNew = isNew;
		}

		public double[] sensorValues;
		public boolean isNew;
	}

	private double[] sensorValues = { Double.NaN, Double.NaN };
	private boolean isNew = false;
	private Thread dataCollector;
	private boolean isFinished = false;
	private double[] min = new double[2];
	private double[] max = new double[2];
	private double[][] distances = new double[2][SAMPLES_TO_AVERAGE];
	private int counter;
	private UltrasonicSensorPWM sensor1, sensor2;
}
