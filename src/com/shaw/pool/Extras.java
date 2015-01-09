package com.shaw.pool;

public class Extras {

	/*
	
	//	private double collisionWallTime(Point p, Vector v, boolean horizontal, boolean vertical) {
	//		//Returns time of collision with wall
	//		
	//		
	//		
	//		
	//	}
	
		private void checkCollision() {
		
			double colTime = 2;
			Ball[] colliders = new Ball[2];
		
			for (int i = 0; i < objectBall.length; i++) {
		
				//Check for collision between cue ball and object balls
		
				if (isCollision(objectBall[i].getPosition(), cueBall.getPosition())) {
					collide(objectBall[i], cueBall);
				}
		
				//Check for collision between object balls
				for (int j = i+1; j < objectBall.length; j++) {
					//Only need to check unique combinations
		
		
					if (isCollision(objectBall[i].getPosition(), objectBall[j].getPosition())) {
						collide(objectBall[i], objectBall[j]);
					}					
				}
		
				//Check for collision with the walls
				checkWallCollision(objectBall[i]);
		
			}
		
		
			if (colTime <= 1) {
		
			}
		
			checkWallCollision(cueBall);
		
		}

	private void checkWallCollision(Ball ball) {
		//Check and handle collisions with the wall boundaries
	
		double x = ball.getPosition().getX();
		double y = ball.getPosition().getY();
	
		collideWall(ball,(x - Ball.RADIUS < 0 || x + Ball.RADIUS > WIDTH), 
				(y - Ball.RADIUS < 0 || y + Ball.RADIUS > HEIGHT) );
	
	
	}

	//	private double collisionWallTime(Point p, Vector v, boolean horizontal, boolean vertical) {
	//		//Returns time of collision with wall
	//		
	//		
	//		
	//		
	//	}
	
		private void collideWall(Ball ball, boolean horizontal, boolean vertical){
	
			double dx = ball.getVelocity().getX();
			double dy = ball.getVelocity().getY();
	
			if (horizontal)
				ball.addVelocity(new Vector(-2*dx, 0));
	
			if (vertical)
				ball.addVelocity(new Vector(0, -2*dy));
			if (horizontal || vertical)		//Slows ball if it hits a wall
				ball.slow(0.9);
	
		}

	private void collide(Ball b1, Ball b2) {
	
		//The resultant force is equal (newton's 3rd)
		//to the sum of force in the direction of contact
	
		//		seperate(b1, b2);
	
	
		Vector v1 = new Vector(b1.getPosition(),b2.getPosition());		//Direction of contact
	
		//		Point p1 = b1.getPosition();
		//		p1.translate(dx, dy);
	
		v1.setLength(b1.getVelocity().length() * Math.cos(b1.getVelocity().angle(v1)));
	
		Vector v2 = new Vector(b2.getPosition(),b1.getPosition());		//Reverse of v1
		//		System.out.println("b2 to b1: " + v2.toString());
		v2.setLength(b2.getVelocity().length() * Math.cos(b2.getVelocity().angle(v2)));
		//		System.out.println("b2.v.angle(v2) = " + b2.getVelocity().angle(v2));
		//		System.out.println("b2.v = " + b2.getVelocity().toString());
		//
		//		System.out.println("v2 = " + v2.toString());
		//		if (v1.length() == 0 && v2.length() == 0) {
		//			seperate(b1, b2);
		//			return;
		//		}
		//			
	
		b2.addVelocity(v1);
		b1.addVelocity(v2);
	
		v1 = v1.scalar(-1);
		v2 = v2.scalar(-1);
	
		b1.addVelocity(v1);
		b2.addVelocity(v2);
	
		b1.move();
		b2.move();
	
		//		System.out.println("b1.v: " + b1.getVelocity().toString());
	
		//		timer.stop();
	}

	private boolean isCollision(Point p1, Point p2) {
	
		//Collision if distance between them is smaller than their radius
		return (p1.distance(p2) < 2*Ball.RADIUS);
	
	}

	private void moveBalls() {
		//Move each ball according to its velocity
	
		cueBall.move();
	
		for (int i = 0; i < objectBall.length; i++) {
			objectBall[i].move();
		}
	}

	*/
	
	
}
