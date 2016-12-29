package gameClient;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HostSelectPanel extends JPanel {
	private static final long serialVersionUID = 1;
	
	public HostSelectPanel() {
		super();
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
//		gbc.ipadx = titleImage.getWidth(null);
//		gbc.ipady = titleImage.getHeight(null);
		gbc.insets = new Insets(40,40,40,40);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridy = 1;
		gbc.gridx = 1;
		JLabel selectLabel = new JLabel("Host or Join?");
		selectLabel.setFont(new Font("Helvetica", Font.BOLD, 22));
		selectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(selectLabel,gbc);
		gbc.ipadx = 100;
		gbc.ipady = 25;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridy = 2;
		gbc.gridx = 1;
		gbc.gridwidth = 1;
		add(new JButton("Host"),gbc);
		gbc.gridx = 2;
		add(new JButton("Join"), gbc);
	}
}
