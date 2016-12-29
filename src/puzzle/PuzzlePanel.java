package puzzle;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PuzzlePanel extends JPanel {
	int row = -1;
	int col = -1;
	Image image;
	PuzzlePanel north;
	PuzzlePanel west;
	PuzzlePanel south;
	PuzzlePanel east;
	
	public PuzzlePanel(int row, int col, Image image) {
		super();
		this.image = image;
		this.row = row;
		this.col = col;
	}
	public PuzzlePanel(int row, int col) {
		super();
		this.image = null;
		this.row = row;
		this.col = col;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0,0, this.getWidth(), this.getHeight(), null);
	}
	
	public void changeImage(Image image) {
		this.image = image;
		repaint();
	}
}
