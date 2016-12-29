package spaceInvaders;

import java.awt.Graphics;
import java.awt.Point;

import gameClient.Actor;
import gameClient.Constants;
import gameClient.Point2D;

public abstract class Blast extends Actor {
	private boolean active;
	
	public Blast(Point2D p) {
		super(p);
		active = true;
		initImage(Constants.blastImage);
	}
	
	public Blast() {
		active = true;
		initImage(Constants.blastImage);
	}
	
	public void destroy() {
		active = false;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public int getWidth(int windowWidth) {
		return (int) (windowWidth * Constants.blastWidthScale);
	}
	
	public void update(int windowWidth, int windowHeight) {
		super.update(windowWidth, windowHeight);
		move();
		boolean outOfBounds = outOfBounds(windowWidth, windowHeight);
		if (outOfBounds) {
			destroy();
		}
	}
	
	public int getHeight(int windowHeight) {
		return (int) (windowHeight * Constants.blastHeightScale);
	}
	
	public void draw(Graphics g, int windowWidth, int windowHeight) {
		if (isActive()) {
			Point point = this.position.toPoint();
			g.drawImage(myImage, point.x, point.y, getWidth(windowWidth), getHeight(windowHeight), null);
		}
	}
}
