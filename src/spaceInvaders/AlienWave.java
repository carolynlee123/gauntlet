package spaceInvaders;

import java.awt.Graphics;
import java.util.Random;
import java.util.Vector;

import gameClient.Constants;
import gameClient.Point2D;

public class AlienWave {
	private Vector<Vector<Alien>> aliens;
	private double shotChance;
	private SpaceInvadersGame game;
	private int deadAliens = 0;
	
	public AlienWave(double speed,double shotChance, SpaceInvadersGame game) {
		aliens = new Vector<>();
		this.game = game;
		this.shotChance = shotChance;
		for (int i = 0; i < 4; i++) {
			Vector<Alien> column = new Vector<>();
			
			for (int j = 0; j < 2; j++) {
				Alien freshMeat = new Alien(Constants.startPoint);
				freshMeat.setVector(Constants.i.scale(speed));
				column.add(freshMeat);	// TODO: more appropriate starting position?
			}
			
			aliens.add(column);
		}
	}
	
	public void update(int windowWidth, int windowHeight) {
		// check if wave needs to initialize
		if (aliens != null && aliens.get(0).get(0).getLocation().equals(Constants.startPoint)) {
			initialize(windowWidth, windowHeight);
		}
		
		Random rand = new Random();
		boolean needToReverse = false;
		
		synchronized (aliens) {
			for (Vector<Alien> alienCols : aliens) {
				for (Alien alien : alienCols) {
					alien.rescale(windowWidth, windowHeight);
					alien.move();
					if(alien.outOfBounds(windowWidth, windowHeight)){
						if(alien.isAlive()) {
							needToReverse = true;
						}
					}
					double shootRoll = rand.nextDouble();	// roll a "die" to determine if the alien should fire
					if (shootRoll <= shotChance) {
						if(alien.isAlive()) {
							game.fireAlienBlast(alien);
						}
					}
				}
			}
			if(needToReverse){
				for (Vector<Alien> alienCols : aliens) {
					for (Alien alien : alienCols) {
						Point2D downPoint = new Point2D(alien.getLocation().x, 
								alien.getLocation().y + windowHeight * Constants.alienWaveDownDist);
						alien.moveTo(downPoint);
						alien.reverse();
					}
				}
			}
		}
	}
	
	public Vector<Vector<Alien>> getAliens() {
		return aliens;
	}
	
	public int getNumberOfDeadAliens() {
		return deadAliens;
	}
	
	public void collisionCheck(Blast blast, int windowWidth, int windowHeight) {
		if (blast == null) {
			return;
		}
		
		for (Vector<Alien> alienCols : aliens) {
			for (Alien alien : alienCols) {
				if (alien.isAlive()) {
					if (alien.collided(blast, windowWidth, windowHeight)) {
						alien.kill();
						deadAliens++;
						blast.destroy();
						return;	// blast can only collide with one alien, so if it has, terminate method
					}
				}
			}
		}
	}
	
	private void initialize(int windowWidth, int windowHeight) {
		double x = Constants.alienStartX * windowWidth;
		double y = Constants.alienStartY * windowHeight;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				Alien curr = aliens.get(i).get(j);
				curr.setLocation(new Point2D(x + i * curr.getWidth(windowWidth),
						y + j * curr.getHeight(windowWidth)));
			}
		}
	}
	
	public void draw(Graphics g, int windowWidth, int windowHeight) {
		for (Vector<Alien> aliensCol : aliens) {
			for (Alien alien : aliensCol) {
				alien.draw(g, windowWidth, windowHeight);
			}
		}
	}
	public boolean eliminated(){
		boolean back = true;
		for (Vector<Alien> aliensCol : aliens) {
			for (Alien alien : aliensCol) {
				if(alien.isAlive()){
					back = false;
				}
			}
		}
		return back;
	}
	
}
