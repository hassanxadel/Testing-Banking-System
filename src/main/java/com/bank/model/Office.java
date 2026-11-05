
package com.bank.model;

public class Office {
	private String office_name;
	private String region;
	private String state;
	private String province;
	private String address;
	private Repository repository;
	
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
}
