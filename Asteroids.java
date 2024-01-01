package Astr_pack;

import java.applet.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.*;

/*
 * Main class of Game
 * inherits from Applet
 * Implements Runnable and Keylistner interface
 */
public class Asteroids extends Applet implements Runnable, KeyListener {

	// The main game loop
	Thread gameLoop;
	
	// a double back buffer
	BufferedImage backImage;
	
	//draws all components
	Graphics2D g2D;
	
	//Toggle between bounds
	boolean showBounds = false;
	
	//Create the Asteroids array
	int ASTEROIDS = 20;
	Asteroid[] ast = new Asteroid[ASTEROIDS];
	
	//create Bullet array
	int BULLETS = 10;
	Bullet[] bullets = new Bullet[BULLETS];
	int currentBullet = 0;
	
	//create player ship
	Ship playerShip = new Ship();
	
	//The identity transform
	AffineTransform identity = new AffineTransform();
	
	//Random number generator for Asteroids placement
	Random rand = new Random();
	
	//init Applet
	public void init() {
		//create double buffer to store graphics
		backImage = new BufferedImage(1920,  1080, BufferedImage.TYPE_INT_RGB);
		
		//create our graphics
		g2D = backImage.createGraphics();
		
		//intit player's ship
		playerShip.setX(320);
		playerShip.setY(240);
		
		//Init the Bullets
		for(int n = 0; n < BULLETS; n++) {
			bullets[n] = new Bullet();
		}
		
		//Init the Asteroids
		for(int n = 0; n < ASTEROIDS; n++) {
			ast[n] = new Asteroid();
			ast[n].setRotationVelocity((double)rand.nextInt(3) + 1);
			ast[n].setX((double)rand.nextInt(600) + 20);
			ast[n].setY((double)rand.nextInt(440) + 20);
			ast[n].setMoveAngle(rand.nextInt(360));
			double ang = ast[n].getMoveAngle() - 90;
			ast[n].setVelX(calcAngleVelX(ang));
			ast[n].setVelY(calcAngleVelY(ang));
			
			
			
		}
		
		//Init the listener
		addKeyListener(this);
	}
	
	//updates graphics on window
	public void update(Graphics graphics) {
		//sets transform tto start at identity
		g2D.setTransform(identity);
		
		//erase background
		g2D.setPaint(Color.black);
		g2D.fillRect(0, 0, getSize().width,  getSize().height);
		
		//display game information
		g2D.setColor(Color.white);
		g2D.drawString("Ship: " + Math.round(playerShip.getX()) + "," + Math.round(playerShip.getY()), 5, 10);
		g2D.drawString("Move angel: " + Math.round(playerShip.getMoveAngle()) + 90, 5, 15);
		g2D.drawString("Face angel: " + Math.round(playerShip.getFaceAngle()), 5, 15);
		
		//draw the graphics on the screen
		drawShip();
		drawBullets();
		drawAsteroids();
		
		//paint graphics on the applet
		paint(graphics);
	}
	
	//applet window repaint event -- draw the back buffer
	public void paint(Graphics graphics) {
		//draw the back buffer onto the applet window
		graphics.drawImage(backImage, 0, 0,  this);
	}
	
	//drawship is called in the applet update event
	public void drawShip() {
		g2D.setTransform(identity);
		g2D.translate(playerShip.getX(), playerShip.getY());
		g2D.rotate(Math.toRadians(playerShip.getFaceAngle()));
		g2D.setColor(Color.orange);
		g2D.fill(playerShip.getShape());
	}
	
	//draw bullets is called in the applet update event
	public void drawBullets() {
		//Iterates through the bukkets array
		for(int n = 0; n < BULLETS; n++) {
			
			//check to see if the bullet is alive
			if(bullets[n].isAlive()) {
				g2D.setTransform(identity);
				g2D.translate(bullets[n].getX(), bullets[n].getY());
				g2D.setColor(Color.MAGENTA);
				g2D.draw(bullets[n].getShape());
			}
		}
	}
	//draw Asteroids is called in the applett update event
	public void drawAsteroids() {
		
		// Iterates through Asteroids array
		for (int n = 0; n < ASTEROIDS; n++) {
			
			// check to see if Asteroid is alive
			if(ast[n].isAlive()) {
				g2D.setTransform(identity);
				g2D.translate(ast[n].getX(), ast[n].getY());
				g2D.rotate(Math.toRadians(ast[n].getMoveAngle()));
				g2D.setColor(Color.DARK_GRAY);
				g2D.fill(ast[n].getShape());				
			}
		}
		
	}	
		
