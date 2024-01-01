package Astr_pack;
import java.awt.Rectangle;



public class Bullet extends VectorShape {
	// Constructor 
	public Bullet(){
		// creating a rectangular shape for the bullet constructor.
		Rectangle rectangle = new Rectangle(0,0,1,1);
		setShape(rectangle);
		setAlive(false);
	}
	public Rectangle getBounds() {
		//returns a rectangle with the following bounds of...
		return new Rectangle((int)getX(), (int)getY(), 1, 1);
	}
}
