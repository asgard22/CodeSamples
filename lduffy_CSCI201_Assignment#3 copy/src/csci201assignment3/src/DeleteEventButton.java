package csci201assignment3.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class DeleteEventButton extends JLabel implements MouseListener
{
	private CalendarGraphics calGraphics;
	private Event someEvent;
	private ModifyEventGraphics modGraphics;
	
	public DeleteEventButton(CalendarGraphics calGraphics, Event someEvent, ModifyEventGraphics modGraphics)
	{
		super("Delete Event", SwingConstants.CENTER);
		this.addMouseListener(this);
		this.modGraphics = modGraphics;
		this.calGraphics = calGraphics;
		this.someEvent = someEvent;
		this.setFont(new Font("Helvetica", Font.BOLD, 15));
		this.setBackground(Color.orange);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.setOpaque(true);
	}

	public void mouseClicked(MouseEvent e) 
	{
		int indexToRemove = 0;
		ArrayList<Event> events = calGraphics.getEvents();
		for (int i = 0; i < events.size(); i++)
		{
			if (someEvent.equals(events.get(i)))
			{
				indexToRemove = i;
			}
		}
		if (events.size() > 0)
		{
			events.remove(indexToRemove);
			calGraphics.updateHeaderElements(calGraphics.getDateSelected());
			calGraphics.updateCalendarElements(calGraphics.getDateSelected(), false);
			calGraphics.constructEventElements(calGraphics.getDateSelected());
			calGraphics.revalidate();
			calGraphics.repaint();
		}
		
		modGraphics.setVisible(false);
		modGraphics.dispose();	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
