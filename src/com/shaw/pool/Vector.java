

package com.shaw.pool;

//import java.awt.Point;
import java.awt.geom.Point2D;

/*
 * A vector has magnitude and direction
 * But a point could also be expressed as a vector
 * 
 * Shaw Tan
 * 16/06/2013
*/
public class Vector extends Point2D.Double{

//	private double magnitude;	//The length/speed
//	private double direction;	//The angle it is going
	
	public Vector(double x, double y){
		
		super(x,y);
		
	}
	
//	public Vector(double magnitude, double direction){
//		
//		this.magnitude = magnitude;
//		this.direction = direction;
//		
//		this.x = magnitude * 
//	}
	
	public Vector(Point2D p1, Point2D p2) {
		//Creates a vector between the two points
		super (p2.getX() - p1.getX(), p2.getY() - p1.getY());
		
	}

	public Vector() {
		
		super();
		
	}
	
	public Vector(Vector v) {
		
		super(v.getX(), v.getY());
		
	}

	public Vector add(Vector v) {
		//Vector addition
//		if (!isNaN(v.getX()))
			double x = this.x + v.getX();
		
//		if (!isNaN(v.getY()))
			double y = this.y + v.getY();
	
			return new Vector(x,y);
	}
	
	public Vector subtract(Vector v) {
		
		return this.add(v.scalar(-1));
		
	}
	
	public double angle(Vector v) {
		//Returns the angle between the vectors
		double cosa = dot(v) / (this.length() * v.length());		
		
		//In case of infinity (undefined)
		if (cosa > 1.0)
			cosa = 1.0;
		else if(cosa < -1.0)
			cosa = -1.0;
		else if (isNaN(cosa)) 
			return 0;
		
		return (Math.acos(cosa));
		
	}
	
	public double angle() {
		
		return Math.atan2(this.x, this.y);
		
	}
	
	public void setLength(double length) {
		//Changing the magnitude
		
		double magnitude = this.length();

		if (magnitude == 0){
			this.x = 0.0;
			this.y = 0.0;

			return;
		}
		
		
		this.x *= length / magnitude;
		this.y *= length / magnitude;
		
//		this.magnitude = length;

	}
	
	public double length(){
		//Returns the length (magnitude)		
		return Math.sqrt(this.x*this.x + this.y*this.y);
		
	}
	
	public double dot(Vector v){
		//Returns the scalar (dot) product of two vectors		
		return (this.x * v.getX() + this.y * v.getY());
		
	}
	
	public Vector scalar(double scalar){
		
		double x = this.x * scalar;
		double y = this.y * scalar;
		
		return new Vector(x,y);
	}
	
	public Vector unitVector() {
		//Returns a unit vector
		Vector unit = (Vector) this.clone();
		
		unit.scalar(unit.length());
		
		return unit;
		
	}
	
	public String toString(){
		
		return "Vector: [" + this.x + ", " + this.y + "]";
		
	}
	
	boolean isNaN(double x) {
		//Checks if x is NaN		
		return x != x;
	
	}
	
//	public Vector clone() {
//		
//		return new Vector()
//		
//	}
	
//	public void setDirection(double dir) {
//		
//		double length = this.getLength();
//
//		this.x = length * Math.cos(dir);
//		this.y = length * Math.sin(dir);
//		
////		this.direction = dir;
//		
//	}
//	
//	public double getDirection(){
//		
//		return (Math.atan2(this.y, this.x));
//		
////		return this.direction;
//		
//	}
	
	
	
}
