package csci201assignment2.src;

public abstract class BaseAccount {
	
	private double balance;
	
	public BaseAccount(double balance) 
	{
		setBalance(balance);
	}
	
	public double getBalance() 
	{
		//HOW DO I ROUND THIS?
		return this.balance;
	}
	
	public void setBalance(double balance) 
	{
		this.balance = balance;
	}
	
	// returns the balance after numYears has passed
	// if the account has interest, this method will account for it
	protected abstract double getBalanceAfterNumYears(int numYears);
	
	// returns a string representing the type of account
	// such as “Checking”, “Deluxe Savings”, etc.
	// Note that the quotation marks will need to be changed if you
	// try to copy and paste from here into Eclipse
	public abstract String getAccountType();
	
	//WHAT HAPPENS IF AMOUNT IS PASSED INCORRECTLY?
	public boolean withdraw(double amount) 
	{
		//if amount is a valid number to withdraw
		if (amount >= 0)
		{
			//withdraw that amount, signal that withdrawal was completed successfully with 'true'
			this.balance -= amount;
			return true;
		}

		//if amount is not a valid number to deposit, signal that deposit was unsuccessful with 'false'
		return false;
	}
	
	//WHAT HAPPENS IF AMOUNT IS PASSED INCORRECTLY?
	public boolean deposit(double amount) 
	{
		//if amount is a valid number to deposit
		if (amount >= 0)
		{
			//deposit that amount, signal that deposit was completed successfully with 'true'
			this.balance += amount;
			return true;
		}

		//if amount is not a valid number to deposit, signal that deposit was unsuccessful with 'false'
		return false;
	}
}
