package pong;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import networking.Client;


public class PongGUI extends JPanel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton forfeitButton;
	private JLabel p1Score;
	private JLabel p2Score;
	private JMenuBar jmb;
	private PongPlayField playField;
	private JPanel lowerPanel;
	private PongGameManager pgm; 

	private KeyboardAnimation rightMotion;
	private KeyboardAnimation leftMotion; 
	
	private JLabel rightPaddle; 
	private JLabel leftPaddle;
	
	private boolean singleMode, isNetworked, startServer, userRight; 
	
	public static enum GameStates
	{
		singleRight, singleLeft, localMultLeft, localMultRight, NetLeft, NetRight
	}
	private GameStates gs; 
	private ActionListener endingAction; 
	/*
	public PongGUI(GameStates gs, boolean isServer)
	{
		if(gs == GameStates.singleRight || gs==GameStates.singleLeft)
		{
			
		}
			
	}*/
	public PongGUI(boolean singleMode, boolean isNetworked, boolean startServer, boolean useRight, ActionListener endingAction)
	{
		//super("Pong");
		this.endingAction = endingAction; 
		this.singleMode= singleMode; 
		this.isNetworked = isNetworked;
		this.startServer = startServer;
		this.userRight = useRight; 
		initializeComponents();
		createGUI();
		addEvents();

	}
	public PongGUI(boolean singleMode, boolean isNetworked, boolean startServer, boolean useRight)
	{
		//super("Pong");
		this.singleMode= singleMode; 
		this.isNetworked = isNetworked;
		this.startServer = startServer;
		this.userRight = useRight; 
		initializeComponents();
		createGUI();
		addEvents();

	}
	private void initializeComponents() {
		
		p1Score = new JLabel("Player 1: 0");
		p2Score = new JLabel("Player 2: 0");
		
		// TODO Auto-generated method stub

		jmb = new JMenuBar();
		
		forfeitButton = new JButton("Forfeit");
		
		jmb.add(forfeitButton);
		

		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout());
		
		leftPaddle = new JLabel(new PaddleIcon());
		rightPaddle = new JLabel(new PaddleIcon());
		
		pgm = new PongGameManager( p1Score, p2Score, leftPaddle, rightPaddle,singleMode,userRight,endingAction);
	//	if(startServer) 
		{
				//pgm.setServer(80);
		}
		//if(isNetworked) pgm.setClient(80, "localhost", userRight);
		playField = new PongPlayField(pgm);
		pgm.setPlayField(playField);
		pgm.setGUI(this); 

		playField.setLayout(null);


	}
	public PongGameManager getGameManager()
	{
		return pgm; 
	}
	private void createGUI() {
		// TODO Auto-generated method stub
		setSize(PongGameManager.boardLength,PongGameManager.boardHeight);
		setLocation(700,50);
		
		playField.add(rightPaddle);
		playField.add(leftPaddle); 
		
		setLayout(new BorderLayout());
		add(playField,BorderLayout.CENTER);
		add(jmb, BorderLayout.NORTH);
		
		lowerPanel.add(p1Score);
		lowerPanel.add(p2Score);
		
		add(lowerPanel, BorderLayout.SOUTH);
		
		leftPaddle.setSize( leftPaddle.getPreferredSize());
		leftPaddle.setLocation(PongGameManager.leftPadX,0);
		
		rightPaddle.setSize(rightPaddle.getPreferredSize());
		rightPaddle.setLocation(PongGameManager.rightPadX, 0);

	}
	private void addEvents() {
		// TODO Auto-generated method stub
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFocusable(true);
		this.requestFocusInWindow();
		//this.addKeyListener(new PongGameKeyListener());
		forfeitButton.addActionListener(endingAction);
		
		forfeitButton.addActionListener(new ActionListener()  {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pgm.endGameNoClick(); 
			}
			
		});
		this.addFocusListener(new FocusListener()
		{
	
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
		
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				PongGUI.this.requestFocusInWindow();
		
			}

		});
		
		leftPaddle.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("updating left paddle to location "+leftPaddle.getLocation().y);
				//if(((!isNetworked) && (!singleMode))|| ((!isNetworked) && singleMode && (!userRight)) || (userRight))
				if((!isNetworked)  || (!userRight))
				{
				//System.out.println("updating left paddle location to: "+leftPaddle.getLocation().y);
					pgm.setLeftPaddle(leftPaddle.getLocation().y);
				}
			}

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}});
		rightPaddle.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
			//	System.out.println("updating right paddle to location "+rightPaddle.getLocation().y);

				if( ((!isNetworked) && (!singleMode))|| ((!isNetworked) && singleMode && userRight) || (userRight))
				{
					//System.out.println("updating right paddle location to: "+rightPaddle.getLocation().y);

					pgm.setRightPaddle(rightPaddle.getLocation().y); 
				}
			}

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}});
		if(((!isNetworked)&&(!singleMode)) || ((isNetworked)&&(!userRight)) ||(singleMode && (!userRight)))
		{
			System.out.println("adding left motions");
			leftMotion = new KeyboardAnimation(leftPaddle, 24);
			leftMotion.addAction("W", 0, -15);
			leftMotion.addAction("S", 0,15);
		}
		if(((!isNetworked)&&(!singleMode)) || ((isNetworked)&&(userRight)) ||(singleMode && (userRight)))

	//	if((!isNetworked) || userRight)
		{
			System.out.println("adding right motions");

			rightMotion = new KeyboardAnimation(rightPaddle, 24);
			rightMotion.addAction("UP", 0, -15);
			rightMotion.addAction("DOWN", 0, 15);
		}

	}

	public void setClient(Client cl)
	{
		pgm.setClient(cl);
	}

	public void start()
	{
		Thread th = new Thread(pgm); 
		th.start();
	}
	public static void main(String [] args)
	{
		//PongGUI pg = new PongGUI(true, true, true,false);
		PongGUI pg = new PongGUI(true, false, false,true);

		pg.setVisible(true);
		pg.start();
		JFrame jf = new JFrame();
		jf.setLayout(new BorderLayout());
		jf.add(pg, BorderLayout.CENTER); 
		jf.setVisible(true);
		jf.setSize(1200, 1100);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		PongGUI pg2 = new PongGUI(true, false, false,false);
		pg2.setVisible(true);
		
		JFrame jf2 = new JFrame();
		jf2.setLayout(new BorderLayout());
		jf2.add(pg2, BorderLayout.CENTER); 
		jf2.setVisible(true);
		jf2.setSize(1200, 700);
		jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		*/
	}
}
