package csci201assignment3.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class EditEventButton extends JLabel implements MouseListener
{
	private CalendarGraphics calGraphics;
	private Event someEvent;
	private ModifyEventGraphics modGraphics;
	
	public EditEventButton(CalendarGraphics calGraphics, Event someEvent, ModifyEventGraphics modGraphics)
	{
		super("Edit Event", SwingConstants.CENTER);
		this.addMouseListener(this);
		this.calGraphics = calGraphics;
		this.someEvent =someEvent;
		this.modGraphics = modGraphics;
		this.setFont(new Font("Helvetica", Font.BOLD, 15));
		this.setBackground(Color.orange);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.setOpaque(true);
	}

	public void mouseClicked(MouseEvent e) 
	{
		calGraphics.deconstructHeaderElements();
		calGraphics.deconstructCalendarElements();
		calGraphics.deconstructEventElements();
		calGraphics.add(new AddEventGraphics(calGraphics, someEvent));
		calGraphics.revalidate();
		calGraphics.repaint();	
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
