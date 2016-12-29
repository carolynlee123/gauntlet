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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import gameClient.Game;
import pong.PongGUI;
import snake.FontLibrary;
import snake.Help;
import snake.Snake;
import spaceInvaders.SpaceInvadersPanel;

public class SinglePlayerLobby {
	JFrame lobbyFrame;
	Font customFont;
	Font bigFont;
	Font mediumFont;
	GraphicsEnvironment ge;
	JGameImage selectedPanel;
	int selectedPanelNum = -1;
	Boolean registeredUser;
	Game currentGame;

	public SinglePlayerLobby(JFrame frame, Boolean registered) {
		// the pick your image
		// the zen mode
		// JFrame: zen mode can be at the bottom
		// pick your image can be in the middle
		// puzzle logo can be in the north
		registeredUser = registered;
		lobbyFrame = frame;

		//lobbyFrame.setForeground(Color.CYAN);
			// creating 3 fonts
			try {
				customFont = Font.createFont(Font.TRUETYPE_FONT, new File("kenvector_future_thin.ttf")).deriveFont(12f);
			} catch (FontFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mediumFont = customFont.deriveFont(30f);
			bigFont = customFont.deriveFont(50f);
			ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// register the font
			try {
				ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("kenvector_future_thin.ttf")));
			} catch (FontFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// setBackground(Color.BLUE);
		initializeGUI();
	}

	public void initializeGUI() {
		lobbyFrame.getContentPane().removeAll();
		lobbyFrame.revalidate();
		lobbyFrame.setForeground(Color.BLACK);
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder((new Insets(20, 20, 20, 20))));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		// topPanel.setAlignmentX(CENTER_ALIGNMENT);

		JLabel jp = new JLabel("SINGLE PLAYER GAMES");
		jp.setFont(bigFont);
		jp.setForeground(Color.GREEN);
		jp.setHorizontalAlignment(SwingConstants.CENTER);
		jp.setAlignmentX(Component.CENTER_ALIGNMENT);

		topPanel.add(jp);

		JLabel selectLabel = new JLabel("Select A Game: ");
		selectLabel.setFont(mediumFont);
		selectLabel.setForeground(Color.WHITE);

		selectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		topPanel.add(Box.createGlue());
		topPanel.add(selectLabel);
		lobbyFrame.add(topPanel, BorderLayout.NORTH);
		topPanel.setOpaque(false);

		// available images

		JPanel imagePanel = new JPanel();
		lobbyFrame.add(imagePanel, BorderLayout.CENTER);
		imagePanel.setOpaque(false);
		JPanel imagePanel2 = new JPanel();
		imagePanel2.setOpaque(false);
		imagePanel2.setLayout(new GridLayout(2, 2));
		int x = 0;
		JGameImage temp = new JGameImage(0, new ImageIcon("images/puzzle/5853.jpg").getImage());
		temp.setBorder(BorderFactory.createLineBorder(Color.white, 5));

		imagePanel2.add(temp);

		JGameImage temp1 = new JGameImage(1, new ImageIcon("images/puzzle/Pong.jpg").getImage());
		temp1.setBorder(BorderFactory.createLineBorder(Color.white, 5));
		imagePanel2.add(temp1);

		JGameImage temp2 = new JGameImage(2, new ImageIcon("images/puzzle/space-invaders.jpg").getImage());
		temp2.setBorder(BorderFactory.createLineBorder(Color.white, 5));
		imagePanel2.add(temp2);

		JGameImage temp3 = new JGameImage(3, new ImageIcon("images/puzzle/unnamed.png").getImage());
		temp3.setBorder(BorderFactory.createLineBorder(Color.white, 5));
		imagePanel2.add(temp3);

		temp.addMouseListener(new GameClicked());
		temp1.addMouseListener(new GameClicked());
		temp2.addMouseListener(new GameClicked());
		temp3.addMouseListener(new GameClicked());

		lobbyFrame.add(imagePanel2, BorderLayout.CENTER);

		JPanel tempPanel = new JPanel();
		lobbyFrame.add(tempPanel, BorderLayout.EAST);
		tempPanel.setOpaque(false);
		JPanel tempPanel1 = new JPanel();
		lobbyFrame.add(tempPanel1, BorderLayout.WEST);
		tempPanel1.setOpaque(false);
		JPanel bottomPanel = new JPanel();

		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
		bottomPanel.add(Box.createGlue());

		// JButton startButton = new JButton("Start");
		
