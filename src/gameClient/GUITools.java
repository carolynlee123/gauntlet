package gameClient;

import java.awt.Rectangle;

/**
 * Contains various miscellaneous functions to assist in drawing
 * @author James
 *
 */
public class GUITools {
	public static Point2D center(Rectangle outer, Rectangle inner) {
		double prelimX = ((double)outer.width)/2 - ((double)inner.width)/2;
		double prelimY = (outer.height/2) - (inner.height/2);
		
		double outerX = outer.x;
		double outerY = outer.y;
		
		double newX = outerX + prelimX;
		double newY = outerY + prelimY;
		return new Point2D(newX, newY);
	}
	
	public static boolean overlap(Rectangle rec1, Rectangle rec2) {
		return rec1.intersects(rec2) || rec1.contains(rec2) || rec2.contains(rec1);
	}
	
	public static int roundToNearest(double num) {
		return (int)(num + 0.5);
	}
}
