package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Calendar;

import javax.swing.JPanel;






public class PongPlayField extends JPanel {

	/**
	 * 
	 */


	private static final long serialVersionUID = 1L;
	private PongGameManager pgm;
	
	public PongPlayField(PongGameManager pgm)
	{
		super();
		this.pgm = pgm; 
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		//draw board
		g.fillRect(0, 0, PongGameManager.boardLength,PongGameManager. boardHeight);
		g.setColor(Color.WHITE);

		g.drawLine( PongGameManager.boardLength/2,  0,  PongGameManager.boardLength/2, this.getHeight());
		//g.setColor(Color.WHITE);
		//System.out.println(pgm.getBallX()+" " + pgm.getBallY()+" " +pgm.getBallWidth()+" " +pgm.getBallHeight());
		g.fillRect(pgm.getBallX(), pgm.getBallY(), pgm.getBallWidth(), pgm.getBallHeight());
		
		Font f = g.getFont();
		
		g.setFont(new Font("kenvector future thin", Font.PLAIN, 25));
		if(pgm.isPlayerRight())g.drawString("UP & DOWN keys", 3*PongGameManager.boardLength/4, PongGameManager. boardHeight/4);
		else g.drawString("W & S keys", PongGameManager.boardLength/4, PongGameManager. boardHeight/4);

		//g.fillRect(pgm.getLeftPaddleX(), pgm.getLeftPaddleY(), spriteWidth, spriteHeight);
		
		//g.fillRect( pgm.getRightPaddleX(),  pgm.getRightPaddleY(), spriteWidth, spriteHeight);

		//g.fillRect( this.getWidth()/2-5, this.getHeight()/2-5, 20,20);


	}

}

