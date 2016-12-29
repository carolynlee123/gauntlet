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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import gameClient.Game;

public class PuzzleGameManager extends Game {
	Stack<Integer> undoColumnStack;
	Stack<Integer> undoRowStack;
	HashMap<Image, Integer> imageRowMap;
	HashMap<Image, Integer> imageColMap;
	BufferedImage completeImage;
	JFrame pauseFrame;
	Image[][] startBoard;
	PuzzlePanel[][] gameBoard;
	JButton undoButton;
	JButton hintButton;
	JButton forfeitButton;
	JButton pauseButton;
	JButton startOverButton;
	JFrame gui;
	TimerPanel timer;
	String timeTaken;
	Boolean zen;
	Boolean registered;
	Font customFont;
	Font bigFont;
	GraphicsEnvironment ge;
	int timesHint = 0;
	Timer _timer;
	ActionListener finishListener1;
	JButton done;
	JFrame winnerFrame;
	KeyListenPanel keyTemp;

	public PuzzleGameManager(JFrame frame, BufferedImage image, boolean b, boolean registeredUser,
			ActionListener finishListener) {
		super();
		gui = frame;
		zen = b;
		registered = registeredUser;
		gui.getContentPane().removeAll();
		gui.revalidate();

		undoRowStack = new Stack<Integer>();
		undoColumnStack = new Stack<Integer>();
		imageRowMap = new HashMap<Image, Integer>();
		imageColMap = new HashMap<Image, Integer>();
		completeImage = image;
		startBoard = new Image[3][3];
		gameBoard = new PuzzlePanel[3][3];
		finishListener1 = finishListener;

		try {
			// creating 3 fonts
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("kenvector_future_thin.ttf")).deriveFont(12f);
			bigFont = customFont.deriveFont(30f);
			ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("kenvector_future_thin.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}

		initializeBoard(image);
		initializeGUI();
		createEvents();
	}

	public void addToStack(int row, int col) {
		undoRowStack.add(row);
		undoColumnStack.add(col);
	}

	public int getLastRowMove() {
		if (undoRowStack.isEmpty()) {
			return -1;
		}
		return undoRowStack.pop();
	}

	public int getLastColMove() {
		if (undoColumnStack.isEmpty()) {
			return -1;
		}
		return undoColumnStack.pop();
	}

	public void initializeBoard(Image image) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				gameBoard[i][j] = new PuzzlePanel(i, j);
				gameBoard[i][j].addMouseListener(new PuzzlePieceClicked());
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i != 0) {
					gameBoard[i][j].west = gameBoard[i - 1][j];
				}
				if (i != 2) {
					gameBoard[i][j].east = gameBoard[i + 1][j];
				}
				if (j != 0) {
					gameBoard[i][j].north = gameBoard[i][j - 1];
				}
				if (j != 2) {
					gameBoard[i][j].south = gameBoard[i][j + 1];
				}

			}
		}
		BufferedImage img = completeImage;
		int height = img.getHeight();
		int width = img.getWidth();
		height = height / 3;
		width = width / 3;
		int x = 0;
		int y = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!(i == 2 && j == 2)) {
					Image temp = img.getSubimage(x, y, width, height);
					gameBoard[i][j].image = temp;
					imageRowMap.put(temp, i);
					imageColMap.put(temp, j);
				}
				x += width;
			}
			x = 0;
			y = height * (i + 1);
		}
		shuffleBoard();
	}

	public void initializeGUI() {
		// gui = new JFrame();
		gui.setBackground(Color.black);
		keyTemp = new KeyListenPanel();
		keyTemp.setLayout(new BorderLayout());
		keyTemp.requestFocus();
		undoButton = new JButton("Undo");
		//undoButton.setForeground(Color.white);
		hintButton = new JButton("Hint");
		//hintButton.setForeground(Color.white);
		forfeitButton = new JButton("Give Up");
		//forfeitButton.setForeground(Color.white);
		pauseButton = new JButton("Pause");
		//pauseButton.setForeground(Color.white);
		if (!registered) {
			hintButton.setEnabled(false);
		}
		if (zen) {
			pauseButton.setEnabled(false);
		}
		startOverButton = new JButton("StartOver");
		//startOverButton.setForeground(Color.white);

		JToolBar jtb = new JToolBar();
		jtb.setBackground(Color.black);

		jtb.add(startOverButton);
		jtb.add(undoButton);
		jtb.add(hintButton);
		jtb.add(forfeitButton);
		jtb.add(pauseButton);
		
		
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.black);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(jtb);

		JLabel titleLabel = new JLabel("8 SLIDER PUZZLE");
		titleLabel.setForeground(Color.white);
		titleLabel.setFont(bigFont);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		topPanel.add(titleLabel);

		keyTemp.add(topPanel, BorderLayout.NORTH);
		keyTemp.setBackground(Color.black);

		JPanel imagePanel = new JPanel();
		imagePanel.setBackground(Color.black);
		
		imagePanel.setLayout(new GridLayout(5, 3));

		for (int i = 0; i < 5; i++) {

			for (int j = 0; j < 3; j++) {
				if (i == 0 || i == 4) {
					if (i == 0 && j == 1) {

						timer = new TimerPanel();
						timer.setFont(bigFont);
						if (!zen) {
							timer.start();
						}
						timer.setHorizontalAlignment(SwingConstants.CENTER);
						imagePanel.add(timer);
					} else {
						JLabel temp;
						temp = new JLabel("");
						temp.setHorizontalAlignment(SwingConstants.CENTER);
						imagePanel.add(temp);
					}

				} else {
					PuzzlePanel temp = gameBoard[i - 1][j];
					temp.setBorder(BorderFactory.createLineBorder(Color.white));
					imagePanel.add(temp);
				}
			}
		}
		JPanel temp1 = new JPanel();
		temp1.setBackground(Color.black);
		
		JPanel temp2 = new JPanel();
		temp2.setBackground(Color.black);

		keyTemp.add(temp1, BorderLayout.EAST);
		keyTemp.add(temp2, BorderLayout.WEST);

		JPanel south = new JPanel();
		keyTemp.add(south, BorderLayout.SOUTH);
		keyTemp.add(imagePanel, BorderLayout.CENTER);
		gui.setSize(800, 600);
		gui.setLocation(100, 100);
		keyTemp.add(south, BorderLayout.SOUTH);
		keyTemp.add(imagePanel, BorderLayout.CENTER);
		gui.add(keyTemp);
		// gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * gui.setFocusable(true); //gui.addKeyListener(new
		 * PuzzleKeyListener());
		 * gui.getRootPane().getInputMap().put(KeyStroke.getKeyStroke("DOWN"),
		 * "pressed"); gui.getRootPane().getActionMap().put("pressed", new
		 * DownAction() );
		 */
		gui.repaint();
		gui.setVisible(true);
	}

	public void createEvents() {
		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int latestRowMove = getLastRowMove();
				int latestColMove = getLastColMove();

				if (latestRowMove == -1) {
					// no moves to undo
				} else {
					// undo that move
					if (movePiece(latestRowMove, latestColMove, true, gameBoard)) {

						gui.repaint();
					}
				}
				keyTemp.requestFocus();
			}
			
		});
		startOverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						gameBoard[i][j].image = startBoard[i][j];
					}
				}
				// reset the undo stacks
				undoRowStack.removeAllElements();
				undoColumnStack.removeAllElements();
				gui.revalidate();

				gui.repaint();
				keyTemp.requestFocus();
			}
		});
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// pauseTimer
				timer.pauseTimer();
				// display the paused Window screen
				pauseFrame = new JFrame();
				pauseFrame.setBackground(Color.black);
				JPanel temp = new JPanel();
				
				temp.setBackground(Color.black);
				JLabel tempLabel = new JLabel("GAME PAUSED!");
				tempLabel.setFont(bigFont);
				tempLabel.setBackground(Color.black);
				tempLabel.setOpaque(true);
				
				tempLabel.setForeground(Color.white);
				tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
				JButton unPauseButton = new JButton("UNPAUSE THE GAME");
				//unPauseButton.setForeground(Color.white);
				// temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
				// temp.add(tempLabel);
				// temp.add(unPauseButton);
				pauseFrame.add(tempLabel, BorderLayout.CENTER);
				pauseFrame.add(unPauseButton, BorderLayout.SOUTH);
				pauseFrame.setSize(800, 600);
				pauseFrame.setLocationRelativeTo(gui);
				pauseFrame.setVisible(true);
				pauseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gui.setBackground(Color.black);
				gui.setVisible(false);
				unPauseButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// pauseTimer
						timer.unPauseTimer();
						gui.setVisible(true);
						pauseFrame.dispose();
					}
				});
				keyTemp.requestFocus();
			}
		});
		forfeitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				score = 0;
				if (!zen) {
					timer.pauseTimer();
				}
				timer.shutDown();
				winnerFrame = new JFrame();
				winnerFrame.setBackground(Color.black);
				done = new JButton("Ok!");
				done.setOpaque(false);
				final JFrame winnerFrame = new JFrame();
				JPanel northPanel = new JPanel();
				northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
				JLabel giveupLabel = new JLabel("You gave up!");
				giveupLabel.setForeground(Color.white);
				JLabel scoreLabel = new JLabel();
				scoreLabel.setFont(customFont);
				scoreLabel.setForeground(Color.white);
				scoreLabel.setText("You score: " + score);
				giveupLabel.setHorizontalAlignment(JLabel.CENTER);
				giveupLabel.setFont(bigFont);
				scoreLabel.setHorizontalAlignment(JLabel.CENTER);
				scoreLabel.setFont(bigFont);
				scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				giveupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				northPanel.setBackground(Color.black);
				northPanel.add(giveupLabel);
				northPanel.add(scoreLabel);
				winnerFrame.add(northPanel, BorderLayout.NORTH);
				winnerFrame.add(done, BorderLayout.SOUTH);

				//winnerFrame.add(new JLabel(new ImageIcon(completeImage)));
				//ImageIcon tempIcon = new ImageIcon(completeImage);
				JLabel imageLabel = new JLabel(new ImageIcon(completeImage));
				imageLabel.setBackground(Color.black);
				imageLabel.setOpaque(true);
				winnerFrame.add(imageLabel);
				winnerFrame.setSize(500, 500);
				winnerFrame.setVisible(true);
				done.addActionListener(finishListener1);
				done.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						winnerFrame.dispose();
					}
				});
			}
		});

		hintButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				_timer = new Timer(700, new ActionListener() {
					int count = 0;
					int[] latest = { -1, -1 };

					public void actionPerformed(ActionEvent e) {
						count++;

						latest = hintHelper(latest);
						gui.repaint();
						if (count == 3) {
							_timer.stop();
						}
					}
				});

				_timer.start();
				// _timer.stop();

				hintButton.setEnabled(false);
			}
		});
	}

	private int[] hintHelper(int[] latest) {
		// calculate Manhattan distance
		// disable the actionlisteners?

		// computer manhattan distance

		int blankRow = -1;
		int blankCol = -1;
		PuzzlePanel[][] copy = new PuzzlePanel[3][3];
		// find the blank spot and make a copy of the board
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				copy[i][j] = new PuzzlePanel(i, j);
				copy[i][j].row = gameBoard[i][j].row;
				copy[i][j].col = gameBoard[i][j].col;
				copy[i][j].image = gameBoard[i][j].image;
				copy[i][j].north = gameBoard[i][j].north;
				copy[i][j].south = gameBoard[i][j].south;
				copy[i][j].west = gameBoard[i][j].west;
				copy[i][j].east = gameBoard[i][j].east;
				if (gameBoard[i][j].image == null) {

					blankRow = i;
					blankCol = j;
				}
			}
		}
		// now check to see where the blank spot can be moved
		int temp_x = -1;
		int temp_y = -1;

		int[] optionX = { -1, -1, -1, -1 };
		int[] optionY = { -1, -1, -1, -1 };
		int[] distances = { 1000, 1000, 1000, 1000 };
		// north
		if (copy[blankRow][blankCol].north != null) {

			temp_x = copy[blankRow][blankCol].north.row;
			temp_y = copy[blankRow][blankCol].north.col;

			if (movePiece(temp_x, temp_y, true, copy)) {
				// moved the north down was successful
				for (int i = 0; i < 4; i++) {
					if (optionX[i] == -1) {
						optionX[i] = temp_x;
						optionY[i] = temp_y;
						distances[i] = calculateManhattanDistance(copy);
						break;
					}
				}
				movePiece(blankRow, blankCol, true, copy);
			}
		}

		// east
		if (copy[blankRow][blankCol].east != null) {

			temp_x = copy[blankRow][blankCol].east.row;
			temp_y = copy[blankRow][blankCol].east.col;
			if (movePiece(temp_x, temp_y, true, copy)) {
				// moved the north down was successful
				for (int i = 0; i < 4; i++) {
					if (optionX[i] == -1) {
						optionX[i] = temp_x;
						optionY[i] = temp_y;
						distances[i] = calculateManhattanDistance(copy);
						break;
					}
				}
				movePiece(blankRow, blankCol, true, copy);
			}
		}
		// west
		if (copy[blankRow][blankCol].west != null) {

			temp_x = copy[blankRow][blankCol].west.row;
			temp_y = copy[blankRow][blankCol].west.col;
			if (movePiece(temp_x, temp_y, true, copy)) {
				// moved the north down was successful
				for (int i = 0; i < 4; i++) {
					if (optionX[i] == -1) {
						optionX[i] = temp_x;
						optionY[i] = temp_y;
						distances[i] = calculateManhattanDistance(copy);
						break;
					}
				}
				movePiece(blankRow, blankCol, true, copy);
			}
		}
		// south
		if (copy[blankRow][blankCol].south != null) {

			temp_x = copy[blankRow][blankCol].south.row;
			temp_y = copy[blankRow][blankCol].south.col;
			for (int i = 0; i < 4; i++) {
				if (optionX[i] == -1) {
					optionX[i] = temp_x;
					optionY[i] = temp_y;
					distances[i] = calculateManhattanDistance(copy);
					break;
				}
			}
			movePiece(blankRow, blankCol, true, copy);
		}
		int best = -1;
		int best_distance = 10000;
		for (int i = 0; i < 4; i++) {
			if (optionX[i] == -1) {
				continue;
			}
			if (optionX[i] == latest[0] && optionY[i] == latest[1]) {
				continue;
			}
			if (best_distance > distances[i]) {
				best = i;
				best_distance = distances[i];
			}
		}
		movePiece(optionX[best], optionY[best], false, gameBoard);

		latest[0] = blankRow;
		latest[1] = blankRow;
		return latest;

	}

	private int calculateManhattanDistance(PuzzlePanel[][] board) {
		int x = 0;
		int y = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Image temp = board[i][j].image;
				if (temp == null) {
					continue;
				}
				int correct_X = imageRowMap.get(temp);
				int correct_Y = imageColMap.get(temp);
				x += Math.abs(correct_X - i);
				y += Math.abs(correct_Y - j);
			}
		}
		return x + y;
	}

	public boolean movePiece(int row, int col, boolean undo, PuzzlePanel[][] gameBoard1) {
		// find that move
		int x = 0;
		int y = 0;
		PuzzlePanel temp = null;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (gameBoard1[i][j].row == row && gameBoard1[i][j].col == col) {
					temp = gameBoard1[i][j];
					break;
				}
			}
			if (temp != null) {
				break;
			}
		}
		// check neighbors
		if (temp == null) {

			return false;
		}
		PuzzlePanel switchPanel = null;
		if (temp.north != null && temp.north.image == null) {
			switchPanel = temp.north;
		} else if (temp.south != null && temp.south.image == null) {
			switchPanel = temp.south;
		} else if (temp.east != null && temp.east.image == null) {
			switchPanel = temp.east;
		} else if (temp.west != null && temp.west.image == null) {
			switchPanel = temp.west;
		}
		if (switchPanel == null) {

			return false;
		}
		// switch places now

		gameBoard1[switchPanel.row][switchPanel.col].image = temp.image;
		// switchPanel.image = temp.image;
		temp.image = null;
		if (!undo) {
			// valid move, add this to the undo stacks
			addToStack(switchPanel.row, switchPanel.col);
		}

		return true;
	}

	private class PuzzlePieceClicked extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {

			Object source = e.getSource();
			if (!(source instanceof PuzzlePanel)) {
				return;
			}

			PuzzlePanel temp = (PuzzlePanel) source;
			if (movePiece(temp.row, temp.col, false, gameBoard)) {

				gui.repaint();
				if (checkFinished()) {

					// TODO: find ratio with score
					score = 100; // will change later!
					// open a new frame displaying the complete picture;
					// stop the timer
					timeTaken = timer.getTime();
					int scoreCal = timer.getScoreTime();
					score = (10 * 60) - scoreCal;
					if (score < 0) {
						score = 0;
					}
					timer.pauseTimer();
					timer.shutDown();

					winnerFrame = new JFrame();
					winnerFrame.setBackground(Color.black);
					JLabel timeLabel = new JLabel("Finished! Time taken: " + timeTaken);
					JLabel scoreLabel = new JLabel("Your score is: " + score);
					timeLabel.setForeground(Color.white);
					scoreLabel.setForeground(Color.white);
					timeLabel.setHorizontalAlignment(JLabel.CENTER);
					timeLabel.setFont(bigFont);
					scoreLabel.setHorizontalAlignment(JLabel.CENTER);
					scoreLabel.setFont(bigFont);
					scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
					timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
					JPanel northPanel = new JPanel();
					northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
					northPanel.add(timeLabel);
					northPanel.add(scoreLabel);
					northPanel.setBackground(Color.black);
					winnerFrame.add(northPanel, BorderLayout.NORTH);
					JLabel imageLabel = new JLabel(new ImageIcon(completeImage));
					imageLabel.setBackground(Color.black);
					imageLabel.setOpaque(true);
					winnerFrame.add(imageLabel);
					
					done = new JButton("Ok!");
					done.setOpaque(false);
					done.addActionListener(finishListener1);
					winnerFrame.add(done, BorderLayout.SOUTH);
					done.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							winnerFrame.dispose();
						}
					});
					winnerFrame.setSize(500, 500);
					winnerFrame.setVisible(true);
				}
			}
		}
	}

	private boolean checkFinished() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Image temp = gameBoard[i][j].image;
				if (temp == null) {
					continue;
				}

				if (imageRowMap.get(temp) != i || imageColMap.get(temp) != j) {
					return false;
				}
			}
		}
		return true;
	}

	private void shuffleBoard() {
		int counter = 50;
		PuzzlePanel blank = gameBoard[2][2];
		Random rand = new Random();
		while (counter > 0) {
			int temp = rand.nextInt(4);

			if (temp == 0) {
				if (blank.north != null) {
					if (movePiece(blank.north.row, blank.north.col, true, gameBoard)) {
						counter--;
						blank = blank.north;
						continue;
					}
				}
			}
			if (temp == 1) {
				if (blank.south != null) {
					if (movePiece(blank.south.row, blank.south.col, true, gameBoard)) {
						counter--;
						blank = blank.south;
						continue;
					}
				}
			}
			if (temp == 2) {
				if (blank.west != null) {
					if (movePiece(blank.west.row, blank.west.col, true, gameBoard)) {
						counter--;
						blank = blank.west;
						continue;
					}
				}
			}
			if (temp == 3) {
				if (blank.east != null) {
					if (movePiece(blank.east.row, blank.east.col, true, gameBoard)) {
						counter--;
						blank = blank.east;
						continue;
					}
				}
			}
		}

		// get blank spot back to bottom right;
		while (blank.south != null) {
			if (movePiece(blank.south.row, blank.south.col, true, gameBoard)) {
				blank = blank.south;
				continue;
			} else {
				break;
			}
		}
		while (blank.east != null) {
			if (movePiece(blank.east.row, blank.east.col, true, gameBoard)) {
				blank = blank.east;
				continue;
			} else {
				break;
			}
		}
		// copying the start
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				startBoard[i][j] = gameBoard[i][j].image;
			}
		}
	}

	private void movementForKeyPressed(int key) {
		// find the blank spot and make a copy of the board
		
		int blankRow = -1;
		int blankCol = -1;
		PuzzlePanel[][] copy = new PuzzlePanel[3][3];
		// find the blank spot and make a copy of the board
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				copy[i][j] = new PuzzlePanel(i, j);
				copy[i][j].row = gameBoard[i][j].row;
				copy[i][j].col = gameBoard[i][j].col;
				copy[i][j].image = gameBoard[i][j].image;
				copy[i][j].north = gameBoard[i][j].north;
				copy[i][j].south = gameBoard[i][j].south;
				copy[i][j].west = gameBoard[i][j].west;
				copy[i][j].east = gameBoard[i][j].east;
				if (gameBoard[i][j].image == null) {

					blankRow = i;
					blankCol = j;
				}
			}
		}
		
		
		
		// now check to see that the blank can be moved
		if (key == 0) {
			// testing to see if the north of the blank can be moved down
			if (copy[blankRow][blankCol].west != null) {

				int temp_x = copy[blankRow][blankCol].west.row;
				int temp_y = copy[blankRow][blankCol].west.col;

				if (movePiece(temp_x, temp_y, true, copy)) {
					// was successful, now actually move that piece down
					
					movePiece(temp_x, temp_y, false, gameBoard);
					gui.repaint();
					return;
				}
			}
		} else if (key == 1) {
			
			if (copy[blankRow][blankCol].east != null) {

				int temp_x = copy[blankRow][blankCol].east.row;
				int temp_y = copy[blankRow][blankCol].east.col;

				if (movePiece(temp_x, temp_y, true, copy)) {
					// was successful, now actually move that piece down
					
					movePiece(temp_x, temp_y, false, gameBoard);
					gui.repaint();
					return;
				}
			}
		} else if (key == 2) {
			
			if (copy[blankRow][blankCol].north != null) {

				int temp_x = copy[blankRow][blankCol].north.row;
				int temp_y = copy[blankRow][blankCol].north.col;

				if (movePiece(temp_x, temp_y, true, copy)) {
					// was successful, now actually move that piece down
					
					movePiece(temp_x, temp_y, false, gameBoard);
					gui.repaint();
					return;
				}
			}
		} else if (key == 3) {
			
			if (copy[blankRow][blankCol].south != null) {

				int temp_x = copy[blankRow][blankCol].south.row;
				int temp_y = copy[blankRow][blankCol].south.col;

				if (movePiece(temp_x, temp_y, true, copy)) {
					// was successful, now actually move that piece down
					
					movePiece(temp_x, temp_y, false, gameBoard);
					gui.repaint();
					return;
				}
				
			}
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

	public class KeyListenPanel extends JPanel {

		public KeyListenPanel() {
			ActionMap actionMap = getActionMap();
			int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
			InputMap inputMap = getInputMap(condition);

			inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Up");
			actionMap.put("Up", new KeyAction("Up"));
			
			inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Down");
			actionMap.put("Down", new KeyAction("Down"));
			
			inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Right");
			actionMap.put("Right", new KeyAction("Right"));
			
			inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Left");
			actionMap.put("Left", new KeyAction("Left"));
		}

		private class KeyAction extends AbstractAction {
			String action;
			public KeyAction(String text) {
				super(text);
				action = text;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (action.equals("Down")) {
					movementForKeyPressed(0);
				} else if (action.equals("Up")) {
					movementForKeyPressed(1);
				} else if (action.equals("Right")) {
					movementForKeyPressed(2);
				} else if (action.equals("Left")) {
					movementForKeyPressed(3);
				}
			}
		}
	}
}