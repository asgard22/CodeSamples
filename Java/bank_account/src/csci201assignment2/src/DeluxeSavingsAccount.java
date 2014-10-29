package csci201assignment2.src;

public class DeluxeSavingsAccount extends SavingsAccount
{
	public DeluxeSavingsAccount(double balance)
	{
		super(balance);
		annualInterestRate = 0.05;
	}
	
	public String getAccountType()
	{
		return "Deluxe Savings";
	}
}
