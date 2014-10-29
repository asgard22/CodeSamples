package csci201assignment3.src;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CancelEventButton extends JButton implements ActionListener
{

	private AddEventGraphics eventGraphics;
	
	public CancelEventButton(AddEventGraphics eventGraphics)
	{
		super("Cancel");
		this.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.addActionListener(this);
		this.eventGraphics = eventGraphics;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		eventGraphics.deconstructEventGraphics();
		eventGraphics.getCalGraphics().updateHeaderElements(eventGraphics.getCalGraphics().getDateSelected());
		eventGraphics.getCalGraphics().updateCalendarElements(eventGraphics.getCalGraphics().getDateSelected(), false);
		eventGraphics.getCalGraphics().constructEventElements(eventGraphics.getCalGraphics().getDateSelected());
		eventGraphics.getCalGraphics().revalidate();
		eventGraphics.getCalGraphics().repaint();
	}

}
