package csci201assignment3.src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class AboutGraphics extends JFrame
{
	public AboutGraphics()
	{
		JPanel organizer = new JPanel(new BorderLayout());
		JLabel nameAndLectureTimeAndTimeFinished = new JLabel("<html>NAME: Liam  <br>   LECTURE TIME: 8:30 am, MWF  <br>    DATE FINISHED: 00/00/0000<html>", SwingConstants.CENTER);
		nameAndLectureTimeAndTimeFinished.setFont(new Font("Helvetica", Font.BOLD, 20));
		nameAndLectureTimeAndTimeFinished.setBackground(Color.orange);
		nameAndLectureTimeAndTimeFinished.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		nameAndLectureTimeAndTimeFinished.setOpaque(true);
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("liam.jpg"));
		} catch (IOException e) {
			System.out.println("problem");
		}
		
		Image newimg = img.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
												
		ImageIcon imgicn = new ImageIcon(newimg);
		JLabel image = new JLabel(imgicn);
		
		organizer.add(nameAndLectureTimeAndTimeFinished, BorderLayout.NORTH);
		organizer.add(image, BorderLayout.CENTER);
		
		this.add(organizer);
	}
}
