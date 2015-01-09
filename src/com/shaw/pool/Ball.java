package com.shaw.pool;

import java.awt.*;

/*
 * This is a billards ball. uh yeah
 */
public class Ball {

	public final static double RADIUS = 8 / 2 * TablePanel.CONVERT;	//in centimeters (5.715cm)
	public final double MASS = 160;		//in grams (definitely unneeded)
	
	private Color color;		//Color of the ball
	
	private Position position;		//Where the ball is
	
	private Vector velocity;		//Speed and direction of the ball
	
	private boolean active;
	
//	Ball(Point position){
//		
//		//Create the ball!
////		this.speed = 0;
////		this.direction = 0;
//		
//		this.position = position;
//		
//	}
	
	Ball(Color color){
		
		this.color = color;
		
		this.position = new Position();
		this.velocity = new Vector();
		this.active = true;
	}
	
	public void setPosition(Point position){
		
		//New position
		this.position = new Position(position);
		
	}
	

	
	public void setPosition(Position position) {
		
		this.position = position;
		
		
	}

	public void move() {
		//Moves the ball according to its velocity
		this.position.translate((int)velocity.getX(), (int)velocity.getY());
		this.slow(0.01);
		
	}
	
	public void move(double time) {
		//Moves the ball less than one 'Tick'
		
		this.position.translate((velocity.getX() * time), (velocity.getY() * time));
		this.slow(0.01 * time);
	}
	
	public void addVelocity(Vector v) {
		
		this.velocity = this.velocity.add(v);
		
	}

	public void slow(double factor) {
		//Slows the ball by a factor
		
		this.velocity = this.velocity.scalar(1 - factor);
//		if (this.velocity.length() < 1) {
//			this.velocity = new Vector();
//		}
		
	}

	public void sink() {
		
//		this.setPosition(new Point(600, 600));
		this.slow(1);
		this.active = false;
		
	}
	
	public void restart(){
		this.active = true;
		this.slow(1);
	}
	
	public Position getPosition(){
		
		return this.position;
		
	}
	
	public Point getPoint() {
		
		return this.position.getPoint();
	}
	
	public Color getColor(){
		
		return this.color;
		
	}

	public Vector getVelocity() {
		
		return this.velocity;
		
	}
	
	public boolean isActive() {
		
		return active;
		
	}
}
