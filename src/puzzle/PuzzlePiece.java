package puzzle;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class PuzzlePiece extends JPanel {
	int num;
	Image image;
	
	public PuzzlePiece(int num, Image image) {
		super();
		this.image = image;
		this.num = num;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0,0, this.getWidth(), this.getHeight(), null);
	}
	
	public void changeImage(Image image) {
		
	}
	
	
}
