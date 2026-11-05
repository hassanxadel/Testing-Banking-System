package com.bank.model;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.security.SecureRandom;

public class Transaction {
	private String transaction_number;
	private Account sender;
	private Account reciever;
	private double amount;
	private ZonedDateTime time;
	private boolean accepted;
	
	Transaction(Account sender, Account reciever, double amount)
	{
		this(sender, reciever, amount, false);
	}

	// isDeposit: external deposits are always accepted; regular transfers require sufficient sender balance.
	Transaction(Account sender, Account reciever, double amount, boolean isDeposit)
	{
	    final SecureRandom secureRandom = new SecureRandom();
        long timestamp = System.currentTimeMillis();
        long randomPart = secureRandom.nextLong();
		transaction_number = String.format("%016d", Math.abs(timestamp + randomPart));
		this.sender = sender;
		this.reciever = reciever;
		this.amount = amount;

		time = ZonedDateTime.now(ZoneId.of("UTC"));
		if (isDeposit) accepted = true;
		else if(amount > sender.get_balance()) accepted = false;
		else accepted = true;

	}
	

	String get_transaction_number() {
		return transaction_number;
	}
	
	Account get_sender() {
		return sender;
	}
	
	Account get_reciever() {
		return reciever;
	}
	
	double get_transaction_amount() {
		return amount;
	}
	
	ZonedDateTime get_time() {
		return time; 
	}
	
	String get_time_string() {
		return time.toString();
	}
	
	boolean is_accepted() {
		return accepted;
	}
}
