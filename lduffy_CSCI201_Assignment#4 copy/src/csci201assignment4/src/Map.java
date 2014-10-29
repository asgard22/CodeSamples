package csci201assignment4.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

public class Map extends JPanel
{
	private boolean xmlFileOpened;
	private Cell[][] cells;
	private boolean drawn;
	private Graphics mapGraphics;
	private Vector<Car> carsInSimulation;
	
	public Map(Vector<Car> carsInSimulation)
	{
		super();
		xmlFileOpened = false;
		cells = new Cell[9][9];
		drawn = false;
		this.carsInSimulation = carsInSimulation;
	}
	
	public void setXMLOpened(boolean tf)
	{
		this.xmlFileOpened = tf;
	}
	
	public void addCell(int row, int col,  String type, int degree)
	{
		Cell cell = new Cell(row, col, type, 360-degree, this);
		
		cells[row][col] = cell;
	}

	public void paintComponent(Graphics g)
	{
		if (xmlFileOpened)
		{
			drawCoordinates(g);
			drawGrid(g);
			if (!drawn)
			{
				drawn = true;
				mapGraphics = g;
			}
		}
	}
		
	public void drawCoordinateMarker(Graphics g, int row, int col, String symbol)
	{
		int heightOfDisplay = this.getHeight();
		int widthOfDisplay = this.getWidth();
		
		int upperYOfGrid = ((heightOfDisplay-475)/2);
		int leftXOfGrid = ((widthOfDisplay-500)/2);
		
		int upperYOfCell = upperYOfGrid + ((row)*50);
		int leftXOfCell = leftXOfGrid + ((col)*50);
		
		g.setFont(new Font("Arial", Font.BOLD, 20));
		
		g.drawString(symbol, leftXOfCell, upperYOfCell);
	}
	
	public void drawCoordinates(Graphics g)
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (i == 0)
				{
					if (j != 0)
					{
						drawCoordinateMarker(g, i, j, "" + j);
					}
				}
				else if (j == 0)
				{
					if (i != 0)
					{
						switch(i)
						{
							case 1:
								drawCoordinateMarker(g, i, j, "A");
								break;
							case 2:
								drawCoordinateMarker(g, i, j, "B");
								break;
							case 3:
								drawCoordinateMarker(g, i, j, "C");
								break;
							case 4:
								drawCoordinateMarker(g, i, j, "D");
								break;
							case 5:
								drawCoordinateMarker(g, i, j, "E");
								break;
							case 6:
								drawCoordinateMarker(g, i, j, "F");
								break;
							case 7:
								drawCoordinateMarker(g, i, j, "G");
								break;
							case 8:
								drawCoordinateMarker(g, i, j, "H");
								break;
							case 9:
								drawCoordinateMarker(g, i, j, "I");
								break;
						}
					}
				}
			}
		}
	}
	
	public void drawGrid(Graphics g)
	{
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				cells[i][j].drawCell(g);
				g.setColor(Color.black);				
				g.drawRect(cells[i][j].getLeftX(), cells[i][j].getUpperY(), 50,50);
			}
		}
	}

	public void addCar(Car c)
	{
		int row = c.row;
		int col = c.col;
	}

	public void repaintMap() 
	{
		this.drawCoordinates(mapGraphics);
		this.drawGrid(mapGraphics);
		this.repaint();
	}

	public Cell[][] getCells() 
	{
		return cells;
	}

	public Vector<Car> getCars() 
	{
		return carsInSimulation;
	}
}
