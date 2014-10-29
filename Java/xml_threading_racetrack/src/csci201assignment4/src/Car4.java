package csci201assignment4.src;

import java.util.Random;
import java.util.Vector;

public class Car4 extends Car implements Runnable
{	
	// TODO implement runnable and AI
	private Cell cell;
	private Thread blinkThread4;
	private int turnIndex;
	private int blinks;
	private boolean moveMade;
	private Vector<int[]> previousMoves;
	private int[] turnOptions;
	private int[] timeSinceMoves;
	private int longestTimeIndex;
	private int secondLongestTimeIndex;
	private int thirdLongestTimeIndex;
	private int fourthLongestTimeIndex;
	private MainWindow mw;

	public Car4(String color, int row, int col, int AItype, float speed, Cell[][] cells, MainWindow mainWindow)
	{
		super(color, row, col, AItype, speed, cells, mainWindow);
		this.cell = cells[row-1][col-1];
		turnIndex = 0;
		blinks = 0;
		previousMoves = new Vector<int[]>();
		turnOptions = new int[4];
		timeSinceMoves = new int[4];
		for(int i = 0; i < 4; i++)
		{
			timeSinceMoves[i] = 0;
		}
		moveMade = false;
		longestTimeIndex = 0;
		secondLongestTimeIndex = 0;
		thirdLongestTimeIndex = 0;
		this.mw = mainWindow;
		blinkThread4 = new Thread(this);
		blinkThread4.start();
	}
	
	public Cell getCell()
	{
		return this.cell;
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
	
	public void run() 
	{
		while(true)
		{
            try {
            	Cell cell = this.cell;
				blinkThread4.sleep(milisPerChange);
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
	
		////make random move at first
		
		if(!moveMade)
		{
			moveMade = true;
			while(true)
			{
				Random r = new Random();
				int index = r.nextInt(4);
				if (turnOptions[index] == 1)
				{
					turnIndex = index;
					return;
				}
			}
		}
		
		this.findFourthLongestTime();
		
		if(turnOptions[longestTimeIndex] == 1)
		{
			turnIndex = longestTimeIndex;
			timeSinceMoves[longestTimeIndex] = 0;
			return;
		}
		else if(turnOptions[secondLongestTimeIndex] == 1)
		{
			turnIndex = secondLongestTimeIndex;
			timeSinceMoves[secondLongestTimeIndex] = 0;
			return;
		}
		else if(turnOptions[thirdLongestTimeIndex] == 1)
		{
			System.out.println("turn verified.");
			turnIndex = thirdLongestTimeIndex;
			timeSinceMoves[thirdLongestTimeIndex] = 0;
			return;
		}
		else if(turnOptions[fourthLongestTimeIndex] == 1)
		{
			System.out.println("turn verified.");
			turnIndex = fourthLongestTimeIndex;
			timeSinceMoves[fourthLongestTimeIndex] = 0;
			return;
		}
	}

	private void findLongestTime() {
		int longestTime = -1;
		for(int i = 0; i < timeSinceMoves.length; i++)
		{
			if(timeSinceMoves[i] > longestTime)
			{
				longestTime = timeSinceMoves[i];
				longestTimeIndex = i;
				incrementAllExcept(i);
			}
		}
	}
	
	private void findSecondLongestTime() {
		int secondLongestTime = -1;
		findLongestTime();
		for(int i = 0; i < timeSinceMoves.length; i++)
		{
			if(timeSinceMoves[i] > secondLongestTime && i != longestTimeIndex)
			{
				secondLongestTime = timeSinceMoves[i];
				secondLongestTimeIndex = i;
				incrementAllExcept(i);
			}
		}
	}
	
	private void findThirdLongestTime() {
		int thirdLongestTime = -1;
		findSecondLongestTime();
		for(int i = 0; i < timeSinceMoves.length; i++)
		{
			if(timeSinceMoves[i] > thirdLongestTime && i != longestTimeIndex && i != secondLongestTimeIndex)
			{
				thirdLongestTime = timeSinceMoves[i];
				thirdLongestTimeIndex = i;
				incrementAllExcept(i);
			}
		}
	}
	
	private void findFourthLongestTime() {
		int fourthLongestTime = -1;
		findThirdLongestTime();
		for(int i = 0; i < timeSinceMoves.length; i++)
		{
			if(timeSinceMoves[i] > fourthLongestTime && i != longestTimeIndex && i != secondLongestTimeIndex && i != thirdLongestTimeIndex)
			{
				fourthLongestTime = timeSinceMoves[i];
				fourthLongestTimeIndex = i;
				incrementAllExcept(i);
			}
		}
	}
	
	private void incrementAllExcept(int index)
	{
		for(int i = 0; i < timeSinceMoves.length; i++)
		{
			if (i != index)
			{
				timeSinceMoves[i]++;
			}
		}
	}
}

