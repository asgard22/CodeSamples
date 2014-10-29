package csci201assignment3.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;

public class AboutButton extends JMenuItem implements ActionListener
{
	private CalendarGraphics calGraphics;
	private boolean aboutWindowClosed;
	
	public AboutButton(CalendarGraphics calGraphics)
	{
		super("About");
		aboutWindowClosed = true;
		this.addActionListener(this);
		this.calGraphics = calGraphics;
		this.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.setBackground(Color.orange);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (aboutWindowClosed)
		{
			AboutGraphics aboutGraphics = new AboutGraphics();
			aboutGraphics.setSize(500,500);
			WindowListener exitListener = new WindowAdapter() {
	            public void windowClosing(WindowEvent e) 
	            {
	            	aboutWindowClosed = true;
	            }
	        };
	        aboutGraphics.addWindowListener(exitListener);
	        aboutGraphics.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			aboutGraphics.setResizable(false);
			aboutWindowClosed = false;
			aboutGraphics.setVisible(true);
		}
	}
}
