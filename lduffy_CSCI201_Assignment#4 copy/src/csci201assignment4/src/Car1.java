package csci201assignment4.src;

import java.util.Vector;

public class Car1 extends Car implements Runnable
{
	Cell cell;
	private int[] turnOptions;
	//0 is left turn, 1 is north turn, 2 is right turn, 3 is south turn
	private int turnIndex;
	private Thread blinkThread1;
	private boolean firstMoveTried;
	private int[] previousMove;
	private int blinks;
	private MainWindow mw;
	
	public Car1(String color, int row, int col, int AItype, float speed, Cell[][] cells, MainWindow mainWindow)
	{
		super(color, row, col, AItype, speed, cells, mainWindow);
		turnIndex = 0; 
		previousMove = new int[2];
		turnOptions = new int[4];
		firstMoveTried = false;
		blinks = 0;
		this.mw = mainWindow;
		this.cell = cells[row-1][col-1];
		blinkThread1 = new Thread(this);
		blinkThread1.start();
	}
	
	public void chooseDirection()
	{		
		////record which moves are valid (which directions have road)
		
		//left turn, second row, first column
		this.turnOptions[0] = this.cell.colors[1][0];
		//north turn, first row, second column
		this.turnOptions[1] = this.cell.colors[0][1];
		//right turn, second row, third column
		this.turnOptions[2] = this.cell.colors[1][2];
		//south turn, third row, second column
		this.turnOptions[3] = this.cell.colors[2][1];
		
		while(true)
		{
			//if this is the first move attempted
			if(!firstMoveTried)
			{
				//try to move left
				if(turnOptions[0] == 1)
				{
					turnIndex = 0;
					break;
				}
				firstMoveTried = true;
			}
			
			//if left move is not available, or if this is not the first move attempted
			if(this.nextClockwiseMoveValid())
			{
				changeDirectionByNeg90();
				 break;
			}
			else
			{
				changeDirectionByNeg90();
			}
		}
	}
	
	public boolean nextClockwiseMoveValid() 
	{
		int futureTurnIndex = 0;
		if (turnIndex != 3)
		{
			futureTurnIndex = turnIndex+1;
		}
		
		//if there is road in this direction
		if(turnOptions[futureTurnIndex] == 1)
		{
			//determine future coordinates if direction is taken
			int futureRow = this.row;
			int futureCol = this.col;
			switch(futureTurnIndex)
			{
				case 0:
					futureCol-=1;
					break;
				case 1:
					futureRow-=1;
					break;
				case 2:
					futureCol+=1;
					break;
				case 3:
					futureRow+=1;
					break;
			}
			
			//check whether potential coordinates have been visited already
			if (previousMove[0] == futureRow && previousMove[1] == futureCol)
			{
				return false;
			}
		}
		
		//if there is no road in the direction specified
		else
		{
			return false;
		}
		
		//there was a road, and the cell in that direction has not been visited
		return true;
	}

	public void changeDirectionByNeg90()
	{
		if(turnIndex == 3)
		{
			turnIndex = 0;
		}
		else
		{
			turnIndex++;
		}
	}
	
	public void move()
	{				
		previousMove[0] = this.row;
		previousMove[1] = this.col;
		//if a turn exists in the current direction the car is 'facing', move that way
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
				blinkThread1.sleep(milisPerChange);
				this.cell = cell;
				// TODO fix so it blinks 3 times
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
			mw.roadMap.repaintMap();
			blinks++;
		}
	}
}
