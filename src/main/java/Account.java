import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.*;

enum AccountType {individual, business;}

public class Account {
	
	private String account_number;
	private String username;
	private String password;
	
	// full name
	private String fname;
	private String lname;
	private String mname;
	
	// address, phone number, ID, email, and office
	private String address;
	private String city;
	private String province;
	private int zipcode;
	private String phone_number;
	private String national_id;
	private String email;
	private Office office;
	
	// Birth date 
	private LocalDate birth_date;
	
	// Balance
	private double balance;
	// Transactions
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	// Cards 
	private ArrayList<Card> cards = new ArrayList<Card>();
	// Loans
	private ArrayList<Loan> loans = new ArrayList<Loan>();
	// Investments
	private ArrayList<Investment> investments =  new ArrayList<Investment>();
	// Account type
	private AccountType account_type;
	
	//constructor
	Account() {
	    final SecureRandom secureRandom = new SecureRandom();
        long timestamp = System.currentTimeMillis();
        long randomPart = secureRandom.nextLong();
		account_number = String.format("%012d", Math.abs(timestamp + randomPart));
	}
	
	
	// account number
	
	String get_account_number() {
		return account_number;
	}
	
	// Account credentials
	
	void set_username(String username) {
		this.username = username;
	}
	
	void set_password(String password) {
		this.password = password;
	}
	
	String get_username() {
		return username;
	}
	
	String get_password() {
		return password;
	}
	
	// name setters and getters
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
	//--------------------------------------------------------------------
	
	// address
	void set_address(String address) {
		if (address.length() < 256)
			this.address = address;
	}
	
	void set_city(String city) {
		if (city.length() < 50)
			this.city = city;
	}
	
	void set_province(String province) {
		if (province.length() < 50)
			this.province = province;
	}
	
	void set_zipcode(int zipcode) {
		if (zipcode < 999999 && zipcode > 0)
			this.zipcode = zipcode;
	}
	
	String get_address() {
		return address;
	}
	
	String get_full_address() {
		return address + " " + city + " " + province + " | " + zipcode;
	}
	
	String get_city() {
		return city;
	}
	
	String get_province() {
		return province;
	}
	
	int get_zipcode() {
		return zipcode;
	}
	
	//-----------------------------------------------------------------------
	
	// phone number 
	void set_phone_number(String phone_number) {
		if ((phone_number.length() >= 6) && (phone_number.length() <= 22)) {
			this.phone_number = phone_number;
		}
	}
	
	String get_phone_number() {
		return phone_number;
	}
	
	// national id
	void set_national_id(String national_id) {
		if((national_id.length() >= 8) && (national_id.length() <= 20)) 
				this.national_id = national_id;
	}
	
	String get_national_id() {
		return national_id;
	}
	
	//email
	void set_email(String email) {
		Pattern email_pattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
		Matcher email_matcher = email_pattern.matcher(email);
		if(email_matcher.find()) this.email = email;
	}
	
	String get_email() {
		return email;
	}
	
	// office
	void set_office(Office office) {
		this.office = office;
		office.add_assigned_account(this);
	}
	
	Office get_office() {
		return office;
	}
	
	// birth date
	void set_birth_date(int year, int month, int day) {
		if ((year > 1800 && year < 2006) && (month >= 1 && month <= 12) && (day >= 1 && day <= 31))
			this.birth_date = LocalDate.of(year, month, day);
	}
	
	LocalDate get_birth_date() {
		return birth_date;
	}
	
	String get_birth_date_string() {
		return birth_date.toString();
	}
	
	//balance
	void deposit(double amount) {
		balance += amount;
	}
	
	void withdraw(double amount) {
		if (amount <= balance) balance -= amount;
	}
	
	void send_transaction(Account reciever, double amount) {
		Transaction transaction = new Transaction(this, reciever, amount);
		if(transaction.is_accepted()) {
			balance -= amount;
			reciever.deposit(amount);
		}
		reciever.add_transaction(transaction);
		transactions.add(transaction);
	}

	double get_balance() {
		return balance;
	}
	
	// Transactions
	void add_transaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	Transaction get_transaction_by_number(String transaction_number) {
		for(int i = 0; i < transactions.size(); i++) {
			if (transactions.get(i).get_transaction_number().equals(transaction_number)) return transactions.get(i);
		}
		return null;
	}
	
	ArrayList<Transaction> get_transactions() {
		return transactions;
	}
	
	// cards
	void add_card(Card card) {
		cards.add(card);
	}
	
	Card get_card_by_card_number(String card_number) {
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).get_card_number().equals(card_number)) return cards.get(i);
		}
		
		return null;
	}
	
	ArrayList<Card> get_cards() {
		return cards;
	}
	
	// Loans
	
	void add_loan(Loan loan) {
		loans.add(loan);
	}
	
	void accepted_loan(Loan loan) {
		if (loan.is_approved()) balance += loan.get_amount();
	}
	
	ArrayList<Loan> get_loans() {
		return loans;
	}
	
	// Investments
	
	void add_investment(Investment investment) {
		investments.add(investment);
		balance -= investment.get_price();
	}
	
	ArrayList<Investment> get_investments() {
		return investments;
	}
	
	// Account Type
	
	void set_account_type(AccountType account_type) {
		this.account_type = account_type;
	}
	
	AccountType get_account_type() {
		return account_type;
	}
}

