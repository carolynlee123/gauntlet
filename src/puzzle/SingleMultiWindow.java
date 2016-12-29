package puzzle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import snake.FontLibrary;

public class SingleMultiWindow {
	JFrame windowFrame;
	JButton multiPlayerButton;
	JButton singlePlayerButton;
	JLabel selectionLabel;

	private FontLibrary f;
	private Font font;
	private ImageIcon buttonIcon;
	private JLabel mpLabel, spLabel;
	Boolean registered;
	public SingleMultiWindow(JFrame frame, Boolean registered) {
		windowFrame = frame;
		//windowFrame.setLayout(new BorderLayout());
		this.registered = registered;
		initializeGUI();
		addEvents();
		
	}
	
	private void initializeGUI() {
		buttonIcon = new ImageIcon("images/snake/grey_button01.png");
		windowFrame.getContentPane().removeAll();
		windowFrame.getContentPane().setBackground(Color.black);
		windowFrame.revalidate();
		windowFrame.setLayout(new BorderLayout());
		font = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 30);
		selectionLabel = new JLabel ("Select a mode: ");
		selectionLabel.setFont(font);
		selectionLabel.setForeground(Color.GREEN);
		selectionLabel.setOpaque(false);
		selectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Font font1 = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 16);
		//font = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 25);
		mpLabel = new JLabel("Multiplayer");
		mpLabel.setFont(font1);
		mpLabel.setIcon(buttonIcon);

		mpLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		mpLabel.setVerticalTextPosition(SwingConstants.CENTER);
		multiPlayerButton = new JButton(buttonIcon);
		multiPlayerButton.add(mpLabel);
		multiPlayerButton.setFocusPainted(false);
		multiPlayerButton.setContentAreaFilled(false);
		multiPlayerButton.setBorderPainted(false);
		multiPlayerButton.setOpaque(false);

		spLabel = new JLabel("Singleplayer");
		spLabel.setFont(font1);
		spLabel.setIcon(buttonIcon);
		spLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		spLabel.setVerticalTextPosition(SwingConstants.CENTER);
		singlePlayerButton = new JButton (buttonIcon);
		singlePlayerButton.add(spLabel);
		singlePlayerButton.setFocusPainted(false);
		singlePlayerButton.setContentAreaFilled(false);
		singlePlayerButton.setBorderPainted(false);
		singlePlayerButton.setOpaque(false);
		
		
		Font customFont = null;
		try {
			// creating 3 fonts
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("kenvector_future_thin.ttf")).deriveFont(60f);
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("kenvector_future_thin.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		selectionLabel.setFont(customFont);
		Font font = f.getFont("images/snake/kenvector_future_thin.ttf", Font.BOLD, 18);
		
		JLabel multiLabel = new JLabel("Multiplayer");
		multiLabel.setFont(font);
		ImageIcon buttonIcon = new ImageIcon("images/puzzle/grey_button00.png");
		multiLabel.setIcon(buttonIcon);
		multiLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		multiLabel.setVerticalTextPosition(SwingConstants.CENTER);
		multiPlayerButton = new JButton(buttonIcon);
		multiPlayerButton.add(multiLabel);
		multiPlayerButton.setBorderPainted(false);
		multiPlayerButton.setContentAreaFilled(false);
		multiPlayerButton.setFocusPainted(false);
		
		JLabel singleLabel = new JLabel("Single Player");
		singleLabel.setFont(font);
		singleLabel.setIcon(buttonIcon);
		singleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		singleLabel.setVerticalTextPosition(SwingConstants.CENTER);
		singlePlayerButton = new JButton(buttonIcon);
		singlePlayerButton.add(singleLabel);
		singlePlayerButton.setBorderPainted(false);
		singlePlayerButton.setContentAreaFilled(false);
		singlePlayerButton.setFocusPainted(false);
		
		JPanel boxPanel = new JPanel();
		boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
		boxPanel.setOpaque(false);
		JPanel buttonPanels = new JPanel();
		buttonPanels.setLayout(new BoxLayout(buttonPanels, BoxLayout.X_AXIS));
		buttonPanels.setOpaque(false);
		buttonPanels.add(Box.createGlue());
		buttonPanels.add(singlePlayerButton);
		buttonPanels.add(Box.createGlue());
		buttonPanels.add(multiPlayerButton);
		buttonPanels.add(Box.createGlue());
		boxPanel.add(Box.createGlue());
		JPanel selectionPanel = new JPanel();
		selectionPanel.setOpaque(false);
		selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));
		selectionPanel.add(Box.createGlue());
		selectionPanel.add(selectionLabel);
		selectionPanel.add(Box.createGlue());
		boxPanel.add(selectionPanel);
		boxPanel.add(Box.createGlue());
		boxPanel.add(buttonPanels);

		windowFrame.add(boxPanel);

		//boxPanel.add(buttonPanels);
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
		jp.add(Box.createGlue());
		jp.add(selectionLabel);
		jp.add(Box.createGlue());
		jp.setOpaque(false);
		windowFrame.add(boxPanel, BorderLayout.SOUTH);
		windowFrame.add(jp);
		windowFrame.setSize(800,600);
		windowFrame.setLocation(100,100);
		windowFrame.setVisible(true);
		windowFrame.repaint();
		
	}
	private void addEvents() {
		singlePlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SinglePlayerLobby(windowFrame, registered);
			}
		});
		multiPlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MultiplePlayerLobby(windowFrame,registered);
			}
		});
	}
}
