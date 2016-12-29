
package pong;

import java.io.Serializable;

import javax.swing.JLabel;

public class PongPaddle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1487108406183151915L;
	/**
	 * 
	 */
	private int locationX;
	private int locationY;
	private int oldX;
	private int oldY;
	private final int height;
	private final int width;
	private int yLimit;
	private int xLimit;
	private int velocty =5;
	private transient  JLabel label; 
	private String name; 


	public PongPaddle(int x, int y, int xlimit, int ylimit, JLabel l) {
		// TODO Auto-generated constructor stub
		if(x == PongGameManager.leftPadX) name = "Left"	;
		else name = "Right";
		locationX = x;
		locationY =y; 
		xLimit = xlimit;
		yLimit = ylimit; 
		height = PongGameManager.paddleLength;
		width = PongGameManager.paddleWidth; 
		this.label = l; 
	}

	public void updateLocationX(boolean up)
	{
	}
	public void updateLocationY(boolean up)
	{
		if(up && (yLimit>(locationY+5+height))) locationY +=5;
		else if(!up && (0<(locationY-5))) locationY -=5;
		System.out.println("location y is "+locationY);
	}
	public String toString()
	{
		return name+" Paddle at x: " +locationX +" y: "+locationY; 
	}

	public void updateVelocty()
	{
		
	}
	public int getLocationX()
	{
		return locationX;
	}
	public int getLocationY()
	{
		return locationY;
	}
	public void setLocationY(int yloc)
	{

		oldY =locationY; 
		locationY = yloc; 
		
	}
	public void setLabelLocationY(int yloc)
	{
		oldY =locationY; 
		locationY = yloc; 
		label.setLocation(locationX, yloc);
		label.repaint();
	}
	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}
	public int getVelocity()
	{
		return velocty;
	}

}


