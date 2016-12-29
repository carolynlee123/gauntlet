package gameClient;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Actor {
	protected Point2D position;
	protected Vector2D velocity;
	protected Image myImage;
	protected int prevWindowWidth = -1;
	protected int prevWindowHeight = -1;
	protected double widthScale;
	protected double heightScale;
	
	public Actor(Point2D p) {
		this.position = p;
		velocity = Constants.zeroVector;
	}
	
	public Actor() {
		this.position = new Point2D(0,0);
		velocity = Constants.zeroVector;
	}
	
	protected void initImage(String imagePath) {
		File file = null;
		file = new File(imagePath);
		
		try {
			BufferedImage bi = ImageIO.read(file);
			myImage = bi;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void setLocation(Point2D p) {
		this.position = p;
	}
	
	public Point2D getLocation() {
		return position;
	}
	
	public void setVector(Vector2D newVector) {
		this.velocity = newVector;
	}
	
	public Vector2D getVector() {
		return this.velocity;
	}
	
	public void move() {
		position = velocity.add(position);
	}
	
	public void moveTo(Point2D p) {
		position = p;
	}
	
	public abstract int getWidth(int windowWidth);
	
	public abstract int getHeight(int windowHeight);
	
	/**
	 * Checks if two actors have collided. Does not perform any actions upon either actor involved.
	 * @param otherActor
	 * @param windowWidth
	 * @param windowHeight
	 * @return True if actors collided, false otherwise.
	 */
	public boolean collided(Actor otherActor, int windowWidth, int windowHeight) {
		Rectangle otherRect = otherActor.asRectangle(windowWidth, windowHeight);
		Rectangle myRect = this.asRectangle(windowWidth, windowHeight);
		return otherRect.intersects(myRect) || myRect.intersects(otherRect) || 
				otherRect.contains(myRect) || myRect.contains(otherRect);
	}
	
	/**
	 * Returns true iff this actor has crossed the bounds of the window in any direction
	 * @param windowWidth
	 * @param windowHeight
	 * @return
	 */
	public boolean outOfBounds(int windowWidth, int windowHeight) {
		Rectangle window = new Rectangle(0 ,0 , windowWidth, windowHeight);
		Rectangle myRectangle = this.asRectangle(windowWidth, windowHeight);
		
		boolean outOfBounds = !window.contains(myRectangle);
		return outOfBounds;
	}

	/**
	 * Returns a rectangle representation of the actor
	 * @param windowWidth Width of the JFrame the actor will be drawn in
	 * @param windowHeight Height of the JFrame the actor will be drawn in
	 * @return
	 */
	public Rectangle asRectangle(int windowWidth, int windowHeight) {
		Point roundedPosition = this.position.toPoint();
		return new Rectangle(roundedPosition.x, roundedPosition.y, getWidth(windowWidth), getHeight(windowHeight));
	}
	
	public abstract void draw(Graphics g, int width, int height);

	public void update(int windowWidth, int windowHeight) {
		rescale(windowWidth, windowHeight);
	}
	
	public void rescale(int windowWidth, int windowHeight) {
		// if prevWindowWidth & Height haven't been initialized, don't need to re-scale
		if (prevWindowWidth <= 0 || prevWindowHeight <= 0) {
			prevWindowWidth = windowWidth;
			prevWindowHeight = windowHeight;
		}
		else if (prevWindowWidth != windowWidth || prevWindowHeight != windowHeight) {
			double deltaWidth = ((double)windowWidth)/prevWindowWidth;
			double deltaHeight = ((double)windowHeight)/prevWindowHeight;
			
			// update position
			double x = position.getX();
			double y = position.getY();
			x *= deltaWidth;
			y *= deltaHeight;
			position = new Point2D(x,y);
			
			// update velocity
			double magX = velocity.getX();
			double magY = velocity.getY();
			magX = magX * deltaWidth;
			magY = magY * deltaHeight;
			velocity = new Vector2D(new Point2D(magX, magY));
			
			// update previous dimensions
			prevWindowWidth = windowWidth;
			prevWindowHeight = windowHeight;
		}
	}
}
