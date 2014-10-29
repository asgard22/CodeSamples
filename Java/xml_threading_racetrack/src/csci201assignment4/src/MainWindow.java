package csci201assignment4.src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MainWindow extends JFrame
{
	public Map roadMap;
	
	private JPanel mainWindowContents;
	private JMenuBar mainWindowMenuBar;
	private Table carTable;
	private File xmlFile;
	private Vector<Car> carsInSimulation;
	
	public MainWindow()
	{
		super("Roadway Simulator");
		
		this.mainWindowContents = new JPanel(new BorderLayout());
		
		this.mainWindowMenuBar = new JMenuBar();
		mainWindowMenuBar.add(new FileOpener(this));
		mainWindowContents.add(mainWindowMenuBar, BorderLayout.NORTH);

		this.carsInSimulation = new Vector<Car>();
		
		this.roadMap = new Map(carsInSimulation);
		this.carTable = new Table();
		mainWindowContents.add(carTable, BorderLayout.EAST);
		mainWindowContents.add(roadMap, BorderLayout.CENTER);

		this.add(mainWindowContents);
			
		this.setMinimumSize(new Dimension(725,560));
	}
	
	public void setXMLFile(File file)
	{
		this.xmlFile = file;
		parseXMLFile();
		roadMap.setXMLOpened(true);
		this.revalidate();
		this.repaint();
	}

	public void parseXMLFile()
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			 documentBuilder = documentBuilderFactory.newDocumentBuilder();
			 Document doc = documentBuilder.parse(this.xmlFile);
			 doc.getDocumentElement().normalize();
			 NodeList rows = doc.getElementsByTagName("row");
			 for (int i = 0; i < rows.getLength(); i++)
			 {
				 Node row = rows.item(i);
				 if (row.getNodeType() == Node.ELEMENT_NODE)
				 {
					 Element rowElement = (Element) row;
					  				 
					 NodeList columns = rowElement.getElementsByTagName("tile");
					 for (int j = 0; j < columns.getLength(); j++)
					 {
						 Node column = columns.item(j);
						 if (column.getNodeType() == Node.ELEMENT_NODE)
						 {
							String type = column.getAttributes().getNamedItem("type").getNodeValue();
							int degree = Integer.valueOf(column.getAttributes().getNamedItem("degree").getNodeValue());
							roadMap.addCell(i, j, type, degree);
						 }
					 }
				 }
			 }
			 
			 NodeList cars = doc.getElementsByTagName("car");
			 for (int i = 0; i < cars.getLength(); i++)
			 {
				 String color = cars.item(i).getAttributes().getNamedItem("color").getNodeValue();
				 int AItype =  Integer.parseInt(cars.item(i).getAttributes().getNamedItem("ai").getNodeValue());
				 float speed = Float.parseFloat(cars.item(i).getAttributes().getNamedItem("speed").getNodeValue());
				 Element carElement = (Element) cars.item(i);
				 NodeList location = carElement.getElementsByTagName("location");
				 String rowLetter = location.item(0).getAttributes().getNamedItem("y").getNodeValue();
				 int row = -1;
				 if (rowLetter.equals("A"))
				 {
					 row = 1;
				 }
				 else if(rowLetter.equals("B"))
				 {
					 row = 2;
				 }
				 else if (rowLetter.equals("C"))
				 {
					 row = 3;
				 }
				 else if (rowLetter.equals("D"))
				 {
					 row = 4;
				 }
				 else if (rowLetter.equals("E"))
				 {
					 row = 5;
				 }
				 else if (rowLetter.equals("F"))
				 {
					 row = 6;
				 }
				 else if (rowLetter.equals("G"))
				 {
					 row = 7;
				 }
				 else if (rowLetter.equals("H"))
				 {
					 row = 8;
				 }
				 else if (rowLetter.equals("I"))
				 {
					 row = 9;
				 }
				 int col = Integer.parseInt(location.item(0).getAttributes().getNamedItem("x").getNodeValue());
				 Car c = null;
				 if (AItype == 1)
				 {
					 c = new Car1(color, row, col, AItype, speed, roadMap.getCells(), this);
				 }
				 else if (AItype == 2)
				 {
					 c = new Car2(color, row, col, AItype, speed, roadMap.getCells(), this);
				 }
				 else if (AItype == 3)
				 {
					 c = new Car3(color, row, col, AItype, speed, roadMap.getCells(), this);
				 }
				 else if (AItype == 4)
				 {
					 c = new Car4(color, row, col, AItype, speed, roadMap.getCells(), this);
				 }
				 this.addCar(c);
			 }
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addCar(Car c)
	{
		this.carsInSimulation.add(c);
		roadMap.addCar(c);
	}

	public synchronized void repaintTable() 
	{
		this.remove(carTable);
		this.carTable.resetTable();
		for (int i = 0; i < carsInSimulation.size(); i++)
		{
			Object[] tableInfo = new String[3];
			tableInfo[0] = "" + carsInSimulation.get(i).AItype;
			tableInfo[1] = "" + carsInSimulation.get(i).col;
			tableInfo[2] = "" + carsInSimulation.get(i).letterCol;
			carTable.addRow(tableInfo);
  		}
		mainWindowContents.add(carTable, BorderLayout.EAST);
		this.revalidate();
	}
}
