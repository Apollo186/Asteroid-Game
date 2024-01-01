package Astr_pack;
import java.awt.Rectangle;
import java.awt.Polygon;
public class Ship extends VectorShape{
	//attributes
	private int [] shipX = {-6, -3, 0, 3, 6, 0};
	private int [] shipY = {6, 7, 7, 7, 6, -7};
	
	// Constructor 
	public Ship() {
		// creating a rectangular shape for the ship constructor.
		//Rectangle rectangle = new Rectangle(0,0,1,1);
		//setShape(rectangle);
		Polygon polygon = new Polygon(shipX, shipY, shipX.length);
		setShape(polygon);
		setAlive(false);
	}
	public Rectangle getBounds() {
		//returns a rectangle with the following bounds of...
		return new Rectangle((int)getX() -6, (int) getY() - 6, 12, 12);
	}
}
