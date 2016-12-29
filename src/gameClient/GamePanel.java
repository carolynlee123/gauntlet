package gameClient;

import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public abstract class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1;
	
	protected Game game;
	protected ActionListener endAction;
	
	public GamePanel(ActionListener endAction) {
		this.endAction = endAction;
	}
	
	@Override
	protected abstract void paintComponent(Graphics g);
	
	@Override
	public void run() {
		while (true) {
			if (game == null) {
				System.out.println("GamePanel Error: game isnull, continuing");	// TODO James: better error reporting
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;	// do nothing if game hasn't been instantiated
			}
			game.updateWindowDimensions(this.getWidth(), this.getHeight());
		}
	}
}
