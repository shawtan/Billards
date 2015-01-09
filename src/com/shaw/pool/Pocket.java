package com.shaw.pool;

import java.awt.Point;

public class Pocket{

	public final static int RADIUS = (int) (Ball.RADIUS * 2);
	
	private Position position;
	
	public Pocket() {
		this.position = new Position();
	}
	
	public Pocket(int x, int y){
		
		this.position = new Position(x,y);
	}

	public Position getPosition() {
		return position;
	}
	
	public Point getPoint() {
		
		return position.getPoint();
		
	}
	
	
	
}
