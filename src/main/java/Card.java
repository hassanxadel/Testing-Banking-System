import java.security.SecureRandom;
import java.time.LocalDate;

enum CardType {Debit, Credit, Prepaid, Purchases;}
enum Network {Visa, Mastercard, AmericanExpress, Discover;}
public class Card {
	private String holder_name;
	private String card_number;
	private int cvv;
	private LocalDate expiration;
	private int pin;
	private Account account;
	private CardType type;
	private Network network;
	private boolean reviewed;
	private boolean accepted;
	private boolean remove;

	Card(Account account, CardType type, Network network) {
		this.account = account;
		this.type = type;
		this.network = network;
		holder_name = account.get_name();
	    final SecureRandom secureRandom = new SecureRandom();
        long timestamp = System.currentTimeMillis();
        long randomPart = secureRandom.nextLong();
		card_number = String.format("%016d", Math.abs(timestamp + randomPart)); // this not real credit card number generator
		cvv = 100 + secureRandom.nextInt(900);
		expiration = LocalDate.now();
		expiration = expiration.plusYears(5);
	}

	String get_holder_name() {
		return holder_name;
	}
	
	String get_card_number() {
		return card_number;
	}
	
	int get_cvv() {
		return cvv;
	}
	
	LocalDate get_expiration() {
		return expiration;
	}
	
	String get_expiration_string() {
		return expiration.getYear() + "-" + expiration.getMonthValue();
	}
	
	void set_pin(int pin) {
		String pin_string = String.valueOf(pin);
		if (pin_string.length() == 4) this.pin = pin;
	}
	
	int get_pin() {
		return pin;
	}
	
	Account get_account() {
		return account;
	}
	
	CardType get_type() {
		return type;
	}
	
	Network get_network() {
		return network;
	}
	
	void set_accepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	boolean get_accepted() {
		return accepted;
	}
	
	void set_remove(boolean remove) {
		this.remove = remove;
	}
	
	boolean get_remove() {
		return remove;
	}
	
	void set_reviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}
	
	boolean get_reviewed() {
		return reviewed;
	}
}
