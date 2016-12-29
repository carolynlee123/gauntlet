package spaceInvaders;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameClient.Actor;
import gameClient.Constants;
import gameClient.Point2D;
import gameClient.Vector2D;

public class Ship extends Actor {
	private boolean isAlive;
	
	public Ship(Point2D p) {
		super(p);
		widthScale = Constants.shipWidthScale;
		heightScale = Constants.shipHeightScale;
		initImage();
	}
	
	private void initImage() {
		File file = null;
		file = new File(Constants.shipImage);
		
		try {
			BufferedImage bi = ImageIO.read(file);
			myImage = bi;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@Override
	public void setVector(Vector2D vector) {
		this.velocity = vector;
		
		double newTheta = vector.getAngle();
		
		// Restrict direction to only point left or right
		if (newTheta != 0 && newTheta != Math.PI) {
			double distPI = Math.abs(newTheta - Math.PI);
			double dist0 = Math.abs(newTheta);
			
			if (dist0 <= distPI) {
				this.velocity = new Vector2D(vector.getMagnitude(), 0);
			}
			else {
				this.velocity = new Vector2D(vector.getMagnitude(), Math.PI);
			}
		}
	}

	@Override
	public void draw(Graphics g, int windowWidth, int windowHeight) {
		if (myImage != null) {
			int x = (int) position.getX();
			int y = (int) position.getY();
			
			g.drawImage(myImage, x, y, getWidth(windowWidth), getHeight(windowHeight), null);
		}
	}
	
	public void kill() {
		isAlive = false;
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	@Override
	public int getWidth(int windowWidth) {
		return (int) (windowWidth * widthScale);
	}

	@Override
	public int getHeight(int windowHeight) {
		return (int) (windowHeight * heightScale);
	}

	@Override
	public void update(int windowWidth, int windowHeight) {
		super.update(windowWidth, windowHeight);	// rescale, if necessary
		move();
		if (outOfBounds(windowWidth, windowHeight)) {	// if out of bounds, move back
			double x = getLocation().x;
			
			if (x < 0) {
				x = 0;
			}
			else {
				x = windowWidth - 1 - getWidth(windowWidth);
			}
			
			moveTo(new Point2D(x, getLocation().y));
			setVector(Constants.zeroVector);
		}
	}

}
