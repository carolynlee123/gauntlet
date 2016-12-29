package gameClient;

import java.awt.Point;

public class Point2D {
	public double x;
	public double y;
	
	public Point2D(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public Point toPoint() {
		return new Point(GUITools.roundToNearest(x), GUITools.roundToNearest(y));
	}
}
