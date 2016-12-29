package gameClient;

import java.awt.Graphics;

public abstract class Game {
	protected long score;
	protected int windowWidth;
	protected int windowHeight;
	protected boolean gameOver;
	
	public Game() {
		score = 0;
	}
	
	public long getScore() {
		return score;
	}
	
	public void updateWindowDimensions(int windowWidth, int windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
	}
	
	public abstract boolean updateGame();
	
	public abstract void drawGame(Graphics g);
	
	public boolean gameOver() {
		return gameOver;
	}
}
