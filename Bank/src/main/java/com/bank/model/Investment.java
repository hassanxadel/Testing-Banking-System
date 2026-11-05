
package com.bank.model;

public class Investment {
	
	private double price;
	private double return_rate;
	private int duration_months;
	private int payment_duration;

	Investment(double price, double return_rate, int duration_months, int payment_duration) {
		if (price > 0 && return_rate > 0 && duration_months > 0 && payment_duration > 0)
		{
			this.price = price;
			this.return_rate = return_rate;
			this.duration_months = duration_months;
			this.payment_duration = payment_duration;
		}
	}
	
	double get_price() {
		return price;
	}
	
	double get_return_rate() {
		return return_rate;
	}
	
	int get_duration_months() {
		return duration_months;
	}
	
	int get_payment_duration() {
		return payment_duration;
	}
}
