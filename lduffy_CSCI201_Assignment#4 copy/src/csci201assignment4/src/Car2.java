package csci201assignment4.src;

import java.util.Random;
import java.util.Vector;

public class Car2 extends Car implements Runnable
{
	private Cell cell;
	private Thread blinkThread2;
	private Vector<int[]> previousMoves;
	private int[] turnOptions;
	private boolean[] validOptions;
	private int turnIndex;
	private int blinks;
	private boolean moved;
	private MainWindow mw;
	
	// TODO implement runnable and AI
	public Car2(String color, int row, int col, int AItype, float speed, Cell[][] cells, MainWindow mainWindow)
	{
		super(color, row, col, AItype, speed, cells, mainWindow);
		turnIndex = 0;
		previousMoves = new Vector<int[]>();
		turnOptions = new int[4];
		validOptions = new boolean[4];
		for (int i = 0; i < validOptions.length; i++)
		{
			validOptions[i] = true;
		}
		this.mw = mainWindow;
		this.cell = cells[row-1][col-1];
		moved = false;
		blinkThread2 = new Thread(this);
		blinkThread2.start();
	}

	public void move()
	{
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
				blinkThread2.sleep(milisPerChange);
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
			mw.roadMap.repaintMap();
			blinks++;
		}
	}

	private void chooseDirection() 
	{
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
		
		if(!moved)
		{
			moved = true;
			if(turnOptions[0] == 1)
			{
				turnIndex = 0;
				return;
			}
		}
		
		////record which moves would revisit cells	
		this.checkForRepeats();
				
		//check if any options are still available
		if(noOptionsLeft())
		{
			//if no options remain, clear the list of previous moves, and reset possible moves based solely on
			//available road
			previousMoves.removeAllElements();
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
				Random r = new Random();
				int nextMove = r.nextInt(4);
				if (turnOptions[nextMove] == 1)
				{
					turnIndex = nextMove;
					break;
				}
			}
		}
		else
		{
			while(true)
			{
				Random r = new Random();
				int nextMove = r.nextInt(4);
				if (turnOptions[nextMove] == 1)
				{
					turnIndex = nextMove;
					break;
				}
			}
		}
	}
	
	public boolean noOptionsLeft() 
	{
		for(int i = 0; i < turnOptions.length; i++)
		{
			if (turnOptions[i] == 1)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public boolean randomMoveValid(int turnIndex)
	{
		while(true)
		{
			//if there is road in this direction
			if (turnOptions[turnIndex] == 1)
			{
				//if this is a valid move to make
				if(validOptions[turnIndex])
				{
					//determine future coordinates if direction is taken
					int futureRow = this.row;
					int futureCol = this.col;
					switch(turnIndex)
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
					for(int i = 0; i < previousMoves.size(); i++)
					{
						if (previousMoves.get(i)[0] == futureRow && previousMoves.get(i)[1] == futureCol)
						{
							validOptions[turnIndex] = false;
						}
					}
				}
			}
			else
			{
				validOptions[turnIndex] = false;
			}
		}
	}
	
	public void checkForRepeats()
	{
		int futureRow = this.row;
		int futureCol = this.col;
		
		futureCol-=1;
		for(int i = 0; i < previousMoves.size(); i++)
		{
			if (previousMoves.get(i)[0] == futureRow && previousMoves.get(i)[1] == futureCol)
			{
				turnOptions[0] = 0;
			}
		}
		futureCol+=1;
		
		futureRow-=1;
		for(int i = 0; i < previousMoves.size(); i++)
		{
			if (previousMoves.get(i)[0] == futureRow && previousMoves.get(i)[1] == futureCol)
			{
				turnOptions[1] = 0;
			}
		}
		futureRow+=1;
		
		futureCol+=1;
		for(int i = 0; i < previousMoves.size(); i++)
		{
			if (previousMoves.get(i)[0] == futureRow && previousMoves.get(i)[1] == futureCol)
			{
				turnOptions[2] = 0;
			}
		}
		futureCol-=1;
		
		futureRow+=1;
		for(int i = 0; i < previousMoves.size(); i++)
		{
			if (previousMoves.get(i)[0] == futureRow && previousMoves.get(i)[1] == futureCol)
			{
				turnOptions[3] = 0;
			}
		}
		futureRow-=1;
	}
}
