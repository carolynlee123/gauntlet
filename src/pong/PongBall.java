package pong;

import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

import org.omg.CORBA.TRANSIENT;

import gameClient.Vector2D;

public class PongBall implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private float velocityX = 20;
	private float velocityY = -10; 
	private int xLoc;
	private int yLoc;

	private int xStart;
	private int yStart; 
	private final int height =50;
	private final int width =50 ;
	public static final int Dim = 50; 
	private int velocty;
	private transient PongPlayField ppf; 
	private transient  PongGameManager pgm; 
	private transient Random rand; 
	private static final float slowdown = 0.35f; 
	public PongBall(PongPlayField ppf, PongGameManager pgm )
	{

		xStart = PongGameManager.boardLength/2;
		yStart = PongGameManager.boardLength/2; 
		xLoc = xStart;
		yLoc = yStart; 
		this.ppf = ppf; 
		this.pgm = pgm; 
		rand = new Random(); 

	}
	public PongBall()
	{
		xStart = PongGameManager.boardLength/2;
		yStart = PongGameManager.boardLength/2; 
		xLoc = xStart;
		yLoc = yStart; 
		rand = new Random(); 
	}
	public void setPlayField(PongPlayField field)
	{
		ppf = field;
	}
	public void setGameManager(PongGameManager game)
	{
		pgm = game; 
	}

	public int getLocationX()
	{
		return xLoc;
	}
	public int getLocationY()
	{
		return yLoc;
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
	public boolean checkRightPaddleCollide()
	{
		if(((xLoc+width>pgm.getRightPaddleX() && xLoc+width<pgm.getRightPaddleX()+PongGameManager.paddleWidth) ||((xLoc<pgm.getRightPaddleX()+PongGameManager.paddleWidth)&& xLoc>pgm.getRightPaddleX()) ))
		{
			if((yLoc<pgm.getRightPaddleY()+PongGameManager.paddleLength && yLoc>pgm.getRightPaddleY()) || 
					(yLoc+Dim>pgm.getRightPaddleY() && 
							yLoc+Dim<pgm.getRightPaddleY()+PongGameManager.paddleLength))
			{
				System.out.println("Collision at right paddle at x: "+pgm.getRightPaddleX()+" y: "+pgm.getRightPaddleY());
				return true;
			}
		}
		return false;
	
	}
	public boolean checkLeftPaddleCollide() {
		// TODO Auto-generated method stu
		if(xLoc>pgm.getLeftPaddleX()&& xLoc<pgm.getLeftPaddleX()+PongGameManager.paddleWidth)
		{
			if((yLoc<pgm.getLeftPaddleY()+PongGameManager.paddleLength && yLoc>pgm.getLeftPaddleY()) || 
					(yLoc+Dim>pgm.getLeftPaddleY() && 
							yLoc+Dim<pgm.getLeftPaddleY()+PongGameManager.paddleLength)
					)
			{
				return true;
			}
		}
		return false;
	}
	public void randomBallRelease()
	{
		xLoc = xStart;
		yLoc = yStart; 
		
		int vx = rand.nextInt(40) - 20;
		if(Math.abs(vx)<10)
		{
			if(vx<0) vx-=10;
			else vx+= 10; 
		}
		velocityX = vx;  
		velocityY = rand.nextInt(40) - 20;  

	}
	public void randomiseVelocity()
	{

		int roundupY = 4;
		if(roundupY<0) roundupY=1; 
		int roundupX = 4;
		if(roundupX<0) roundupX=1; 
		System.out.println("round ups are x: " + roundupX+ " y: " +roundupY);
		velocityY += rand.nextInt(2*roundupY)-roundupY;
		velocityX += rand.nextInt(2*roundupX)-roundupX;

	}
	public float getVelocityX()
	{
		return velocityX;
	}
	public float getVelocityY()
	{
		return velocityY;
	}
	public void racketBounce(boolean right)
	{
		int yball = yLoc+Dim/2;
		int ypad; 
		if(right)ypad = pgm.getRightPaddleY()+PongGameManager.paddleLength/2;
		else ypad = pgm.getLeftPaddleY()+PongGameManager.paddleLength/2;
		if(yball>ypad)
		{
			velocityY = Math.abs(velocityY)+0.5f*(yball-ypad);
			if(velocityX<0) velocityX = -(velocityX-slowdown*(yball-ypad));
			else velocityX = -(velocityX+slowdown*(yball-ypad));
			t =0;
			ty =0;
		}
		else
		{
			velocityY = -Math.abs(velocityY)-0.5f*(ypad-yball);
			if(velocityX<0) velocityX = -(velocityX-slowdown*(ypad-yball));
			else velocityX = -(velocityX+slowdown*(ypad-yball));
			t=0; 
			ty =0; 
		}

	}
	private int t =0;
	private int ty =0;
	public void moveBallCycle() {
		// TODO Auto-generated method stub
		xLoc += velocityX;
		yLoc += velocityY;
		if(velocityX>20)
		{
			
			velocityX-=.1*Math.pow(t, 1.1); 
			t++;
		}
		else if(velocityX<-20)
		{
			velocityX+=.1*Math.pow(t, 1.1); 
			t++;

		}
		else t =0; 
		if(velocityY>20)
		{
			
			velocityY-=.5*Math.pow(ty, 1.1); 
			ty++;
		}
		else if(velocityY<-20)
		{
			velocityY+=.5*Math.pow(ty, 1.1); 
			ty++;

		}
		else ty =0; 
		if(yLoc<0)
		{
			yLoc = 0;
			velocityY = - velocityY; 
			//randomiseVelocity(); 

		}
		if(yLoc+height>PongGameManager.boardHeight)
		{
			velocityY = - velocityY;
			yLoc = PongGameManager.boardHeight-height;
			//randomiseVelocity(); 

		}

		if(checkRightPaddleCollide())
		{
			//velocityX = -velocityX; 
			racketBounce(true);
			xLoc =PongGameManager.rightPadX -PongGameManager.paddleWidth; 
			//randomiseVelocity(); 
		}
			
		if(checkLeftPaddleCollide() )
		{
			//velocityX = -velocityX; 
			racketBounce(false); 
			xLoc =PongGameManager.leftPadX+PongGameManager.paddleWidth;
			//randomiseVelocity(); 

				
		}
		if(xLoc<0)
		{
			xLoc = xStart;
			yLoc = yStart; 
			pgm.incrementPlayer1Score(false);
			if(pgm.isOnline) pgm.requestBall();
			else randomBallRelease();
		}
		if(xLoc>PongGameManager.boardLength)
		{
			xLoc = xStart;
			yLoc = yStart; 
			pgm.incrementPlayer2Score(false);
			if(pgm.isOnline) pgm.requestBall();
			else randomBallRelease();
		}
		if(pgm.isGameOver())
		{
			pgm.reset();
		}

		
		
		ppf.repaint();
		
	}



}
