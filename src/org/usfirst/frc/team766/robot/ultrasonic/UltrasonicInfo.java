package org.usfirst.frc.team766.robot.ultrasonic;

public class UltrasonicInfo {

	public UltrasonicInfo(double[] distances, double angle, boolean isNew) {
		this.distances = distances;
		this.angle = angle;
		this.isNew = isNew;
	}
	
	public UltrasonicInfo(double distance, boolean isNew){
		distances[0] = distance;
		this.isNew = isNew;
	}
	
	public UltrasonicInfo(double[] distances, boolean isNew) {
		this.distances = distances;
		this.angle = Double.NaN;
		this.isNew = isNew;
	}
	
	public double[] getDistances(){
		return distances;
	}
	
	public double getDistance1(){
		return distances[0];
	}
	
	public double getDistance2(){
		return distances[1];
	}
	
	public double getAngle(){
		return angle;
	}
	
	public boolean isNew(){
		return isNew;
	}
	
	private double[] distances;
	private double angle;
	boolean isNew;
}
