package csci201assignment2.src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class User 
{
	private CheckingAccount checkingAccount;
	private SavingsAccount savingsAccount; 
	private boolean loggedIn;
	private String name;
	private String password;
	
	User()
	{
		loggedIn = false;
		name = "";
		password = "";
		checkingAccount = new CheckingAccount(0);
		savingsAccount = new BasicSavingsAccount(0);
	}
	
	public void logIn()
	{
		loggedIn = true;
	}
	
	public void logOut()
	{
		loggedIn = false;
	}
	
	public boolean isLoggedIn()
	{
		return loggedIn;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String newName)
	{
		name = newName;
	}
	
	public double getCheckingBalance()
	{
		return checkingAccount.getBalance();
	}
	
	public double getSavingsBalance()
	{
		return savingsAccount.getBalance();
	}
	
	public double getSavingsBalanceAfterNumYears(int numYears)
	{
		return savingsAccount.getBalanceAfterNumYears(numYears);
	}
	
	public double getCheckingBalanceAfterNumYears(int numYears)
	{
		return (checkingAccount.getBalanceAfterNumYears(numYears));
	}
	
	public double getSavingsInterestAfterNumYears(int numYears)
	{
		return (savingsAccount.getBalanceAfterNumYears(numYears)*savingsAccount.getAnnualInterestRate());
	}
	
	public void addToChecking(double deposit)
	{
		double currentBalance = this.checkingAccount.getBalance();
		this.checkingAccount.setBalance(currentBalance + deposit);
	}
	
	public void addToSavings(double deposit)
	{
		double currentBalance = this.savingsAccount.getBalance();
		this.savingsAccount.setBalance(currentBalance + deposit);
	}
	
	public void removeFromChecking(double withdrawal)
	{
		double currentBalance = this.checkingAccount.getBalance();
		this.checkingAccount.setBalance(currentBalance - withdrawal);
	}
	
	public void removeFromSavings(double withdrawal)
	{
		double currentBalance = this.savingsAccount.getBalance();
		this.savingsAccount.setBalance(currentBalance - withdrawal);
	}
	
	public String getSavingsType()
	{
		return savingsAccount.getAccountType();
	}
	
	public void updateSavingsType()
	{
		double currentBalance = savingsAccount.getBalance();
		if (savingsAccount.getBalance()+checkingAccount.getBalance() < 1000)
		{
			savingsAccount = new BasicSavingsAccount(currentBalance);
		}
		else if (savingsAccount.getBalance()+checkingAccount.getBalance() < 10000)
		{
			savingsAccount = new PremiumSavingsAccount(currentBalance);
		}
		else
		{
			savingsAccount = new DeluxeSavingsAccount(currentBalance);
		}
	}
	
	//updates user from the file information
	public void updateIn(String fileName)
	{
		FileReader fileReader = null;
		
		try {
			fileReader = new FileReader(fileName);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		String password = "";
		
		try {
			password = bufferedReader.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
				
		//reads in balance in checking accounts
		double checkingBalance = 0; 
		try 
		{
			checkingBalance = Double.parseDouble(bufferedReader.readLine());
		} 
		catch (NumberFormatException e) {
			//Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			//Auto-generated catch block
			e.printStackTrace();
		}
		
		//reads in balance in savings accounts
		double savingsBalance = 0;
		try {
			savingsBalance = Double.parseDouble(bufferedReader.readLine());
		} catch (NumberFormatException e) {
			//Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			//Auto-generated catch block
			e.printStackTrace();
		}
		
		//updates users checking account
		this.checkingAccount.setBalance(checkingBalance);
		//updates users savings account
		this.savingsAccount.setBalance(savingsBalance);
		
		//signifies that user is logged in
		this.loggedIn = true; 
		
		//gives user a name
		this.name = fileName;
		
		this.password = password;
	}

	public void updateOut()
	{
		//sets up ability to overwrite user file
		FileWriter fw = null;
		PrintWriter pw = null;
		try 
		{
			fw = new FileWriter(this.name, false);
			pw = new PrintWriter(fw);
			pw.println(this.password);
			pw.println(this.checkingAccount.getBalance());
			pw.println(this.savingsAccount.getBalance());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
