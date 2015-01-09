package com.shaw.pool;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/*
 * A panel of the table
 * Draws things
 * Also handles input
 * 
 */

public class TablePanel extends JPanel{


	public final static int CONVERT = 2;

	final int WIDTH = 370 * CONVERT;		//in centimeters (370cm)
	final int HEIGHT = 180 * CONVERT;		//in centimeters (180cm)
	final int BORDER_WIDTH = (int) (2*Ball.RADIUS);	//What idk

//	final int OFFSET = (int) (2*Ball.RADIUS);
	
	final int TICK = 50;

//	boolean inGame;
	
	Vector shootStrength;
	
	Ball cueBall = new Ball(Color.white);
	Ball objectBall[] = new Ball[15];

	Pocket pocket[] = {new Pocket(0,0), new Pocket(WIDTH/2,0), new Pocket(WIDTH,0),
			new Pocket(0,HEIGHT), new Pocket(WIDTH/2,HEIGHT), new Pocket(WIDTH,HEIGHT)};
	
	Timer timer;
	CollisionPhysics thread;		//Use a thread instead to predict collisions? Yes


	TablePanel() {

		this.setPreferredSize(new Dimension(WIDTH + 2*BORDER_WIDTH, HEIGHT + 2*BORDER_WIDTH));

		createBalls();

		timer = new Timer(TICK, new TimerListener());
		

		
		BillardsMouseAdapter mouseAdapter = new BillardsMouseAdapter(cueBall);
		
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseListener(mouseAdapter);
		this.setFocusable(true);
		
		newGame();

//				thread = new Thread();


	}

	public void newGame(){
		//Starts a new billards game
//		inGame = true;
		
		
		positionBalls();

//		cueBall.addVelocity(new Vector(8,0));

		//Start action!
		thread = new CollisionPhysics(cueBall, objectBall, new Dimension(WIDTH, HEIGHT));
		thread.add(pocket);
		thread.start();
		timer.start();

	}
	

	private void positionBalls(){
		//Place the billard balls
		cueBall.setPosition(new Point(WIDTH / 4, HEIGHT / 2));
//		cueBall.slow(1);
		cueBall.restart();
		//Position of the top triangle
		int x = WIDTH * 3/4;
		int y = HEIGHT /2;


		//		Point p = new Point(LENGTH*3/4, WIDTH / 2);	//Position of the top triangle

		int i = 0;
		loop:
			for (int j = 0; j < 5; j++) {
				for (int k = 0; k <= j; k++) {
//					objectBall[i].slow(1);

					objectBall[i].restart();
					
					objectBall[i].setPosition(
							new Position((x + j*Math.sqrt(3)*Ball.RADIUS), 
									(y + (2*k-j)*Ball.RADIUS)));


//					objectBall[i].setPosition(
//							new Point((int)(x + j*Math.sqrt(3)*(Ball.RADIUS + 5)), 
//									(int) (y + (2*k-j)* (Ball.RADIUS + 3))));


					i++;

					if (i >= objectBall.length)
						break loop;
				}
			}
	}

	private void createBalls(){
		//Creates instances of the balls in the colors they should be

		final Color[] BALL_COLOR = {Color.yellow, Color.blue, Color.red, 
				Color.pink, Color.orange, Color.green, Color.gray};

		objectBall[8-1] = new Ball(Color.black);		//8-Ball is black
		for (int i = 0; i < BALL_COLOR.length; i++) {
			for (int j = 0; j < 2; j++) {
				objectBall[i + j*8] = new Ball(BALL_COLOR[i]);
			}
		}	

	}
	
	// TODO Auto-generated method stub
	
			
		
	
	public void shootBall(Vector strength, boolean shoot) {
		//Firing the cue ball
		
		if (strength.length() < 200) {
		shootStrength = strength;
		} else {
			shootStrength = strength.scalar(200 / strength.length());
		}
		
		if (shoot) {
			
			cueBall.addVelocity(shootStrength.scalar(0.1));
			shootStrength = null;		
		}
		
	}

	public class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//Timer tick to repaint the board every once in a while
			//TODO: Check for scoring

