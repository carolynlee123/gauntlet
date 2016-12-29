package puzzle;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import gameClient.Game;
import puzzle.SinglePlayerLobby.FinishListener;
import snake.Help;

public class MenuWindow extends Game {
	JFrame windowFrame;
	Font customFont;
	Font bigFont;
	Font mediumFont;
	GraphicsEnvironment ge;
	BufferedImage selectedImage;
	BufferedImage randomImage;
	JImage selectedPanel;
	JLabel tempLabel;
	JImage replacePanel;
	JPanel imagePanel;
	JPanel imagePanel2;
	Boolean registered;
	JCheckBox zenMode;
	ActionListener finishListener1;
	Game puzzle;
	

	public MenuWindow(JFrame frame, Boolean registeredUser, ActionListener finishListener, Boolean singlePlayer) {

		// the pick your image
		// the zen mode
		// JFrame: zen mode can be at the bottom
		// pick your image can be in the middle
		// puzzle logo can be in the north
		windowFrame = frame;
		windowFrame.getContentPane().removeAll();
		windowFrame.revalidate();
		registered = registeredUser;
		this.finishListener1 = finishListener;

		try {
			// creating 3 fonts
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("kenvector_future_thin.ttf")).deriveFont(12f);
			// bigFont = Font.createFont(Font.TRUETYPE_FONT, new
			// File("kenvector_future_thin.ttf")).deriveFont(30f);
			mediumFont = customFont.deriveFont(30f);
			bigFont = customFont.deriveFont(50f);
			ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("kenvector_future_thin.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		windowFrame.setBackground(Color.black);
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.black);
		topPanel.setBorder(new EmptyBorder((new Insets(20, 20, 20, 20))));

		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		// topPanel.setAlignmentX(CENTER_ALIGNMENT);

		JLabel jp = new JLabel("8 SLIDER PUZZLE");
		jp.setForeground(Color.white);
		jp.setFont(bigFont);
		jp.setHorizontalAlignment(SwingConstants.CENTER);
		jp.setAlignmentX(Component.CENTER_ALIGNMENT);

		topPanel.add(jp);

		JLabel selectLabel = new JLabel("Select an Image: ");
		selectLabel.setForeground(Color.white);
		selectLabel.setFont(mediumFont);

		selectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		topPanel.add(Box.createGlue());
		topPanel.add(selectLabel);
		windowFrame.add(topPanel, BorderLayout.NORTH);

		// available images

		imagePanel = new JPanel();
		imagePanel.setBackground(Color.black);
		imagePanel.setOpaque(true);
		windowFrame.add(imagePanel, BorderLayout.CENTER);

		// zen mode:

		JPanel jp1 = new JPanel();
		jp1.setBackground(Color.black);
		// JLabel zenLabel = new JLabel("ZEN");
		JLabel jl1 = new JLabel("Or choose your own image: ");
		jl1.setForeground(Color.white);
		jl1.setFont(customFont);

		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF, PNG", "jpg", "gif", "png");
		fc.setFileFilter(filter);

		JButton fileButton = new JButton("Browse your files");
		fileButton.setFont(customFont);
		// In response to a button click:
		JButton startButton = new JButton("Start");
		JButton randomButton = new JButton("Start with Random Image");
		JPanel jp2 = new JPanel();
		jp1.add(jl1);
		jp1.add(fileButton);
		
	
		zenMode = new JCheckBox("Zen Mode?");
		zenMode.setForeground(Color.white);
		
		if (registered && singlePlayer) {
			jp2.add(zenMode);
		}

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		bottomPanel.add(jp1);
		bottomPanel.add(jp2);
		bottomPanel.setBackground(Color.black);
		jp2.add(startButton);
		jp2.setBackground(Color.black);
		jp2.add(randomButton);
		windowFrame.add(bottomPanel, BorderLayout.SOUTH);

		imagePanel2 = new JPanel();
		imagePanel2.setBackground(Color.black);
		imagePanel2.setLayout(new GridLayout(2, 2));
		int x = 0;
		JImage temp = null;
		JImage temp1 = null;
		JImage temp2 = null;
		JImage temp3 = null;
		JImage temp4 = null;
		
		Random rand = new Random();
		int randomNumber = rand.nextInt(5);
		
		

		try {
			
			temp = new JImage(x, ImageIO.read(new File("images/puzzle/Teddy-Bear-41.jpg")));

			temp.setBorder(BorderFactory.createLineBorder(Color.black, 5));
			if (randomNumber == 0) {
				randomImage = temp.image;
			}

			imagePanel2.add(temp);

			temp1 = new JImage(x, ImageIO.read(new File("images/puzzle/fresh_food.jpg")));
			temp1.setBorder(BorderFactory.createLineBorder(Color.black, 5));
			imagePanel2.add(temp1);
			
			if (randomNumber == 1) {
				randomImage = temp1.image;
			}

			temp2 = new JImage(x, ImageIO.read(new File("images/puzzle/hd_nature_wallpaper.jpg")));
			temp2.setBorder(BorderFactory.createLineBorder(Color.black, 5));
			imagePanel2.add(temp2);
			
			if (randomNumber == 2) {
				randomImage = temp2.image;
			}

			temp3 = new JImage(x, ImageIO.read(new File("images/puzzle/big_thumb.jpg")));
			temp3.setBorder(BorderFactory.createLineBorder(Color.black, 5));
			imagePanel2.add(temp3);
			
			if (randomNumber == 3) {
				randomImage = temp3.image;
			}

			temp4 = new JImage(x, ImageIO.read(new File("images/puzzle/doheny.jpg")));
			temp4.setBorder(BorderFactory.createLineBorder(Color.black, 5));
			imagePanel2.add(temp4);
			
			if (randomNumber == 4) {
				randomImage = temp4.image;
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// JImage temp5 = new JImage();
		tempLabel = new JLabel("Your own image here!");
		tempLabel.setForeground(Color.white);
		// temp5.add(temp6);
		tempLabel.setFont(customFont);
		tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tempLabel.setVerticalAlignment(SwingConstants.CENTER);

		imagePanel2.add(tempLabel);

//		JMenuBar jmb = new JMenuBar();
//		JMenuItem helpItem = new JMenuItem("Help");
//    	helpItem.setMnemonic('H');
//    	helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
//    	helpItem.addActionListener(new ActionListener() {
//    		@Override
//    		public void actionPerformed(ActionEvent ae) {
//    			Help helpWindow = new Help("images/puzzle/helpPuzzle.txt");
//    			helpWindow.display();
//    		}
//    	});
//    	jmb.add(FirstWindows.aboutMenuItem);
//		jmb.add(helpItem);

//		windowFrame.setJMenuBar(jmb);

		windowFrame.add(imagePanel2, BorderLayout.CENTER);

		JPanel tempPanel = new JPanel();
		tempPanel.setBackground(Color.black);
		windowFrame.add(tempPanel, BorderLayout.EAST);

		JPanel tempPanel1 = new JPanel();
		tempPanel1.setBackground(Color.black);
		windowFrame.add(tempPanel1, BorderLayout.WEST);
		temp.addMouseListener(new PictureClicked());
		temp1.addMouseListener(new PictureClicked());
		temp2.addMouseListener(new PictureClicked());
		temp3.addMouseListener(new PictureClicked());
		temp4.addMouseListener(new PictureClicked());

		windowFrame.setSize(800, 600);
		windowFrame.setLocation(100, 100);
		windowFrame.setVisible(true);
		//windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(windowFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					
					String filePath = file.getAbsolutePath();
					BufferedImage tempImage = null;
					try {
						tempImage = ImageIO.read(new File(filePath));
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
					if (tempLabel != null) {
						imagePanel2.remove(tempLabel);
						replacePanel = new JImage();
						replacePanel.image = tempImage;
						replacePanel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
						replacePanel.addMouseListener(new PictureClicked());
						tempLabel = null;
						imagePanel2.add(replacePanel);
						imagePanel2.revalidate();
						imagePanel2.repaint();
					} else {
						replacePanel.image = tempImage;
						tempLabel = null;
						imagePanel2.repaint();

					}

				} 
			}

		});
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selectedImage != null) {
					puzzle = new PuzzleGameManager(windowFrame, selectedImage, zenMode.isSelected(), registered, finishListener1);
				}
			}
		});
		
		randomButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (randomImage != null) {
					puzzle = new PuzzleGameManager(windowFrame, randomImage, zenMode.isSelected(), registered, finishListener1);
				}
			}
		});
		
		
	}

	private class PictureClicked extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			
			Object source = e.getSource();
			if (!(source instanceof JImage)) {
				return;
			}
			JImage temp = (JImage) source;
			selectedImage = temp.image;
			

			temp.setBorder(BorderFactory.createLineBorder(Color.red, 5));
			if (selectedPanel != null) {
				selectedPanel.setBorder(BorderFactory.createLineBorder(Color.black, 5));
			}
			selectedPanel = temp;
		}

	}

	private class JImage extends JPanel {
		int num;
		BufferedImage image;

		public JImage() {
			super();
		}

		public JImage(int num, BufferedImage image) {
			super();
			this.image = image;
			this.num = num;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (image != null) {
				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		}
	}

	/*public static void main(String[] args) {
		new MenuWindow(new JFrame();
	}*/

	@Override
	public boolean updateGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void drawGame(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	public long getScore() {
		if (puzzle!=null) {
			return puzzle.getScore();
		}
		return 0;
	}

}