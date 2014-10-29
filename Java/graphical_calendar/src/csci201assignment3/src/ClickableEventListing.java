package csci201assignment3.src;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class ClickableEventListing extends JLabel implements MouseListener
{
	private CalendarGraphics calGraphics;
	private Event someEvent;
	
	public ClickableEventListing(String text, int position, CalendarGraphics calGraphics, Event someEvent)
	{
		super(text, position);
		this.calGraphics = calGraphics;
		this.addMouseListener(this);
		this.someEvent = someEvent;
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		ModifyEventGraphics modifyEventGraphics = new ModifyEventGraphics(someEvent, calGraphics);
		modifyEventGraphics.setVisible(true);
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
