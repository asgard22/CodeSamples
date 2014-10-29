package csci201assignment2.src;

import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

public class Bank 
{
	private static User currentUser;
	private static boolean userBanking;
	
	//executes banking program
	public static void main (String args[])
	{		
		userBanking = true;
		currentUser = new User();
		Scanner in = new Scanner(System.in);
		
		//program loops until until user is entirely done with the program.
		while (userBanking)
		{
			System.out.println("");
			//if user is logged in
			if(currentUser.isLoggedIn())
			{
				//displays menu of options for a logged in user
				loggedInMenu(in);
			}
			//if user is not logged in yet
			else
			{
				System.out.println("Welcome to the bank.");
				//displays menu of options for a logged out user
				loggedOutMenu(in);
			}
		}
		in.close();
	}
	
	//presents the options a user has if they are logged in or out
	private static void loggedInMenu(Scanner in)
	{		
		//displays menu of options to user
		System.out.println("    1) View Account Information");
		System.out.println("    2) Make a Deposit");
		System.out.println("    3) Make a Withdrawal");
		System.out.println("    4) Determine Balance in x Years");
		System.out.println("    5) Logout");
		System.out.println("    6) Quit Program");
		System.out.println("Which action would you like to take? ");
		
		//records user response, and makes sure it is valid
		int userResponse = 0;
		if (in.hasNextInt())
		{
			String line = in.nextLine();
			try 
			{
				userResponse = Integer.parseInt(line);
				if (userResponse < 0)
				{
					System.out.println("That is not valid input. Please enter a positive number.");
					return;
				}
			}
			catch (Exception e)
			{
				System.out.println("That is not valid input. Please enter a number.");
				return;
			}
		}
		else
		{
			System.out.println("That is not valid input. Please enter a number.");
			in.nextLine();
			return;
		}
		
		//executes a given part of the program based on user input
		switch(userResponse)
		{
			case 1:
				//displays users account information
				viewAccountInfo();
				break;
			case 2:
				//makes deposit for user
				makeDeposit(in);
				break;
			case 3:
				//makes withdrawal for user
				makeWithdrawal(in);
				break;
			case 4:
				//determines users balance within x years
				determineBalance(in);
				break;
			case 5:
				//logs user out
				System.out.println("Thank you for coming into the bank!");
				currentUser.updateOut();
				currentUser.logOut();
				break;
			case 6:
				//quits program
				System.out.println("Goodbye!");
				userBanking = false;
				break;
			default:
				//tells user that they entered the wrong number
				System.out.println("You did not enter a valid number. Please enter a number from 1-5.");
				return;
		}
	}
	private static void loggedOutMenu(Scanner in)
	{
		//scanner for reading user input
		int userResponse = 0;
		
		while (true)
		{
			System.out.println("    1) I need to log into an existing account.");
			System.out.println("    2) I need to create an account.");
			System.out.println("    3) I need to quit this program.");
			System.out.println("Which option represents your needs?: ");
			if (in.hasNextInt())
			{
				String line = in.nextLine();
				try 
				{
					userResponse = Integer.parseInt(line);
					if (userResponse < 0)
					{
						System.out.println("That is not valid input. Plese enter a positive number.");
						continue;
					}
					break;
				}
				catch (Exception e)
				{
					System.out.println("That is not valid input. Please enter a number.");
					continue;
				}
			}
			else
			{
				System.out.println("That is not valid input. Please enter a number");
				in.nextLine();
				continue;
			}		
		}
		
		//executes a given part of the program based on user input		
		switch(userResponse)
		{
			case 1:
				//logs user in
				logIn(in);
				break;
			case 2:
				//creates account for user
				createAccount(in);
				break;
			case 3:
				userBanking = false;
				System.out.println("Goodbye!");
				break;
			default:
				//tells user that they entered the wrong number
				System.out.println("You did not enter a valid number. Please enter a number from 1-3.");
				break;
		}
	}