			repaint();
			

		}

	}

	protected void paintComponent(Graphics g){
		
		g.translate(BORDER_WIDTH, BORDER_WIDTH);

		final Color TableGreen = new Color(5,85,25);

		//Draw table
		g.setColor(Color.black);
		g.fillRect(-BORDER_WIDTH, -BORDER_WIDTH, WIDTH + 2*BORDER_WIDTH, HEIGHT + 2*BORDER_WIDTH);

		g.setColor(TableGreen);
	
//		g.fillRoundRect(BORDER_WIDTH, BORDER_WIDTH, WIDTH, HEIGHT, BORDER_WIDTH,BORDER_WIDTH);
		g.fillRoundRect(0,0, WIDTH, HEIGHT, BORDER_WIDTH,BORDER_WIDTH);
		
		//Draw Pockets
		g.setColor(Color.BLACK);
		for (int i = 0; i < pocket.length; i++) {
//			g.fillOval((int) (pocket[i].getPoint().getX() - Pocket.RADIUS + BORDER_WIDTH), (int)(pocket[i].getPoint().getY() - Pocket.RADIUS + BORDER_WIDTH), (int)(Pocket.RADIUS*2), (int)(Pocket.RADIUS*2));

			g.fillOval((int) (pocket[i].getPoint().getX() - Pocket.RADIUS), (int)(pocket[i].getPoint().getY() - Pocket.RADIUS), (int)(Pocket.RADIUS*2), (int)(Pocket.RADIUS*2));
		}
		
		
		//Draw balls
		//Cue ball first
		drawBall(g,cueBall,-1);
		
		for (int i = 0; i < objectBall.length; i++) {
			if (!objectBall[i].isActive()) {
				
				objectBall[i].setPosition(new Point((int) (i*2*Ball.RADIUS),(int) (-BORDER_WIDTH + Ball.RADIUS)));
				
//				continue;
			}
			
			drawBall(g,objectBall[i],i+1);
			
		}
		
		//Draw shoot strength / angle
		
		if (shootStrength != null) {
//			System.out.println("Drawing shoot");
			
			int radius = (int) shootStrength.length();
			int angle = (int) Math.toDegrees(shootStrength.angle()) - 90;

//			System.out.println("radius = " + radius + "angle = " + angle + " ball " + cueBall.getPosition().toString());
			
			g.setColor(Color.LIGHT_GRAY);
			
			g.fillArc((int)cueBall.getPosition().getX() - radius, (int)cueBall.getPosition().getY() - radius, 
					2 * radius, 2 * radius, angle - 5, 10);
		
		}

		if (!cueBall.isActive()) {
			//just end the game if the cueball drops in a pocket...
			g.drawString("GAME OVER \n (You sunk the cue ball)", 50, 50);
			endGame();
			
		}
		
		
	}

	private void endGame() {

		thread.end();
		timer.stop();
		
	}

	private void drawBall(Graphics g, Ball ball, int i) {

		Point p = ball.getPoint();
		g.setColor(ball.getColor());
		g.fillOval((int) (p.getX() - Ball.RADIUS), (int)(p.getY() - Ball.RADIUS), (int)(Ball.RADIUS*2), (int)(Ball.RADIUS*2));
		
		if (i > 0) {
			numberBall(g, p, i);
		}
		
	}

	private void numberBall(Graphics g, Point p, int i) {
		//Number the balls
		g.setColor(Color.WHITE);
		g.drawString("" + (i+1), (int) (p.getX() - Ball.RADIUS/2),
				(int) (p.getY() + Ball.RADIUS));
		g.setFont(new Font(null, Font.BOLD, (int) Ball.RADIUS * 2));
		
	}

	public class BillardsMouseAdapter implements MouseListener, MouseMotionListener {

		private Ball ball;
		private boolean shooting;
		
		public BillardsMouseAdapter(Ball ball) {
					
			this.ball = ball;		
			
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			e.translatePoint(-BORDER_WIDTH, -BORDER_WIDTH);

			if (shooting) {
				
				shootBall(new Vector(this.ball.getPosition(), e.getPoint()), false);
				
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			e.translatePoint(-BORDER_WIDTH, -BORDER_WIDTH);
			
			if (new Vector(e.getPoint(), ball.getPosition()).length() < Ball.RADIUS){
				//Clicked within the cue ball
				System.out.println("Cueball clicked "+ cueBall.getVelocity().toString());
				if (cueBall.getVelocity().length() < 1){
				shooting = true;
				}
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			e.translatePoint(-BORDER_WIDTH, -BORDER_WIDTH);

			if (shooting) {
				
				shootBall(new Vector(this.ball.getPosition(), e.getPoint()), true);
				shooting = false;
			}
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
}