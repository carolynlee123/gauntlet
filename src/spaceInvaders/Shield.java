package spaceInvaders;

import java.awt.Color;
import java.awt.Graphics;

import gameClient.Actor;
import gameClient.Constants;
import gameClient.Point2D;
import gameClient.Vector2D;

public class Shield extends Actor {
	private int durability;

	public Shield(Point2D p) {
		super(p);
		durability = 10;
	}
	
	public void takeHit() {
		durability--;
	}
	
	@Override
	public void setVector(Vector2D newVector) {	// disable setVector, shields must always be stationary
		this.velocity = Constants.zeroVector;
	}
	
	public boolean isActive() {
		return durability > 0;
	}

	@Override
	public void draw(Graphics g, int width, int height) {
		Color theColor = null;
		if (isActive()) {
			int red = (int)(255 - 25.5 * durability);
			int blue = (int)(25.5 * durability);
			theColor = new Color(red, 0, blue);
		}
		else {
			theColor = Color.BLACK;
		}
		
		g.setColor(theColor);
		int x = (int) position.getX();
		int y = (int) position.getY();
		int shieldWidth = (int) (width * Constants.shieldWidthScale);
		int shieldHeight = (int) (height * Constants.shieldWidthScale);
		g.fillRect(x, y, shieldWidth, shieldHeight);
	}

	@Override
	public int getWidth(int windowWidth) {
		return (int) (windowWidth * Constants.shieldWidthScale);
	}

	@Override
	public int getHeight(int windowHeight) {
		return (int) (windowHeight * Constants.shieldHeightScale);
	}
	
	public void resetDurability(){
		durability = 10;
	}

}