	//Start the game - start running the game loop
	public void start() {
		//crate a game loop thread
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	
	//the run event - game loop

	public void run() {
		// get the current thread
		Thread thread = Thread.currentThread();
		
		//loops continues as long as the tread is still alive
		while(thread == gameLoop) {
			try {
				//Update the game
				gameUpdate();
				
				//Set the target frame rate 50fps
				Thread.sleep(20);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			repaint();
		}
	}
	
	//Threqds Stop
	public void stop() {
		gameLoop = null;
	}
	
	//moves and animates the objects
	public void gameUpdate() {
		updateShip();
		updateBullets();
		updateAsteroids();
		checkCollisions();
	}
	
	//update ship position bases on velocity
	public void updateShip() {
		//update ship x pos
		playerShip.incX(playerShip.getVelX());
		
		//wrap around left / right
		if(playerShip.getX() < -10) {
			playerShip.setX(getSize().width +10);
		}
		else if(playerShip.getX() > getSize().width + 10) {
			playerShip.setX(-10);
		}
		
		//update ship Y pos
		playerShip.incY(playerShip.getVelY());
		
		//wrap around top / bottom
		if(playerShip.getY() < -10) {
			playerShip.setY(getSize().height +10);
		}
		else if(playerShip.getY() > getSize().height + 10) {
			playerShip.setY(-10);
		}
	}
	
	//update bullets
	public void updateBullets() {
		//Iterate bullet array
		for (int n = 0; n < BULLETS; n++) {
			
			//is the bullet alive
			if(bullets[n].isAlive()) {
				
				//update bullets x pos
				bullets[n].incX(bullets[n].getVelX());
				
				//bullet disappears if at left /right edge
				if (bullets[n].getX() < 0 || bullets[n].getX() > getSize().width) {
					bullets[n].setAlive(false);
				}
				
				//update bullets y pos
				bullets[n].incY(bullets[n].getVelY());
				
				//bullet disappears if at left /right edge
				if (bullets[n].getY() < 0 || bullets[n].getY() > getSize().height) {
					bullets[n].setAlive(false);
				}
			}
		}
	}
	
	//update Asteroid
	public void updateAsteroids() {
		//move and rotate the asteroids
		for (int n = 0; n < ASTEROIDS; n++) {
			//is this asteroid being used?
			if (ast[n].isAlive()) {
				//update the asteroidâ€™s X value
				ast[n].incX(ast[n].getVelX());
				
				//warp the asteroid at screen edges
				if (ast[n].getX() < -20)
					ast[n].setX(getSize().width + 20);
				else if (ast[n].getX() > getSize().width + 20)
					ast[n].setX(-20);
				
				//update the asteroidâ€™s Y value
				ast[n].incY(ast[n].getVelY());
				//warp the asteroid at screen edges
				if (ast[n].getY() < -20)
					ast[n].setY(getSize().height + 20);
				else if (ast[n].getY() > getSize().height + 20)
					ast[n].setY(-20);
				
				//update the asteroidâ€™s rotation
				ast[n].incMoveAngle(ast[n].getRotationVelocity());
				//keep the angle within 0-359 degrees
				if (ast[n].getMoveAngle() < 0)
					ast[n].setMoveAngle(360 - ast[n].getRotationVelocity());
				else if (ast[n].getMoveAngle() > 360)
					ast[n].setMoveAngle(ast[n].getRotationVelocity());
			}
		}
	}
	
	// check collision with ship or bullet
	public void checkCollisions() {
		//iterate through the asteroids array
		for (int m = 0; m<ASTEROIDS; m++) {
			//is this asteroid being used?
			if (ast[m].isAlive()) {
				/*
				* check for collision with bullet
				*/
				for (int n = 0; n < BULLETS; n++) {
					//is this bullet being used?
					if (bullets[n].isAlive()) {
						//perform the collision test
						if (ast[m].getBounds().contains(bullets[n].getX(), bullets[n].getY())){
							bullets[n].setAlive(false);
							ast[m].setAlive(false);
							continue;
						}
					}
				}
				/*
				* check for collision with ship
				*/
				if (ast[m].getBounds().intersects(playerShip.getBounds())) {
					ast[m].setAlive(false);
					playerShip.setX(320);
					playerShip.setY(240);
					playerShip.setFaceAngle(0);
					playerShip.setVelX(0);
					playerShip.setVelY(0);
					continue;
				}
			}
		}
	}
 
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
 
	public void keyReleased(KeyEvent e) {
		
	    int keyCode = e.getKeyCode();

	    switch (keyCode) {
	        case KeyEvent.VK_LEFT:
	        case KeyEvent.VK_RIGHT:
	            // Stop rotating the ship when left or right arrow key is released
	            playerShip.setMoveAngle(playerShip.getFaceAngle() - 90);
	            playerShip.setVelX(calcAngleVelX(playerShip.getMoveAngle()) * 0.1);
	            playerShip.setVelY(calcAngleVelY(playerShip.getMoveAngle()) * 0.1);
	            break;

	        case KeyEvent.VK_UP:
	        case KeyEvent.VK_DOWN:
	            // Stop applying thrust to the ship when up or down arrow key is released
	            playerShip.setVelX(calcAngleVelX(playerShip.getMoveAngle()) * 0.1);
	            playerShip.setVelY(calcAngleVelY(playerShip.getMoveAngle()) * 0.1);
	            break;

	        // Add more cases as needed for other keys

	        default:
	            break;
	    }
	    
	}


	
 
	public void keyPressed(KeyEvent k) {
		int keyCode = k.getKeyCode();
		switch (keyCode) {
		    case KeyEvent.VK_Q:
		    	stop();
		    	
			case KeyEvent.VK_LEFT:
				/*Good
				 playerShip.incFaceAngle(-5);
				if (playerShip.getFaceAngle() < 0) playerShip.setFaceAngle(360-5);
				 */
				//left arrow rotates ship left 5 degrees
				playerShip.incFaceAngle(-5);
				playerShip.setMoveAngle(playerShip.getFaceAngle() + 0);
				playerShip.incVelX(calcAngleVelX(playerShip.getMoveAngle()) * 0.05);
				//playerShip.incVelY(calcAngleVelY(playerShip.getMoveAngle()) * 0.05);
				break;
			case KeyEvent.VK_RIGHT:
				/*Good
				 playerShip.incFaceAngle(5);
				if (playerShip.getFaceAngle() > 360) playerShip.setFaceAngle(5);
				break;
				 */
				//right arrow rotates ship right 5 degrees
				playerShip.incFaceAngle(5);
				playerShip.setMoveAngle(playerShip.getFaceAngle() + 180);
				playerShip.incVelX(calcAngleVelX(playerShip.getMoveAngle()) * 0.05);
				break;
			case KeyEvent.VK_DOWN:
				
				playerShip.setMoveAngle(playerShip.getFaceAngle() + 90);
				playerShip.incVelX(calcAngleVelX(playerShip.getMoveAngle()) * 0.05);
				playerShip.incVelY(calcAngleVelY(playerShip.getMoveAngle()) * 0.05);
				break;
			case KeyEvent.VK_UP:
				//up arrow adds thrust to ship (1/10 normal speed)
				playerShip.setMoveAngle(playerShip.getFaceAngle() - 90);
				playerShip.incVelX(calcAngleVelX(playerShip.getMoveAngle()) * 0.05);
				playerShip.incVelY(calcAngleVelY(playerShip.getMoveAngle()) * 0.05);
			break;
			//Ctrl, Enter, or Space can be used to fire weapon
			case KeyEvent.VK_CONTROL:
			case KeyEvent.VK_ENTER:
			case KeyEvent.VK_SPACE:
				//fire a bullet
				currentBullet++;
				if (currentBullet > BULLETS - 1) currentBullet = 0;
					bullets[currentBullet].setAlive(true);
					//point bullet in same direction ship is facing
					bullets[currentBullet].setX(playerShip.getX());
					bullets[currentBullet].setY(playerShip.getY());
					bullets[currentBullet].setMoveAngle(playerShip.getFaceAngle() - 90);
					//fire bullet at angle of the ship
					double angle = bullets[currentBullet].getMoveAngle();
					double svx = playerShip.getVelX();
					double svy = playerShip.getVelY();
					bullets[currentBullet].setVelX(svx + calcAngleVelX(angle) * 2);
					bullets[currentBullet].setVelY(svy + calcAngleVelY(angle) * 2);
					break;
				}
		}
	
	public double calcAngleVelX(double angle) {
		return (double) (Math.cos(angle * Math.PI / 180));
	}
	
	public double calcAngleVelY(double angle) {
		return (double) (Math.sin(angle * Math.PI / 180));
		}
}