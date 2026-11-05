
public class Loan {
	private Account account;
	private double amount;
	private double interest_rate;
	private int duration_months;
	private boolean reviewed;
	private boolean approved;
	
	Loan(Account account, double amount, double interest_rate, int duration_months) {
		if (amount > 0 && interest_rate > 0)
		{
			this.amount = amount;
			this.interest_rate = interest_rate;
			this.duration_months = duration_months;
			this.account = account;
		}
	}
	
	void set_approved(boolean approved) {
		this.approved = approved;
	}
	
	boolean is_approved() {
		return approved;
	}
	
	double get_amount() {
		return amount;
	}
	
	double get_interest_rate() {
		return interest_rate;
	}
	
	void set_reviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}
	
	boolean is_reviewed() {
		return reviewed;
	}
	
	int get_duration_months() {
		return duration_months;
	}
	
	Account get_account() {
		return account;
	}
}
