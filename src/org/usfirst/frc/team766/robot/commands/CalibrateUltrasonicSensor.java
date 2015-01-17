package org.usfirst.frc.team766.robot.commands;

import java.util.ArrayList;

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
    	boolean isNew = false;
    	double currentValue = UltrasonicSensor.getInstance().getDistance(isNew);
    	if(isNew){
    		distances.add(currentValue);
    		if(distances.size()>=100){
    			double mean = 0;;
    			for(double curValue: distances){
    				mean+=curValue;
    			}
    			mean/=distances.size();
    			double standardDev = 0;
    			for(double curValue: distances){
    				curValue-=mean;
    				curValue*=curValue;
    				standardDev+=curValue;
    			}
    			standardDev = Math.sqrt(standardDev);
    			pr("Mean: "+mean);
    			pr("Standard Deviation: "+standardDev);
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
    }
    
    private void pr (String printData){
    	System.out.println("Calibrate Ultrasonic: "+printData);
    }
    
    boolean isFinished = false;
    ArrayList<Double> distances = new ArrayList<Double>();
}
