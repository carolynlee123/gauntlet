package spaceInvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import gameClient.Constants;
import gameClient.GUITools;
import gameClient.Game;
import gameClient.Point2D;
import gameClient.Vector2D;

public class SpaceInvadersGame extends Game {
	private AlienWave wave;
	private Ship ship;
	private Vector<Shield> shields;
	private Vector<Blast> blasts;
	private int numShipBlasts = 0;
	private int maxShipBlasts = 1;
	private boolean gameStarted = false;
	private boolean initialized = false;
	private boolean gameOver = false;
	private double speed = 1;
	private double shotChance = 0.005;
	private int score = 0;
	
	// TODO: this is only for testing. set this to false by the time we present.
	private boolean godMode = false;
	
	public SpaceInvadersGame(int windowWidth, int windowHeight) {
		super();
		
		// perform pre-initialization. 
		// cannot initialize with window dimensions unless drawing the panel.
		
		// initialize wave
		wave = new AlienWave(speed,shotChance, this);
		
		// initialize ship
		ship = new Ship(new Point2D(0,0));
		
		// initialize shields
		shields = new Vector<>();
		for (int i = 0; i < 4; i++) {
			shields.add(new Shield(Constants.startPoint));
		}
		
		blasts = new Vector<>();
		
		updateWindowDimensions(windowWidth, windowHeight);
	}
	
	public void startGame() {
		gameStarted = true;
	}
	
	/**
	 * Update the positions of all actors, perform collision procedures.
	 * Returns true iff the ship has been destroyed.
	 * @param windowWidth
	 * @param windowHeight
	 * @return
	 */
	public boolean updateGame() {
		if (!gameStarted) {
			return false;
		}
		
		// game hasn't been properly initialized yet
		if (!initialized) {	// TODO: make a more robust start condition
			initializeGame();
			initialized = true;	// TODO: set initialized to true here, or in initializeGame()?
			return false;
		}
		
		// TODO: update positions
		wave.update(windowWidth, windowHeight);
		ship.update(windowWidth, windowHeight);

		updateBlasts();
		detectActorCollisions();
		updateBlasts();
		updateShields();
		
		if(wave.eliminated()){
			speed++;
			shotChance+=0.001;
			score+=wave.getNumberOfDeadAliens();
			score+=(1000*shotChance)+speed;
			wave = new AlienWave(speed, shotChance, this);
			for(int i=0;i<shields.size();i++){
				shields.get(i).resetDurability();
			}
		}
		return ship.isAlive();
	}
	
	private void updateShields() {
		synchronized (shields) {
			for (Shield shield : shields) {
				shield.update(windowWidth, windowHeight);
			}
		}
	}
	
	private void updateBlasts() {
		Vector<Blast> toRemove = new Vector<>();
		synchronized (blasts) {
			for (Blast blast : blasts) {
				if (blast.isActive()) {
					blast.update(windowWidth, windowHeight);
				}
				else {
					toRemove.add(blast);
				}
			}
			for (Blast blast : toRemove) {
				blasts.remove(blast);
				if (blast instanceof ShipBlast) {
					numShipBlasts--;
				}
			}
		}
		
		detectActorCollisions();
		
		if(wave.eliminated()){
			speed++;
			shotChance+=0.001;
			score+=wave.getNumberOfDeadAliens();
			score+=1;
			wave = new AlienWave(speed, shotChance, this);
			
			synchronized (shields) {
				for(int i=0;i<shields.size();i++){
					shields.get(i).resetDurability();
				}
			}
		}
	}
	
	/**
	 * Handle all collision logic between actors
	 */
	private void detectActorCollisions() {
		synchronized (blasts) {
			for (Blast blast : blasts) {
				if (blast == null || !blast.isActive()) {
					continue;
				}
				
				if (blast instanceof ShipBlast) {	// aliens should only respond to blasts from the ship
					wave.collisionCheck(blast, windowWidth, windowHeight);
				}
				else if (blast instanceof AlienBlast) {
					if (blast.collided(ship, windowWidth, windowHeight)&&!gameOver&&!godMode) {
						gameOver = true;
						score+=wave.getNumberOfDeadAliens();
						break;
					}
				}
				
				if (blast.isActive()) {	// don't bother checking shield collisions if blast is already destroyed
					for (Shield shield : shields) {
						if (shield.isActive() && blast.collided(shield, windowWidth, windowHeight)) {
							blast.destroy();
							shield.takeHit();
						}
					}
				}
			}
		}
		
		synchronized (wave) {
			for (Vector<Alien> aliensCol : wave.getAliens()) {
				for (Alien alien : aliensCol) {
					if(alien.isAlive()){
						for (Shield shield : shields) {
							if (alien.collided(shield, windowWidth, windowHeight)&&!gameOver&&!godMode) {
								gameOver = true;
								score+=wave.getNumberOfDeadAliens();
								break;
							}
						}
					}
				}
			}
		}
	}
	
	public long getScore() {
		return score;
	}
	
	/**
	 * Method to be called when setting up the game for the first time
	 */
	private void initializeGame() {
		double shipX = windowWidth * Constants.shipStartX;
		double  shipY = windowHeight * Constants.shipStartY;
		ship.setLocation(new Point2D(shipX, shipY));

		int shieldWidth = (int) (windowWidth * Constants.shieldWidthScale);
		for (int i = 0; i < 4; i++) {
			int outerX = windowWidth * i / 4;
			int outerY = (int) (windowHeight * Constants.shieldStartY);
			Rectangle outerSpace = new Rectangle(outerX, outerY, windowWidth / 4, windowHeight / 4);
			Rectangle shieldRect = new Rectangle(0, 0, shieldWidth, shieldWidth);
			Point2D shieldPoint = GUITools.center(outerSpace, shieldRect);
			shields.get(i).setLocation(shieldPoint);
		}
		
		wave.update(windowWidth, windowHeight);
		initialized = true;
	}
	
	/**
	 * Draws the game in its current state
	 * @param g
	 * @param windowWidth
	 * @param windowHeight
	 * @return
	 */
	public void drawGame(Graphics g) {
		if (!gameStarted) {
			return;
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowWidth, windowHeight);
		
		if (!gameOver) {
			wave.draw(g, windowWidth, windowHeight);
			ship.draw(g, windowWidth, windowHeight);
			for (int i = 0; i < shields.size(); i++) {
				shields.get(i).draw(g, windowWidth, windowHeight);
			}
			for (Blast blast : blasts) {
				blast.draw(g, windowWidth, windowHeight);
			}
		}
	}
	
	public void moveShip(boolean moveRight){
		if (moveRight) {	// move right
			ship.setVector(Constants.i.setMagnitude(10));
		} else {	// if not move right, then move left
			ship.setVector(Constants.i.setMagnitude(-10));
		}
	}
	
	public void stopShip() {
		ship.setVector(new Vector2D(0,0));
	}
	
	public void fireShipBlast() {
		if (blasts == null || numShipBlasts >= maxShipBlasts) {
			return;
		}

		numShipBlasts++;
		blasts.add(new ShipBlast(ship, windowWidth, windowHeight));
	}
	
	public void fireAlienBlast(Alien alien){
		if(blasts==null){
			return;
		}
		
		blasts.add(new AlienBlast(alien, windowWidth, windowHeight));
	}
	
	public boolean gameOver() {
		return gameOver;
	}
}
