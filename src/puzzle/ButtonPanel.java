package puzzle;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
//import javax.swing.JPanel;

public class ButtonPanel extends JButton {
	
	private static final long serialVersionUID = 1;
	ImageIcon imageIcon;
	String text;
	Font font;
	int height;
	int width;
	int style;
    public ButtonPanel(ImageIcon icon, String text, Font font)
    {
        super();
        this.imageIcon = icon;
        this.text = text;
        this.font = font;
        
    }
    
    public ButtonPanel(ImageIcon icon, String text, Font font, int height, int width, int style)
    {
        super();
        this.imageIcon = icon;
        this.text = text;
        this.font = font;
        this.style = style;
        if (style == 1) {
        	this.height = height/10;
            this.width = width/10;
        }
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setPreferredSize(new Dimension(40, 40));
        g.drawImage(imageIcon.getImage(),0,0,getWidth(),getHeight(),this);
        //FontMetrics metrics = getFontMetrics(font);
        //float size = font.getSize2D();
        //float textWidth = metrics.stringWidth(text);
        float size = (float) Math.floor((getWidth())/10);
        g.setFont(font.deriveFont(size));
        g.setFont(font);
        g.drawString(text, (int) Math.floor(getWidth()/2.5), getHeight()/2);
    }
}


