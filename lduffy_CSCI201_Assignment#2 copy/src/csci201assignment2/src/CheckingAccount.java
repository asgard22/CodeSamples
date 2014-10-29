package csci201assignment2.src;

public class CheckingAccount extends BaseAccount
{
	CheckingAccount(double balance)
	{
		super(balance);
	}
	
	// returns the balance after numYears has passed
	// if the account has interest, this method will account for it
	protected double getBalanceAfterNumYears(int numYears)
	{
		return this.getBalance();
	}
	
	// returns a string representing the type of account
	public String getAccountType()
	{
		return "Checking";
	}
}
