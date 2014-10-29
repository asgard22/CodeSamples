package csci201assignment3.src;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class PrevMonthArrowButton extends JLabel implements MouseListener
{
	private CalendarGraphics calGraphics;
	
	public PrevMonthArrowButton(CalendarGraphics calGraphics)
	{
		this.addMouseListener(this);
		this.calGraphics = calGraphics;
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("left_arrow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
												
		ImageIcon imgicn = new ImageIcon(img);
		
		this.setIcon(imgicn);
	}

	public void mouseClicked(MouseEvent e) 
	{
		calGraphics.getDateSelected().add(Calendar.MONTH, -1);
		calGraphics.getDateSelected().set(Calendar.DAY_OF_MONTH, 1);
		calGraphics.updateHeaderElements(calGraphics.getDateSelected());
		calGraphics.updateCalendarElements(calGraphics.getDateSelected(), true);
		calGraphics.updateEventElements(calGraphics.getDateSelected());
		calGraphics.getDateSelected().set(Calendar.DAY_OF_MONTH, 1);
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
