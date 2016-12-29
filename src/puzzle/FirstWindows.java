package puzzle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import database.DataClient;
import snake.FontLibrary;
import snake.Help;

public class FirstWindows {
	private JFrame frame;
	private JButton loginButton;
	private JButton createUserButton;
	private JButton playButton;
	private JLabel loginLabel, createUserLabel, playLabel;
	private JLabel logInInfoLabel;
	private JLabel welcomeLabel;
	private ImageIcon buttonIcon;
	private Image background;
	private FontLibrary f;
	private Font welcomeFont, buttonFont, playFont;
	private BufferedImage bg;
	private MainPanel mainPanel;
	public boolean isRegisteredUser;
	public static DataClient client;
	public static JMenuItem aboutMenuItem;
	public static JMenuItem helpItem;

	public FirstWindows() {
		initializeGUI();
		createEvents();
	}

	public void initializeGUI() {
		client = new DataClient("localhost", 6789);
		frame = new JFrame();
		buttonIcon = new ImageIcon("images/snake/grey_button01.png");
		buttonFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 22);
		mainPanel = new MainPanel();
		
		frame.setSize(900, 600);
		//frame.add(middlePanel, BorderLayout.CENTER);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel.setOpaque(false);
		frame.add(mainPanel);
		frame.setVisible(true);
		
		


