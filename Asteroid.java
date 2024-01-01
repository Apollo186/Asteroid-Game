package Astr_pack;
import java.awt.Polygon;
import java.awt.Rectangle;
public class Asteroid extends VectorShape {
  //attributes
  private int [] astX = {-20, -13,0,20,22,20,12,2,-10,-22,-16};
  private int [] astY = {20,23,17,20,16,-20,-22,-14,-17,-20,-5};
  protected double rotVel;
  
  public double getRotationVelocity() {
	  return rotVel;
  }
  public void setRotationVelocity(double rotVel ) {
	  this.rotVel = rotVel;
  }
  // Constructor 
  public Asteroid(){
	// creating a polygon shape for the asteroid constructor.
    Polygon polygon = new Polygon(astX,astY,astX.length);
    setShape(polygon);
    setAlive(true);
    setRotationVelocity(0.0);  
  }
  public Rectangle getBounds() {
	  //returns a rectangle with the following bounds of...
	  return new Rectangle((int)getX() - 20, (int)getY() - 20, 40, 40);
  }
}