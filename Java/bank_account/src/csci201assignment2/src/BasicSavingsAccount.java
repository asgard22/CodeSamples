package csci201assignment2.src;

public class BasicSavingsAccount extends SavingsAccount
{
	public BasicSavingsAccount(double balance)
	{
		super(balance);
		annualInterestRate = 0.001;
	}
	
	public String getAccountType()
	{
		return "Basic Savings";
	}
}
