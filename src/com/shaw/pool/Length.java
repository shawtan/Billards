package com.shaw.pool;

import java.awt.Dimension;

/*
 * This probably shouldn't be a separate class...
 * It's supposed to be an arbitrary unit for calculations
 * maybe based on the mm
 */

public class Length extends Dimension{
	
	public int d;
	
	Length(int d){
		
		this.d = (d*5);
		
	}
	
	

}
