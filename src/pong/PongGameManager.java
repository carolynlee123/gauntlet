
package pong;

import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;

import gameClient.Game;
import networking.Client;
import networking.Server;

public class PongGameManager extends Game implements  Runnable{
	
	private PongPaddle PaddleRight;
	private PongPaddle  PaddleLeft;
	
	private PongPaddle localPaddle; 
	private PongPaddle netPaddle; 
	
	private PongBall Ball;
	private PongPlayField ppf; 
	public Boolean [][] collisionMap;
	private int player1Score;
	private int player2Score;

	public final static int paddleLength = 175;
	public final static int paddleWidth = 50; 
	
	public final static int maxScore = 11; 
	
	public static final int boardHeight = 800;
	public static final int boardLength = 1000;
	
	private final int updateRate; 
	public final JLabel j1;
	public final JLabel j2; 
	
	public int sockID; 
	public boolean isOnline = false;

	private boolean isServer = false;
	
	private boolean localRight; 
	
	private Client client; 
	
	public static enum CtrlStates
	{
		Local, Right, Left ;
	}
	private CtrlStates cs = CtrlStates.Local; 
	public final static int rightPadX  =boardLength-100;
	public final static int leftPadX = 50; 

	private Server server; 
	public PongGUI pg; 
	private PongServer pServer;
	private PongNetClient pClient; 
	private boolean singleMode; 
	private ActionListener endingAction; 
	private AbstractButton aButton = null;
	
	public double aiTarget; 
	public PongGameManager()
	{
		 PaddleLeft = new PongPaddle(leftPadX,0, boardLength, boardHeight,null); 
		 PaddleRight = new PongPaddle(rightPadX, 0, boardLength, boardHeight,null);
		 ppf = new PongPlayField(this); 
		 aButton = new JButton(); 
		 player2Score =0;
		 player1Score =1; 
		 this.j1 = null;
		 this.j2 = null; 
		 updateRate = 20; 
		 this.singleMode = false; 
		 gameOver = false; 
	}
	
	public PongGameManager(JLabel scoreRight, JLabel scoreLeft, JLabel leftPaddle, JLabel rightPaddle, boolean singleMode, boolean usingRight, ActionListener actlisten)
	{
		 PaddleLeft = new PongPaddle(leftPadX,0, boardLength, boardHeight,leftPaddle); 
		 PaddleRight = new PongPaddle(rightPadX, 0, boardLength, boardHeight,rightPaddle);
		 endingAction = actlisten; 
		 ppf = new PongPlayField(this); 
		 aButton = new JButton(); 
		 aButton.addActionListener(actlisten);
		 player2Score =0;
		 player1Score =1; 
		 if(usingRight)
		 {
			 netPaddle = PaddleRight; 
		 }
		 else
		 {
			 netPaddle = PaddleLeft; 
		 }
		 this.j1 = scoreLeft;
		 this.j2 = scoreRight; 
		 updateRate = 20; 
		 this.singleMode = singleMode; 
		 localRight = usingRight; 
		 gameOver = false; 
	}
	public PongGameManager(JLabel scoreRight, JLabel scoreLeft, JLabel leftPaddle, JLabel rightPaddle, boolean singleMode, boolean usingRight)
	{
		 PaddleLeft = new PongPaddle(leftPadX,0, boardLength, boardHeight,leftPaddle); 
		 PaddleRight = new PongPaddle(rightPadX, 0, boardLength, boardHeight,rightPaddle);
		 
		 ppf = new PongPlayField(this); 
		 
		 player2Score =0;
		 player1Score =1; 
		 
		 this.j1 = scoreLeft;
		 this.j2 = scoreRight; 
		 updateRate = 20; 
		 this.singleMode = singleMode; 
		 localRight = usingRight; 
	}
	
