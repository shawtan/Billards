package com.shaw.pool;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Position extends Point2D.Double {

	public Position(double x, double y) {
		
		super(x,y);
		
	}
	
	public Position() {
		
		super();
		
	}
	
	public Position(Point position) {
		
		super(position.getX(), position.getY());
		
	}

	public void translate(double x, double y) {
		
		this.setLocation(this.x + x, this.y + y);
		
	}

	public Point getPoint() {
		
		return new Point((int)this.getX(), (int)this.getY());
		
	}
	

}
