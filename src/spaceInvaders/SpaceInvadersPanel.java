package spaceInvaders;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameClient.GamePanel;

public class SpaceInvadersPanel extends GamePanel {
	private static final long serialVersionUID = 1L;
	
	private SpaceInvadersGame game;
	
	private GameStartWindow gsw;
	
	public SpaceInvadersPanel(ActionListener endAction) {
		super(endAction);
		game = new SpaceInvadersGame(WIDTH, HEIGHT);
		this.setLayout(new BorderLayout());
		gsw = new GameStartWindow(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFocusable(false);
				gsw.getStartButton().setEnabled(false);
				revalidate();
				repaint();
				startGame();
			}
		});
		add(gsw);
	}
	
	public SpaceInvadersGame getGame() {
		return game;
	}
	
	private void startGame() {
		setFocusable(true);
		requestFocus();
		addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()){
					case KeyEvent.VK_LEFT:	// TODO: ship movement needs to respond faster to button presses
						moveShip(false);
						break;
					case KeyEvent.VK_RIGHT:
						moveShip(true);
						break;
					case KeyEvent.VK_SPACE:
						fireShipBlast();
						break;
					case KeyEvent.VK_A:
						moveShip(false);
						break;
					case KeyEvent.VK_D:
						moveShip(true);
						break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						stopShip();
						break;
					case KeyEvent.VK_RIGHT:
						stopShip();
						break;
					case KeyEvent.VK_A:
						stopShip();
						break;
					case KeyEvent.VK_D:
						stopShip();
						break;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
			
		});

		game.startGame();
		
		// start a thread to give 60 FPS
		new Thread() {
			@Override
			public void run() {
				removeAll();
				revalidate();
				repaint();
				while (game!=null && !game.gameOver()) {
					try {
						Thread.sleep(15);
						// = repaint panel every 15
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
				}

				GameOverWindow temp = new GameOverWindow(game.getScore(), endAction);
				SpaceInvadersPanel.this.add(temp, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		}.start();
	}
	
	public void moveShip(boolean moveRight){
		// make sure game is the type we expect
		if (game == null || !(game instanceof SpaceInvadersGame)) {
			return;
		}
		
		game.moveShip(moveRight);
	}
	
	public void stopShip() {
		if (game == null || !(game instanceof SpaceInvadersGame)) {
			return;
		}
		
		game.stopShip();
	}
	
	public void fireShipBlast() {
		if (!(game instanceof SpaceInvadersGame)) {
			return;
		}
		
		game.fireShipBlast();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
		if (game != null) {
			game.updateWindowDimensions(this.getWidth(), this.getHeight());
			game.updateGame();
			game.drawGame(g);
		}
	}
}
