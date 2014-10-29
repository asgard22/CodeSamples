package csci201assignment2.src;

public class PremiumSavingsAccount extends SavingsAccount
{
	public PremiumSavingsAccount(double balance)
	{
		super(balance);
		annualInterestRate = 0.01;
	}
	
	public String getAccountType()
	{
		return "Premium Savings";
	}
}
