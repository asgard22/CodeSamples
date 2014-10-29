package csci201assignment2.src;

public abstract class SavingsAccount extends BaseAccount 
{
	protected double annualInterestRate;
	
	public SavingsAccount(double balance)
	{
		super(balance);
	}
	
	// returns the balance after numYears has passed
	// if the account has interest, this method will account for it
	protected double getBalanceAfterNumYears(int numYears)
	{
		//uses formula, balance = principle*((1+rate)^(time))
		
		return this.getBalance()*(Math.pow((1+annualInterestRate),(numYears)));
	}

	public double getAnnualInterestRate()
	{
		return annualInterestRate;
	}
	
	// returns a string representing the type of account
	// such as “Checking”, “Deluxe Savings”, etc.
	// Note that the quotation marks will need to be changed if you
	// try to copy and paste from here into Eclipse
	public abstract String getAccountType();
}
