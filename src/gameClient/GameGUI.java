package gameClient;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import spaceInvaders.SpaceInvadersPanel;

public class GameGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private SpaceInvadersPanel gamePanel;
	
	public GameGUI(String title) {
		super(title);
		setSize(620, 480);

		// TODO James: remove this hotfix. window components should change their positions properly to suit window resizing.
//		setResizable(false);
		
		setLocation(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(1,1));
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("continue button clicked");
			}
		};
		gamePanel = new SpaceInvadersPanel(al);
		add(gamePanel);
	}
	
	public static void main(String [] args) {
		GameGUI gameGUI = new GameGUI("The Crucible");
		
		gameGUI.setVisible(true);
	}
}
