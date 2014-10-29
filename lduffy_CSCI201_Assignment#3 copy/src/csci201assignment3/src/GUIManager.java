package csci201assignment3.src;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUIManager 
{
	private static CalendarGraphics calGraphics;
	private static ArrayList<Event> events;
	
	public static void main(String args[])
	{
		Calendar currentDate = new GregorianCalendar(TimeZone.getTimeZone("PST"));
		events = new ArrayList<Event>();
		calGraphics = new CalendarGraphics(currentDate, events);
		calGraphics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calGraphics.setSize(500,500);
		calGraphics.setResizable(false);
		calGraphics.setVisible(true);
	}
}
