
enum Role {Teller, Banker, Loan_Processor, Manager;}


public class Employee {
	
	private String username;
	private String password;
	
	// name 
	private String fname;
	private String lname;
	private String mname;
	
	// national identification  number
	private String national_id;

	// address, phone number, and office
	private String address;
	private String city;
	private String province;
	private int zipcode;
	private String phone_number;
	private Office office;
	
	// role and salary
	private Role role;
	private double salary;

	Employee() {
		
	}
	
	Employee(String username, String password, Role role, Office office, double salary) {
		this.username = username;
		this.password = password;
		this.office = office;
		this.role = role;
		this.salary =salary;
	}
	
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
	
	void set_national_id(String national_id) {
		this.national_id = national_id;
	}
	
	String get_national_id() {
		return national_id;
	}

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
	
	void set_phone_number(String phone_number) {
		if ((phone_number.length() >= 6) && (phone_number.length() <= 22)) {
			this.phone_number = phone_number;
		}
	}
	
	String get_phone_number() {
		return phone_number;
	}
	
	void set_office(Office office) {
		this.office = office;
	}
	
	Office get_office() {
		return office;
	}
	
	void set_role(Role role) {
		this.role = role;
	}
	
	Role get_role() {
		return role;
	}
	
	void set_salary(double salary) {
		if (salary > 0) this.salary = salary;
	}
	
	double get_salary() {
		return salary;
	}
	
	void accept_loan(Loan loan) {
		loan.set_approved(true);
		loan.set_reviewed(true);
	}
	
	void reject_loan(Loan loan) {
		loan.set_approved(false);
		loan.set_reviewed(true);
	}
	
	void accept_card(Card card) {
		card.set_accepted(true);
		card.set_reviewed(true);
	}
	
	void reject_card(Card card) {
		card.set_accepted(false);
		card.set_reviewed(true);
	}
}
