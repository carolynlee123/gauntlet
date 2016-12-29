package puzzle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import gameClient.Game;
import networking.Client;
import networking.Server;
import pong.PongGUI;
import pong.PongGameManager;
import puzzle.SinglePlayerLobby.FinishListener;
import snake.FontLibrary;
import snake.Help;
import snake.Snake;
import spaceInvaders.SpaceInvadersPanel;

public class MultiplePlayerLobby {
	JFrame frame;
	Font customFont;
	Font bigFont;
	Font medFont;
	GraphicsEnvironment ge;
	ButtonPanel jb;
	ButtonPanel jb1;
	JButton startButton;
	JTextField jtf;
	JTextField jtf1;
	int currentGameNum = 0;
	int numFinished = 0;
	Client client;
	Server server;
	Game currentGame;
	private boolean won;
	private long totalScore = 0;
	boolean isRegistered;
	

	private FontLibrary f;
	private Font font = FontLibrary.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 30);

	MultiplePlayerLobby(JFrame temp, boolean registered) {
		frame = temp;
		frame.getContentPane().removeAll();
		frame.getContentPane().setBackground(Color.black);
		//frame.setLayout(new GridLayout(1,2, 5, 5);
		this.isRegistered = registered;
		
		frame.revalidate();
		//JButton challengeButton = new JButton("Host a Match");
		try {
			// creating 3 fonts
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("kenvector_future_thin.ttf")).deriveFont(30f);
		
			bigFont = Font.createFont(Font.TRUETYPE_FONT, new File("kenvector_future_thin.ttf")).deriveFont(40f);
			medFont = Font.createFont(Font.TRUETYPE_FONT, new File("kenvector_future_thin.ttf")).deriveFont(30f);

			//mediumFont = customFont.deriveFont(20f);
			//bigFont = customFont.deriveFont(30f);
			ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("kenvector_future_thin.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		createGUI();
		addEvents();
	}
	
	private void createGUI() {
		JLabel tempLabel = new JLabel("Choose an option:");
		tempLabel.setFont(bigFont);
		tempLabel.setForeground(Color.green);
		jb = new ButtonPanel(new ImageIcon("images/puzzle/grey_button00.png"), "Host", customFont);
		jb1 = new ButtonPanel(new ImageIcon("images/puzzle/grey_button00.png"), "Join", customFont);
		
		tempLabel.setBorder( new EmptyBorder( 20, 20, 20, 20 ));
		tempLabel.setHorizontalAlignment(JLabel.CENTER);
		
		jb.setBorder( new EmptyBorder( 3, 3, 3, 3 ));
		jb1.setBorder( new EmptyBorder( 3, 3, 3, 3 ));
		//JButton joinButton = new JButton("Join a Match");
		JPanel tempPanel = new JPanel();	
		tempPanel.setLayout(new GridLayout(0,2,30,30));
		tempPanel.setBorder( new EmptyBorder( 150, 50, 150, 50 ));

		
		tempPanel.add(jb);
		tempPanel.add(jb1);
		tempPanel.setOpaque(false);
		JPanel tempLabelPanel = new JPanel();
		tempLabelPanel.setLayout(new BoxLayout(tempLabelPanel, BoxLayout.X_AXIS));
		tempLabelPanel.add(Box.createGlue());
		tempLabelPanel.add(tempLabel);
		tempLabelPanel.add(Box.createGlue());
		tempLabelPanel.setOpaque(false);
		frame.add(tempLabelPanel, BorderLayout.NORTH);
		frame.add(tempPanel, BorderLayout.CENTER);
		frame.repaint();
		
		System.out.println("repainting");
		frame.setSize(700,500);
		frame.setLocation(100,100);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void addEvents() {
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open a host gui");
				hostGUI();
			}
		});
		jb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open a join gui");
				joinGUI();
			}
		});
		
		if(FirstWindows.aboutMenuItem.getActionListeners().length==0){
		FirstWindows.aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JDialog jd = new JDialog();
				jd.setTitle("LeaderBoard");
				jd.setLocationRelativeTo(null);
				jd.setSize(200, 300);
				jd.setModal(true);

				JTabbedPane tab = new JTabbedPane();
				Map<String, Integer> map =null;
				if(currentGameNum==1){
					map = FirstWindows.client.getSliderLeader();
				}else if(currentGameNum==2){
					map = FirstWindows.client.getSnakeLeader();
				}else if(currentGameNum==3){
					map = FirstWindows.client.getSpaceLeader();
				}else if(currentGameNum==4){
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
				tab.add("current", scroll);
				}
				
				Map<String, Integer> total_map =null;
				total_map = FirstWindows.client.getLeader();
				if(total_map!=null){
				String[] head2 = new String[2];
				head2[0] = "name";
				head2[1] = "score";
				DefaultTableModel model2 = new DefaultTableModel(head2, 0);
				for (Map.Entry<String, Integer> entry : total_map.entrySet())
				{
//				    System.out.println(entry.getKey() + "/" + entry.getValue());
					Object[] o = {entry.getKey(), entry.getValue()};
					model2.addRow(o);
				}
				JTable table2 = new JTable(model2);
				table2.setAutoCreateRowSorter(true);
				JScrollPane scroll2 = new JScrollPane(table2);
				tab.add("total", scroll2);
				}
				jd.add(tab);
				jd.setVisible(true);
			}
			});
		}
		if(FirstWindows.helpItem.getActionListeners().length==0){
    	FirstWindows.helpItem.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent ae) {
    			if(currentGameNum==1){
        			Help helpWindow = new Help("images/puzzle/helpPuzzle.txt");
        			helpWindow.display();
				}else if(currentGameNum==2){
	    			Help helpWindow = new Help("images/snake/help");
	    			helpWindow.display();
				}else if(currentGameNum==3){
//	    			Help helpWindow = new Help("images/puzzle/helpPuzzle.txt");
//	    			helpWindow.display();
				}else if(currentGameNum==4){
//	    			Help helpWindow = new Help("images/puzzle/helpPuzzle.txt");
//	    			helpWindow.display();
				}
    		}
    	});
		}
		
	}
	private void hostGUI() {
		frame.getContentPane().removeAll();
		frame.getContentPane().setBackground(Color.BLACK);
		JPanel back = new JPanel();
		back.setOpaque(false);
		frame.add(back);
		JLabel jl = new JLabel("Port");
		jl.setFont(font);
		jl.setForeground(Color.GREEN);
		back.add(jl);

		
		JPanel jp = new JPanel();
		jp.setOpaque(false);
		JPanel jp1 = new JPanel();
		jp.setOpaque(false);
		// LabelPanel jp2 = new LabelPanel(new ImageIcon("sorry.png"), "");
		JLabel jp2 = new JLabel("Port:");
		jp2.setFont(bigFont);
		jp2.setForeground(Color.GREEN);
		jp2.setOpaque(false);
		jtf = new JTextField(5);
		jtf.setFont(bigFont);
		jtf.setBackground(Color.gray);
		ImageIcon buttonIcon = new ImageIcon("images/snake/grey_button01.png");
		JLabel startLabel = new JLabel("Start");
		Font font1 = FontLibrary.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 18);
		startLabel.setFont(font1);
		startLabel.setIcon(buttonIcon);
		startLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		startLabel.setVerticalTextPosition(SwingConstants.CENTER);
		ImageIcon blah = new ImageIcon("images/puzzle/grey_button00.png");
		startButton = new JButton(blah);
		startButton.setBorder(null);
		startButton.add(startLabel);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.setOpaque(false);
		//ButtonPanel jb = new ButtonPanel(new ImageIcon("buttons/grey_button00.png"), "Start");

		jp.add(jp2);
		jp.add(jtf);

		//Background jp_temp = new Background();
		JPanel jp_temp = new JPanel();
		jp_temp.setOpaque(false);
		jp_temp.setLayout(new BoxLayout(jp_temp, BoxLayout.Y_AXIS));

		frame.add(jp_temp);

		jp.setBackground(new Color(0, 0, 0, 0));
		jp.setOpaque(true);
		jp1.setBackground(new Color(0, 0, 0, 0));
		jp1.setOpaque(true);
		jp_temp.add(Box.createGlue());
		jp_temp.add(Box.createGlue());
		jp_temp.add(jp);
		jp_temp.add(Box.createGlue());
		jp_temp.add(jp1);
		jp_temp.add(Box.createGlue());

		JPanel temp = new JPanel();
		temp.setLayout(new BorderLayout());
		temp.add(startButton, BorderLayout.CENTER);
		jp1.add(temp);
		

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String hostPort = jtf.getText();
				System.out.println("Here! Port is: " + hostPort);
				//TODO: open a game server with this port
				try {
					server = new Server(Integer.parseInt(hostPort), MultiplePlayerLobby.this);
					//server.start();
					client = new Client(Integer.parseInt(hostPort), "localhost", MultiplePlayerLobby.this, 0);
					//client.start();
					startButton.setEnabled(false);
					waitWindow();
				}
				catch (Exception e1) {
					System.out.println(e1.getMessage());
					System.out.println("invalid inputs");
					return;
					
				}
				
			}
		});

		temp.setBackground(new Color(237, 237, 237, 237));
		startButton.setBackground(new Color(237, 237, 237, 237));
		frame.revalidate();
		frame.repaint();
	}
	
	private void joinGUI() {
		frame.getContentPane().removeAll();
		frame.getContentPane().setBackground(Color.black);
		JPanel bg = new JPanel();
		frame.add(bg);

		JPanel ip = new JPanel();
		JLabel ip_label = new JLabel("IP: ");
		ip_label.setForeground(Color.GREEN);
		ip_label.setFont(bigFont);

		jtf1 = new JTextField(10);
		jtf1.setFont(bigFont);
		jtf1.setBackground(Color.gray);

		ip.add(ip_label);
		ip.add(jtf1);

		JLabel jl = new JLabel("Port");
		jl.setForeground(Color.green);
		bg.add(jl);

		JPanel jp = new JPanel();
		JPanel jp1 = new JPanel();
		// LabelPanel jp2 = new LabelPanel(new ImageIcon("sorry.png"), "");
		JLabel jp2 = new JLabel("Port:");
		jp2.setForeground(Color.GREEN);
		jp2.setFont(bigFont);

		jtf = new JTextField(5);
		jtf.setFont(bigFont);
		jtf.setBackground(Color.gray);

		//ButtonPanel jb = new ButtonPanel(new ImageIcon("buttons/grey_button00.png"), "Connect");

		
		ImageIcon buttonIcon = new ImageIcon("images/puzzle/grey_button00.png");
		JLabel startLabel = new JLabel("Start");
		startLabel.setIcon(buttonIcon);
		Font font1 = FontLibrary.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 18);
		startLabel.setFont(font1);
		startLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		startLabel.setVerticalTextPosition(SwingConstants.CENTER);
		ImageIcon blah = new ImageIcon("images/puzzle/grey_button00.png");
		startButton = new JButton(blah);
		startButton.setBorder(null);
		startButton.add(startLabel);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.setOpaque(false);
		jp.add(jp2);
		jp.add(jtf);

		JPanel jp_temp = new JPanel();
		jp_temp.setOpaque(false);
		jp_temp.setLayout(new BoxLayout(jp_temp, BoxLayout.Y_AXIS));

		frame.add(jp_temp);

		jp.setBackground(new Color(0, 0, 0, 0));
		jp.setOpaque(true);
		ip.setBackground(new Color(0, 0, 0, 0));
		ip.setOpaque(true);

		jp1.setBackground(new Color(0, 0, 0, 0));
		jp1.setOpaque(true);
		jp_temp.add(Box.createGlue());
		jp_temp.add(Box.createGlue());
		jp_temp.add(ip);
		jp_temp.add(jp);
		jp_temp.add(Box.createGlue());
		jp_temp.add(jp1);
		jp_temp.add(Box.createGlue());

		JPanel temp = new JPanel();
		temp.setLayout(new BorderLayout());
		temp.add(startButton, BorderLayout.CENTER
				);
		jp1.add(temp);

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int port = Integer.parseInt(jtf.getText());
				}
				catch (NumberFormatException nfe) {
					System.out.println("Invalid port input");
					return;
				}
				startButton.setEnabled(false);
				int port = Integer.parseInt(jtf.getText());
				String ip1 = jtf1.getText();
				System.out.println("port is :" + port);
				System.out.println("ip is:" + ip1);
				client = new Client(port, ip1, MultiplePlayerLobby.this, 1);
				//client.start();
				
			}
		});

		temp.setBackground(new Color(237, 237, 237, 237));
		startButton.setBackground(new Color(237, 237, 237, 237));
		frame.revalidate();
		frame.repaint();
		
		
	}
	
	public void goToNextGame() {
		//make a constants thing
		if (currentGameNum == 0) {
			currentGameNum ++;
			//System.out.println("Client id before puzzle is: " + client.getId());
			//System.out.println("Starting the puzzle multiplayer game");
			//start the slider game
			currentGame = new MenuWindow(frame, isRegistered, new MultiFinishedListener(), false);
		}
		else if (currentGameNum == 1) {
			//the current game was puzzle, so move onto snake
			//System.out.println("Client id before snake is: " + client.getId());
			currentGameNum ++;
			currentGame = new Snake(new MultiFinishedListener(), frame);
	    	//snake.setJMenuBar(menuBar);
		}
		else if (currentGameNum == 2) {
			//current game was snake, so move onto space invaders
			//System.out.println("Client id before space is: " + client.getId());
			//System.out.println("Going to Space Invaders");
			currentGameNum ++;
			frame.getContentPane().removeAll();
			SpaceInvadersPanel temp = new SpaceInvadersPanel(new MultiFinishedListener());
			currentGame = temp.getGame();
			frame.add(temp);
			frame.revalidate();
			frame.repaint();
		}
		else if (currentGameNum == 3) {
			
			
			PongGUI pGUI ;
			if(client.getID()==0)
			{
				pGUI = new PongGUI(false,true,false,true,new MultiFinishedListener());
			}
			else
			{
				pGUI = new PongGUI(false,true,false,false,new MultiFinishedListener());
			}
			if(server!=null)
			{
					PongGameManager pgmServer = new PongGameManager(); 
					server.setPongGameManager(pgmServer);
					Thread th = new Thread(pgmServer);
					th.start(); 

			}
			client.setPongGameManager(pGUI.getGameManager());
			currentGame = pGUI.getGameManager();
			currentGameNum ++;

			pGUI.setClient(client);
			pGUI.start(); 
			frame.getContentPane().removeAll();
			frame.revalidate();
			frame.setSize(1200, 1200);
			frame.add(pGUI);
			frame.repaint();
		}
		else {
			//display the winner window
			//System.out.println("The series of games are over!");
			//System.out.println("Client id is: " + client.getId());
			Long temp1 = (Long) client.getId();
			Long temp2 = (Long) totalScore;
			Long [] scores = {temp1, temp2};
			client.sendObject(scores);
			//check to see how has the higher score
			//update the won variable
		}
	}
	public void setWinnerLoserFrame(long winner, long winnerScore, long loserScore) {
		frame.getContentPane().removeAll();
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));
		JLabel temp1 = new JLabel();
		JLabel temp2 = new JLabel();
		JButton backButton = new JButton("Back to mode selection");
		System.out.println("winner id is: " + winner);
		if (winnerScore == -1) {
			temp1.setText("Connection Lost! Game Over!");
		}
		else if (winnerScore == loserScore) {
			temp1.setText("You tied! With score: " + totalScore);
			temp2.setText("Opponent's score: "+ loserScore);
		}
		else if (winnerScore == totalScore) {
			temp1.setText("You won! With score: " + totalScore);
			temp2.setText("Opponent's score: "+ loserScore);
		}
		else {
			temp1.setText("You lost! With score: " + totalScore);
			temp2.setText("Opponent's score: "+ winnerScore);
		}
		temp1.setHorizontalAlignment(JLabel.CENTER);
		temp1.setFont(bigFont);
		temp1.setForeground(Color.WHITE);
		temp1.setOpaque(false);
		temp2.setHorizontalAlignment(JLabel.CENTER);
		temp2.setFont(bigFont);
		temp2.setForeground(Color.WHITE);
		temp2.setOpaque(false);
		tempPanel.add(Box.createGlue());
		tempPanel.add(temp1);
		tempPanel.add(temp2);
		tempPanel.add(Box.createGlue());
		tempPanel.setOpaque(false);
		tempPanel.add(backButton);
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (server!= null) {
					server.reset();
				}
				if (client != null) {
					client.setKeepGoing(false);
				}
				server = null;
				client = null;
				new SingleMultiWindow(frame, isRegistered);
			}
			
		});
		frame.setLayout(new GridBagLayout());
		frame.add(tempPanel);
		frame.revalidate();
		frame.repaint();
	}
	
	public void finishedGame() {
		numFinished ++;
		//System.out.println("Finished is now: " + numFinished);
		if (numFinished == 2) {
			//System.out.println("Going to the next game");
			numFinished = 0;
			goToNextGame();
		}
	}
	
	public class MultiFinishedListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//get the score
			//put a waiting window
			totalScore += currentGame.getScore();
			
			int result = (int) currentGame.getScore();
			Lock lock = new ReentrantLock();
			lock.lock();
			//System.out.println("finish listener+ " + result + " " + MultiplePlayerLobby.this.currentGameNum);
			if(MultiplePlayerLobby.this.currentGameNum==1){
				FirstWindows.client.update_slider_score(result);
			}else if(MultiplePlayerLobby.this.currentGameNum==2){
				FirstWindows.client.update_snake_score(result);
			}else if(MultiplePlayerLobby.this.currentGameNum==3){
				FirstWindows.client.update_space_score(result);
			}else if(MultiplePlayerLobby.this.currentGameNum==4){
				FirstWindows.client.update_pong_score(result);
			}
			lock.unlock();
			
			client.sendObject("--Done");
			frame.getContentPane().removeAll();
			JLabel temp = new JLabel("Waiting for the other player to finish");
			temp.setHorizontalAlignment(JLabel.CENTER);
			temp.setForeground(Color.WHITE);
			temp.setFont(medFont);
			frame.add(temp);

			frame.revalidate();
			frame.repaint();
		}
	}
	private void waitWindow() {
		frame.getContentPane().removeAll();
		JLabel temp = new JLabel("Waiting for a player to join.");
		temp.setHorizontalAlignment(JLabel.CENTER);
		temp.setFont(medFont);
		temp.setForeground(Color.WHITE);
		frame.add(temp);
		frame.revalidate();
		frame.repaint();
	}
}
