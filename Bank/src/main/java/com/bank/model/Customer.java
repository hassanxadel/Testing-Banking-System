package com.bank.model;

import java.util.ArrayList;

public class Customer {
	// name 
	private String fname;
	private String lname;
	private String mname;
	private String id;
	private ArrayList<Account> accounts = new ArrayList<Account>();
	
	void set_fname(String fname) {
		if (fname.length() <= 25)
			this.fname = fname;
	}
	
	void set_lname(String lname) {
		if (lname.length() <= 25)
			this.lname = lname;
	}
	
	void set_mname(String mname) {
		if (mname.length() < 25)
			this.mname = mname;
	}
	
	String get_fname() {
		return fname;
	}
	
	String get_lname() {
		return lname;
	}
	
	String get_mname() {
		return mname;
	}
	
	String get_name() {
		return fname + " " + mname + " " + lname;
	}
	
	void set_id(String id) {
		this.id = id;
	}
	
	String get_id() {
		return id;
	}
	
	void add_account(Account account) {
		accounts.add(account);
	}
	
	void close_account(Account account) {
		for(int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).get_account_number().equals(account.get_account_number())) {
				double balance = accounts.get(i).get_balance();
				accounts.get(i).withdraw(balance);
				accounts.remove(i);
			}
		}	
	}
	
	Account get_account_by_number(String account_number) {
		for(int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).get_account_number().equals(account_number)) return accounts.get(i);
		}
		
		return null;
	}
	
	
	ArrayList<Account> get_accounts() {
		return accounts;
	}
}
