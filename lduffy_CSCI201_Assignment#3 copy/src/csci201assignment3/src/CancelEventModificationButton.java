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

public class CancelEventModificationButton extends JLabel implements MouseListener 
{
	private ModifyEventGraphics modGraphics;
	
	public CancelEventModificationButton(ModifyEventGraphics modGraphics)
	{
		super("Cancel", SwingConstants.CENTER);
		this.addMouseListener(this);
		this.modGraphics = modGraphics;
		this.setFont(new Font("Helvetica", Font.BOLD, 15));
		this.setBackground(Color.orange);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.setOpaque(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
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
