package csci201assignment4.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

public class Cell 
{
	public int[][] colors;
	
	private int row;
	private int col;
	private int upperYOfCell;
	private int leftXOfCell;
	private String type;
	private int degree;
	private Map parentMap;
	private Vector<Car> carsOnCell;
	private Vector<Car> carsInSimulation;
	private boolean hasCar;
	
	private Cell[][] cells;
	public boolean isBlank;

	public Cell(int row, int col, String type, int degree, Map parentMap)
	{
		this.colors = new int[3][3];
		
		this.parentMap = parentMap;
		
		this.upperYOfCell = this.getUpperY();
		this.leftXOfCell = this.getLeftX();
		
		this.row = row;
		this.col = col;
		
		this.type = type;
		this.degree = degree;
		
		this.carsOnCell = new Vector<Car>();
		this.carsInSimulation = parentMap.getCars();
		
		isBlank = false;
		
		this.cells = parentMap.getCells();
				
		if (type.equals("i"))
		{
			colors[0][0] = 0;
			colors[0][1] = 1;
			colors[0][2] = 0;
			colors[1][0] = 0;
			colors[1][1] = 1;
			colors[1][2] = 0;
			colors[2][0] = 0;
			colors[2][1] = 1;
			colors[2][2] = 0;
		}
		else if (type.equals("l"))
		{
			colors[0][0] = 0;
			colors[0][1] = 1;
			colors[0][2] = 0;
			colors[1][0] = 0;
			colors[1][1] = 1;
			colors[1][2] = 1;
			colors[2][0] = 0;
			colors[2][1] = 0;
			colors[2][2] = 0;
		}
		else if (type.equals("t"))
		{
			colors[0][0] = 0;
			colors[0][1] = 0;
			colors[0][2] = 0;
			colors[1][0] = 1;
			colors[1][1] = 1;
			colors[1][2] = 1;
			colors[2][0] = 0;
			colors[2][1] = 1;
			colors[2][2] = 0;
		}
		else if (type.equals("blank"))
		{
			colors[0][0] = 0;
			colors[0][1] = 0;
			colors[0][2] = 0;
			colors[1][0] = 0;
			colors[1][1] = 0;
			colors[1][2] = 0;
			colors[2][0] = 0;
			colors[2][1] = 0;
			colors[2][2] = 0;
			isBlank = true;
		}
		else if (type.equals("+"))
		{
			colors[0][0] = 0;
			colors[0][1] = 1;
			colors[0][2] = 0;
			colors[1][0] = 1;
			colors[1][1] = 1;
			colors[1][2] = 1;
			colors[2][0] = 0;
			colors[2][1] = 1;
			colors[2][2] = 0;
		}
	
		int numLeftTurns = degree/90;		
	
		for (int i = 0; i < numLeftTurns; i++)
		{
			rotateCell(degree);
		}
	}

	public void rotateCell(int degree)
	{
		
		int[][] rotatedColors = new int[3][3];
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				rotatedColors[i][j] = colors[3-j-1][i];
			}
		}
		
		this.colors = rotatedColors;
	}

	public void drawCell(Graphics g)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				
				int upperYOfSubCell = this.getUpperY()+(i*17);
				int leftXOfSubCell = this.getLeftX()+(j*17);
				
				if (colors[i][j] == 0)
				{
					g.setColor(Color.green);
				}
				else
				{
					g.setColor(Color.black);
				}
				
				g.fillRect(leftXOfSubCell, upperYOfSubCell, 17, 17);
			
				if (i == 1 && j == 1)
				{
					for (int k = 0; k < carsInSimulation.size(); k++)
					{
						Car c = carsInSimulation.get(k);
						if (c.getCell().equals(this))
						{
							if (c.visible)
							{
								c.drawCar(leftXOfSubCell, upperYOfSubCell, g);
							}
						}
					}
				}
			}
		}
	}

	public int getLeftX() 
	{
		int widthOfDisplay = parentMap.getWidth();
		
		int leftXOfGrid = ((widthOfDisplay-450)/2);
		
		int leftXOfCell = leftXOfGrid + ((col)*50);	
		
		return leftXOfCell;
	}
	
	public int getUpperY() 
	{
		int heightOfDisplay = parentMap.getHeight();
		
		int upperYOfGrid = ((heightOfDisplay-450)/2);
		
		int upperYOfCell = upperYOfGrid + ((row)*50);
		
		return upperYOfCell;
	}

	public void addCar(Car c)
	{
		hasCar = true;
		carsOnCell.add(c);
	}

	public void removeCar(Car c)
	{
		hasCar = false;
		carsOnCell.remove(c);
	}

	public String getType() 
	{
		return type;
	}

	public Cell[][] getCells() 
	{
		return cells;
	}
}
