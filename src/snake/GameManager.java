package snake;

import java.util.Random;

import javax.swing.Timer;

import puzzle.SinglePlayerLobby.FinishListener;

// Game Logic

public class GameManager {
	protected int boardx[] = new int[900]; //30x30 board size
	protected int boardy[] = new int[900];
	protected int snakeLen = 3;
	protected boolean finished = false;
	protected int xApplePos, yApplePos;
	protected boolean moveUp, moveDown, moveLeft, moveRight;
	protected long score;
	Random rand = new Random();
	protected Timer timer;
	int scoreMult = 1;
	
	public GameManager() {
		score = 0;
		
		//snakeFinishListener = finishListener;
	}
	
	protected void scaleSnake() {
		for (int i = 0; i < snakeLen; i++) {
			boardx[i] = 150 - i*10;
			boardy[i] = 150;
		}
		System.out.println("Level " + Snake.level);
		if (Snake.level == 2){
			scoreMult = 2;
		}
		if (Snake.level == 3) {
			scoreMult = 3;
		}
	}
	
	protected void createApple() {
		xApplePos = (rand.nextInt(29) + 1) * 10;
		yApplePos = (rand.nextInt(29) + 1) * 10;
	}
	
	protected void move() {
		// default to moving right
		for (int i = snakeLen; i > 0; i--) {
            boardx[i] = boardx[(i - 1)];
            boardy[i] = boardy[(i - 1)];
        }
		if (moveUp) {
	           boardy[0]-=10;
		}
	    if (moveDown) {
            boardy[0]+=10;
        }
        if (moveLeft) {
            boardx[0]-=10;
        }
        if (moveRight) {
            boardx[0]+=10;
        }
	}
	
	protected void detectCollision() {
		// hit any of the walls
		if (boardy[0] >= 450) {
		    finished = true;
		}
		if (boardy[0] < 0) {
		    finished = true;
		}
		if (boardx[0] >= 450) {
		    finished = true;
		}
		if (boardx[0] < 0) {
		    finished = true;
		}
		// hit self
		for (int i = snakeLen; i > 0; i--) {
			if (i > 4 && (boardx[0] == boardx[i]) && (boardy[0] == boardy[i])) {
		        finished = true;
		    }
		}
		if (finished) {
			timer.stop();
		}
	}

	protected boolean atApple() {
		if ((boardx[0] == xApplePos && boardy[0] == yApplePos)) {
			snakeLen++;
			score+=scoreMult;
			createApple();
			return true;
		}
		return false;
	}
}
