package csci201assignment4.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class Car
{
	public Color color;
	public int row;
	public int col;
	public int AItype;
	public float speed;
	public boolean visible;
	public String letterCol;
	public String[] letterCols;
		
	protected long milisPerChange;
	protected long milisSinceMove;
	protected Cell[][] cells;
		
	public Car(String color, int row, int col, int AItype, float speed, Cell[][] cells, MainWindow mainWindow)
	{
		if (color.equals("black"))
		{
			this.color = Color.black;
		}
		else if(color.equals("blue"))
		{
			this.color = Color.blue;
		}
		else if(color.equals("cyan"))
		{
			this.color = Color.cyan;
		}
		else if (color.equals("grey") || color.equals("gray"))
		{
			this.color = Color.gray;
		}
		else if (color.equals("green"))
		{
			this.color = Color.green;
		}
		else if (color.equals("magenta"))
		{
			this.color = Color.magenta;
		}
		else if (color.equals("orange"))
		{
			this.color = Color.orange;
		}
		else if (color.equals("pink"))
		{
			this.color = Color.pink;
		}
		else if (color.equals("red"))
		{
			this.color = Color.red;
		}
		else if (color.equals("yellow"))
		{
			this.color = Color.yellow;
		}
		else
		{
			this.color = Color.white;
		}
		this.row = row;
		this.col = col;
		this.AItype = AItype;
		this.speed = speed;
		
		float blinksPerSecond = speed*3;
		float boolTransitionsPerSecond = blinksPerSecond*2;
		float secondsBetweenTransitions = 1/boolTransitionsPerSecond;
		this.milisPerChange = (long) (1000*secondsBetweenTransitions);
		this.milisSinceMove = 0;
		this.visible = true;
		this.cells = cells;
		
		letterCol = new String();
		letterCols = new String[9];
		String[] letterOptions = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
		
		for(int i = 0; i < letterOptions.length; i++)
		{
			letterCols[i] = letterOptions[i];
		}
		
		letterCol = letterCols[this.row-1];
	}
	
	public boolean existsAt(int row, int col)
	{
		if (this.row == row && this.col == col)
		{
			return true;
		}
		return false;
	}

	public void drawCar(int leftXOfSubCell, int upperYOfSubCell, Graphics g) 
	{
		g.setColor(this.color);
		g.fillRect(leftXOfSubCell, upperYOfSubCell, 17, 17);
		g.setColor(Color.black);
		g.drawRect(leftXOfSubCell, upperYOfSubCell, 16, 16);
		g.setFont(new Font("Helvetica", Font.BOLD, 15));
		g.drawString(Integer.toString(this.AItype), leftXOfSubCell+5, upperYOfSubCell+15);
	}

	public abstract Cell getCell();

	public float getColor() {
		return this.speed;
	}
}
