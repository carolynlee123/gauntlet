package snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;

import gameClient.Game;
import puzzle.SinglePlayerLobby.FinishListener;

public class Snake extends Game {
	
	private static final long serialVersionUID = 1L;
	//Dimensions for the game
	private final static Dimension minSize = new Dimension(400, 300);
	private final static Dimension maxSize = new Dimension(800, 600);
	private final static Dimension midSize = new Dimension(700, 500);
	// blahblah
	private JPanel startPanel, scorePanel, southPanel;
	private BoardPanel boardPanel;
	private GamePanel gamePanel;
	private JTextField scoreText;
	private JButton easyButton, mediumButton, hardButton, quitButton, returnButton;
	private JLabel quitLabel, returnLabel;
	private Font quitFont;
	private Font scoreFont;
	private ImageIcon buttonIcon;
	private ImageIcon snakeIcon;
	private JLabel easyLabel, mediumLabel, hardLabel;
	private Font font;
	private FontLibrary f;
	private GameManager gm;
	private BufferedImage bg;
	private static JMenuBar menuBar;
	private Help helpWindow;
	private ActionListener snakeActionListener;
	private FinishListener snakeFinishListener;
	private JButton gmButton;
	private JFrame snakeFrame;	
	private JFrame winnerFrame;
	static int level;
	
	public Snake(ActionListener ae, JFrame frame) {
		super();
		snakeFrame = frame;
		snakeFrame.getContentPane().removeAll();
		snakeFrame.repaint();
		snakeFrame.setForeground(Color.BLACK);
		snakeActionListener = ae;
		snakeFrame.setSize(maxSize);
		snakeFrame.setMinimumSize(minSize);
		snakeFrame.setMaximumSize(maxSize);
		snakeFrame.setLocation(500, 100);
		snakeFrame.setBackground(Color.black); 
		snakeFrame.setFocusable(true);
		snakeFrame.requestFocusInWindow();
		initializeComponents();
		addEvents();
		snakeFrame.add(startPanel);
		snakeFrame.revalidate();
	}
	
