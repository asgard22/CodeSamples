package csci201assignment3.src;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class UpdateEventButton extends JButton implements ActionListener
{
	private AddEventGraphics eventGraphics;
	private CalendarGraphics calGraphics;
	private Event eventToBeEdited;
	
	public UpdateEventButton(AddEventGraphics eventGraphics, Event eventToBeEdited)
	{
		super("Update Event");
		this.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.addActionListener(this);
		this.eventToBeEdited = eventToBeEdited;
		this.eventGraphics = eventGraphics;
		this.calGraphics = eventGraphics.getCalGraphics();
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
				eventToBeEdited.setTitle(eventGraphics.getEventTitle());
				eventToBeEdited.setLocation(eventGraphics.getEventLocation());
				eventToBeEdited.setStart(eventGraphics.getStartTime());
				eventToBeEdited.setEnd(eventGraphics.getEndTime());
	
				calGraphics.getEventsList().remove(eventToBeEdited);
				eventGraphics.getCalGraphics().addEvent(eventToBeEdited);
				eventGraphics.deconstructEventGraphics();
				eventGraphics.getCalGraphics().updateHeaderElements(eventGraphics.getCalGraphics().getDateSelected());
				eventGraphics.getCalGraphics().updateCalendarElements(eventGraphics.getCalGraphics().getDateSelected(), false);
				eventGraphics.getCalGraphics().constructEventElements(eventGraphics.getCalGraphics().getDateSelected());
				System.out.println("update!");
				eventGraphics.getCalGraphics().revalidate();
				eventGraphics.getCalGraphics().repaint();
			}
		}
	}
}