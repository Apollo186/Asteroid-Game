/*
Bryant Munyao

09/21/2023
COMP 167 Section 001
This is the first part of a beginner project, in which we will be creating the game Asteroids.
In this part, we will be creating a class named "VectorShape" based off the given uml.
*/
package Astr_pack;
import java.awt.Shape;

public class VectorShape {
  //attributes 
	private Shape shape;
	private boolean alive;
	private double x;
	private double y;
	private double velX;
	private double velY;
	private double moveAngle;
	private double faceAngle;
	

  // Accessor Methods
	public Shape getShape() {
		return shape;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getVelX() {
		return velX;
	}
	public double getVelY() {
		return velY;
	}
	
	public double getMoveAngle() {
		return moveAngle;
	}
	public double getFaceAngle() {
		return faceAngle;
	}
	
	public boolean isAlive() {
		return alive;
	
	}
  // Mutators 
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public void setX(double x) {
	 this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public void setVelY(double velY) {
		this.velY = velY;
	}
	public void setMoveAngle(double moveAngle) {
		this.moveAngle = moveAngle;
	}
	public void setFaceAngle(double faceAngle) {
		this.faceAngle = faceAngle;
	}

  // Mutations  
	public void incX(double i) {
		this.x += i;
	}
	public void incY(double i) {
		this.y += i;
	}
	public void incVelX(double i){
		this.velX += i;
	}
	public void incVelY(double i){
		this.velY += i;
	}
	public void incMoveAngle(double i) {
		this.moveAngle += i; 
	}
	public void incFaceAngle(double i) {
		this.faceAngle += i; 
	}
  // Constructor
	public VectorShape() {
		setShape(null);
		setAlive(false);
		setX(0.0);
		setY(0.0);
		setVelX(0.0);
		setVelY(0.0);
		setFaceAngle(0.0);
		setMoveAngle(0.0);
		
	}
	
	
	
}
