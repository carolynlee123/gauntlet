package spaceInvaders;

import gameClient.Constants;
import gameClient.GUITools;
import gameClient.Point2D;
import gameClient.Vector2D;

public class ShipBlast extends Blast {

	public ShipBlast(Ship ship, int windowWidth, int windowHeight) {
		Point2D shipPoint = ship.getLocation();
		int blastHeight = getHeight(windowHeight);
		
		Point2D p = GUITools.center(
				ship.asRectangle(windowWidth, windowHeight), 
				this.asRectangle(windowWidth, windowHeight));
		p.y = shipPoint.y - blastHeight;
		
		position = p;
		
		setVector(Constants.j.setMagnitude(-10));
	}
	
	@Override
	public void setVector(Vector2D vector) {
		Point2D toPoint = vector.toPoint2D();
		
		toPoint.x = 0;
		toPoint.y = Math.abs(toPoint.y) * -1;
		
		this.velocity = new Vector2D(toPoint);
	}
	
}
