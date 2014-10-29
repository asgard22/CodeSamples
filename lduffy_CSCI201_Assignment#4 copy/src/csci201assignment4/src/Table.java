package csci201assignment4.src;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table extends JScrollPane
{
	private JTable tableDisplay;
	private DefaultTableModel dtm;
	
	public Table()
	{
		resetTable();
	 }
	
	public void resetTable()
	{
		this.tableDisplay = new JTable();
		tableDisplay.setPreferredScrollableViewportSize(new Dimension(200, 600)); 
	    tableDisplay.setFillsViewportHeight(true);
		this.dtm = new DefaultTableModel(0, 0);
		String header[] = new String[] {"CAR", "X", "Y"};
		dtm.setColumnIdentifiers(header);
		tableDisplay.setModel(dtm);
		tableDisplay.getTableHeader().setReorderingAllowed(false);
		tableDisplay.getTableHeader().setResizingAllowed(false);
	    this.getViewport().add(tableDisplay);
	    this.setSize(new Dimension(200,600));
	}
	
	public void addRow(Object[] tableInfo)
	{
		dtm.addRow(tableInfo);
	}
}