	//options if a user is logged out
	private static void logIn(Scanner in)
	{
		//file reader and buffered reader for checking if file exists and checking password
		FileReader fileReader = null;
		String fileName = "";
		BufferedReader bufferedReader;
		
		//loops the username prompt until the user enters a valid username, or quits to the main menu using 'q'
		while (true)
		{
			System.out.println("");
			//prompt for username
			System.out.print("Username: ");
			//check if that username exists
			String userResponse = in.nextLine();
			//check if the user wants to go back to the main menu
			if (userResponse.equals("q"))
			{
				//if they do, go back
				return;
			}
			//try to open the user file associated with the username
			File file = new File(userResponse);
			fileName = userResponse;
			
			if (file.exists())
			{
				break;
			}
			else
			{
				System.out.println("That username does not match an account at our bank.");
				System.out.println("Please try again, or enter 'q' to return to the main menu.");		
			}
		}

		//loops the password prompt until the user enters a valid password, or quits to the main menu using 'q'
		while(true)
		{
			//prompt for password
			System.out.print("Password: ");
			
			String userResponse = in.nextLine();
			
			if (userResponse.equals("q"))
			{
				return;
			}
			
			//store the line of the file containing the password
			try {
				fileReader = new FileReader(fileName);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			bufferedReader = new BufferedReader(fileReader);
			String password = "";
			try {
				password = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//if the password entered by the user matches the password given by the user
			if (password.equals(userResponse))
			{
				//transfer the contents of the file to the user, logging them in.
				currentUser.updateIn(fileName);
				System.out.println("Welcome to your accounts, " + currentUser.getName() + ".");
				break;
			}
			else
			{
				System.out.println("That password does not match the password of the username you provided.");
				System.out.println("Please try again, or press 'q' to exit to the main menu");
			}
		}
	}
	private static void createAccount(Scanner in)
	{
		//file reader and buffered reader for checking if file exists and checking password
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		
		//loops the username prompt until the user enters a valid username, or quits to the main menu using 'q'
		while (true)
		{
			System.out.println("");
			//prompt for username
			System.out.print("Username: ");
			//check if that username exists
			try
			{
				String userResponse = in.nextLine();
				//check if the user wants to go back to the main menu
				if (userResponse.equals("q"))
				{
					//if they do, go back
					return;
				}
				
				File file = new File(userResponse);
				
				if (file.exists())
				{
					System.out.println("I’m sorry, but the username " +  userResponse + " is already associated with an account");
					System.out.println("Please try again (or enter ‘q’ to return to the main menu).");
					continue;
				}
				else
				{
					System.out.println("Great, that username is not in use!");
					//if the file doesn't exist, create it
					fileWriter = new FileWriter(userResponse);
					//if no exception was thrown, the file opened.
					break;
				}
			}
			//if the file isn't found
			catch (IOException e)
			{
				System.out.println("File could not be written to the current directory.");
			}
		}
		
		//if the user has entered a valid username, prompt for password
		System.out.print("Password: ");
		
		String userResponse = in.nextLine();
		
		//store the password
		printWriter = new PrintWriter(fileWriter);
		printWriter.println(userResponse);
		
		//get user's desired checking balance
		double initialChecking = 0;
		while (true)
		{
			System.out.println("How much would you like to deposit in checking?");
			if (in.hasNextDouble())
			{
				String line = in.nextLine();
				try 
				{
					initialChecking = Double.parseDouble(line);
					if (initialChecking < 0)
					{
						System.out.println("That is not valid input. Please enter a positive number.");
						continue;
					}
					break;
				}
				catch (Exception e)
				{
					System.out.println("That is not valid input. Please enter a number.");
				}
			}
			else
			{
				in.nextLine();
				System.out.println("That is not valid input. Please enter a number");
			}
		}
		
		//get user's desired savings balance
		double initialSavings = 0;
		while (true)
		{
			System.out.println("How much would you like to deposit in savings?");
			if (in.hasNextDouble())
			{
				String line = in.nextLine();
				try 
				{
					initialSavings = Double.parseDouble(line);
					if (initialSavings < 0)
					{
						System.out.println("That is not valid input. Plese enter a positive number.");
						continue;
					}
					break;
				}
				catch (Exception e)
				{
					System.out.println("That is not valid input. Please enter a number.");
				}
			}
			else
			{
				in.nextLine();
				System.out.println("That is not valid input. Please enter a number");
			}
		}
		
		//record empty accounts for the user
		printWriter.println(String.valueOf(initialChecking));
		printWriter.println(String.valueOf(initialSavings));

		printWriter.flush();
		printWriter.close();
		try {
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Thank you, your account has been created.");
	}

	//options if a user is logged in
	private static void viewAccountInfo()
	{
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("You have a checking account with a balance of $" + df.format(currentUser.getCheckingBalance()));
		System.out.println("You have a " + currentUser.getSavingsType() + " account with a balance of $" + df.format(currentUser.getSavingsBalance()));
	}
	private static void makeDeposit(Scanner in)
	{
		int userResponse = 0;
		DecimalFormat df = new DecimalFormat("#.00");
		
		System.out.println("Here are the accounts you have:");
		
		while (true)
		{
			System.out.println("    1) Checking");
			System.out.println("    2) " + currentUser.getSavingsType());
			System.out.println("Into which account would you like to make a deposit? ");
			if (in.hasNextInt())
			{
				String line = in.nextLine();
				try 
				{
					userResponse = Integer.parseInt(line);
					if (userResponse < 0)
					{
						System.out.println("That is not valid input. Plese enter a positive number.");
						continue;
					}
				}
				catch (Exception e)
				{
					System.out.println("That is not valid input. Please enter a number.");
					continue;
				}
			}
			else
			{
				System.out.println("That is not valid input. Please enter a number");
				in.nextLine();
				continue;
			}		
			
			switch(userResponse)
			{
				case 1:
					double checkingDeposit;
					while (true)
					{
						System.out.println("How much would you like to deposit into your checking account?");
						if (in.hasNextDouble())
						{
							String line = in.nextLine();
							try
							{
								checkingDeposit = Double.parseDouble(line);
								if (checkingDeposit < 0)
								{
									System.out.println("That is not valid input. Please enter a positive number.");
									continue;
								}
								currentUser.addToChecking(checkingDeposit);
								currentUser.updateSavingsType();
								System.out.println("$" + df.format(checkingDeposit) + " deposited into your checking account.");
								return;
							}
							catch (Exception e)
							{
								System.out.println("That is not valid input. Please enter a number");
								continue;
							}
						}
						else
						{
							in.nextLine();
							System.out.println("That is not valid input. Please enter a number");
						}
					}
				case 2:
					double savingsDeposit;
					while (true)
					{
						System.out.println("How much to deposit into your " + currentUser.getSavingsType() + " account?");
						if (in.hasNextDouble())
						{
							String line = in.nextLine();
							try
							{
								savingsDeposit = Double.parseDouble(line);
								if (savingsDeposit < 0)
								{
									System.out.println("That is not valid input. Please enter a positive number.");
									continue;
								}
								currentUser.addToSavings(savingsDeposit);
								currentUser.updateSavingsType();
								System.out.println("$" + df.format(savingsDeposit) + " deposited into your " + currentUser.getSavingsType() + " account.");
								return;
							}
							catch (Exception e)
							{
								System.out.println("That is not valid input. Please enter a number");
								continue;
							}
						}
						else
						{
							in.nextLine();
							System.out.println("That is not valid input. Please enter a number");
						}
					}
				default:
					System.out.println("That is not a valid number. Please enter 1 or 2.");
					continue;
			}
		}
	}
	private static void makeWithdrawal(Scanner in)
	{
		int userResponse = 0;
		DecimalFormat df = new DecimalFormat("#.00");
		
		System.out.println("Here are the accounts you have:");
		System.out.println("    1) Checking");
		System.out.println("    2) " + currentUser.getSavingsType());	

		while (true)
		{
			System.out.println("From which account would you like to withdraw? ");
			if (in.hasNextInt())
			{
				String line = in.nextLine();
				try 
				{
					userResponse = Integer.parseInt(line);
					if (userResponse < 0)
					{
						System.out.println("That is not valid input. Plese enter a positive number.");
						continue;
					}
				}
				catch (Exception e)
				{
					System.out.println("That is not valid input. Please enter a number.");
					continue;
				}
			}
			else
			{
				System.out.println("That is not valid input. Please enter a number");
				in.nextLine();
				continue;
			}	
			
			switch(userResponse)
			{
				case 1:
					double checkingWithdrawal;
					while (true)
					{
						System.out.println("How much would you like to withdraw from checking account?");
						if (in.hasNextDouble())
						{
							String line = in.nextLine();
							try
							{
								checkingWithdrawal = Double.parseDouble(line);
								if (checkingWithdrawal < 0)
								{
									System.out.println("That is not valid input. Please enter a positive number.");
									continue;
								}
								else if (currentUser.getCheckingBalance()-checkingWithdrawal < 0)
								{
									System.out.println("You do not have enough money in your account to withdraw that amount.");
									continue;
								}
								currentUser.removeFromChecking(checkingWithdrawal);
								currentUser.updateSavingsType();
								System.out.println("$" + df.format(checkingWithdrawal) + " withdrawn from your checking account.");
								return;
							}
							catch (Exception e)
							{
								System.out.println("That is not valid input. Please enter a number");
								continue;
							}
						}
						else
						{
							in.nextLine();
							System.out.println("That is not valid input. Please enter a number");
						}
					}
				case 2:
					double savingsWithdrawal;
					while (true)
					{
						System.out.println("How much would you like to withdraw from checking account?");
						if (in.hasNextDouble())
						{
							String line = in.nextLine();
							try
							{
								savingsWithdrawal = Double.parseDouble(line);
								if (savingsWithdrawal < 0)
								{
									System.out.println("That is not valid input. Please enter a positive number.");
									continue;
								}
								else if (currentUser.getSavingsBalance()-savingsWithdrawal < 0)
								{
									System.out.println("You do not have enough money in your account to withdraw that amount.");
									continue;
								}
								currentUser.removeFromSavings(savingsWithdrawal);
								currentUser.updateSavingsType();
								System.out.println("$" + df.format(savingsWithdrawal) + " withdrawn from your "+ currentUser.getSavingsType() + " savings account.");
								return;
							}
							catch (Exception e)
							{
								System.out.println("That is not valid input. Please enter a number");
								continue;
							}
						}
						else
						{
							in.nextLine();
							System.out.println("That is not valid input. Please enter a number");
						}
					}
				default:
					System.out.println("That is not a valid number. Please enter 1 or 2.");
					break;
			}
		}
	}
	private static void determineBalance(Scanner in)
	{
		int userResponse = 0;
		
		while(true)
		{
			System.out.println("In how many years?");
			if (in.hasNextInt())
			{
				String line = in.nextLine();
				try 
				{
					userResponse = Integer.parseInt(line);
					if (userResponse < 0)
					{
						System.out.println("That is not valid input. Please enter a positive number.");
						continue;
					}
					break;
				}
				catch (Exception e)
				{
					System.out.println("That is not valid input. Please enter a number.");
					continue;
				}
			}
			else
			{
				System.out.println("That is not valid input. Please enter a number.");
				in.nextLine();
				continue;
			}
		}
		
		DecimalFormat df = new DecimalFormat("#.00");
				
		//column displaying the years
		String col1Components = "Year,----";
		for (int i = 0; i <= userResponse; i++)
		{
			col1Components += ("," + i);
		}
		String[] col1 = col1Components.split(",");
				
		//column displaying the amounts each year
		String maxAmount = String.valueOf(currentUser.getSavingsBalanceAfterNumYears(userResponse));
		String col2Components = "Amount,";
		for (int i = 0; i < df.format(Double.parseDouble(maxAmount)).length(); i++)
		{
			col2Components += "-";
		}
		for (int i = 0; i <= userResponse; i++)
		{
			col2Components += ("," + currentUser.getSavingsBalanceAfterNumYears(i));
		}
		String[] col2 = col2Components.split(",");
		
		
		//column displaying the interest each year
		String maxInterest = String.valueOf(currentUser.getSavingsInterestAfterNumYears(userResponse));
		String col3Components = "Interest,";
		for (int i = 0; i < df.format(Double.parseDouble(maxInterest)).length(); i++)
		{
			col3Components += "-";
		}
		for (int i = 0; i <= userResponse; i++)
		{
			col3Components += ("," + currentUser.getSavingsInterestAfterNumYears(i));
		}
		String[] col3 = col3Components.split(",");
		
		String space = "     ";
		
		//prints columns
		System.out.println(col1[0] + space + col2[0] + spaceFormatter(df.format(Double.parseDouble(maxAmount)).length(), col2[0].length()) + col3[0]);
		System.out.println(col1[1] + space + col2[1] + space + col3[1]);
		for (int i = 2; i < col1.length; i++)
		{
			System.out.println(col1[i] + "        $" + df.format(Double.parseDouble(col2[i])) + spaceFormatter(df.format(Double.parseDouble(maxAmount)).length()-1, df.format(Double.parseDouble(col2[i])).length()) + "$" + df.format(Double.parseDouble(col3[i])));
		}
	}
	
	private static String spaceFormatter(int maxSpaces, int columnItemLength)
	{
		String spaces = "";
		for (int i = 0; i < maxSpaces+5-columnItemLength; i++)
		{
			spaces += " ";
		}
		
		return spaces;
	}
}
