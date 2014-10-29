package csci201assignment4.src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

public class FileOpener extends JMenuItem implements ActionListener
{
	private File selectedFile;
	private MainWindow mw;
	
	public FileOpener(MainWindow mw)
	{
		super("Open File...");
		this.mw = mw;
		this.addActionListener(this);
		this.selectedFile = null;
	}

	public void actionPerformed(ActionEvent e) 
	{
		JFileChooser fc = new JFileChooser();
        int returnValue = fc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          selectedFile = fc.getSelectedFile();
          mw.setXMLFile(selectedFile);
        }
	}
}