		JMenuBar jmb = new JMenuBar();
		JMenu helpMenu = new JMenu("LeaderBoard");
		aboutMenuItem = new JMenuItem("LeaderBoard");
		helpMenu.add(aboutMenuItem);
		jmb.add(helpMenu);
		helpItem = new JMenuItem("Help");
    	helpItem.setMnemonic('H');
    	helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
		jmb.add(helpItem);
		frame.setJMenuBar(jmb);
	}
	
	private class MainPanel extends JPanel {
		
		public MainPanel() {
			try {
				bg = ImageIO.read(new File("images/main/background.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			setLayout(new BorderLayout());
			
			welcomeFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 80);
			welcomeLabel = new JLabel("The Gauntlet");
			welcomeLabel.setFont(welcomeFont);
			welcomeLabel.setOpaque(false);
			welcomeLabel.setForeground(Color.CYAN);
			loginLabel = new JLabel("Log In");
			loginLabel.setIcon(buttonIcon);
			
			loginLabel.setFont(buttonFont);
			loginLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			loginLabel.setVerticalTextPosition(SwingConstants.CENTER);
			loginButton = new JButton(buttonIcon);
			loginButton.add(loginLabel);
			loginButton.setBorderPainted(false);
			loginButton.setContentAreaFilled(false);
			loginButton.setFocusPainted(false);
			
			playLabel = new JLabel("Enter the Arcade!");
			playFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 16);
			playLabel.setIcon(buttonIcon);
			playLabel.setFont(playFont);
			playLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			playLabel.setVerticalTextPosition(SwingConstants.CENTER);
			playButton = new JButton(buttonIcon);
			playButton.add(playLabel);
			playButton.setBorderPainted(false);
			playButton.setContentAreaFilled(false);
			playButton.setFocusPainted(false);
			
			createUserLabel = new JLabel("Register");
			createUserLabel.setFont(buttonFont);
			createUserLabel.setIcon(buttonIcon);
			createUserLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			createUserLabel.setVerticalTextPosition(SwingConstants.CENTER);
			createUserButton = new JButton(buttonIcon);
			createUserButton.add(createUserLabel);
			createUserButton.setBorderPainted(false);
			createUserButton.setContentAreaFilled(false);
			createUserButton.setFocusPainted(false);
			
			logInInfoLabel = new JLabel("You are currently not logged in!");
			Font loginFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 24);
			logInInfoLabel.setFont(loginFont);
			logInInfoLabel.setForeground(Color.RED);
			
			JPanel northPanel1 = new JPanel();
			northPanel1.setLayout(new BoxLayout(northPanel1, BoxLayout.X_AXIS));
			northPanel1.add(Box.createGlue());
			northPanel1.add(welcomeLabel);
			northPanel1.add(Box.createGlue());
			JPanel northPanel2 = new JPanel();
			northPanel2.setLayout(new BoxLayout(northPanel2, BoxLayout.X_AXIS));
			northPanel2.add(Box.createGlue());
			northPanel2.add(logInInfoLabel);
			northPanel2.add(Box.createGlue());
			JPanel northPanel = new JPanel();
			northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
			northPanel.add(Box.createGlue());
			northPanel.add(northPanel1);
			northPanel.add(northPanel2);
			northPanel.add(Box.createGlue());
			JPanel centerPanel = new JPanel();
			centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
			centerPanel.add(Box.createGlue());
			centerPanel.add(playButton);
			centerPanel.add(Box.createGlue());
			JPanel southPanel = new JPanel();
			southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
			southPanel.add(Box.createGlue());
			southPanel.add(loginButton);
			southPanel.add(Box.createGlue());
			southPanel.add(createUserButton);
			southPanel.add(Box.createGlue());
			northPanel1.setOpaque(false);
			northPanel2.setOpaque(false);
			northPanel.setOpaque(false);
			centerPanel.setOpaque(false);
			southPanel.setOpaque(false);
			add(northPanel, BorderLayout.NORTH);
			add(centerPanel, BorderLayout.CENTER);
			add(southPanel, BorderLayout.SOUTH);
			
			setBackground(frame.getBackground());
			setOpaque(false);
			/*JPanel northPanel = new JPanel();
			northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
			northPanel.add(welcomeLabel); 
			northPanel.add(logInInfoLabel);
			northPanel.setOpaque(tr
			ue);
			northPanel.setBackground(frame.getBackground());
			JPanel southPanel = new JPanel();
			southPanel.add(loginButton);
			southPanel.add(createUserButton);
			JPanel middlePanel = new JPanel();
			middlePanel.add(playButton);
			add(northPanel, BorderLayout.NORTH);
			add(southPanel, BorderLayout.SOUTH);
			add(middlePanel, BorderLayout.CENTER);*/
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	

	private class LogInWindow extends JDialog {
		private static final long serialVersionUID = 1;
		JTextField userNameTF;
		JLabel userNameLabel, passwordLabel, loginLabel;
		JTextField passwordTF;
		JButton loginButton;
		JLabel userStatus;
		JPanel userStatusPanel, temp, user, pass;
		private Font loginFont, errorFont;
		private JPasswordField passwordF;

		public LogInWindow() {
			super();
			initializeGUI();
			addEvents();
		}

		public void initializeGUI() {
			setSize(400, 400);
			setLocation(100, 100);
			setLocationRelativeTo(frame);
			this.getRootPane().setOpaque(false);
			this.getContentPane().setBackground(Color.black);
			this.setBackground(Color.black);
			errorFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.PLAIN, 15);
			loginFont = FontLibrary.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 18);
			userNameTF = new JTextField(18);
			userNameTF.setFont(buttonFont);
			passwordF = new JPasswordField(18);
			passwordF.setEchoChar('*');
			passwordF.setFont(buttonFont);
			userNameLabel = new JLabel("User Name: ");
			userNameLabel.setFont(loginFont);
			userNameLabel.setForeground(Color.GREEN);
			passwordLabel = new JLabel("Password: ");
			passwordLabel.setFont(loginFont);
			passwordLabel.setForeground(Color.GREEN);
			loginLabel = new JLabel("Log In");
			loginLabel.setFont(loginFont);
			loginLabel.setIcon(buttonIcon);
			loginLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			loginLabel.setVerticalTextPosition(SwingConstants.CENTER); 
			loginButton = new JButton(buttonIcon);
			loginButton.add(loginLabel);
			loginButton.setBorderPainted(false);
			loginButton.setContentAreaFilled(false);
			loginButton.setFocusPainted(false);
			userStatus = new JLabel(" ");
			userStatus.setFont(errorFont);
			
			temp = new JPanel();
			temp.setOpaque(false);
			user = new JPanel();
			user.setOpaque(false);
			user.add(userNameLabel);
			user.add(userNameTF);
			pass = new JPanel();
			pass.setOpaque(false);
			pass.add(passwordLabel);
			pass.add(passwordF);
			temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
			userStatusPanel = new JPanel();
			userStatusPanel.setOpaque(false);
			userStatusPanel.setLayout(new BoxLayout(userStatusPanel, BoxLayout.X_AXIS));
			userStatusPanel.add(Box.createGlue());
			userStatusPanel.add(userStatus);
			userStatusPanel.add(Box.createGlue());
			JPanel jp = new JPanel();
			jp.setLayout(new FlowLayout(FlowLayout.RIGHT));
			jp.add(loginButton);
			jp.setOpaque(false);
			temp.add(jp);
			temp.add(Box.createGlue());
			temp.add(userStatusPanel);
			temp.add(Box.createGlue());
			temp.add(user);
			temp.add(pass);
			temp.add(Box.createGlue());
			temp.add(jp);
			temp.add(Box.createGlue());
			add(temp);
		}

		public void addEvents() {
			
			loginButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					String userName = userNameTF.getText();
					String password = String.valueOf(passwordF.getPassword());
					String[] combo = new String[2];
					
					combo[0] = userName;
					combo[1] = password;
					boolean result = client.checkname_pw(combo);
					

					// use this info to get credientials in the central server
					// send data to central server
					
//					LogInInfo info = new LogInInfo(userName, password, result);

					//send this info
					
					
					// read the info response back from server
					
					// update this boolean if login was correct
					if (result) {
						isRegisteredUser = true;
						//change the 
						// close this JDialog
						logInInfoLabel.setText("You are now signed in as " + userName);
						LogInWindow.this.setVisible(false);
					} else {
						
						userStatus.setText("Username & Password combination Invalid!");
						Font errorFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.PLAIN, 15);
						userStatus.setFont(errorFont);
						userStatus.setForeground(Color.red);
						//userStatus.repaint();
						//repaint();
						userStatusPanel.repaint();
						userStatusPanel.revalidate();
					}
				}
			});
			//setModal(true);
			setVisible(true);
		}
	}
	
	private class CreateUserWindow extends JDialog {
		private static final long serialVersionUID = 1;
		JTextField userNameTF;
		JLabel userNameLabel;
		JLabel passwordLabel;
		JLabel registerLabel;
		JPanel userStatusPanel, temp, user, pass;
		JTextField passwordTF;
		JButton createButton;
		JLabel userStatus;
		private Font errorFont;
		private JPasswordField passwordF;
		private Font createUserFont;
		// revalidate();

		public CreateUserWindow() {
			super();
			
			initializeGUI();
			addEvents();
		}

		public void initializeGUI() {
			setSize(400, 400);
			setLocation(100, 100);
			setLocationRelativeTo(frame);
			this.getRootPane().setOpaque(false);
			this.getContentPane().setBackground(Color.black);
			this.setBackground(Color.black);

			createUserFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 18);
			userNameTF = new JTextField(18);
			userNameTF.setFont(buttonFont);
			passwordF = new JPasswordField(18);
			passwordF.setEchoChar('*');
			passwordF.setFont(buttonFont);
			//passwordTF = new JTextField(20);
			//passwordTF.setFont(createUserFont);
			errorFont = f.getFont("images/snake/kenvector_future_thin.ttf", Font.PLAIN, 15);
			userNameLabel = new JLabel("User Name: ");
			userNameLabel.setFont(createUserFont);
			userNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			userNameLabel.setVerticalTextPosition(SwingConstants.CENTER);
			userNameLabel.setOpaque(false);
			userNameLabel.setForeground(Color.GREEN);
			passwordLabel = new JLabel("Password: ");
			passwordLabel.setFont(createUserFont);
			passwordLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			passwordLabel.setVerticalTextPosition(SwingConstants.CENTER);
			passwordLabel.setOpaque(false);
			passwordLabel.setForeground(Color.green);
			registerLabel = new JLabel("Register");
			registerLabel.setFont(createUserFont);
			registerLabel.setIcon(buttonIcon);
			registerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			registerLabel.setVerticalTextPosition(SwingConstants.CENTER);
			registerLabel.setOpaque(false);
			createButton = new JButton(buttonIcon);
			createButton.add(registerLabel);
			createButton.setBorderPainted(false);
			createButton.setContentAreaFilled(false);
			createButton.setFocusPainted(false);
			createButton.setOpaque(false);
			userStatus = new JLabel(" ");
			userStatus.setFont(errorFont);
			temp = new JPanel();
			temp.setOpaque(false);
			user = new JPanel();
			user.setOpaque(false);
			user.add(userNameLabel);
			user.add(userNameTF);
			pass = new JPanel();
			pass.setOpaque(false);
			pass.add(passwordLabel);
			pass.add(passwordF);
			temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
			temp.add(Box.createGlue());
			userStatusPanel = new JPanel();
			userStatusPanel.setOpaque(false);
			userStatusPanel.setLayout(new BoxLayout(userStatusPanel, BoxLayout.X_AXIS));
			userStatusPanel.add(Box.createGlue());
			userStatusPanel.add(userStatus);
			userStatusPanel.add(Box.createGlue());
			temp.add(userStatusPanel);
			temp.add(Box.createGlue());
			temp.add(user);
			temp.add(pass);
			temp.add(Box.createGlue());
			JPanel jp = new JPanel();
			jp.setLayout(new FlowLayout(FlowLayout.RIGHT));
			jp.add(createButton);
			jp.setOpaque(false);
			temp.add(jp);
			temp.add(Box.createGlue());
			add(temp);
		}

		public void addEvents() {
			
			createButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					String userName = userNameTF.getText();
					//String password = passwordTF.getText();
					String password = String.valueOf(passwordF.getPassword());
					// use this info to get credientials in the central server
					// send data to central server
					String[] combo = new String[3];
					combo[0] = userName;
					combo[1] = password;
					combo[2] = "useless";
					boolean result = client.add_user(combo);
//					LogInInfo userCreate = new LogInInfo(userName, password, result);
					
					
					//read back the response
					
					
					if (result) {
						isRegisteredUser = true;
						logInInfoLabel.setText("You are now signed in as " + userName);
						CreateUserWindow.this.setVisible(false);
						// close this JDialog
						

					} else {
						
						userStatus.setText("Username already taken! Try a new one.");
						userStatus.setFont(errorFont);
						userStatus.setForeground(Color.red);
						//userStatus.repaint();
						//repaint();
						userStatusPanel.repaint();
						userStatusPanel.revalidate();
					}
				}
			});
			setModal(true);
			setVisible(true);
		}

	}

	public void createEvents() {
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				new LogInWindow();
				// open a Jdialog requesting log-in information;
				// update the JLabel if the log-in info was correct;
				// else, update the JDialog to display label text saying
				// there was incorrect credentials
				// set registered user to be true
			}
		});

		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// remove content of this window, draw new components on
				new SingleMultiWindow(frame, isRegisteredUser);
				//new SingleMultiWindow(frame, false);

			}
		});

		createUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// open a JDialog with text fields to create a user
				// if username isn't taken, update the server with log-in info
				// change the log-in info Label
				new CreateUserWindow();
			}
		});
	}
	
	public static void main(String [] args) { 
		  new FirstWindows(); 
	  }
	 

}
