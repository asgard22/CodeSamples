package csci201assignment3.src;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AddEventButton extends JButton implements ActionListener
{
	private AddEventGraphics eventGraphics;
	
	public AddEventButton(AddEventGraphics eventGraphics)
	{
		super("Add Event");
		this.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.addActionListener(this);
		this.eventGraphics = eventGraphics;
	}


 	public void actionPerformed(ActionEvent e) 
	{
		if (eventGraphics.getEventTitle().replaceAll("\\p{Z}","").equals(""))
		{
			JOptionPane warning = new JOptionPane();
			warning.showMessageDialog(eventGraphics.getCalGraphics(),
				    "You forgot to enter some of the event information!",
				    "Error",
				    JOptionPane.WARNING_MESSAGE);
		}
		else if (eventGraphics.getEventLocation().replaceAll("\\p{Z}","").equals(""))
		{
			JOptionPane warning = new JOptionPane();
			warning.showMessageDialog(eventGraphics.getCalGraphics(),
				    "You forgot to enter some of the event information!",
				    "Error",
				    JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			if (eventGraphics.getStartTime().getTimeInMillis() > eventGraphics.getEndTime().getTimeInMillis())
			{
				JOptionPane warning = new JOptionPane();
				warning.showMessageDialog(eventGraphics.getCalGraphics(),
					    "You're event ends before it starts, try again!",
					    "Error",
					    JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				Event someEvent = new Event(eventGraphics.getEventTitle(), eventGraphics.getEventLocation(), eventGraphics.getStartTime(), eventGraphics.getEndTime());
				System.out.println(someEvent.getStartTime());
				System.out.println(someEvent.getEndTime());
				eventGraphics.getCalGraphics().addEvent(someEvent);
				eventGraphics.deconstructEventGraphics();
				eventGraphics.getCalGraphics().updateHeaderElements(eventGraphics.getCalGraphics().getDateSelected());
				eventGraphics.getCalGraphics().updateCalendarElements(eventGraphics.getCalGraphics().getDateSelected(), false);
				eventGraphics.getCalGraphics().constructEventElements(eventGraphics.getCalGraphics().getDateSelected());
				eventGraphics.getCalGraphics().revalidate();
				eventGraphics.getCalGraphics().repaint();
			}
		}
	}
}
