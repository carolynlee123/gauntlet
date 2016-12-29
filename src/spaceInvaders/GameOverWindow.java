package spaceInvaders;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class GameOverWindow extends JPanel{
	private static final long serialVersionUID = 1L;
	private static final String NAME = "Name";
	private static final String SCORE = "Score";
	private static final Font TABLE_FONT = new Font("Arial", Font.BOLD, 20);
	private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 20);
	
	private JPanel all;
	private JLabel title1, PlayerScore;
	private JLabel filler1, filler2;
	private long score = 0;
	private JTable leaderboard;
	private JButton continueButton;
	public GameOverWindow(long score, ActionListener endActionListener){
		super();
		this.score = score;
		setSize(620, 480);
//		setLocation(400,400);
		init();
		createGUI();
		addEvent(endActionListener);
		//setVisible(true);
	}
	private void init(){
		setLayout(new BorderLayout());
		all = new JPanel();
		all.setLayout(new BoxLayout(all,BoxLayout.Y_AXIS));
		all.setBackground(Color.BLACK);
		title1 = new JLabel("Game Over");
		filler1 = new JLabel("  ");
		filler2 = new JLabel("  ");
		PlayerScore = new JLabel("Your Score: " + score);
	}
	public void createGUI(){
		filler1.setFont(new Font("Arial", Font.BOLD, 30));
		
		title1.setForeground(Color.RED);
		title1.setAlignmentX(Component.CENTER_ALIGNMENT);
		title1.setFont(new Font("Arial", Font.BOLD, 40));
		
		PlayerScore.setForeground(Color.GREEN);
		PlayerScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		PlayerScore.setFont(new Font("Arial", Font.BOLD, 35));
		
		filler2.setFont(new Font("Arial", Font.BOLD, 100));
		
		all.setBackground(Color.BLACK);
		all.add(filler1);
		all.add(title1);
		all.add(PlayerScore);
		
		// create the table
//		String[] nametemp = {"Albert", "Bern", "Chris", "Donny", "Ellie"};
//		Integer[] scoretemp = {50, 30, 45, 20, 100};
//		
//		String[] tabs = {NAME, SCORE};
//		Object[][] data = new Object[nametemp.length][2];
//		for(int i=0;i<nametemp.length;i++){
//			data[i][0] = nametemp[i];
//			data[i][1] = scoretemp[i];
//		}
//		DefaultTableModel tableModel = new DefaultTableModel(data, tabs);
//		leaderboard = new JTable(tableModel);
//		
//		// format the header text
//		JTableHeader leaderHeader = leaderboard.getTableHeader();
//		leaderHeader.setFont(TABLE_FONT);
//		leaderHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GREEN));
////		leaderboard.setRowMargin(10);
//		leaderboard.setRowHeight(20);
//		leaderHeader.setDefaultRenderer(new HeaderRenderer(leaderboard));
//		
//		// create the body of the table
//		leaderboard.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//		leaderboard.setOpaque(true);
//		leaderboard.setBackground(Color.BLACK);
//		leaderboard.setFont(TABLE_FONT);
//		leaderboard.setForeground(Color.GREEN);
//		leaderboard.getTableHeader().setBackground(Color.BLACK);
//		leaderboard.getTableHeader().setForeground(Color.GREEN);
//		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
//		leaderboard.getColumn(NAME).setCellRenderer(centerRenderer);
//		leaderboard.getColumn(SCORE).setCellRenderer(centerRenderer);
//		leaderboard.setShowGrid(false);
////		leaderboard.setShowVerticalLines(false);
////		leaderboard.setShowHorizontalLines(true);
//		JScrollPane jsp = new JScrollPane(leaderboard);
//		jsp.getViewport().setBackground(Color.BLACK);
//		jsp.setBorder(BorderFactory.createEmptyBorder());	// remove white border around leaderboard
//		all.add(jsp);
		
		// button to continue
		JPanel continuePanel = new JPanel();
		continuePanel.setOpaque(false);
		continuePanel.setLayout(new BorderLayout());
		continueButton = new JButton("Click Here to Continue");
		continueButton.setBackground(Color.BLACK);
		continueButton.setOpaque(true);
		continueButton.setBorderPainted(false);
		continueButton.setForeground(Color.GREEN);
		continueButton.setFont(BUTTON_FONT);
		continuePanel.add(continueButton, BorderLayout.CENTER);
		all.add(continuePanel);
		
		add(all, BorderLayout.CENTER);
		continueButton.requestFocus();
		
		setVisible(true);
	}
	public void addEvent(ActionListener endActionListener){
		//setDefaultCloseOperation(JPanel.EXIT_ON_CLOSE);
		continueButton.addActionListener(endActionListener);
	}
	
	
//	public static void main(String[] args) {
//		GameOverWindow test = new GameOverWindow();
//		test.setVisible(true);
//	}
	
	// code taken from stackoverflow: 
	// http://stackoverflow.com/questions/7493369/jtable-right-align-header/7494597#7494597
	private static class HeaderRenderer implements TableCellRenderer {

	    DefaultTableCellRenderer renderer;

	    public HeaderRenderer(JTable table) {
	        renderer = (DefaultTableCellRenderer)
	        		table.getTableHeader().getDefaultRenderer();
	        renderer.setHorizontalAlignment(JLabel.CENTER);
	    }

	    @Override
	    public Component getTableCellRendererComponent(
	        JTable table, Object value, boolean isSelected,
	        boolean hasFocus, int row, int col) {
	        return renderer.getTableCellRendererComponent(
	            table, value, isSelected, hasFocus, row, col);
	    }
	}
}
