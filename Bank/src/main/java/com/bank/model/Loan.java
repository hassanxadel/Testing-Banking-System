
package com.bank.model;

public class Loan {
	private double amount;
	private double interest_rate;
	private boolean approved;
	
	Loan(double amount, double interest_rate) {
		if (amount > 0 && interest_rate > 0)
		{
			this.amount = amount;
			this.interest_rate = interest_rate;
	
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
}
