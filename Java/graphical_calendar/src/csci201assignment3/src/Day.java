package csci201assignment3.src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Day extends JPanel implements MouseListener
{
	//whether this day has events
	public boolean hasEvents;
	//the events on this day
	private ArrayList<Event> events;
	//the date this day represents
	private Calendar date;
	//whether this day is day selected by the user
	private boolean isSelected;
	//whether this day is today
	private boolean isCurrentDate;
	private ArrayList<Day> days;
	private Calendar dateSelected;
	private Calendar currentDate;
	private CalendarGraphics calGraphics;
	private Color defaultBackground;
	
	public Day(Calendar date, CalendarGraphics calGraphics, boolean enabled, boolean fromMonthArrow)
	{
		super(new BorderLayout());
		
		//stores calendar graphics, its parent in the GUI
		this.calGraphics = calGraphics;
		
		//stores the date of the day
		this.date = date;
		
		events = new ArrayList<Event>();
		
		//checks if any event logged on the calendar occurs on this day
		int eventsSize = calGraphics.getEvents().size();
		for (int i = 0; i < eventsSize; i++)
		{
			Event someEvent = calGraphics.getEvents().get(i);
			Calendar eventStartTime = someEvent.getStart();
			Calendar eventEndTime = someEvent.getEnd();
			Calendar thisDayTime = this.getDate();
			System.out.println(thisDayTime.getTime());
			//if the current day's time is between the start and end of some event
			if (eventStartTime.get(Calendar.MONTH) == thisDayTime.get(Calendar.MONTH) && eventStartTime.get(Calendar.DAY_OF_MONTH) == thisDayTime.get(Calendar.DAY_OF_MONTH))
			{
				hasEvents = true;
				System.out.println(hasEvents);
			}
		}
		
		JLabel dayNum;
		
		//displays the number of the day in the month
		if(hasEvents)
		{
			dayNum = new JLabel(new SimpleDateFormat("dd").format(date.getTime()) + "*");
		}
		else
		{
			dayNum = new JLabel(new SimpleDateFormat("dd").format(date.getTime()));
		}
		dayNum.setFont(new Font("Helvetica", Font.BOLD, 30));
		this.add(dayNum, BorderLayout.NORTH);
		//stores the other day objects in the month
		this.days = calGraphics.getDays();
		//stores the current date selected
		this.dateSelected = calGraphics.getDateSelected();
		//stores the current time
		this.currentDate = calGraphics.getCurrentDate();
		
		if (this.date.get(Calendar.DAY_OF_MONTH) == dateSelected.get(Calendar.DAY_OF_MONTH) && this.date.get(Calendar.MONTH) == dateSelected.get(Calendar.MONTH) && this.date.get(Calendar.YEAR) == dateSelected.get(Calendar.YEAR))
		{
			System.out.println(this.date.getTime());
			isSelected = true;
			this.setBackground(Color.blue);	
		}
			
		//is this day the current date?
		if (this.date.get(Calendar.DAY_OF_MONTH) == calGraphics.getCurrentDate().get(Calendar.DAY_OF_MONTH) && this.date.get(Calendar.MONTH) == calGraphics.getCurrentDate().get(Calendar.MONTH) && this.date.get(Calendar.YEAR) == calGraphics.getCurrentDate().get(Calendar.YEAR))
		{
			//if this day is today, set its default background equal to yellow
			this.defaultBackground = Color.yellow;
			//if this is the first time
			if (!calGraphics.getConstructedYet())
			{
				this.setIsSelected(true);
				this.setBackground(Color.blue);	
				calGraphics.setConstructedYet(true);
			}
			else
			{
				if (!isSelected)
				{
					this.setBackground(this.defaultBackground);
				}
			}
		}
		else
		{
			//if this day is not today, set its default background equal to gray
			this.defaultBackground = new Color(210,210,210);
			if (isSelected)
			{
				this.setBackground(Color.blue);
			}
			else
			{
				this.setBackground(new Color(210,210,210));
			}
		}
		if (this.date.get(Calendar.DAY_OF_MONTH) == 1 && this.date.get(Calendar.MONTH) == calGraphics.getDateSelected().get(Calendar.MONTH))
		{
			if (fromMonthArrow)
			{
				isSelected = true;
				this.setBackground(Color.blue);
			}
		}
		//is the day in the current month?
		if (!enabled)
		{
			//if this day is not within the current month, it is not displayed as enabled
			dayNum.setEnabled(false);
			this.setBackground(new Color(210,210,210));
		}
		else
		{
			//if this day is in the current month, add a mouse listener
			this.addMouseListener(this);
		}
		Border border = BorderFactory.createLineBorder(new Color(155,155,155));
		this.setForeground((new Color(22,36,51)));
		this.setBorder(border);
		this.setOpaque(true);		
		//checks if any event logged on the calendar occurs on this day
	}
	
	public void setIsSelected(boolean tf)
	{
		isSelected = tf;
	}
	
	public Calendar getDate()
	{
		return date;
	}
	
	public boolean isSelected()
	{
		return isSelected;
	}
	
	public boolean getHasEvents()
	{
		return hasEvents;
	}
	
	public Color getDefaultBackground()
	{
		return this.defaultBackground;
	}
	
	public void setHasEvents(boolean tf)
	{
		hasEvents = tf;
	}
	
	public void addToEvents(Event event)
	{
		calGraphics.getEvents().add(event);
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if (!isSelected)
		{
			for (int i = 0; i < days.size(); i++)
			{
				Day someDay = days.get(i);
				if (someDay.isSelected())
				{
					someDay.setIsSelected(false);
					if (someDay.isEnabled())
					{
						if (someDay.isCurrentDate())
						{
							someDay.setBackground(Color.yellow);
						}
						else
						{
							someDay.setBackground(someDay.getDefaultBackground());
						}
					}
					else
					{
						someDay.setBackground(new Color(210,210,210));
					}
				}
			}
			
			calGraphics.setDateSelected(date);
			this.setIsSelected(true);
			this.setBackground(Color.blue);
			calGraphics.updateEventElements(calGraphics.getDateSelected());
		}
		else
		{
			  if (e.getClickCount() == 2) 
			  {
				    System.out.println("double clicked");
					calGraphics.deconstructHeaderElements();
					calGraphics.deconstructCalendarElements();
					calGraphics.deconstructEventElements();
					calGraphics.add(new AddEventGraphics(calGraphics, calGraphics.getDateSelected()));
					calGraphics.revalidate();
					calGraphics.repaint();
			  }			
		}
	}

	private boolean isCurrentDate() {
		if (this.date.get(Calendar.DAY_OF_MONTH) == calGraphics.getCurrentDate().get(Calendar.DAY_OF_MONTH) && this.date.get(Calendar.MONTH) == calGraphics.getCurrentDate().get(Calendar.MONTH) && this.date.get(Calendar.YEAR) == calGraphics.getCurrentDate().get(Calendar.YEAR))
		{
			return true;
		}
		
		return false;
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

	public ArrayList<Event> getEvents() 
	{
		return events;
	}

	public void addEvent(Event event) 
	{
		events.add(event);
	}
}
