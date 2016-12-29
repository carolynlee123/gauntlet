package spaceInvaders;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;

public class GameStartWindow extends JPanel{
	private JButton Start;
	private ImagePanel all;
	private JLabel title1, title2;
	private JLabel filler1, filler2;
	public GameStartWindow(ActionListener actionListener){
		//super("The Crucible");
//		setSize(620, 480);
//		setLocation(400,400);
		init();
		createGUI();
		addEvent(actionListener);
	}
	private void init(){
		setLayout(new BorderLayout());
		BufferedImage temp=null;
		try {
			temp = ImageIO.read(new File("images/space_invaders/StartScreenBg.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		all = new ImagePanel(temp);
		all.setLayout(new BoxLayout(all,BoxLayout.Y_AXIS));
		title1 = new JLabel("Space");
		title2 = new JLabel("Invaders");
		filler1 = new JLabel("  ");
		filler2 = new JLabel("  ");
		Start = new JButton("Start game");
	}
	public void createGUI(){
		filler1.setFont(new Font("Aerial", Font.BOLD, 30));
		
		title1.setForeground(Color.WHITE);
		title1.setAlignmentX(Component.CENTER_ALIGNMENT);
		title1.setFont(new Font("Aerial", Font.BOLD, 40));
		
		title2.setForeground(Color.GREEN);
		title2.setAlignmentX(Component.CENTER_ALIGNMENT);
		title2.setFont(new Font("Aerial", Font.BOLD, 40));
		
		filler2.setFont(new Font("Aerial", Font.BOLD, 100));
		
		Start.setForeground(Color.WHITE);
		Start.setOpaque(false);
		Start.setContentAreaFilled(false);
		Start.setBorderPainted(false);
		Start.setAlignmentX(Component.CENTER_ALIGNMENT);
		Start.setFont(new Font("Aerial", Font.PLAIN, 40));
		
		all.add(filler1);
		all.add(title1);
		all.add(title2);
		all.add(filler2);
		all.add(Start);
		add(all, BorderLayout.CENTER);
	}
	public void addEvent(ActionListener actionListener){
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		Start.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
				if (Start.isEnabled()) {
					Start.setForeground(Color.GREEN);
				}
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	if (Start.isEnabled()) {
		    		Start.setForeground(Color.WHITE);
		    	}
		    }
		});
		Start.addActionListener(actionListener);
	}
	
	public JButton getStartButton() {
		return Start;
	}
	
	class ImagePanel extends JPanel{
		private static final long serialVersionUID = 1L;
		private BufferedImage bgImage;
		ImagePanel(BufferedImage img){
			bgImage = img;
		}
		protected void paintComponent(Graphics g) {

		    super.paintComponent(g);
		        g.drawImage(bgImage, 0, 0,getWidth(), getHeight(), null);
		}

	}
	
//	public static void main(String[] args) {
//		GameStartWindow test = new GameStartWindow();
//		test.setVisible(true);
//	}

}
