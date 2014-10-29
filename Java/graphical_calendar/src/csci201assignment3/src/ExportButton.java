package csci201assignment3.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.border.BevelBorder;

public class ExportButton extends JMenuItem implements ActionListener
{
	private CalendarGraphics calGraphics;
	
	public ExportButton(CalendarGraphics calGraphics)
	{
		super("Export");
		this.addActionListener(this);
		this.calGraphics = calGraphics;
		this.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.setBackground(Color.orange);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}

	public void actionPerformed(ActionEvent e) 
	{
		ArrayList<Event> events = calGraphics.getEvents();
		FileWriter fw = null;
		PrintWriter pw = null;
		
		try {
			fw = new FileWriter("events.csv");
			pw = new PrintWriter(fw);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for (int i = 0; i < events.size(); i++)
		{
			Event someEvent = events.get(i);
			String eventTitle = someEvent.getTitle();
			String eventLocation = someEvent.getLocation();
			String eventStartTime = someEvent.getStartTime();
			String eventEndTime = someEvent.getEndTime();
			String eventDate = someEvent.getEventDate();
			
			String eventLine = eventTitle + "," + eventLocation + "," + eventStartTime + "," + eventEndTime + "," + eventDate;
			pw.println(eventLine);
		}
		pw.flush();
	}
	
	
}
