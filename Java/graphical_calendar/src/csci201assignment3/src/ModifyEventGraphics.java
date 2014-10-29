package csci201assignment3.src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ModifyEventGraphics extends JFrame
{
	private Event someEvent;
	private CalendarGraphics calGraphics;
	
	public ModifyEventGraphics(Event someEvent, CalendarGraphics calGraphics)
	{
		this.someEvent = someEvent;
		this.setSize(new Dimension(300,300));
		JPanel areaLayout = new JPanel(new BorderLayout());
		JPanel buttonLayout = new JPanel(new GridLayout(1,3));
		EditEventButton edit = new EditEventButton(calGraphics, someEvent, this);
		DeleteEventButton delete = new DeleteEventButton(calGraphics, someEvent, this);
		CancelEventModificationButton cancel = new CancelEventModificationButton(this);
		JLabel header = new JLabel("Would you like to edit or delete this event?");
		header.setFont(new Font("Helvetica", Font.BOLD, 15));
		areaLayout.add(header, BorderLayout.NORTH);
		buttonLayout.add(edit);
		buttonLayout.add(delete);
		buttonLayout.add(cancel);
		areaLayout.add(buttonLayout, BorderLayout.CENTER);
		
		this.add(areaLayout);
	}
}
