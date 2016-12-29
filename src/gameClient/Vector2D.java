package gameClient;


/**
 * Used for calculating speed and direction.
 * Stores x and y coordinates, as well as magnitude and angle.
 * @author James
 *
 */
public class Vector2D
{
	private Point2D p;
	private double magnitude;
	private double theta;
	
	/**
	 * Point constructor
	 * Other data members calculated automatically
	 * @param p Point representing the vector's <x,y> form. Assumes starting point is the origin (0,0).
	 */
	public Vector2D(Point2D p) {
		this.p = p;
		double x = p.getX();
		double y = p.getY();
		this.magnitude = Math.sqrt(x*x + y*y);
		this.theta = Math.atan(y / x);
	}
	
	/**
	 * Magnitude, angle constructor
	 * Other data members calculated automatically
	 * @param magnitude The speed
	 * @param angle The direction, in radians
	 */
	public Vector2D(double magnitude, double angle) {
		this.magnitude = magnitude;
		this.theta = angle;
		
		double x = (Math.cos(angle) * magnitude); 
		double y = (Math.sin(angle) * magnitude);
		this.p = new Point2D(x, y);
	}
	
	public double getMagnitude() {
		return magnitude;
	}
	
	public Vector2D setMagnitude(double magnitude) {
		return new Vector2D(magnitude, this.theta);
	}
	
	public double getAngle() {
		return theta;
	}
	
	public double getX() {
		return p.getX();
	}
	
	public double getY() {
		return p.getY();
	}
	
	/**
	 * Add two Vector2Ds together without changing either operand
	 * @param rhs The other Vector2D to add to
	 * @return The resulting Vector2D
	 */
	public Vector2D add(Vector2D rhs) {
		double newX = this.p.getX() + rhs.p.getX();
		double newY = this.p.getY() + rhs.p.getY();
		Point2D newPoint = new Point2D(newX, newY);
		return new Vector2D(newPoint);
	}
	
	/**
	 * Subtract two vectors without changing either operand
	 * @param rhs The Vector2D to subtract from the current Vector2D
	 * @return The resulting Vector2D
	 */
	public Vector2D subtract(Vector2D rhs) {
		double newX = this.p.getX() - rhs.p.getX();
		double newY = this.p.getY() - rhs.p.getY();
		Point2D newPoint = new Point2D(newX, newY);
		return new Vector2D(newPoint);
	}
	
	/**
	 * Multiply a Vector2D by a scalar value.
	 * Does not alter the original Vector2D.
	 * @param scalar The value to multiply by
	 * @return The resulting Vector2D
	 */
	public Vector2D scale(double scalar) {
		double newMagnitude = this.magnitude * scalar;
		return new Vector2D(newMagnitude, theta);
	}
	
	/*
	 * Rotates clockwise
	 */
	public Vector2D rotate(double angle) {
		return new Vector2D(magnitude, theta + angle);
	}
	
	public boolean equals(Vector2D rhs) {
		return this.magnitude == rhs.magnitude && this.theta == rhs.theta;
	}
	
	/**
	 * Returns the result of the current vector added to a point.
	 * @param rhs The point to add.
	 * @return The resulting Vector2D
	 */
	public Point2D add(Point2D rhs) {
		double newX = p.getX() + rhs.getX();
		double newY = p.getY() + rhs.getY();
		Point2D newPoint = new Point2D(newX, newY);
		return newPoint;
	}
	
	/**
	 * Get the current Vector2D's Point representation.
	 * @return The Point equivalent to the current Vector2D.
	 */
	public Point2D toPoint2D() {
		return p;
	}
}