	public void setServer( Server s)
	{
		 isServer = true;
		server =s; 
	}
	public void setClient(Client cl)
	{
		isOnline = true; 

		client = cl; 
	}
	public void setGUI(PongGUI pg)
	{
		this.pg = pg; 
	}
	public boolean isPlayerRight()
	{
		return localRight; 
	}
	public void setServer(int sockID)
	{
		pServer = new PongServer(sockID);
		pServer.start();
	}
	public void setClient(int sockID, String ip, boolean usingRightPaddle)
	{
		localRight = usingRightPaddle; 
		pClient = new PongNetClient(sockID,ip, this);
		pClient.start(); 
		isOnline = true; 
		if(usingRightPaddle)
		{
			localPaddle = PaddleRight; 
			netPaddle = PaddleLeft; 
		}
		else
		{
			localPaddle = PaddleLeft;
			netPaddle = PaddleRight; 
		}
		
	}
	public void setSocket(int sockID, boolean isServer)
	{
		isOnline = true;
		this.sockID = sockID; 
		this.isServer = isServer; 
	}

	
	public PongPlayField getPlayField()
	{
		return ppf; 
	}
	public void setPlayField(PongPlayField ppf)
	{
		this.ppf = ppf; 
		 Ball = new PongBall(this.ppf,this);
	}
	public boolean isOnline()
	{
		return isOnline; 
	}
	public PongBall getPongBall()
	{
		return Ball; 
	}
	
	private void initialize() {
	}
	public void startGame() {
	}
	public void endGame() {
		if(gameOver) return; 
		if(isOnline())
		{
		client.sendObject("~~EndGame");
		client.sendObject("~~Done");
	
		}
		if(aButton!= null) aButton.doClick();
		gameOver = true; 

	}
	public void endGameFromClient()
	{
		if(gameOver) return; 

		if(aButton!= null) aButton.doClick();
		gameOver = true; 
	}
	public void endGameNoClick() {
		// TODO Auto-generated method stub
		if(gameOver) return; 

		gameOver = true; 
		if(isOnline())
		{
		client.sendObject("~~EndGame");
		client.sendObject("~~Done");
	
		}
	}
	public PongPaddle getLeftPaddle()
	{
		return PaddleLeft;
	}
	
	public PongPaddle getRightPaddle()
	{
		return PaddleRight; 
	}
	
	public int getLeftPaddleX()
	{
		return PaddleLeft.getLocationX();
	}
	public int getLeftPaddleY()
	{
		return PaddleLeft.getLocationY();
	}
	public int getRightPaddleX()
	{
		return PaddleRight.getLocationX();
	}
	public int getRightPaddleY()
	{
		return PaddleRight.getLocationY();
	}
	public void setNetPaddle(PongPaddle paddle)
	{
		netPaddle.setLabelLocationY(paddle.getLocationY());
	}
	public void setNetPaddle(int y )
	{
		if(localRight) PaddleLeft.setLabelLocationY(y);
		else PaddleRight.setLabelLocationY(y);
	}
	public void setRightPaddle(int yloc)
	{
		PaddleRight.setLocationY(yloc);
	//	if(isOnline) client.sendObject(PaddleRight.getLocationY());
	}
	public void setLeftPaddle(int yloc)
	{
		PaddleLeft.setLocationY(yloc);
		//if(isOnline) client.sendObject(PaddleLeft.getLocationY());
	}
	public int getBallX()
	{
		return Ball.getLocationX();
	}
	public int getBallY()
	{
		return Ball.getLocationY(); 
	}
	public int getBallHeight()
	{
		return Ball.getHeight();
	}
	public int getBallWidth()
	{
		return Ball.getWidth();
	}
	public void setBall(PongBall ball)
	{
		System.out.println("settingBall");
		Ball = ball; 
		Ball.setGameManager(this);
		Ball.setPlayField(ppf);
	}
	public void updateHighScores() {
	}
	
	public void updateRightPaddle(boolean up) 
	{
		System.out.println("updating right paddle");
		PaddleRight.updateLocationY(up);

	}
	
