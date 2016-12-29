package spaceInvaders;

import java.awt.Graphics;

import gameClient.Actor;
import gameClient.Constants;
import gameClient.GUITools;
import gameClient.Point2D;

public class Alien extends Actor {
	private boolean isAlive;
	public boolean first_touch = true;
	public Alien(Point2D p) {
		super(p);
		initImage(Constants.alienImage);
		isAlive = true;
	}
	
	public void draw(Graphics g, int windowWidth, int windowHeight) {
		if (myImage != null && this.isAlive()) {
			int x = GUITools.roundToNearest(position.x);
			int y = GUITools.roundToNearest(position.y);
			int width = (int) (windowWidth * Constants.alienWidthScale);
			int height = (int) (windowHeight * Constants.alienHeightScale);
			g.drawImage(myImage, x, y, width, height, null);
		}
	}
	public void kill(){
		isAlive = false;
		//then probably erase the image
	}
	public boolean isAlive(){
		//check if this alien is alive
		return isAlive;
	}

	@Override
	public int getWidth(int windowWidth) {
		return (int) (windowWidth * Constants.alienWidthScale);
	}

	@Override
	public int getHeight(int windowHeight) {
		return (int) (windowHeight * Constants.alienHeightScale);
	}
	
	public void reverse(){
		this.setVector(this.velocity.rotate(Math.PI));
	}
}