	private void initializeComponents() {
		menuBar = new JMenuBar();
    	JMenuItem help = new JMenuItem("Help");
    	help.setMnemonic('H');
    	help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
    	helpWindow = new Help();
    	help.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent ae) {
    			helpWindow.display();
    		}
    	});
    	menuBar.add(help);
		//snakeFrame.setJMenuBar(menuBar);
		buttonIcon = new ImageIcon("images/snake/grey_button01.png");
		easyButton = new JButton(buttonIcon);
		mediumButton = new JButton(buttonIcon);
		hardButton = new JButton(buttonIcon);
		try {
			bg = ImageIO.read(new File("images/snake/snakeBackground.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startPanel = new StartPanel();
		gm = new GameManager();
	}
	
	private void addEvents() {
		//snakesetDefaultCloseOperation(EXIT_ON_CLOSE);
		easyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				startPanel.setVisible(false);
				gamePanel = new GamePanel(300);
				snakeFrame.add(gamePanel);
				gamePanel.setVisible(true);
				gamePanel.setFocusable(true);
				gamePanel.requestFocusInWindow();
				gamePanel.setOpaque(false);
			}
		});	
		mediumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				startPanel.setVisible(false);
				gamePanel = new GamePanel(125);
				snakeFrame.add(gamePanel);
				gamePanel.setVisible(true);
				gamePanel.setFocusable(true);
				gamePanel.requestFocusInWindow();
				gamePanel.setOpaque(false);
			}
		});	
		hardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				startPanel.setVisible(false);
				gamePanel = new GamePanel(50);
				snakeFrame.add(gamePanel);
				gamePanel.setVisible(true);
				gamePanel.setFocusable(true);
				gamePanel.requestFocusInWindow();
				gamePanel.setOpaque(false);
			}
		});	
	
	}
	
	/*protected void restart() {
		Snake snake = new Snake();
		snake.setJMenuBar(menuBar);
		snake.setVisible(true);
	}*/
	
	class StartPanel extends JPanel {
		
		public StartPanel() {
			setLayout(new BorderLayout());
			JLabel titleLabel = new JLabel();
			titleLabel.setIcon(snakeIcon);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
			
			font = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 24);
			
			
			easyLabel = new JLabel("Easy");
			easyLabel.setFont(font);
			mediumLabel = new JLabel("Medium");
			mediumLabel.setFont(font);
			hardLabel = new JLabel("Hard");
			hardLabel.setFont(font);
			easyLabel.setIcon(buttonIcon);
			easyLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			easyLabel.setVerticalTextPosition(SwingConstants.CENTER);
			mediumLabel.setIcon(buttonIcon);
			mediumLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			mediumLabel.setVerticalTextPosition(SwingConstants.CENTER);
			hardLabel.setIcon(buttonIcon);
			hardLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			hardLabel.setVerticalTextPosition(SwingConstants.CENTER);
			
			easyButton.add(easyLabel);
			easyButton.setBorderPainted(false);
			easyButton.setContentAreaFilled(false);
			easyButton.setFocusPainted(false);
			mediumButton.add(mediumLabel);
			mediumButton.setBorderPainted(false);
			mediumButton.setContentAreaFilled(false);
			mediumButton.setFocusPainted(false);
			hardButton.add(hardLabel);
			hardButton.setBorderPainted(false);
			hardButton.setContentAreaFilled(false);
			hardButton.setFocusPainted(false);
			
			buttonPanel.add(Box.createGlue());
			buttonPanel.add(easyButton);
			buttonPanel.add(Box.createGlue());
			buttonPanel.add(mediumButton);
			buttonPanel.add(Box.createGlue());
			buttonPanel.add(hardButton);
			buttonPanel.add(Box.createGlue());
			buttonPanel.setOpaque(true);
			buttonPanel.setBackground(Color.black);
			add(buttonPanel, BorderLayout.SOUTH);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	class GamePanel extends JPanel {
		private BoardPanel boardPanel;
		public GamePanel(int delay) {
			boardPanel = new BoardPanel(delay);
			setLayout(new BorderLayout());
			setPreferredSize(minSize);
			setForeground(Color.BLACK);
			scorePanel = new JPanel();
			scorePanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			scoreText = new JTextField("Score: 0");
			scoreFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 18);
			scoreText.setFont(scoreFont);
			scoreText.setEditable(false);
			scoreText.setBorder(null);
			scoreText.setForeground(Color.WHITE);
			scoreText.setOpaque(false);
			scorePanel.add(scoreText, gbc);
			scorePanel.setOpaque(false);
			 
			buttonIcon = new ImageIcon("images/snake/grey_button01.png");
			quitFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD,20);
			
			quitLabel = new JLabel("Quit");
			quitLabel.setIcon(buttonIcon);
			quitLabel.setFont(quitFont);
			quitLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			quitLabel.setVerticalTextPosition(SwingConstants.CENTER);
			quitButton = new JButton(buttonIcon);
			quitButton.add(quitLabel);
			quitButton.setBorderPainted(false);
			quitButton.setContentAreaFilled(false);
			quitButton.setFocusPainted(false);
			returnLabel = new JLabel("Return");
			returnLabel.setIcon(buttonIcon);
			returnLabel.setFont(quitFont);
			returnLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			returnLabel.setVerticalTextPosition(SwingConstants.CENTER);
			returnButton = new JButton(buttonIcon);
			returnButton.add(returnLabel);
			returnButton.setBorderPainted(false);
			returnButton.setContentAreaFilled(false);
			returnButton.setFocusPainted(false);
			returnButton.setEnabled(false);
			southPanel = new JPanel();
			southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
			//southPanel.add(returnButton);
			southPanel.add(Box.createGlue());
			southPanel.add(quitButton);
			
			add(scorePanel, BorderLayout.NORTH);
			scorePanel.setOpaque(false);
			JPanel centerPanel = new JPanel();
			centerPanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc1 = new GridBagConstraints();
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			centerPanel.add(boardPanel, gbc1);
			centerPanel.setOpaque(false);
			add(scorePanel, BorderLayout.NORTH);
			add(centerPanel, BorderLayout.CENTER);
			add(southPanel, BorderLayout.SOUTH);
			southPanel.setOpaque(false);
			
			quitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					gm.timer.stop();
					UIManager.put("OptionPane.messageFont", f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 15));
					JOptionPane.showMessageDialog(gamePanel, "Forfeiting Game!");
					//restart();
					
				}
			});
			quitButton.addActionListener(snakeActionListener);
			returnButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					gamePanel.setVisible(false);
					gamePanel.setVisible(true);
				}
			});
		}
		protected void displayGameOver() {
			String gameOverText = "Game Over! " + "Final Score: " + gm.score; // + System.lineSeparator() + "Press Return";
			UIManager.put("OptionPane.messageFont", f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 15));
			JPanel gameOverPanel = new JPanel();
			//gameOverPanel.setForeground(Color.BLACK);
			gameOverPanel.setOpaque(false);
			gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
			JLabel gameOverLabel = new JLabel(gameOverText);
			gameOverLabel.setForeground(Color.RED);
			gameOverLabel.setOpaque(false);
			Font gmFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 40);
			gameOverLabel.setFont(gmFont);
			JPanel jp = new JPanel();
			jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
			jp.add(Box.createGlue());
			jp.add(gameOverLabel);
			jp.add(Box.createGlue());
			Font btFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD,22);
			gmButton = new JButton(buttonIcon);
			JLabel gmLabel = new JLabel("Done");
			gmLabel.setOpaque(false);
			gmLabel.setIcon(buttonIcon);
			gmLabel.setFont(btFont);
			gmLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			gmLabel.setVerticalTextPosition(SwingConstants.CENTER);
			gmButton.add(gmLabel);
			gmButton.addActionListener(snakeActionListener);
			gmButton.setBorderPainted(false);
			gmButton.setContentAreaFilled(false);
			gmButton.setFocusPainted(false);
			gmButton.setOpaque(false);
			JPanel jp2 = new JPanel();
			jp2.setLayout(new BoxLayout(jp2, BoxLayout.X_AXIS));
			jp2.add(Box.createGlue());
			jp2.add(gmButton);
			jp2.add(Box.createGlue());
			jp.setOpaque(false);
			jp2.setOpaque(false);
			gameOverPanel.add(Box.createGlue());
			gameOverPanel.add(jp);
			gameOverPanel.add(Box.createGlue());
			gameOverPanel.add(jp2);
			gameOverPanel.add(Box.createGlue());
			this.removeAll();
			this.add(gameOverPanel);
			this.revalidate();
			this.setForeground(Color.BLACK);
			gmButton.addActionListener(snakeFinishListener);
			//JOptionPane.showMessageDialog(gamePanel, gameOverText);
			
		}
		
		/*@Override
		protected void paintComponent(Graphics g) {
			if (gm.)
		}*/
	}
	
	class BoardPanel extends JPanel implements ActionListener{
		
		private static final long serialVersionUID = 1L;
		private Image appleImage, blankImage, snakeImage;
		private ImageIcon snakeIcon, appleIcon, blankIcon;
		private int[][] board;
		Random rand = new Random();
		//int level;
		private int timeDelay;
		Dimension boardDim = new Dimension(450, 450);
		
		public BoardPanel(int delay) {
			timeDelay = delay;
			if (timeDelay == 250) {
				level = 1;
			}
			if (timeDelay == 115) {
				level = 2;
			}
			if (timeDelay == 50) {
				level = 3;
			}
			setPreferredSize(boardDim);
			setBorder(BorderFactory.createLineBorder(Color.WHITE, 5, false));
			//setLayout(new FlowLayout());
			appleIcon = new ImageIcon("images/snake/apple.png");
			appleImage = appleIcon.getImage();
			snakeIcon = new ImageIcon("images/snake/snake.png");
			snakeImage = snakeIcon.getImage();
			blankIcon = new ImageIcon("images/snake/grey_tile.png");
			blankImage = blankIcon.getImage();
			
			//this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.setBackground(Color.black);
		
			KeyboardFocusManager kbfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			kbfm.addKeyEventDispatcher(new KeyEventDispatcher() {
				public boolean dispatchKeyEvent(KeyEvent e) {
					gm.moveUp = false;
					gm.moveDown = false;
					gm.moveLeft = false;
					gm.moveRight = false;
					int key = e.getKeyCode();
					if (key == KeyEvent.VK_UP) {
						gm.moveUp = true; 
					}
					else if (key == KeyEvent.VK_DOWN) {
						gm.moveDown = true;
					}
					else if (key == KeyEvent.VK_LEFT) {
						gm.moveLeft = true;
					}
					else if (key == KeyEvent.VK_RIGHT) {
						gm.moveRight = true;
					}
					return false;
				}
			});
			gm.scaleSnake();
			gm.createApple();
			CountDownPanel cd = new CountDownPanel();
			//displayCountdown();
			startGame();
		}
		
		/*private void displayCountdown() {
			int count = 3;
			JLabel timerLabel = new JLabel();
			Timer timer1 = new Timer(1000, this);
			timer1.start();
			while (count >= 0) {
				String text = String.valueOf(count);
				timerLabel.setText(text);
				timerLabel.setOpaque(false);
				timerLabel.setForeground(Color.WHITE);
				add(timerLabel);
				revalidate();
				if (count <= 0) {
					String text1 = "GO!";
					timerLabel.setText(text1);
					timerLabel.setOpaque(false);
					timerLabel.setForeground(Color.WHITE);
					timerLabel.revalidate();
					timer1.stop();
				}
				count--;
			}
		}*/
		
		private void startGame() {
			gm.timer = new Timer(timeDelay, this);
			gm.timer.start();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			if (!gm.finished) {
				super.paintComponent(g);
				g.drawImage(appleImage, gm.xApplePos, gm.yApplePos, this);
				for (int i = 0; i < gm.snakeLen; i++) {
			        g.drawImage(snakeImage, gm.boardx[i], gm.boardy[i], this);	   
				}
			}
			if (gm.atApple()) {
				score++;
				gamePanel.remove(scorePanel);
				scorePanel.remove(scoreText);
				scorePanel.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				scoreText = new JTextField("Score: " + gm.score);
				scoreFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 18);
				scoreText.setFont(scoreFont);
				scoreText.setEditable(false);
				scoreText.setBorder(null);
				scoreText.setForeground(Color.WHITE);
				scoreText.setOpaque(false);
				scorePanel.add(scoreText, gbc);
				gamePanel.add(scorePanel, BorderLayout.NORTH);
				gamePanel.revalidate();
				gamePanel.repaint();
			}
			if (gm.finished) {
				gm.timer.stop();
				gamePanel.displayGameOver();
				//restart();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (!gm.finished) {
				gm.atApple();
				gm.detectCollision();
				gm.move();
			}
			revalidate();
			repaint();
		}
	}
	
	class CountDownPanel {
		private int count = 3;
		private JLabel countdownLabel;
		private JDialog jdl;
		
		public CountDownPanel() {
			countdownLabel = new JLabel();
			Timer timer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					if (count >= 0) {
						countdownLabel.setText(String.valueOf(count));
						countdownLabel.setForeground(Color.RED);
						count--;
						if (count < 0) {
							String go = "GO!";
							countdownLabel.removeAll();
							countdownLabel.setText(String.valueOf(go));
							countdownLabel.setForeground(Color.GREEN);
							countdownLabel.revalidate();
							//jdl.dispose();
						}
					}
					else {
						jdl.dispose();
					}
				}
			});
			Frame countdownWindow = new Frame();
			jdl = new JDialog(countdownWindow, "Countdown!", true);
			jdl.getContentPane().setBackground(Color.BLACK);;
			Font cdfont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 100);
			countdownLabel.setFont(cdfont);
			countdownLabel.setOpaque(false);
			jdl.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			jdl.add(countdownLabel, gbc);
			jdl.setSize(450, 450);
			jdl.setLocationRelativeTo(gamePanel);
			//timer.setRepeats(true);
			timer.start();
			jdl.setVisible(true);
		}
	}

	@Override
	public boolean updateGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void drawGame(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public long getScore() {
		return gm.score;
	}
	/*public static void main(String[] args) {
    	Snake snake = new Snake();
    	snake.setJMenuBar(menuBar);
    	snake.setVisible(true);
	}*/

}