	public void updateLeftPaddle(boolean up) {
		PaddleLeft.updateLocationY(up);
		
	}
	public void incrementPlayer1Score(boolean fromClient)
	{
		player1Score++; 
		if(j1!=null)j1.setText("Player 1: "+player1Score);
		if(isOnline &&(!fromClient)) client.sendObject("~~IncrementPlayer1Score");
	}
	public void incrementPlayer2Score(boolean fromClient)
	{
		player2Score++; 
		if(j2!=null)j2.setText("Player 2: "+player2Score);
		if(isOnline&&(!fromClient) )client.sendObject("~~IncrementPlayer2Score");


	}
	public boolean isGameOver()
	{
		if ((player1Score==maxScore)||(player2Score==maxScore))
		{
			if(aButton!= null) aButton.doClick();
			gameOver = true; 
			endGame(); 
			return true; 
		}
		return gameOver; 
	}
	public void reset()
	{
		player1Score=0;
		player2Score =0; 
	}
	public void updateCollisionMap() {
	}
	public void clearCollisionMap() {
	}
	public void initializeCollisionMap() {
	}
	public void releaseBall() {
	}
	public void sendGameInfo() {
	}
	public int getPlayer1Score()
	{
		return 0;
	}
	public int getPlayer2Score()
	{
		return 0;
	}

	public void requestBall()
	{ 
		client.sendObject("~~RequestBall");
	}
	public void run()
	{
		if(isOnline)
		{
			client.sendObject("~~ActivatePGM");
		}
		while(!gameOver)
		{
			//System.out.println("game running");
			isGameOver();
			Ball.moveBallCycle();
			ppf.repaint();
			if(isOnline)
			{
				
				if(localRight)
				{
					System.out.println("right paddle sent is "+PaddleRight.toString());
					client.sendObject(PaddleRight.getLocationY());
				}
				else client.sendObject(PaddleLeft.getLocationY());
			}
			try
			{
				Thread.sleep(1000/updateRate);
			}
			catch (InterruptedException ex) { }
			if(singleMode)
			{
				//manageAImoves(); 
				manageAIsmart(); 
			}
			
			if(isServer)
			{
				//pClient.sendObject(Ball);
				server.sendObjectToAll(Ball);
			}
			pg.repaint();
		}
	}
	private boolean aiMoveUP = true;
	private void manageAImoves() {
		// TODO Auto-generated method stub
		if(localRight)
		{
			if(aiMoveUP)
			{
				if(PaddleLeft.getLocationY()+PongGameManager.paddleLength>=PongGameManager.boardHeight)
				{
					aiMoveUP = false;
				}
				else PaddleLeft.setLabelLocationY(PaddleLeft.getLocationY()+2);
			}
			else
			{
				if(PaddleLeft.getLocationY()==0)
				{
					aiMoveUP = true;
				}
				else
				{
					PaddleLeft.setLabelLocationY(PaddleLeft.getLocationY()-10);
				}
			}
		}
	}
	private void manageAIsmart()
	{
		aiTarget = getFutureBall(Ball.getLocationX(),Ball.getLocationY(),Ball.getVelocityX(),Ball.getVelocityY(),0);
		if(aiTarget<0 || aiTarget>boardLength) return;
		if(aiTarget+7>PaddleLeft.getLocationY() && aiTarget-7<PaddleLeft.getLocationY()) return; 
		if(aiTarget>PaddleLeft.getLocationY())
		{
				if(PaddleLeft.getLocationY()+PongGameManager.paddleLength+10>PongGameManager.boardHeight) return; 
				PaddleLeft.setLabelLocationY(PaddleLeft.getLocationY()+10);
		}
		else 
		{
			if(PaddleLeft.getLocationY()-10<0) return; 
			PaddleLeft.setLabelLocationY(PaddleLeft.getLocationY()-10);
			
		}
	}
	public float getFutureBall(int x, int y, float Vx, float Vy, int levels)
	{
		int targetx =leftPadX; 
		if(Vx>0) targetx = rightPadX;
		
		int targety = 0; 
		if(Vy>0) targety = boardHeight; 
		//determine if ball bounces
		float tbounce = (targety-y)/Vy;
		float xbounce = x+Vx*(tbounce);
		if(tbounce<0 || xbounce<0 || xbounce>rightPadX)
		{
			return y+Vy*(targetx-x)/Vx; 
		}
		else return getFutureBall((int)xbounce,  targety,  Vx,  -Vy, levels++);
		
	}
	public long getScore()
	{
		if(localRight) return player1Score; 
		else return player2Score; 
	}
	@Override
	public boolean updateGame() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void drawGame(Graphics g) {
		// TODO Auto-generated method stub
		ppf.repaint();
	}



}