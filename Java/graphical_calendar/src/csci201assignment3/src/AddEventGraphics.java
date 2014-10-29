package csci201assignment3.src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class AddEventGraphics extends JPanel
{
	private CalendarGraphics calGraphics;
	private JTextField titleEntryBox;
	private JTextField locationEntryBox;
	
	private JComboBox yearEntry;
	private JComboBox monthEntry;
	private JComboBox dayEntry;
	
	private JComboBox startTimeHourEntry;
	private JComboBox startTimeMinuteEntry;
	private JComboBox startTimeAMPMEntry;
	
	private JComboBox endTimeHourEntry;
	private JComboBox endTimeMinuteEntry;
	private JComboBox endTimeAMPMEntry;
	
	private JPanel entryLines;
	
	private Calendar dateSelected;
	
	public AddEventGraphics(CalendarGraphics calGraphics)
	{
		super(new BorderLayout());
		this.calGraphics = calGraphics;
		
		constructEventGraphics();
	}
	
	public AddEventGraphics(CalendarGraphics calGraphics, Calendar dateSelected)
	{
		super(new BorderLayout());
		this.calGraphics = calGraphics;
		
		constructEventGraphicsWithDate(dateSelected);
	}

	public AddEventGraphics(CalendarGraphics calGraphics, Event eventToBeEdited)
	{
		super(new BorderLayout());
		this.calGraphics = calGraphics;
		
		constructEventGraphicsWithEvent(eventToBeEdited);
	}
	
	public void constructEventGraphics()
	{
		titleEntryBox = new JTextField();
		locationEntryBox = new JTextField();
		yearEntry = new JComboBox();
		monthEntry = new JComboBox();
		dayEntry = new JComboBox();
		startTimeHourEntry = new JComboBox();
		startTimeMinuteEntry = new JComboBox();
		startTimeAMPMEntry = new JComboBox();
		endTimeHourEntry = new JComboBox();
		endTimeMinuteEntry = new JComboBox();
		endTimeAMPMEntry = new JComboBox();
		
		entryLines = new JPanel(new GridLayout(12,1));
		
		JLabel windowInstructions = new JLabel("Enter the information for your event!", SwingConstants.CENTER);
		windowInstructions.setFont(new Font("Helvetica", Font.BOLD, 20));
		windowInstructions.setBackground(Color.ORANGE);
		windowInstructions.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		windowInstructions.setOpaque(true);

		
		JPanel titleEntryLine = new JPanel(new GridLayout(1,2));
		JLabel titleEntryText = new JLabel("Title:", SwingConstants.CENTER);
		titleEntryText.setFont(new Font("Helvetica", Font.BOLD, 20));
		titleEntryLine.add(titleEntryText);
		titleEntryLine.add(titleEntryBox);
		
		JPanel locationEntryLine = new JPanel(new GridLayout(1,2));
		JLabel locationEntryText = new JLabel("Location:", SwingConstants.CENTER);
		locationEntryText.setFont(new Font("Helvetica", Font.BOLD, 20));
		locationEntryLine.add(locationEntryText);
		locationEntryLine.add(locationEntryBox);
		
		JLabel yearEntryText = new JLabel("Year:", SwingConstants.CENTER);
		yearEntryText.setFont(new Font("Helvetica", Font.BOLD, 20));
		for (int i = 2014; i > 1879; i--)
		{
			yearEntry.addItem("" + i);
		}
		
		JLabel monthEntryText = new JLabel("Month:", SwingConstants.CENTER);
		monthEntryText.setFont(new Font("Helvetica", Font.BOLD, 20));
		for (int i = 1; i < 13; i++)
		{
			monthEntry.addItem("" + i);
		}
		
		JLabel dayEntryText = new JLabel("Day:", SwingConstants.CENTER);
		dayEntryText.setFont(new Font("Helvetica", Font.BOLD, 20));
		
		for (int i = 1; i < 31; i++)
		{
			dayEntry.addItem("" + i);
		}
		
		monthEntry.addActionListener
		(
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					dayEntry.removeAllItems();
					int month = Integer.parseInt((String)monthEntry.getSelectedItem())-1;
					Calendar tempCal = new GregorianCalendar(Calendar.YEAR, month, 1);
					int lastDay = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);
					for (int i = 1; i <= lastDay; i++)
					{
						dayEntry.addItem("" + i);
					}
				}
	
			}
		);
		
		JPanel startTimeLine = new JPanel(new GridLayout(1,4));
		startTimeHourEntry.addItem("1");
		startTimeHourEntry.addItem("2");
		startTimeHourEntry.addItem("3");
		startTimeHourEntry.addItem("4");
		startTimeHourEntry.addItem("5");
		startTimeHourEntry.addItem("6");
		startTimeHourEntry.addItem("7");
		startTimeHourEntry.addItem("8");
		startTimeHourEntry.addItem("9");
		startTimeHourEntry.addItem("10");
		startTimeHourEntry.addItem("11");
		startTimeHourEntry.addItem("12");
		startTimeMinuteEntry.addItem("00");
		startTimeMinuteEntry.addItem("15");
		startTimeMinuteEntry.addItem("30");
		startTimeMinuteEntry.addItem("45");
		startTimeAMPMEntry.addItem("AM");
		startTimeAMPMEntry.addItem("PM");
		JLabel startTimeText = new JLabel("Start Time:", SwingConstants.CENTER);
		startTimeText.setFont(new Font("Helvetica", Font.BOLD, 20));
		startTimeLine.add(startTimeText);
		startTimeLine.add(startTimeHourEntry);
		startTimeLine.add(startTimeMinuteEntry);
		startTimeLine.add(startTimeAMPMEntry);
		
		JPanel endTimeLine = new JPanel(new GridLayout(1,2));
		endTimeHourEntry.addItem("1");
		endTimeHourEntry.addItem("2");
		endTimeHourEntry.addItem("3");
		endTimeHourEntry.addItem("4");
		endTimeHourEntry.addItem("5");
		endTimeHourEntry.addItem("6");
		endTimeHourEntry.addItem("7");
		endTimeHourEntry.addItem("8");
		endTimeHourEntry.addItem("9");
		endTimeHourEntry.addItem("10");
		endTimeHourEntry.addItem("11");
		endTimeHourEntry.addItem("12");
		endTimeMinuteEntry.addItem("00");
		endTimeMinuteEntry.addItem("15");
		endTimeMinuteEntry.addItem("30");
		endTimeMinuteEntry.addItem("45");
		endTimeAMPMEntry.addItem("AM");
		endTimeAMPMEntry.addItem("PM");
		JLabel endTimeText = new JLabel("End Time:", SwingConstants.CENTER);
		endTimeText.setFont(new Font("Helvetica", Font.BOLD, 20));
		endTimeLine.add(endTimeText);
		endTimeLine.add(endTimeHourEntry);
		endTimeLine.add(endTimeMinuteEntry);
		endTimeLine.add(endTimeAMPMEntry);
		
		JPanel buttonLine = new JPanel(new GridLayout(1,2));
		AddEventButton createButton = new AddEventButton(this);
		CancelEventButton cancelButton = new CancelEventButton(this);
		buttonLine.add(createButton);
		buttonLine.add(cancelButton);
		
		entryLines.add(windowInstructions);
		entryLines.add(titleEntryLine);
		entryLines.add(locationEntryLine);
		entryLines.add(yearEntryText);
		entryLines.add(yearEntry);
		entryLines.add(monthEntryText);
		entryLines.add(monthEntry);
		entryLines.add(dayEntryText);
		entryLines.add(dayEntry);
		entryLines.add(startTimeLine);
		entryLines.add(endTimeLine);
		entryLines.add(buttonLine);
		
		this.add(entryLines, BorderLayout.CENTER);
	}
	
	public void deconstructEventGraphics()
	{
		calGraphics.remove(this);
	}
	
	public void constructEventGraphicsWithDate(Calendar dateSelected)
	{
		titleEntryBox = new JTextField();
		locationEntryBox = new JTextField();
		yearEntry = new JComboBox();
		monthEntry = new JComboBox();
		dayEntry = new JComboBox();
		startTimeHourEntry = new JComboBox();
		startTimeMinuteEntry = new JComboBox();
		startTimeAMPMEntry = new JComboBox();
		endTimeHourEntry = new JComboBox();
		endTimeMinuteEntry = new JComboBox();
		endTimeAMPMEntry = new JComboBox();
		this.dateSelected = dateSelected;
		
		entryLines = new JPanel(new GridLayout(6,1));
		
		JLabel windowInstructions = new JLabel("Enter the information for your event!", SwingConstants.CENTER);
		windowInstructions.setFont(new Font("Helvetica", Font.BOLD, 20));
		windowInstructions.setBackground(Color.ORANGE);
		windowInstructions.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		windowInstructions.setOpaque(true);

		
		JPanel titleEntryLine = new JPanel(new GridLayout(1,2));
		JLabel titleEntryText = new JLabel("Title:", SwingConstants.CENTER);
		titleEntryText.setFont(new Font("Helvetica", Font.BOLD, 20));
		titleEntryLine.add(titleEntryText);
		titleEntryLine.add(titleEntryBox);
		
		JPanel locationEntryLine = new JPanel(new GridLayout(1,2));
		JLabel locationEntryText = new JLabel("Location:", SwingConstants.CENTER);
		locationEntryText.setFont(new Font("Helvetica", Font.BOLD, 20));
		locationEntryLine.add(locationEntryText);
		locationEntryLine.add(locationEntryBox);
		
		JPanel startTimeLine = new JPanel(new GridLayout(1,4));
		startTimeHourEntry.addItem("1");
		startTimeHourEntry.addItem("2");
		startTimeHourEntry.addItem("3");
		startTimeHourEntry.addItem("4");
		startTimeHourEntry.addItem("5");
		startTimeHourEntry.addItem("6");
		startTimeHourEntry.addItem("7");
		startTimeHourEntry.addItem("8");
		startTimeHourEntry.addItem("9");
		startTimeHourEntry.addItem("10");
		startTimeHourEntry.addItem("11");
		startTimeHourEntry.addItem("12");
		startTimeMinuteEntry.addItem("00");
		startTimeMinuteEntry.addItem("15");
		startTimeMinuteEntry.addItem("30");
		startTimeMinuteEntry.addItem("45");
		startTimeAMPMEntry.addItem("AM");
		startTimeAMPMEntry.addItem("PM");
		JLabel startTimeText = new JLabel("Start Time:", SwingConstants.CENTER);
		startTimeText.setFont(new Font("Helvetica", Font.BOLD, 20));
		startTimeLine.add(startTimeText);
		startTimeLine.add(startTimeHourEntry);
		startTimeLine.add(startTimeMinuteEntry);
		startTimeLine.add(startTimeAMPMEntry);
		
		JPanel endTimeLine = new JPanel(new GridLayout(1,2));
		endTimeHourEntry.addItem("1");
		endTimeHourEntry.addItem("2");
		endTimeHourEntry.addItem("3");
		endTimeHourEntry.addItem("4");
		endTimeHourEntry.addItem("5");
		endTimeHourEntry.addItem("6");
		endTimeHourEntry.addItem("7");
		endTimeHourEntry.addItem("8");
		endTimeHourEntry.addItem("9");
		endTimeHourEntry.addItem("10");
		endTimeHourEntry.addItem("11");
		endTimeHourEntry.addItem("12");
		endTimeMinuteEntry.addItem("00");
		endTimeMinuteEntry.addItem("15");
		endTimeMinuteEntry.addItem("30");
		endTimeMinuteEntry.addItem("45");
		endTimeAMPMEntry.addItem("AM");
		endTimeAMPMEntry.addItem("PM");
		JLabel endTimeText = new JLabel("End Time:", SwingConstants.CENTER);
		endTimeText.setFont(new Font("Helvetica", Font.BOLD, 20));
		endTimeLine.add(endTimeText);
		endTimeLine.add(endTimeHourEntry);
		endTimeLine.add(endTimeMinuteEntry);
		endTimeLine.add(endTimeAMPMEntry);
		
		JPanel buttonLine = new JPanel(new GridLayout(1,2));
		AddEventFromDayClickButton createButton = new AddEventFromDayClickButton(this);
		CancelEventButton cancelButton = new CancelEventButton(this);
		buttonLine.add(createButton);
		buttonLine.add(cancelButton);
		
		entryLines.add(windowInstructions);
		entryLines.add(titleEntryLine);
		entryLines.add(locationEntryLine);
		entryLines.add(startTimeLine);
		entryLines.add(endTimeLine);
		entryLines.add(buttonLine);
		
		this.add(entryLines, BorderLayout.CENTER);
	}

	public void constructEventGraphicsWithEvent(Event eventToBeEdited)
	{
		titleEntryBox = new JTextField();
		titleEntryBox.setText(eventToBeEdited.getTitle());
		locationEntryBox = new JTextField();
		locationEntryBox.setText(eventToBeEdited.getLocation());
		yearEntry = new JComboBox();
		monthEntry = new JComboBox();
		dayEntry = new JComboBox();
		startTimeHourEntry = new JComboBox();
		startTimeMinuteEntry = new JComboBox();
		startTimeAMPMEntry = new JComboBox();
		endTimeHourEntry = new JComboBox();
		endTimeMinuteEntry = new JComboBox();
		endTimeAMPMEntry = new JComboBox();
		this.dateSelected = dateSelected;
		
		entryLines = new JPanel(new GridLayout(12,1));
		
		JLabel windowInstructions = new JLabel("Edit the information for your event!", SwingConstants.CENTER);
		windowInstructions.setFont(new Font("Helvetica", Font.BOLD, 20));
		windowInstructions.setBackground(Color.ORANGE);
		windowInstructions.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		windowInstructions.setOpaque(true);

		
		JPanel titleEntryLine = new JPanel(new GridLayout(1,2));
		JLabel titleEntryText = new JLabel("Title:", SwingConstants.CENTER);
		titleEntryText.setFont(new Font("Helvetica", Font.BOLD, 20));
		titleEntryLine.add(titleEntryText);
		titleEntryLine.add(titleEntryBox);
		
		JPanel locationEntryLine = new JPanel(new GridLayout(1,2));
		JLabel locationEntryText = new JLabel("Location:", SwingConstants.CENTER);
		locationEntryText.setFont(new Font("Helvetica", Font.BOLD, 20));
		locationEntryLine.add(locationEntryText);
		locationEntryLine.add(locationEntryBox);
		
		JLabel yearEntryText = new JLabel("Year:", SwingConstants.CENTER);
		yearEntryText.setFont(new Font("Helvetica", Font.BOLD, 20));
		for (int i = 2014; i > 1879; i--)
		{
			yearEntry.addItem("" + i);
		}
		yearEntry.setSelectedItem(String.valueOf(eventToBeEdited.getStart().get(Calendar.YEAR)));
		
		JLabel monthEntryText = new JLabel("Month:", SwingConstants.CENTER);
		monthEntryText.setFont(new Font("Helvetica", Font.BOLD, 20));
		for (int i = 1; i < 13; i++)
		{
			monthEntry.addItem("" + i);
		}
		monthEntry.setSelectedItem(String.valueOf(eventToBeEdited.getStart().get(Calendar.MONTH)+1));
		
		JLabel dayEntryText = new JLabel("Day:", SwingConstants.CENTER);
		dayEntryText.setFont(new Font("Helvetica", Font.BOLD, 20));
		
		for (int i = 1; i < 31; i++)
		{
			dayEntry.addItem("" + i);
		}
		
		monthEntry.addActionListener
		(
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					dayEntry.removeAllItems();
					int month = Integer.parseInt((String)monthEntry.getSelectedItem())-1;
					Calendar tempCal = new GregorianCalendar(Calendar.YEAR, month, 1);
					int lastDay = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);
					for (int i = 1; i <= lastDay; i++)
					{
						dayEntry.addItem("" + i);
					}
				}
	
			}
		);
		
		dayEntry.setSelectedItem(String.valueOf(eventToBeEdited.getStart().get(Calendar.DAY_OF_MONTH)));
		
		JPanel startTimeLine = new JPanel(new GridLayout(1,4));
		startTimeHourEntry.addItem("1");
		startTimeHourEntry.addItem("2");
		startTimeHourEntry.addItem("3");
		startTimeHourEntry.addItem("4");
		startTimeHourEntry.addItem("5");
		startTimeHourEntry.addItem("6");
		startTimeHourEntry.addItem("7");
		startTimeHourEntry.addItem("8");
		startTimeHourEntry.addItem("9");
		startTimeHourEntry.addItem("10");
		startTimeHourEntry.addItem("11");
		startTimeHourEntry.addItem("12");
		startTimeHourEntry.setSelectedItem(String.valueOf(eventToBeEdited.getStart().get(Calendar.HOUR)));
		startTimeMinuteEntry.addItem("00");
		startTimeMinuteEntry.addItem("15");
		startTimeMinuteEntry.addItem("30");
		startTimeMinuteEntry.addItem("45");
		startTimeMinuteEntry.setSelectedItem(String.valueOf(eventToBeEdited.getStart().get(Calendar.MINUTE)));
		startTimeAMPMEntry.addItem("AM");
		startTimeAMPMEntry.addItem("PM");
		if(eventToBeEdited.getStart().get(Calendar.AM_PM) == Calendar.AM)
		{
			startTimeAMPMEntry.setSelectedItem("AM");
		}
		else
		{
			startTimeAMPMEntry.setSelectedItem("PM");
		}
		JLabel startTimeText = new JLabel("Start Time:", SwingConstants.CENTER);
		startTimeText.setFont(new Font("Helvetica", Font.BOLD, 20));
		startTimeLine.add(startTimeText);
		startTimeLine.add(startTimeHourEntry);
		startTimeLine.add(startTimeMinuteEntry);
		startTimeLine.add(startTimeAMPMEntry);
		
		JPanel endTimeLine = new JPanel(new GridLayout(1,2));
		endTimeHourEntry.addItem("1");
		endTimeHourEntry.addItem("2");
		endTimeHourEntry.addItem("3");
		endTimeHourEntry.addItem("4");
		endTimeHourEntry.addItem("5");
		endTimeHourEntry.addItem("6");
		endTimeHourEntry.addItem("7");
		endTimeHourEntry.addItem("8");
		endTimeHourEntry.addItem("9");
		endTimeHourEntry.addItem("10");
		endTimeHourEntry.addItem("11");
		endTimeHourEntry.addItem("12");
		endTimeHourEntry.setSelectedItem(String.valueOf(eventToBeEdited.getEnd().get(Calendar.HOUR)));
		endTimeMinuteEntry.addItem("00");
		endTimeMinuteEntry.addItem("15");
		endTimeMinuteEntry.addItem("30");
		endTimeMinuteEntry.addItem("45");
		endTimeMinuteEntry.setSelectedItem(String.valueOf(eventToBeEdited.getEnd().get(Calendar.MINUTE)));
		endTimeAMPMEntry.addItem("AM");
		endTimeAMPMEntry.addItem("PM");
		if(eventToBeEdited.getEnd().get(Calendar.AM_PM) == Calendar.AM)
		{
			endTimeAMPMEntry.setSelectedItem("AM");
		}
		else
		{
			endTimeAMPMEntry.setSelectedItem("PM");
		}
		JLabel endTimeText = new JLabel("End Time:", SwingConstants.CENTER);
		endTimeText.setFont(new Font("Helvetica", Font.BOLD, 20));
		endTimeLine.add(endTimeText);
		endTimeLine.add(endTimeHourEntry);
		endTimeLine.add(endTimeMinuteEntry);
		endTimeLine.add(endTimeAMPMEntry);
		
		JPanel buttonLine = new JPanel(new GridLayout(1,2));
		UpdateEventButton updateButton = new UpdateEventButton(this, eventToBeEdited);
		CancelEventButton cancelButton = new CancelEventButton(this);
		buttonLine.add(updateButton);
		buttonLine.add(cancelButton);
		
		entryLines.add(windowInstructions);
		entryLines.add(titleEntryLine);
		entryLines.add(locationEntryLine);
		entryLines.add(yearEntryText);
		entryLines.add(yearEntry);
		entryLines.add(monthEntryText);
		entryLines.add(monthEntry);
		entryLines.add(dayEntryText);
		entryLines.add(dayEntry);
		entryLines.add(startTimeLine);
		entryLines.add(endTimeLine);
		entryLines.add(buttonLine);
		
		this.add(entryLines, BorderLayout.CENTER);
	}
	
	public String getEventTitle()
	{
		return titleEntryBox.getText();
	}
	
	public String getEventLocation()
	{
		return locationEntryBox.getText();
	}
	
	public Calendar getStartTime()
	{
		int year = Integer.parseInt((String)this.yearEntry.getSelectedItem());
		int month = Integer.parseInt((String)this.monthEntry.getSelectedItem())-1;
		int day = Integer.parseInt((String)this.dayEntry.getSelectedItem());
		int hour = Integer.parseInt((String)this.startTimeHourEntry.getSelectedItem());
		int minute = Integer.parseInt((String)this.startTimeMinuteEntry.getSelectedItem());

		String AMorPM = (String)this.startTimeAMPMEntry.getSelectedItem();

		Calendar startCal = new GregorianCalendar(year, month, day, hour, minute);
				
		if (AMorPM.equals("AM"))
		{
			startCal.set(Calendar.AM_PM, Calendar.AM);
		}
		else
		{
			startCal.set(Calendar.AM_PM, Calendar.PM);
		}
		
		return startCal;
	}
	
	public Calendar getStartTimeForDayClick()
	{
		int hour = Integer.parseInt((String)this.startTimeHourEntry.getSelectedItem());
		int minute = Integer.parseInt((String)this.startTimeMinuteEntry.getSelectedItem());

		String AMorPM = (String)this.startTimeAMPMEntry.getSelectedItem();

		Calendar startCal = new GregorianCalendar(dateSelected.get(Calendar.YEAR), dateSelected.get(Calendar.MONTH), dateSelected.get(Calendar.DAY_OF_MONTH), hour, minute);
				
		if (AMorPM.equals("AM"))
		{
			startCal.set(Calendar.AM_PM, Calendar.AM);
		}
		else
		{
			startCal.set(Calendar.AM_PM, Calendar.PM);
		}
		
		return startCal;
	}
	
	public Calendar getEndTime()
	{
		int year = Integer.parseInt((String)this.yearEntry.getSelectedItem());
		int month = Integer.parseInt((String)this.monthEntry.getSelectedItem())-1;
		int day = Integer.parseInt((String)this.dayEntry.getSelectedItem());
		int hour = Integer.parseInt((String)this.endTimeHourEntry.getSelectedItem());
		int minute = Integer.parseInt((String)this.endTimeMinuteEntry.getSelectedItem());

		String AMorPM = (String)this.endTimeAMPMEntry.getSelectedItem();
		
		Calendar endCal = new GregorianCalendar(year, month, day, hour, minute);
	
		if (AMorPM.equals("AM"))
		{
			endCal.set(Calendar.AM_PM, Calendar.AM);
		}
		else
		{
			endCal.set(Calendar.AM_PM, Calendar.PM);
		}
		
		return endCal;	
	}
	
	public Calendar getEndTimeForDayClick()
	{
		int hour = Integer.parseInt((String)this.endTimeHourEntry.getSelectedItem());
		int minute = Integer.parseInt((String)this.endTimeMinuteEntry.getSelectedItem());

		String AMorPM = (String)this.endTimeAMPMEntry.getSelectedItem();

		Calendar endCal = new GregorianCalendar(dateSelected.get(Calendar.YEAR), dateSelected.get(Calendar.MONTH), dateSelected.get(Calendar.DAY_OF_MONTH), hour, minute);
				
		if (AMorPM.equals("AM"))
		{
			endCal.set(Calendar.AM_PM, Calendar.AM);
		}
		else
		{
			endCal.set(Calendar.AM_PM, Calendar.PM);
		}
		
		return endCal;
	}
	
	public CalendarGraphics getCalGraphics()
	{
		return calGraphics;
	}
}
