package pong;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class PaddleIcon implements Icon {

	private int width;
	private int height;
	
	public PaddleIcon()
	{
		height = PongGameManager.paddleLength;
		width = PongGameManager.paddleWidth; 
	}
	public PaddleIcon(int width, int height)
	{
		this.width = width;
		this.height = height; 
	}
	@Override
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public void paintIcon(Component arg0, Graphics arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		arg1.setColor(Color.white);
		arg1.fillRect(arg2, arg3, width, height);
		
	}

}
