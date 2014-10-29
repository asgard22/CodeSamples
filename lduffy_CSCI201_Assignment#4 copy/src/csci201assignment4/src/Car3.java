package csci201assignment4.src;

import java.util.Random;
import java.util.Vector;

public class Car3 extends Car implements Runnable
{
	// TODO implement runnable and AI
	private Cell cell;
	private Thread blinkThread3;
	private int blinks;
	private int turnIndex;
	private int[] turnOptions;
	private Vector<int[]> previousMoves;
	private boolean goingEast;
	private int eastMostCol;
	private int westMostCol;
	private MainWindow mw;

	public Car3(String color, int row, int col, int AItype, float speed, Cell[][] cells, MainWindow mainWindow)
	{
		super(color, row, col, AItype, speed, cells, mainWindow);
		this.cell = cells[row-1][col-1];
		this.blinks = 0;
		this.turnIndex = 0;
		this.turnOptions = new int[4];
		this.previousMoves = new Vector<int[]>();
		this.goingEast = true;
		this.eastMostCol = 0;
		this.westMostCol = 0;
		
		determineEastMostCol(cells);
		determineWestMostCol(cells);
		
		this.mw = mainWindow;
		
		this.blinkThread3 = new Thread(this);
		this.blinkThread3.start();
	}

	private void determineEastMostCol(Cell[][] cells) {
		for(int i = 8; i >= 0; i--)
		{
			for(int j = 0; j < 9; j++)
			{
				if(!cells[i][j].getType().equals("blank"))
				{
					this.eastMostCol = i;
					return;
				}
			}
		}
	}

	private void determineWestMostCol(Cell[][] cells) {
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(!cells[i][j].getType().equals("blank"))
				{
					this.westMostCol = i;
					return;
				}
			}
		}
	}
	
	public void move()
	{	
		
		switch (turnIndex)
		{
			case 0:
				//go left
				this.col-=1;
				break;
			case 1:
				//go north
				this.row-=1;
				break;
			case 2:
				//go right
				this.col+=1;
				break;
			case 3:
				//go south
				this.row+=1;
				break;
		}
		letterCol = letterCols[this.row-1];
		
		this.cell = cells[this.row-1][this.col-1];
	}
	
	public Cell getCell()
	{
		return this.cell;
	}
	
	public void run() 
	{
		while(true)
		{
            try {
            	Cell cell = this.cell;
				blinkThread3.sleep(milisPerChange);
				this.cell = cell;
				if (blinks == 6)
				{
					this.chooseDirection();
					this.move();
					mw.repaintTable();
					blinks = 0;
					milisSinceMove = 0;
				}
				else
				{
					milisSinceMove+=milisPerChange;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (this.visible)
			{
				this.visible = false;
			}
			else
			{
				this.visible = true;
			}
			blinks++;
			mw.roadMap.repaintMap();
		}
	}

	private void chooseDirection() 
	{
		//if traveling east
		if(goingEast)
		{
			//if you've reached the eastmost column
			if(this.col == eastMostCol)
			{     
				//switch directions to west
				goingEast = false;
			}
		}
		//if traveling west
		else
		{
			//if you've reached the westmost column
			if(this.col == westMostCol)
			{
				//switch directions to east
				goingEast = true;
			}
		}
		
		////record current position as previous move
		
		int[] currentPos = {this.row, this.col};
		previousMoves.add(currentPos);
		
		////record which moves are valid (which directions have road)
		
		//left turn, second row, first column
		this.turnOptions[0] = this.cell.colors[1][0];
		//north turn, first row, second column
		this.turnOptions[1] = this.cell.colors[0][1];
		//right turn, second row, third column
		this.turnOptions[2] = this.cell.colors[1][2];
		//south turn, third row, second column
		this.turnOptions[3] = this.cell.colors[2][1];
		
		////try to move in the direction you want to move, if possible
		
		if(goingEast)
		{
			if(turnOptions[2] == 1)
			{
				turnIndex = 2;
				return;
			}
		}
		else
		{
			if(turnOptions[0] == 1)
			{
				turnIndex = 0;
				return;
			}
		}
		
		////if the direction you want to move is not available, pick a random direction and go in that direction
		while(true)
		{
			Random r = new Random();
			int nextMove = r.nextInt(4);
			if (turnOptions[nextMove] == 1)
			{
				turnIndex = nextMove;
				return;
			}
		}	
	}
}

