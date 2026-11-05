import java.util.ArrayList;

public class Office {
	private String office_name;
	private String region;
	private String state;
	private String province;
	private String address;
	private Repository repository;
	private ArrayList<Account> assigned_accounts = new ArrayList<Account>();
	private ArrayList<Employee> assigned_employees = new ArrayList<Employee>();
	
	Office(String office_name, String region, String province, String address, Repository repository) {
		this.office_name = office_name;
		this.address = address;
		this.region = region;
		this.province = province;
		this.repository = repository;
	}
	
	void set_office_name(String office_name) {
		this.office_name = office_name;
	}
	
	String get_office_name() {
		return office_name;
	}
	
	void set_region(String region) {
		this.region = region;
	}
	
	String get_region() {
		return region;
	}
	
	void set_state(String state) {
		this.state = state;
	}
	
	String get_state() {
		return state;
	}
	
	void set_province(String province) {
		this.province = province;
	}
	
	String get_province() {
		return province;
	}
	
	void set_address(String address) {
		this.address = address;
	}
	
	String get_address() {
		return address;
	}
	
	String get_full_address() {
		return address + " " + province + " " + state + " " + region;
	}
	
	void set_repository(Repository repository) {
		this.repository = repository;
	}
	
	Repository get_repository() {
		return repository;
	}
	
	void add_assigned_employee(Employee e) {
		assigned_employees.add(e);
	}
	
	ArrayList<Employee> get_assigned_employees() {
		return assigned_employees;
	}
	
	void add_assigned_account(Account a) {
		assigned_accounts.add(a);
	}
	
	ArrayList<Account> get_assigned_accounts() {
		return assigned_accounts;
	}
}
