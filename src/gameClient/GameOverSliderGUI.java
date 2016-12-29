package gameClient;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverSliderGUI extends JFrame {
	public GameOverSliderGUI () {
		setSize(500,500);
		setLocation(100,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//
		JPanel jp= new JPanel();
		JLabel jl = new JLabel("Game Over!");
		
		jp.add(jl);
		add(jp, BorderLayout.NORTH);
		
		JImage finishedImage = new JImage(0, new ImageIcon("Teddy-Bear-41.jpg").getImage());
		add(finishedImage, BorderLayout.CENTER);
		
		JButton okButton = new JButton("Ok!");
		add(okButton, BorderLayout.SOUTH);
		setVisible(true);
		
	}
	
	private class JImage extends JPanel {
		int num;
		Image image;
		public JImage(int num, Image image) {
			super();
			this.image = image;
			this.num = num;
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0,0, this.getWidth(), this.getHeight(), null);
		}
	}

	/*public static void main(String [] args) {
		new GameOverSliderGUI();
	}*/

}
