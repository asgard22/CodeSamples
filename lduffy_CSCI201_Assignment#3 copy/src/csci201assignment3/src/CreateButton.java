package csci201assignment3.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.border.BevelBorder;

public class CreateButton extends JMenuItem implements ActionListener
{
	private CalendarGraphics calGraphics;
	
	public CreateButton(CalendarGraphics calGraphics)
	{
		super("Create");
		this.addActionListener(this);
		this.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.setBackground(Color.orange);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.calGraphics = calGraphics;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		calGraphics.deconstructHeaderElements();
		calGraphics.deconstructCalendarElements();
		calGraphics.deconstructEventElements();
		calGraphics.add(new AddEventGraphics(calGraphics));
		calGraphics.revalidate();
		calGraphics.repaint();
	}
}
