package com.shaw.pool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/*
 * This checks for and handles collisions between the objects
 * It works-ish
 * 
 * TODO: Write/Figure out a collision detection that works
 * 
 * Shaw Tan
 * 30/08/2012
 */


public class CollisionPhysics extends Thread {

	final int TICK = 40;

	boolean inGame;

	Ball ball[];
	Pocket pocket[];

	Dimension d;

	public CollisionPhysics(Ball[] ball) {

		super();

		this.ball = ball;

	}

	public CollisionPhysics(Ball ball, Ball[] ballArr, Dimension dimension) {

		super();

		Ball newBall[] = new Ball[ballArr.length + 1];
		for (int i = 0; i < ballArr.length; i++) {
			newBall[i] = ballArr[i];
		}
		newBall[ballArr.length] = ball;

		this.ball = newBall;
		this.d = dimension;
		
//		this.inGame = true;


	}

	public void add(Pocket[] pocket){

		this.pocket = pocket;

	}

	public void run() {
		
		this.inGame = true;
		
		try{
			while (inGame) {

				//Look for collisions
				checkCollision();


				this.sleep(TICK);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

	private void checkCollision() {

		double colTime = 2;
		Ball[] collider = new Ball[2];

		for (int i = 0; i < ball.length; i++) {
			
			if (!ball[i].isActive()) {
				continue;
			}
			
			//Check for collision between object balls
			for (int j = i+1; j < ball.length; j++) {
				//Only need to check unique combinations
				
				
				if (!ball[j].isActive()) {
					continue;
				}

				//				double t = collisionTime(ball[i], ball[j]);
				double t = collisionTime(ball[i], ball[j]);

				if (t < colTime){
					colTime = t;
					collider[0] = ball[i];
					collider[1] = ball[j];
				}

			}

			//Check if it falls into a pocket

			double t = pocketCollisionTime(ball[i]);

			if (t < colTime){
				colTime = t;
				collider[0] = null;
				collider[1] = ball[i];
			}			

			//Check for collision with the walls
			//			checkWallCollision(ball[i]);

			t = wallCollisionTime(ball[i]);

			if (t < colTime){
				colTime = t;
				collider[0] = ball[i];
				collider[1] = null;
			}


		}

		if (colTime <= 1) {

			//			System.out.println("Collision between " + collider[0].getColor().toString() + " ball" +
			//						" and " + collider[1].getColor().toString() + " ball");
			moveBalls(colTime);

			if (collider[0] != null && collider[1] != null) {	

				collide(collider[0], collider[1]);

			} else if (collider[0] != null) {

				collideWall(collider[0]);

			} else if (collider[1] != null){

				System.out.println("Pocketdrop");
				
				pocketDrop(collider[1]);

			}
			
			if (colTime < 0) {
				
				System.out.println("negative time " + (collider[0] == null) + "||" + (collider[1] == null));
				
				return;
				
			}

			try {
				this.sleep((long) (colTime * TICK));
				this.run();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			moveBalls(1);

		}

	}

	private double pocketCollisionTime(Ball ball) {
		//Returns time of falling in pocket
		double t;
		Position p = ball.getPosition();
		p.translate(ball.getVelocity().getX(), ball.getVelocity().getY());

		for (int i = 0; i < pocket.length; i++) {

			t = collisionDetectionSample(ball.getPosition(),pocket[i].getPosition(),ball.getVelocity(),new Vector(), Pocket.RADIUS);
			if (t <= 1) {
				return t;
			}
			
		}

		return 2;


	}

	private void pocketDrop(Ball ball) {

System.out.println("pocketdrop method");
		for (int i = 0; i < pocket.length; i++) {
			Vector v = new Vector(ball.getPosition(), pocket[i].getPoint());
			if (v.length() <= Pocket.RADIUS + 50) {
				System.out.println("SCORE!");
				ball.sink();
				return;
			}

		}

	}

	private double wallCollisionTime(Ball ball) {
		//Returns time of collision with wall
		double x = ball.getPosition().getX();
		double y = ball.getPosition().getY();
		double dx = ball.getVelocity().getX();
		double dy = ball.getVelocity().getY();

		double border, location, change;

		border = 0;
		location = x - Ball.RADIUS;
		change = dx;

		if (location + change < border) {

			return ((location - border) / change);
		}

		border = d.getWidth();
		location = x + Ball.RADIUS;
		change = dx;

		if (location + change > border) {

			return ((border - location) / change);
		}

		border = 0;
		location = y - Ball.RADIUS;
		change = dy;

		if (location + change < border) {

			return ((location - border) / change);
		}

		border = d.getHeight();
		location = y + Ball.RADIUS;
		change = dy;

		if (location + change > border) {

			return ((border - location) / change);
		}

		return 2;


	}

	private void collideWall(Ball ball){

		System.out.println("wall collision");
		
		double x = ball.getPosition().getX();
		double y = ball.getPosition().getY();
		double dx = ball.getVelocity().getX();
		double dy = ball.getVelocity().getY();

		double border, location, change;

		boolean horizontal = false, vertical = false;

		border = 0;
		location = x - Ball.RADIUS;
		change = dx;

		if (location + change < border) {

			horizontal = true;
		}

		border = d.getWidth();
		location = x + Ball.RADIUS;
		change = dx;

		if (location + change > border) {

			horizontal = true;			
		}

		border = 0;
		location = y - Ball.RADIUS;
		change = dy;

		if (location + change < border) {

			vertical = true;
		}

		border = d.getHeight();
		location = y + Ball.RADIUS;
		change = dy;

		if (location + change > border) {

			vertical = true;
		}


		if (horizontal)
			ball.addVelocity(new Vector(-2*dx, 0));

		if (vertical)
			ball.addVelocity(new Vector(0, -2*dy));

		if (horizontal || vertical)	{	//Slows ball if it hits a wall
			ball.slow(0.01);
//			System.out.println("wall collision of ball " + ball.getColor().toString());
		}

	}

	private void collide(Ball b1, Ball b2) {

		//The resultant force is equal (newton's 3rd)
		//to the sum of force in the direction of contact
		System.out.println("Colliding " + b1.getColor().toString() + " and " + b2.getColor().toString());

		Vector v1 = new Vector(b1.getPosition(),b2.getPosition());		//Direction of contact
		v1.setLength(b1.getVelocity().length() * Math.cos(b1.getVelocity().angle(v1)));

		Vector v2 = new Vector(b2.getPosition(),b1.getPosition());		//Reverse of v1
		v2.setLength(b2.getVelocity().length() * Math.cos(b2.getVelocity().angle(v2)));

		b1.addVelocity(v2);
		b2.addVelocity(v1);

//		v1 = v1.scalar(-1);
//		v2 = v2.scalar(-1);

		b1.addVelocity(v1.scalar(-1));
		b2.addVelocity(v2.scalar(-1));

	}

	private double collisionTime2(Ball b1, Ball b2){
		
		final int FLAG = 2;
		
		//Change it to the first ball's movement/location relative to the second ball
		Vector displacement = new Vector(b1.getPosition(),b2.getPosition());
		Vector velocity = new Vector(b1.getVelocity().subtract(b2.getVelocity()));
		
		if (Math.abs(velocity.length()) < 0.00000001){
			//It isn't moving
//			System.out.println(velocity.length() + " == 0.0");
			return FLAG;
		}
		
		if (displacement.length() - Ball.RADIUS*2 > velocity.length()) {
			//It doesn't go fast enough to get close
			return FLAG;
		}
		
		double angle = velocity.angle(displacement);
		
		if (Math.cos(angle) <= 0) {
			//It is going in the opposite direction
			return FLAG;
		}
		
		
		//Find the distance needed for the balls to JUST touch
		//Using sine law
		double angle2 = Math.asin(displacement.length()*Math.sin(angle)/(2*Ball.RADIUS));
		
		if (angle2 < Math.PI/2){		
			angle2 = Math.PI - angle2;	//Due to the ambiguous angle case
		} else {
			System.out.println("a2 = "+angle2);
			
		}
		
		double angle3 = Math.PI - angle - angle2;
		
		if (angle3 < 0) {
			System.out.println("a1=" + angle + " a2=" + angle2);
//			return FLAG;
		}
		
		double move = 2*Ball.RADIUS * Math.sin(angle3) / Math.sin(angle);
//		move = 2*Ball.RADIUS*Math.sin(angle3)/Math.sin(angle);
		
		double t = move / velocity.length();
		
		//Using Cosion rule (this isn't working)
//		double t;
//		double move;
		
//		double a = 1;
//		double b = 2*displacement.length()*Math.sin(angle);
//		double c = 4*Ball.RADIUS*Ball.RADIUS-displacement.length()*displacement.length();
//		
//		if (b*b-4*a*c <= 0.0){
//			return FLAG;
//		}
//		
//		move = (b-Math.sqrt(b*b-4*a*c))/(2*a);
//		
//		if (move < 0){
//			System.out.println("neg move");
//			move = (b+Math.sqrt(b*b-4*a*c))/(2*a);
//		}
//		
//		if (move < 0){
//			return FLAG;
//		}
//		
//		t = move / velocity.length();
		
		if (angle <0){
			System.out.println("neg angle");
		}
		if (angle2 <0){
			System.out.println("neg angle2");
		}		if (angle3 <0){
			System.out.println("neg angle3" + angle3);
		}
		
//		if (Math.abs(t) > 1){
//			return FLAG;
//		}
		
		if (t < 0){
			return Math.abs(t);
		}
	
		
		return t;
		
//		return FLAG;
	}
	

	private double collisionTime(Ball b1, Ball b2) {

		final int FLAG = 2;
		
		double t = collisionTime2(b1,b2);
		
		
		double t2 = collisionDetectionSample(b1.getPosition(), b2.getPosition(), b1.getVelocity(), b2.getVelocity(), 2*Ball.RADIUS);
		
		if (t != 2 || t2 !=2 ){
		System.out.println("dt=" + (t-t2));
		}
		
		return t;

	}

	private double collisionDetectionSample(Position A, Position B, Vector dA, Vector dB, double sumRadii) {

		final double FLAG = 2;

		/*
		 * Copied/Adapted from Gamesutra :(
		 * http://www.gamasutra.com/view/feature/131424/pool_hall_lessons_fast_accurate_.php
		 * Pool Hall Lessons: Fast, Accurate Collision Detection Between Circles or Spheres
		 * By Joe van den Heuvel,Miles Jackson
		 */

		Vector movevec = dA.subtract(dB);

		// Early Escape test: if the length of the movevec is less
		// than distance between the centers of these circles minus 
		// their radii, there's no way they can hit. 
		double dist = B.distance(A);
//		double sumRadii = (Ball.RADIUS * 2);
		dist -= sumRadii;
		if(movevec.length() < dist){
			return FLAG;
		}

		// Normalize the movevec
		Vector N = (Vector) movevec.clone();
		N = N.unitVector();

		// Find C, the vector from the center of the moving 
		// circle A to the center of B
		Vector C = new Vector(A, B);

		// D = N . C = ||C|| * cos(angle between N and C)
		double D = N.dot(C);

		// Another early escape: Make sure that A is moving 
		// towards B! If the dot product between the movevec and 
		// B.center - A.center is less that or equal to 0, 
		// A isn't isn't moving towards B
		if(D <= 0){
			return FLAG;
		}
		// Find the length of the vector C
		double lengthC = C.length();

		double F = (lengthC * lengthC) - (D * D);

		// Escape test: if the closest that A will get to B 
		// is more than the sum of their radii, there's no 
		// way they are going collide
		double sumRadiiSquared = sumRadii * sumRadii;
		if(F >= sumRadiiSquared){
			return FLAG;
		}

		// We now have F and sumRadii, two sides of a right triangle. 
		// Use these to find the third side, sqrt(T)
		double T = sumRadiiSquared - F;

		// If there is no such right triangle with sides length of 
		// sumRadii and sqrt(f), T will probably be less than 0. 
		// Better to check now than perform a square root of a 
		// negative number. 
		if(T < 0){
			return FLAG;
		}

		// Therefore the distance the circle has to travel along 
		// movevec is D - sqrt(T)
		double distance = D - Math.sqrt(T);

		// Get the magnitude of the movement vector
		double mag = movevec.length();

		// Finally, make sure that the distance A has to move 
		// to touch B is not greater than the magnitude of the 
		// movement vector. 
		if(mag < distance){
			return FLAG;
		}

		// Set the length of the movevec so that the circles will just 
		// touch


		double t = (Math.abs(distance / movevec.length()));
//				System.out.println("collision at t=" + t);
		if (t > 1) {
			return FLAG;
		}
		
		
		return t;
		//		movevec.normalize();
		//		movevec.times(distance);
		//
		//		return true;

	}

	private void moveBalls() {
		//Move each ball according to its velocity
		moveBalls(1);
	}

	private void moveBalls(double time) {
		//Move each ball according to its velocity

		for (int i = 0; i < ball.length; i++) {
			ball[i].move(time);
		}	

	}

	public void end() {

		this.inGame = false;
		
	}

}