		ImageIcon buttonIcon = new ImageIcon("images/puzzle/grey_button00.png");
		JLabel startLabel = new JLabel("Start");
		startLabel.setIcon(buttonIcon);
		FontLibrary f;
		Font font = FontLibrary.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 18);
		startLabel.setFont(font);
		startLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		startLabel.setVerticalTextPosition(SwingConstants.CENTER);
		JButton startButton = new JButton(buttonIcon);
		startButton.add(startLabel);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);

		//ButtonPanel startButton = new ButtonPanel(new ImageIcon("grey_button01.png"), "Start", customFont);
		bottomPanel.add(startButton);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedPanelNum == 0) {
					
					currentGame = new MenuWindow(lobbyFrame, registeredUser, new FinishListener(),true);

					//long score = temp.getScore();

					// wait for game to be finished, return back to this

				} else if (selectedPanelNum == 1) {
					PongGUI pongGUI = new PongGUI(true,false,false,true, new FinishListener());
					currentGame = pongGUI.getGameManager();
					lobbyFrame.getContentPane().removeAll();
					lobbyFrame.add(pongGUI);
					lobbyFrame.revalidate();
					lobbyFrame.repaint();
					lobbyFrame.setSize(1200, 1200);
					pongGUI.start(); 
					// open the pong game
				} else if (selectedPanelNum == 2) {
					
					// make a frame, add spaceinvaders to that frame;
					lobbyFrame.getContentPane().removeAll();
					SpaceInvadersPanel temp = new SpaceInvadersPanel(new FinishListener());
					currentGame = temp.getGame();
					lobbyFrame.add(temp);
					lobbyFrame.revalidate();
					lobbyFrame.repaint();

					// set frame visible
					// new SpaceInvadersPanel();
				} else if (selectedPanelNum == 3) {
					// open the snake game
					 currentGame = new Snake(new FinishListener(), lobbyFrame);
			    	//snake.setJMenuBar(menuBar);
			    	
			    	//get the score
				} else {
					// no selection don't do anything yet
				}
			}
		});
		bottomPanel.setOpaque(false);
		lobbyFrame.add(bottomPanel, BorderLayout.SOUTH);
		
		lobbyFrame.setSize(800, 600);
		lobbyFrame.setLocation(100, 100);
		lobbyFrame.setVisible(true);
		lobbyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(FirstWindows.aboutMenuItem.getActionListeners().length==0){
		FirstWindows.aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JDialog jd = new JDialog();
				jd.setTitle("LeaderBoard");
				jd.setLocationRelativeTo(null);
				jd.setSize(200, 300);
				jd.setModal(true);
				Map<String, Integer> map =null;
				if(selectedPanelNum==0){
					map = FirstWindows.client.getSliderLeader();
				}else if(selectedPanelNum==3){
					map = FirstWindows.client.getSnakeLeader();
				}else if(selectedPanelNum==2){
					map = FirstWindows.client.getSpaceLeader();
				}else if(selectedPanelNum==1){
					map = FirstWindows.client.getPongLeader();
				}
				if(map!=null){
				String[] head = new String[2];
				head[0] = "name";
				head[1] = "score";
				DefaultTableModel model = new DefaultTableModel(head, 0);
				for (Map.Entry<String, Integer> entry : map.entrySet())
				{
//				    System.out.println(entry.getKey() + "/" + entry.getValue());
					Object[] o = {entry.getKey(), entry.getValue()};
					model.addRow(o);
				}
				JTable table = new JTable(model);
				table.setAutoCreateRowSorter(true);
				JScrollPane scroll = new JScrollPane(table);
				jd.add(scroll);
				jd.setVisible(true);
				}
			}
			});
		}

		if(FirstWindows.helpItem.getActionListeners().length==0){
    	FirstWindows.helpItem.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent ae) {
    			if(selectedPanelNum==0){
        			Help helpWindow = new Help("images/puzzle/helpPuzzle.txt");
        			helpWindow.display();
				}else if(selectedPanelNum==3){
	    			Help helpWindow = new Help("images/snake/help");
	    			helpWindow.display();
				}else if(selectedPanelNum==2){
//	    			Help helpWindow = new Help("images/puzzle/helpPuzzle.txt");
//	    			helpWindow.display();
				}else if(selectedPanelNum==1){
//	    			Help helpWindow = new Help("images/puzzle/helpPuzzle.txt");
//	    			helpWindow.display();
				}
    		}
    	});
		}
	}

	
	public class FinishListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//get the score
			//send the score
			
			int result = (int) currentGame.getScore();
			//System.out.println("finish listener+ " + result + " " + SinglePlayerLobby.this.selectedPanelNum);
			if(SinglePlayerLobby.this.selectedPanelNum==0){
				FirstWindows.client.update_slider_score(result);
			}else if(SinglePlayerLobby.this.selectedPanelNum==3){
				FirstWindows.client.update_snake_score(result);
			}else if(SinglePlayerLobby.this.selectedPanelNum==2){
				FirstWindows.client.update_space_score(result);
			}else if(SinglePlayerLobby.this.selectedPanelNum==1){
				FirstWindows.client.update_pong_score(result);
			}
			
			
			//go back to the singleplayer lobby gui
			
			SinglePlayerLobby.this.initializeGUI();
		}
	}

	private class JGameImage extends JPanel {
		int num;
		Image image;

		public JGameImage(int num, Image image) {
			super();
			this.image = image;
			this.num = num;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

	private class GameClicked extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			
			Object source = e.getSource();
			if (!(source instanceof JGameImage)) {
				return;
			}
			JGameImage temp = (JGameImage) source;
			selectedPanelNum = temp.num;
			
			temp.setBorder(BorderFactory.createLineBorder(Color.red, 5));
			if (selectedPanel != null) {
				selectedPanel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
			}
			selectedPanel = temp;
		}
	}
	/*
	 * public static void main (String [] args) { new Lobby(); }
	 */

}
