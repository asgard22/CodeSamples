package csci201assignment3.src;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Event 
{
	public String title;
	public String location;
	public Calendar start;
	public Calendar end;
	
	public Event(String title, String location, Calendar start, Calendar end)
	{
		this.title = title;
		this.location = location;
		this.start = start;
		this.end = end;
	}
	
	public void printInfo()
	{
		System.out.println(title);
		System.out.println(location);
		System.out.println(start.getTime());
		System.out.println(end.getTime());
	}
	
	public void setStart(Calendar start)
	{
		this.start = start;
	}
	
	public void setEnd(Calendar end)
	{
		this.end = end;
	}
	
	public Calendar getStart()
	{
		return start;
	}
	
	public String getStartTime()
	{
		SimpleDateFormat df = new SimpleDateFormat("HH:mm a");
		return df.format(this.getStart().getTime());
	}
	
	public Calendar getEnd()
	{
		return end;
	}
	
	public String getEndTime()
	{
		SimpleDateFormat df = new SimpleDateFormat("HH:mm a");
		return df.format(this.getEnd().getTime());
	}
	
	public String getEventDate()
	{
		SimpleDateFormat dfy = new SimpleDateFormat("YYYY");
		SimpleDateFormat dfm = new SimpleDateFormat("MM");
		SimpleDateFormat dfd = new SimpleDateFormat("dd");
		return (dfy.format(this.getEnd().getTime()) +"/" + dfm.format(this.getEnd().getTime()) + "/" + dfd.format(this.getEnd().getTime()));
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getLocation()
	{
		return location;
	}

	public void setTitle(String eventTitle) 
	{

		this.title = eventTitle;
	}

	public void setLocation(String eventLocation) 
	{
		this.location = eventLocation;
	}
}
