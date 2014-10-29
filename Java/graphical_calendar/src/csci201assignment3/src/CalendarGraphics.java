package csci201assignment3.src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class CalendarGraphics extends JFrame
{
	//organizes all the calendar graphics components
	private JPanel allOrganizer;
	//organizes the north section calendar graphics
	private JPanel northOrganizer;
	//contains the menu bar
	private JMenuBar menu;
	//items on the menu
	private CreateButton create;
	private ExportButton export;
	private AboutButton about;
	//contains the month, as well as arrow navigators
	private JPanel monthAndArrowHeader;
	//arrow navigators
	PrevMonthArrowButton prevMonthArrowButton;
	NextMonthArrowButton nextMonthArrowButton;
	
	//displays the name of the monthAndArrowHeader
	private JLabel monthName;
	//displays the day names of current month
	private JPanel daysOfWeekDisplay;
	//day names
	JLabel sunday;
	JLabel monday;
	JLabel tuesday;
	JLabel wednesday;
	JLabel thursday;
	JLabel friday;
	JLabel saturday;
	//displays the day buttons of the current month
	private JPanel daysDisplay;
	//holds references to the Day objects populating the daysDisplay JPanel
	private ArrayList<Day> days;
	//contains the Event objects for events created during runtime
	private ArrayList<Event> events;
	//displays the events for the selected day
	private JPanel eventsList;
	//scrolls over the display of events for the selected day
	private JScrollPane eventsDisplay;
	//date selected on calendar
	private Calendar dateSelected;
	//today's date
	private Calendar currentDate;
	//keeps track of whether the calendar has been constructed more than once
	private boolean constructedYet;

	
	public CalendarGraphics(Calendar dateSelected, ArrayList<Event> events)
	{
		this.dateSelected = dateSelected;
		this.currentDate = Calendar.getInstance();
		allOrganizer = new JPanel(new BorderLayout());
		northOrganizer = new JPanel(new BorderLayout());
		menu = new JMenuBar();
		monthAndArrowHeader = new JPanel(new BorderLayout());
		SimpleDateFormat df = new SimpleDateFormat("MMMMMMMMM YYYY");
		monthName = new JLabel(df.format(dateSelected.getTime()), SwingConstants.CENTER);
		daysOfWeekDisplay = new JPanel(new GridLayout(1,7));
		daysDisplay = new JPanel(new GridLayout(6,7));
		days = new ArrayList<Day>();
		this.events = events;
		eventsList = new JPanel();
		eventsList.setLayout(new BoxLayout(eventsList, BoxLayout.PAGE_AXIS));
		eventsDisplay = new JScrollPane(eventsList);
		//sets up graphics organization
		this.add(allOrganizer);

		constructHeaderElements();
		constructCalendarElements(dateSelected, false);
	    constructEventElements(dateSelected);
	    constructedYet = true;
	}
		
	public void setDateSelected(Calendar newDateSelected)
	{
		this.dateSelected = newDateSelected;
	}
	
	public Calendar getDateSelected()
	{
		return this.dateSelected;
	}
	
	public ArrayList<Event> getEvents()
	{
		return this.events;
	}
	
	public ArrayList<Day> getDays()
	{
		return this.days;
	}

	public Calendar getCurrentDate()
	{
		return this.currentDate;
	}
	
	public boolean getConstructedYet()
	{
		return this.constructedYet;
	}
	
	public void constructHeaderElements()
	{
		//creates menu bar
		create = new CreateButton(this);
		export = new ExportButton(this);
		about = new AboutButton(this);
		menu.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		menu.setBackground(new Color(125,145,175));
		menu.add(create);
		menu.add(export);
		menu.add(about);
		
		//creates month navigation arrows
		prevMonthArrowButton = new PrevMonthArrowButton(this);
		nextMonthArrowButton = new NextMonthArrowButton(this);
		
		//formats month name and area with navigation bars and month name
		SimpleDateFormat df = new SimpleDateFormat("MMMMMMM YYYY");
		monthName.setText(df.format(dateSelected.getTime()));
		monthName.setFont(new Font("Helvetica", Font.BOLD, 30));
		monthAndArrowHeader.add(nextMonthArrowButton, BorderLayout.EAST);
		monthAndArrowHeader.add(monthName, BorderLayout.CENTER);
		monthAndArrowHeader.add(prevMonthArrowButton, BorderLayout.WEST);
		monthAndArrowHeader.setBackground(new Color(212,212,212));
		monthAndArrowHeader.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		//formats display of day names 
		daysOfWeekDisplay.setBackground(new Color(212,212,212));
		//creates day names and formats them
		sunday = new JLabel("SUN", SwingConstants.CENTER);
		sunday.setFont(new Font("Helvetica", Font.BOLD, 12));
		monday = new JLabel("MON", SwingConstants.CENTER);
		monday.setFont(new Font("Helvetica", Font.BOLD, 12));
		tuesday = new JLabel("TUES", SwingConstants.CENTER);
		tuesday.setFont(new Font("Helvetica", Font.BOLD, 12));
		wednesday = new JLabel("WED", SwingConstants.CENTER);
		wednesday.setFont(new Font("Helvetica", Font.BOLD, 12));
		thursday = new JLabel("THURS", SwingConstants.CENTER);
		thursday.setFont(new Font("Helvetica", Font.BOLD, 12));
		friday = new JLabel("FRI", SwingConstants.CENTER);
		friday.setFont(new Font("Helvetica", Font.BOLD, 12));
		saturday = new JLabel("SAT", SwingConstants.CENTER);
		saturday.setFont(new Font("Helvetica", Font.BOLD, 12));
		//adds day names so they are visible
		daysOfWeekDisplay.add(sunday);
		daysOfWeekDisplay.add(monday);
		daysOfWeekDisplay.add(tuesday);
		daysOfWeekDisplay.add(wednesday);
		daysOfWeekDisplay.add(thursday);
		daysOfWeekDisplay.add(friday);
		daysOfWeekDisplay.add(saturday);
		
		//adds all elements to north items organizer
		northOrganizer.add(menu, BorderLayout.NORTH);
		northOrganizer.add(monthAndArrowHeader, BorderLayout.CENTER);
		northOrganizer.add(daysOfWeekDisplay, BorderLayout.SOUTH);
		
		//adds north organizer to calendar graphics display
		this.add(northOrganizer, BorderLayout.NORTH);
	}

	public void deconstructHeaderElements()
	{
		//invert
		menu.remove(create);
		menu.remove(export);
		menu.remove(about);
		monthAndArrowHeader.remove(nextMonthArrowButton);
		monthAndArrowHeader.remove(monthName);
		monthAndArrowHeader.remove(prevMonthArrowButton);
		daysOfWeekDisplay.remove(sunday);
		daysOfWeekDisplay.remove(monday);
		daysOfWeekDisplay.remove(tuesday);
		daysOfWeekDisplay.remove(wednesday);
		daysOfWeekDisplay.remove(thursday);
		daysOfWeekDisplay.remove(friday);
		daysOfWeekDisplay.remove(saturday);
		northOrganizer.remove(menu);
		northOrganizer.remove(monthAndArrowHeader);
		northOrganizer.remove(daysOfWeekDisplay);
		this.remove(northOrganizer);
	}
	
	public void updateHeaderElements(Calendar dateSelected)
	{
		this.deconstructHeaderElements();
		this.constructHeaderElements();
	}

	public void constructCalendarElements(Calendar dateSelected, boolean fromMonthArrow)
	{		
		//calendar object now stores the first day of this month
		Calendar firstDayOfThisMonth = new GregorianCalendar(dateSelected.get(Calendar.YEAR), (dateSelected.get(Calendar.MONTH)), 1);
		
		//stores 1-7 the day of the week
		int firstDayOfWeekThisMonth = firstDayOfThisMonth.get(Calendar.DAY_OF_WEEK);
		
		//stores the total days that have been added, visually and in terms of Day objects, to the current month
		int totalDaysAddedToCalendar = 0;

		//after these lines, prevMonthDayCounter stores the last day of the previous month
		Calendar prevMonthDayCounter = new GregorianCalendar(dateSelected.get(Calendar.YEAR), (dateSelected.get(Calendar.MONTH)), 1);
		prevMonthDayCounter.add(Calendar.MONTH, -1);
		prevMonthDayCounter.set(Calendar.DAY_OF_MONTH, prevMonthDayCounter.getActualMaximum(Calendar.DAY_OF_MONTH));
				
		//after these lines, prevMonthDayCounter stores the last sunday of the previous month
		while (prevMonthDayCounter.get(Calendar.DAY_OF_WEEK) != 1)
		{
			prevMonthDayCounter.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		//adds Day objects from the previous month, filling the first week on the calendar up until the first day of the current month
		while (prevMonthDayCounter.get(Calendar.DAY_OF_WEEK) != firstDayOfWeekThisMonth)
		{
			//Day object is initialized with prevMonthDayCounter, because this is the date it represents
			Day day = new Day(prevMonthDayCounter, this, false, fromMonthArrow);
			day.setOpaque(true);
			//adds the Day object to the JPanel that will display it
			daysDisplay.add(day);
			//adds the Day object to the array list that will store it
			days.add(day);
			//adds one to the Day object, for the next day displayed
			prevMonthDayCounter.add(Calendar.DAY_OF_MONTH, 1);
			//keeps track of how many days have been added to the calendar total 
			totalDaysAddedToCalendar++;
		}
				
		//calendar object now stores the first day of this month
		Calendar thisMonthDayCounter = new GregorianCalendar(dateSelected.get(Calendar.YEAR), (dateSelected.get(Calendar.MONTH)), 1);
		
		System.out.println(dateSelected.getTime());
		
		//adds Day objects from this month, filling the weeks on the calendar up until the last day of the current month
		while (thisMonthDayCounter.get(Calendar.DAY_OF_MONTH) != thisMonthDayCounter.getActualMaximum(Calendar.DAY_OF_MONTH))
		{
			//Day object is initialized with thisMonthDayCounter, because this is the date it represents
			Calendar dayCal = (Calendar)thisMonthDayCounter.clone();
			Day day = new Day(dayCal, this, true, fromMonthArrow);
			day.setOpaque(true);
			for (int i = 0; i < events.size(); i++)
			{
				if (events.get(i).getStart().get(Calendar.YEAR) == thisMonthDayCounter.get(Calendar.YEAR) && events.get(i).getStart().get(Calendar.MONTH) == thisMonthDayCounter.get(Calendar.MONTH) && events.get(i).getStart().get(Calendar.DAY_OF_MONTH) == thisMonthDayCounter.get(Calendar.DAY_OF_MONTH))
				{
					day.addEvent(events.get(i));
				}
			}
			//adds the Day object to the JPanel that will display it
			daysDisplay.add(day);
			//adds the Day object to the array list that will store it
			days.add(day);
			//adds one to the Day object, for the next day displayed
			thisMonthDayCounter.add(Calendar.DAY_OF_MONTH, 1);
			//keeps track of how many days have been added to the calendar total 
			totalDaysAddedToCalendar++;
		}
		
		System.out.println(dateSelected.getTime());
				
		//adds last Day Object from this month, filling the last day of the current month
		if (true)
		{
			Calendar dayCal = (Calendar)thisMonthDayCounter.clone();
			//Day object is initialized with thisMonthDayCounter, because this is the date it represents
			Day day = new Day(dayCal, this, true, fromMonthArrow);
			day.setOpaque(true);
			//adds the Day object to the JPanel that will display it
			daysDisplay.add(day);
			//adds the Day object to the arraylist that will store it
			days.add(day);
			thisMonthDayCounter.add(Calendar.DAY_OF_MONTH, 1);
			//keeps track of how many days have been added to the calendar total 
			totalDaysAddedToCalendar++;
		}
				
		//after these lines, calendar object stores the first day of the next month
		Calendar nextMonthDayCounter = new GregorianCalendar(dateSelected.get(Calendar.YEAR), (dateSelected.get(Calendar.MONTH)), 1);
		nextMonthDayCounter.add(Calendar.MONTH, 1);

		//adds Day objects from this month, filling the weeks on the calendar up until the last spot on the JPanel displaying the days
		while (totalDaysAddedToCalendar < 42)
		{
			//Day object is initialized with nextMonthDayCounter, because this is the date it represents
			Day day = new Day(nextMonthDayCounter, this, false, fromMonthArrow);
			day.setOpaque(true);
			//adds the Day object to the JPanel that will display it
			daysDisplay.add(day);
			//adds the Day object to the arraylist that will store it
			days.add(day);
			nextMonthDayCounter.add(Calendar.DAY_OF_MONTH, 1);
			//keeps track of how many days have been added to the calendar total 
			totalDaysAddedToCalendar++;
		}
		
		this.add(daysDisplay, BorderLayout.CENTER);
		this.repaint();
	}
	
	public void deconstructCalendarElements()
	{
		this.remove(daysDisplay);
		this.repaint();
	}
	
	public void updateCalendarElements(Calendar dateSelected, boolean fromNextMonthArrow)
	{
		//removes all days currently displayed on calendar
		for (int i = 0; i < 42; i++)
		{
			Day someDay = days.get(i);
			daysDisplay.remove(someDay);
		}
		//resets array list
		this.days = new ArrayList<Day>();
		
		//places new days on calendar
		System.out.println(dateSelected.getTime());
		this.constructCalendarElements(dateSelected, fromNextMonthArrow);
	}
	
	public void constructEventElements(Calendar dateSelected)
	{
		eventsList.removeAll();
		for (int i = 0; i < days.size(); i++)
		{
			Day someDay = days.get(i);
			if (someDay.isSelected())
			{
				ArrayList<Event> dayEvents = someDay.getEvents();
				for (int j = 0; j < dayEvents.size(); j++)
				{
					Event someEvent = dayEvents.get(j);
					ClickableEventListing listItem = new ClickableEventListing(someEvent.getTitle() + " @ " + someEvent.getLocation() + " FROM: " + someEvent.getStartTime() + " UNTIL: " + someEvent.getEndTime(), SwingConstants.CENTER, this, someEvent);
					listItem.setFont(new Font("Helvetica", Font.BOLD, 20));
					listItem.setBackground(Color.orange);
					listItem.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					listItem.setOpaque(true);
					eventsList.add(listItem);
				}
				
				if (dayEvents.size() == 0)
				{
					JLabel listItem = new JLabel("No Events!", SwingConstants.CENTER);
					listItem.setFont(new Font("Helvetica", Font.BOLD, 20));
					listItem.setBackground(Color.orange);
					listItem.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					listItem.setOpaque(true);
					eventsList.add(listItem);
				}
				
			}
		}
		
		eventsDisplay.setPreferredSize(new Dimension(100,100));
		//create scroll panel
		
		this.add(eventsDisplay, BorderLayout.SOUTH);
		this.revalidate();
		this.repaint();
	}
	
	public void deconstructEventElements()
	{
		this.remove(eventsDisplay);
		eventsList = new JPanel();
		eventsList.setLayout(new BoxLayout(eventsList, BoxLayout.PAGE_AXIS));
		eventsDisplay = new JScrollPane(eventsList);
	}
	
	public void updateEventElements(Calendar dateSelected)
	{
		deconstructEventElements();
		constructEventElements(dateSelected);
	}
	
	public void setConstructedYet(boolean b) 
	{
		constructedYet = b;
	}
	
	public void addEvent(Event someEvent)
	{
		events.add(someEvent);
	}

	
	public ArrayList<Event> getEventsList() 
	{
		return events;
	}
}
