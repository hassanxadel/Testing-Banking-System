import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Gui {
	JFrame main_frame;
	// connect db and load session_accounts, session_employees, session_customers from it
	
	ArrayList<Account> session_accounts = new ArrayList<Account>();
	ArrayList<Employee> session_employees = new ArrayList<Employee>();
	ArrayList<Customer> session_customers = new ArrayList<Customer>();
	ArrayList<Office> session_offices = new ArrayList<Office>();
	ArrayList<Asset> session_assets = new ArrayList<Asset>();
	ArrayList<Repository> session_repositories = new ArrayList<Repository>();
	// dummy Assets1
	Asset asset1 = new Asset(AssetType.Cash, 50_000_000_000.00);
	Asset asset2 = new Asset(AssetType.Gold, 1000);
	
	
	Gui() {
		session_assets.add(asset1);
		session_assets.add(asset2);
		Repository repo1 = new Repository(session_assets);
		session_repositories.add(repo1);
		Office office1 = new Office("Tahrir", "middle east", "Cairo", "27 Tahrir st", repo1);
		session_offices.add(office1);
		Employee teller = new Employee("teller", "teller", Role.Teller, office1, 30000.00);
		Employee banker = new Employee("banker", "banker", Role.Banker, office1, 50000.00);
		Employee loaner = new Employee("loaner", "loaner", Role.Loan_Processor, office1, 70000.00);
		Employee manager = new Employee("manager", "manager", Role.Manager, office1, 100000.00);
		session_employees.add(teller); session_employees.add(banker); session_employees.add(loaner);
		session_employees.add(manager);
		
		main_frame = new JFrame();
		
		JLabel welcome = new JLabel("Welcome to X Bank!\n");
		JButton clogin = new JButton("Customer Login");
		JButton elogin = new JButton("Employee Login");
		JButton alogin = new JButton("Account Login");
		JButton register = new JButton("Register New Cusomer");
		
		main_frame.add(welcome);
		main_frame.add(clogin); main_frame.add(alogin);
		main_frame.add(elogin); main_frame.add(register);
		
		clogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame login_frame = new JFrame();
				
				JLabel msg = new JLabel("Please, enter your credentials");
				JLabel empty = new JLabel(" ");
				JLabel empty2 = new JLabel(" ");
				JLabel empty3 = new JLabel(" ");
				JLabel username = new JLabel("Username: ");
				JLabel password = new JLabel("Password: ");
		
				JTextField username_field = new JTextField(40);
				JPasswordField password_field = new JPasswordField(40);
				
				
				JButton back = new JButton("back");
				JButton login = new JButton("login");
				
				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						login_frame.dispose();
						main_frame.setVisible(true);
					}
				});

				login.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFrame customer_dashboard = new JFrame();
						
						JLabel msg = new JLabel();
						JLabel invalid_credentials = new JLabel("Invalid credentials!");
						JLabel empty = new JLabel(" 					");
						JLabel empty1 = new JLabel(" 					");
						
						JButton sign_out = new JButton("Sign out");
						JButton create_account = new JButton("Create Account");
						JButton view_accounts = new JButton("View Accounts");
						
						String customer_username = username_field.getText();
						String customer_password = new String(password_field.getPassword());
						
						sign_out.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								customer_dashboard.dispose();
								main_frame.setVisible(true);
							}
						});
						

						
						boolean valid = false;
						int current_customer = -1;
						for(int i = 0; i < session_customers.size(); i++) {
							if(customer_username.equals(session_customers.get(i).get_username()) &&
									customer_password.equals(session_customers.get(i).get_password())) {
								valid = true;
								current_customer = i;
								break;
							}
						}
						final int current_customer_final = current_customer;
						
						view_accounts.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame view_accounts_frame = new JFrame();
								String[] columns = {"Account Number", "Account name", "Type", "Address", "office", "Balance", "E-mail"};
								ArrayList<Account> customer_accounts = new ArrayList<Account>(); 
								if (current_customer_final > -1) customer_accounts = session_customers.get(current_customer_final).get_accounts();
								String[][] data = new String[customer_accounts.size()][7];
								for(int i = 0; i < customer_accounts.size(); i++) {
									data[i][0] = customer_accounts.get(i).get_account_number();
									data[i][1] = customer_accounts.get(i).get_name();
									AccountType type = customer_accounts.get(i).get_account_type();
									if(type == AccountType.individual) data[i][2] = "Individual";
									else if(type == AccountType.business) data[i][2] = "Business";
									data[i][3] = customer_accounts.get(i).get_full_address();
									data[i][4] = customer_accounts.get(i).get_office().get_office_name();
									data[i][5] = String.format("%.2f", customer_accounts.get(i).get_balance());
									data[i][6] = customer_accounts.get(i).get_email();
								}
								JButton back = new JButton("Back");
								back.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										view_accounts_frame.dispose();
										customer_dashboard.setVisible(true);
									}
								});
								JSeparator sp = new JSeparator(); 
								JTable accounts_table = new JTable(data, columns);
								JScrollPane scroll_table = new JScrollPane(accounts_table);
								scroll_table.setSize(700, 700);
								JPanel p1 = new JPanel();
								p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p1.add(back);
								p1.add(sp);
								view_accounts_frame.add(p1);
								view_accounts_frame.add(scroll_table);
								view_accounts_frame.setVisible(true);
								customer_dashboard.setVisible(false);
								view_accounts_frame.setSize(800,800);
								view_accounts_frame.setTitle("X Banking System");
								view_accounts_frame.setLayout(new GridLayout(2, 1, 20, 20));
							}
						});
						
						create_account.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame create_account_frame = new JFrame();
								
								JLabel msg = new JLabel("Please fill the following form: ");
								JLabel empty = new JLabel(" 								");
								JLabel fname_label = new JLabel("First name: ");
								JLabel mname_label = new JLabel("Middle name: ");
								JLabel lname_label = new JLabel("Last name: ");
								JLabel address_label = new JLabel("Address: ");
								JLabel city_label = new JLabel("City: ");
								JLabel province_label = new JLabel("Province: ");
								JLabel zipcode_label = new JLabel("Zipcode: ");
								JLabel phone_number_label = new JLabel("Phone number: ");
								JLabel national_id_label = new JLabel("National Id: ");
								JLabel office_label = new JLabel("Office: ");
								JLabel birth_date_label = new JLabel("Birth date: ");
								JLabel birth_year_label = new JLabel("Year: ");
								JLabel birth_month_label = new JLabel("Month: ");
								JLabel birth_day_label = new JLabel("Day: ");
								JLabel email_label = new JLabel("E-mail: ");
								JLabel type_label = new JLabel("Account Type: ");
								JLabel username_label = new JLabel("Enter username: ");
								JLabel password_label = new JLabel("Enter password: ");
								
								JTextField fname_field = new JTextField(15);
								JTextField mname_field = new JTextField(15);
								JTextField lname_field = new JTextField(15);
								JTextField address_field = new JTextField(40);
								JTextField city_field = new JTextField(20);
								JTextField province_field = new JTextField(20);
								JTextField zipcode_field = new JTextField(10);
								JTextField phone_number_field = new JTextField(20);
								JTextField national_id_field = new JTextField(20);
								JComboBox<String> office_combobox = new JComboBox<String>();
								for(int i = 0; i < session_offices.size(); i++) {
									office_combobox.addItem(session_offices.get(i).get_office_name());
								}
								JTextField birth_year_field = new JTextField(6);
								JTextField birth_month_field = new JTextField(3);
								JTextField birth_day_field = new JTextField(3);
								JTextField email_field = new JTextField(20);
								JComboBox<String> type_combobox = new JComboBox<String>();
								type_combobox.addItem("Individual"); type_combobox.addItem("Business");
								JTextField username_field = new JTextField(25);
								JPasswordField password_field = new JPasswordField(25);
								
								JButton back = new JButton("Back");
								JButton create_account = new JButton("Create Account");
								
								back.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										create_account_frame.dispose();
										customer_dashboard.setVisible(true);
									}
								});
								
								JPanel p1 = new JPanel();
								JPanel p2 = new JPanel();
								JPanel p3 = new JPanel();
								JPanel p4 = new JPanel();
								JPanel p5 = new JPanel();
								JPanel p6 = new JPanel();
								JPanel p7 = new JPanel();
								JPanel p8 = new JPanel();
								JPanel p9 = new JPanel();
								JPanel p10 = new JPanel();
								JPanel p11 = new JPanel();
								JPanel p12 = new JPanel();
								
								
								p1.add(back); p1.add(empty);
								p2.add(msg);
								p3.add(fname_label); p3.add(fname_field); p3.add(mname_label); p3.add(mname_field);
								p3.add(lname_label); p3.add(lname_field);
								p4.add(address_label); p4.add(address_field);
								p5.add(city_label); p5.add(city_field); p5.add(province_label); p5.add(province_field);
								p6.add(zipcode_label); p6.add(zipcode_field); p6.add(phone_number_label); p6.add(phone_number_field);
								p7.add(national_id_label); p7.add(national_id_field);
								p8.add(office_label); p8.add(office_combobox); 
								p9.add(birth_date_label); p9.add(birth_year_label); p9.add(birth_year_label); 
								p9.add(birth_year_field); p9.add(birth_month_label); p9.add(birth_month_field); p9.add(birth_day_label); p9.add(birth_day_field);
								p10.add(username_label); p10.add(username_field); p10.add(password_label); p10.add(password_field);
								p11.add(type_label); p11.add(type_combobox); p11.add(email_label); p11.add(email_field);
								p12.add(create_account);
								
								create_account.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										boolean valid = true;
										String username = username_field.getText();
										String password = new String(password_field.getPassword());
										String fname = fname_field.getText();
										String mname = mname_field.getText();
										String lname = lname_field.getText();
										String address = address_field.getText();
										String city = city_field.getText();
										String province = province_field.getText();
										String zipcode = zipcode_field.getText();
										String phone_number = phone_number_field.getText();
										String national_id = national_id_field.getText();
										String office_name = (String) office_combobox.getSelectedItem();
										String year =  birth_year_field.getText();
										String month = birth_month_field.getText();
										String day = birth_day_field.getText();
										String email = email_field.getText();
										String type = (String) type_combobox.getSelectedItem();
										
										JLabel invalid_username = new JLabel("Invalid username!");
										JLabel invalid_password = new JLabel("Invalid Password!");
										JLabel invalid_fname = new JLabel("Invalid first name!");
										JLabel invalid_mname = new JLabel("Invalid middle name!");
										JLabel invalid_lname = new JLabel("Invalid last name!");
										JLabel invalid_city = new JLabel("Invalid city name!");
										JLabel invalid_province = new JLabel("Invalid province!");
										JLabel invalid_zipcode = new JLabel("Invalid zipcode!");
										JLabel invalid_phone_number = new JLabel("Invalid phone number!");
										JLabel invalid_national_id = new JLabel("Invalid national id!");
										JLabel invalid_year = new JLabel("Invalid birth year");
										JLabel invalid_month = new JLabel("Invalid birth month");
										JLabel invalid_day = new JLabel("Invalid birth day");
										JLabel invalid_address = new JLabel("Invalid address");
										JLabel invalid_email = new JLabel("Invalid email");
										JLabel account_exists = new JLabel("Account already exists!");
										
										if(username.length() > 25 || username.length() < 2) {
											valid = false;
											p10.add(invalid_username);
										}
										
										for(int i = 0; i < session_accounts.size(); i++) {
											if(username.equals(session_accounts.get(i).get_username())) {
												valid = false;
												p10.add(account_exists);
											}
										}
										
										if(password.length() > 32 || password.length() < 8) {
											valid = false;
											p10.add(invalid_password);
										}
										
										if(fname.length() < 2 || fname.length() > 25) {
											valid = false;
											p3.add(invalid_fname);
										}
										else {
											for(int i = 0; i < fname.length(); i++) {
												char c = fname.charAt(i);
												if(Character.isDigit(c)) {
													valid = false;
													p3.add(invalid_fname);
													break;
												}
											}
										}
										
										if(mname.length() < 2 || mname.length() > 25) {
											valid = false;
											p3.add(invalid_mname);
										}
										else {
											for(int i = 0; i < mname.length(); i++) {
												char c = mname.charAt(i);
												if(Character.isDigit(c)) {
													valid = false;
													p3.add(invalid_mname);
													break;
												}
											}
										}
										
										if(lname.length() < 2 || lname.length() > 25) {
											valid = false;
											p3.add(invalid_lname);
										}
										else {
											for(int i = 0; i < lname.length(); i++) {
												char c = lname.charAt(i);
												if(Character.isDigit(c)) {
													valid = false;
													p3.add(invalid_lname);
													break;
												}
											}
										}
										
										if(city.length() < 2 || city.length() > 25) {
											valid = false;
											p5.add(invalid_city);
										}
										else {
											for(int i = 0; i < city.length(); i++) {
												char c = city.charAt(i);
												if(Character.isDigit(c)) {
													valid = false;
													p5.add(invalid_city);
													break;
												}
											}
										}
									
										if(province.length() < 2 || province.length() > 25) {
											valid = false;
											p5.add(invalid_province);
										}
										else {
											for(int i = 0; i < province.length(); i++) {
												char c = province.charAt(i);
												if(Character.isDigit(c)) {
													valid = false;
													p5.add(invalid_province);
													break;
												}
											}
										}
										
										if(zipcode.length() < 1 || zipcode.length() > 6) {
											valid = false;
											p6.add(invalid_zipcode);
										}
										else {
											for(int i = 0; i < zipcode.length(); i++) {
												char c = zipcode.charAt(i);
												if(Character.isLetter(c)) {
													valid = false;
													p6.add(invalid_zipcode);
													break;
												}
											}
										}
																				
										if(national_id.length() != 16) {
											valid = false;
											p7.add(invalid_national_id);
										}
										else {
											for(int i = 0; i < national_id.length(); i++) {
												char c = national_id.charAt(i);
												if(Character.isLetter(c)) {
													valid = false;
													p7.add(invalid_national_id);
													break;
												}
											}
										}
										
										if(phone_number.length() < 6 || phone_number.length() > 22) {
											valid = false;
											p6.add(invalid_phone_number);
										}
										else {
											for(int i = 0; i < phone_number.length(); i++) {
												char c = phone_number.charAt(i);
												if(Character.isLetter(c)) {
													valid = false;
													p6.add(invalid_phone_number);
													break;
												}
											}
										}
										
										int year_int = Integer.parseInt(year);
										int month_int = Integer.parseInt(month);
										int day_int = Integer.parseInt(day);
										
										if(year.length() != 4) {
											valid = false;
											p9.add(invalid_year);
										}
										else if(year_int > 2006 || year_int < 1900) {
											valid = false;
											p9.add(invalid_year);
										}
										else {
											for(int i = 0; i < year.length(); i++) {
												char c = year.charAt(i);
												if(Character.isLetter(c)) {
													valid = false;
													p9.add(invalid_year);
													break;
												}
											}
										}
										
										if(month.length() < 1 || month.length() > 2) {
											valid = false;
											p9.add(invalid_month);
										}
										else if(month_int > 12 || month_int < 1) {
											valid = false;
											p9.add(invalid_month);
										}
										else {
											for(int i = 0; i < month.length(); i++) {
												char c = month.charAt(i);
												if(Character.isLetter(c)) {
													valid = false;
													p9.add(invalid_month);
													break;
												}
											}
										}
										
										if(day.length() < 1 || day.length() > 2) {
											valid = false;
											p9.add(invalid_day);
										}
										else if(day_int > 31 || day_int < 1) {
											valid = false;
											p9.add(invalid_day);
										}
										else {
											for(int i = 0; i < day.length(); i++) {
												char c = day.charAt(i);
												if(Character.isLetter(c)) {
													valid = false;
													p9.add(invalid_day);
													break;
												}
											}
										}
										
										if(address.length() < 5) {
											valid = false;
											p4.add(invalid_address);
										}
										
										if(email.length() < 5 || email.length() > 100) {
											valid = false;
											p11.add(invalid_email);
										}
										
										if(valid) {
											int zipcode_int = Integer.parseInt(zipcode);
											JFrame account_created_frame = new JFrame();
											JLabel msg = new JLabel("Your Account created successfully!");
											JButton return_to_dashboard = new JButton("return to dashboard");
											account_created_frame.add(msg);
											account_created_frame.add(return_to_dashboard);
											return_to_dashboard.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													account_created_frame.dispose();
													customer_dashboard.setVisible(true);
												}
											});
											
											create_account_frame.setVisible(false);
											account_created_frame.setVisible(true);
											account_created_frame.setSize(800, 800);
											account_created_frame.setTitle("X Banking System");
											account_created_frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
											
											Account account = new Account();
											account.set_fname(fname);
											account.set_lname(lname);
											account.set_mname(mname);
											account.set_username(username);
											account.set_password(password);
											account.set_address(address);
											account.set_city(city);
											account.set_province(province);
											account.set_zipcode(zipcode_int);
											account.set_phone_number(phone_number);
											account.set_national_id(national_id);
											for(int i = 0; i < session_offices.size(); i++) {
												if (office_name.equals(session_offices.get(i).get_office_name())) {
													account.set_office(session_offices.get(i));
												}
											}
											if(type.equals("Individual")) account.set_account_type(AccountType.individual);
											else account.set_account_type(AccountType.business);
											account.set_birth_date(year_int, month_int, day_int);
											account.set_email(email);
											session_accounts.add(account);
											session_customers.get(current_customer_final).add_account(account);
										}
										
										
									}
								});
								
								p1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
								p2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
								p3.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
								p4.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
								p5.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
								p6.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
								p7.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
								p8.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
								p9.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
								p10.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
								p11.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p12.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								
								create_account_frame.add(p1);
								create_account_frame.add(p2);
								create_account_frame.add(p3);
								create_account_frame.add(p4);
								create_account_frame.add(p5);
								create_account_frame.add(p6);
								create_account_frame.add(p7);
								create_account_frame.add(p8);
								create_account_frame.add(p9);
								create_account_frame.add(p10);
								create_account_frame.add(p11);
								create_account_frame.add(p12);
								
								create_account_frame.setLayout(new GridLayout(12, 1, 10, 10));
								create_account_frame.setVisible(true);
								customer_dashboard.setVisible(false);
								create_account_frame.setSize(800, 800);
								create_account_frame.setTitle("X Banking System");
							}
						});
						
						if(current_customer > -1)  msg.setText("Welcome Back, " + session_customers.get(current_customer).get_name());
						JPanel p1 = new JPanel();
						JPanel p2 = new JPanel();
						JPanel p3 = new JPanel();
						
						p1.add(sign_out); p1.add(empty);
						p2.add(msg); p2.add(empty1);
						p3.add(create_account); p3.add(view_accounts);
						
						
						p1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
						p2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
						p3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
						
						if(valid) {
							login_frame.setVisible(false);
							customer_dashboard.add(p1);
							customer_dashboard.add(p2);
							customer_dashboard.add(p3);
							customer_dashboard.setLayout(new GridLayout(4, 1, 20, 20));
							customer_dashboard.setVisible(true);
							customer_dashboard.setTitle("X Banking System");
							customer_dashboard.setSize(800, 800);
							
						}
						
						else {
							JPanel p6 = new JPanel();
							p6.add(invalid_credentials);
							login_frame.add(p6);
						}
						
						
					}
				});
				
				JPanel p1 = new JPanel();
				JPanel p2 = new JPanel();
				JPanel p3 = new JPanel();
				JPanel p4 = new JPanel();
				JPanel p5 = new JPanel();

				p1.add(back); p1.add(empty2);
				p2.add(msg); p2.add(empty);
				p3.add(username); p3.add(username_field);
				p4.add(password);  p4.add(password_field);
				p5.add(empty3); p5.add(login);


				login_frame.add(p1);
				login_frame.add(p2);
				login_frame.add(p3);
				login_frame.add(p4);
				login_frame.add(p5);
				
				p1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p5.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

				
				login_frame.setLayout(new GridLayout(6, 1, 20, 20));
				login_frame.setTitle("X Banking System");
				login_frame.setSize(800, 800);
				main_frame.setVisible(false);
				login_frame.setVisible(true);
				
			}
		});

		elogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame login_frame = new JFrame();
				
				JLabel msg = new JLabel("Please, enter your credentials");
				JLabel empty = new JLabel(" ");
				JLabel empty2 = new JLabel(" ");
				JLabel empty3 = new JLabel(" ");
				JLabel username = new JLabel("Username: ");
				JLabel password = new JLabel("Password: ");
		
				JTextField username_field = new JTextField(40);
				JPasswordField password_field = new JPasswordField(40);
				
				
				JButton back = new JButton("back");
				JButton login = new JButton("login");
				
				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						login_frame.dispose();
						main_frame.setVisible(true);
					}
				});
				
				login.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String username = username_field.getText();
						String password = new String(password_field.getPassword());
						
						int current_employee = -1;
						
						for(int i = 0; i < session_employees.size(); i++) {
							if(username.equals(session_employees.get(i).get_username()) &&
									password.equals(session_employees.get(i).get_password())) {
								current_employee = i;
								final int current_employee_final = current_employee;
								if(session_employees.get(i).get_role() == Role.Teller) {
									JFrame teller_frame = new JFrame();
									JLabel salary = new JLabel("Your salary is " + session_employees.get(i).get_salary());
									JButton sign_out = new JButton("Sign out");
									sign_out.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											teller_frame.dispose();
											main_frame.setVisible(true);
										}
									});
									
									JLabel msg = new JLabel("Hello," + session_employees.get(current_employee_final).get_name());
									JButton deposit_into_account = new JButton("Deposit into Account");
									
									deposit_into_account.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											JFrame deposit_into_account_frame = new JFrame();
											
											JButton back = new JButton("Back");
											JButton deposit = new JButton("Deposit");
											
											JLabel account_number_label = new JLabel("Reciever Account Number: ");
											JLabel amount_label = new JLabel("Amount to Deposit: ");
											
											SpinnerNumberModel model = new SpinnerNumberModel(0.0, 0.0, 50*200, 50);
											JSpinner amount_spinner = new JSpinner(model);
											
											JTextField account_number_field = new JTextField(20);
											
											back.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													deposit_into_account_frame.dispose();
													teller_frame.setVisible(true);
												}
											});
											
											JPanel p1 = new JPanel();
											JPanel p2 = new JPanel();
											JPanel p3 = new JPanel();
											JPanel p4 = new JPanel();

											
											p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
											p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
											p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
											p4.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

											p1.add(back);
											p2.add(account_number_label); p2.add(account_number_field);
											p3.add(amount_label); p3.add(amount_spinner); p3.add(deposit);
											
											deposit.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													JLabel invalid_account_number = new JLabel("The account number doesn't exist or invalid!");
													String account_number = account_number_field.getText();
													double amount = (double) amount_spinner.getValue();
													
													int reciever_account_index = -1;
													boolean valid = false;
													for(int i = 0; i < session_accounts.size(); i++) {
														if(session_accounts.get(i).get_account_number().equals(account_number)) {
															valid = true;
															reciever_account_index = i;
															break;
														}
													}
													
													if(valid) {
															session_accounts.get(reciever_account_index).deposit(amount);
															JLabel deposit_success = new JLabel("Amount: " + amount + " have beend deposited into account number " + session_accounts.get(reciever_account_index).get_account_number() + " registered under the name: " + session_accounts.get(reciever_account_index).get_name());
															p4.add(deposit_success);
														}
													else {
														p2.add(invalid_account_number);
													}
												}
											});
											
											deposit_into_account_frame.setLayout(new GridLayout(4, 1, 10, 10));
											deposit_into_account_frame.add(p1); deposit_into_account_frame.add(p2);
											deposit_into_account_frame.add(p3); deposit_into_account_frame.add(p4);
											deposit_into_account_frame.setSize(800, 800);
											deposit_into_account_frame.setTitle("X Banking System");
											deposit_into_account_frame.setVisible(true);
											teller_frame.setVisible(false);
										}
									});
									
									JPanel p1 = new JPanel();
									JPanel p2 = new JPanel();
									JPanel p3 = new JPanel();
									p1.add(sign_out); p1.add(msg);
									p2.add(salary);
									p3.add(deposit_into_account);
									p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
									p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
									p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
									
									teller_frame.setLayout(new GridLayout(3,1,10,10));
									teller_frame.add(p1); teller_frame.add(p2); teller_frame.add(p3);
									teller_frame.setVisible(true);
									login_frame.setVisible(false);
									teller_frame.setTitle("X Banking System");
									teller_frame.setSize(800, 800);
								}
								
								else if(session_employees.get(i).get_role() == Role.Banker) {
									JFrame banker_frame = new JFrame();
									JLabel msg = new JLabel("Hello," + session_employees.get(current_employee_final).get_name());
									JLabel salary = new JLabel("Your salary is" + session_employees.get(i).get_salary());
									JButton sign_out = new JButton("Sign out");
									sign_out.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											banker_frame.dispose();
											main_frame.setVisible(true);
										}
									});
									
									JButton review_cards = new JButton("Review Cards");
									
									JPanel p1 = new JPanel();
									JPanel p2 = new JPanel();
									JPanel p3 = new JPanel();
									
									p1.add(sign_out); p1.add(msg);
									p2.add(salary);
									p3.add(review_cards);
									
									review_cards.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											
											JFrame review_cards_frame = new JFrame();
											
											
											JButton back = new JButton("Back");
											JButton approve = new JButton("Approve");
											JButton decline = new JButton("Decline");
											
											String columns[] = {"Account username", "Account Number", "Card Type"};
											
											ArrayList<Card> cards = new ArrayList<Card>();
											
											back.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													review_cards_frame.dispose();
													banker_frame.setVisible(true);
												}
											});
											
											for(int i = 0; i < session_accounts.size(); i++) {
												cards.addAll(session_accounts.get(i).get_cards());
											}
											
											// filter cards
											
											
											for(int i = 0; i < cards.size(); i++) {
												if(cards.get(i).get_reviewed()) {
													cards.remove(cards.get(i));
												}
											}
											
											String data[][] = new String[cards.size()][3];
											
											for(int i = 0; i < cards.size(); i++) {
												data[i][0] = cards.get(i).get_account().get_username();
												data[i][1] = cards.get(i).get_account().get_account_number();
												data[i][2] = cards.get(i).get_type().name();
											}
											
											
											
											JTable view_cards = new JTable(data, columns);
											JPanel p1 = new JPanel();
											JScrollPane p2 = new JScrollPane(view_cards);
											JPanel p3 = new JPanel();
											
											p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
											p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
											
											approve.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													int row = view_cards.getSelectedRow();
													for(int i = 0; i < cards.size();) {
														if(((String)view_cards.getValueAt(row, 1)).equals(cards.get(i).get_account().get_account_number())) {
															cards.get(i).set_reviewed(true);
															cards.get(i).set_reviewed(true);
															cards.remove(i);
															review_cards_frame.repaint();
															review_cards_frame.revalidate();
														}
													}
												}
											});
											
											decline.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													int row = view_cards.getSelectedRow();
													for(int i = 0; i < cards.size();) {
														if(((String)view_cards.getValueAt(row, 1)).equals(cards.get(i).get_account().get_account_number())) {
															cards.get(i).set_reviewed(false);
															cards.get(i).set_reviewed(true);
															cards.remove(i);
															review_cards_frame.repaint();
															review_cards_frame.revalidate();
														}
													}
												}
											});
											
											p1.add(back);
											p3.add(approve); p3.add(decline);
											
											review_cards_frame.setLayout(new GridLayout(3,1,10,10));
											review_cards_frame.add(p1); review_cards_frame.add(p2); review_cards_frame.add(p3);
											review_cards_frame.setVisible(true); banker_frame.setVisible(false);
											review_cards_frame.setSize(800, 800);
											review_cards_frame.setTitle("X Banking System");
										}
									});
									
									p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
									p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
									p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
									
									banker_frame.setLayout(new GridLayout(3,1,10,10));
									banker_frame.add(p1); banker_frame.add(p2); banker_frame.add(p3);
									banker_frame.setVisible(true);
									login_frame.setVisible(false);
									banker_frame.setTitle("X Banking System");
									banker_frame.setSize(800, 800);
								}
								
								else if(session_employees.get(i).get_role() == Role.Loan_Processor) {
									JFrame loan_processor_frame = new JFrame();
									JLabel msg = new JLabel("Hello," + session_employees.get(i).get_name());
									JLabel salary = new JLabel("Your salary is" + session_employees.get(i).get_salary());
									JButton sign_out = new JButton("Sign out");
									JButton review_loans = new JButton("Review Loans");
									
									sign_out.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											loan_processor_frame.dispose();
											main_frame.setVisible(true);
										}
									});
									
									review_loans.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											
											JFrame review_loans_frame = new JFrame();
											
											JButton back = new JButton("Back");
											JButton approve = new JButton("Approve");
											JButton decline = new JButton("Decline");
											
											String columns[] = {"Account username", "Account Number", "Loan Amount", "Interest Rate", "Duration"};
											
											ArrayList<Loan> loans = new ArrayList<Loan>();
											
											for(int i = 0; i < session_accounts.size(); i++) {
												loans.addAll(session_accounts.get(i).get_loans());
											}
											
											// filter cards
											
											for(int i = 0; i < loans.size(); i++) {
												if(loans.get(i).is_reviewed()) {
													loans.remove(loans.get(i));
												}
											}
											
											String data[][] = new String[loans.size()][5];
											
											for(int i = 0; i < loans.size(); i++) {
												data[i][0] = loans.get(i).get_account().get_username();
												data[i][1] = loans.get(i).get_account().get_account_number();
												data[i][2] = Double.toString(loans.get(i).get_amount());
												data[i][3] = Double.toString(loans.get(i).get_interest_rate());
												data[i][4] = Integer.toString(loans.get(i).get_duration_months());
											}
											
											
											
											JTable view_loans = new JTable(data, columns);
											JPanel p1 = new JPanel();
											JScrollPane p2 = new JScrollPane(view_loans);
											JPanel p3 = new JPanel();
											
											p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
											p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
											
											back.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													review_loans_frame.dispose();
													loan_processor_frame.setVisible(true);
												}
											});
											
											approve.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													int row = view_loans.getSelectedRow();
													for(int i = 0; i < loans.size();) {
														if(((String)view_loans.getValueAt(row, 1)).equals(loans.get(i).get_account().get_account_number())) {
															loans.get(i).set_reviewed(true);
															loans.get(i).set_reviewed(true);
															loans.remove(i);
															review_loans_frame.repaint();
															review_loans_frame.revalidate();
														}
													}
												}
											});
											
											decline.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													int row = view_loans.getSelectedRow();
													for(int i = 0; i < loans.size();) {
														if(((String)view_loans.getValueAt(row, 1)).equals(loans.get(i).get_account().get_account_number())) {
															loans.get(i).set_reviewed(false);
															loans.get(i).set_reviewed(true);
															loans.remove(i);
															review_loans_frame.repaint();
															review_loans_frame.revalidate();
														}
													}
												}
											});
											
											p1.add(back);
											p3.add(approve); p3.add(decline);
											
											review_loans_frame.setLayout(new GridLayout(3,1,10,10));
											review_loans_frame.add(p1); review_loans_frame.add(p2); review_loans_frame.add(p3);
											review_loans_frame.setVisible(true); loan_processor_frame.setVisible(false);
											review_loans_frame.setSize(800, 800);
											review_loans_frame.setTitle("X Banking System");
										}
									});
									
									JPanel p1 = new JPanel();
									JPanel p2 = new JPanel();
									JPanel p3 = new JPanel();
									
									p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
									p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
									p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
									
									p1.add(sign_out); p1.add(msg); p2.add(salary); p3.add(review_loans);
									
									loan_processor_frame.setLayout(new GridLayout(3, 1, 10, 10));
									loan_processor_frame.add(p1); loan_processor_frame.add(p2); loan_processor_frame.add(p3);
									loan_processor_frame.setVisible(true);
									login_frame.setVisible(false);
									loan_processor_frame.setTitle("X Banking System");
									loan_processor_frame.setSize(800, 800);
								}
								
								else {
									JFrame manager_frame = new JFrame();
									JLabel msg = new JLabel("Hello," + session_employees.get(i).get_name());
									JLabel salary = new JLabel("Your salary is" + session_employees.get(i).get_salary());
									JButton sign_out = new JButton("Sign out");
									JButton repo = new JButton("Repository");
									sign_out.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											manager_frame.dispose();
											main_frame.setVisible(true);
										}
									});
									
									repo.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											JFrame repo_frame = new JFrame();
											
											
											JButton back = new JButton("Back");
											JPanel p1 = new JPanel();
											JLabel current_capital = new JLabel("Current capital is: " + session_employees.get(current_employee_final).get_office().get_repository().get_capital());
											p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
											
											back.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													repo_frame.dispose();
													manager_frame.setVisible(true);
												}
											});
											
											p1.add(back); p1.add(current_capital);
											
											String columns[] = {"Asset Id", "Asset type", "Amount", "Equivalent to"};
											
											ArrayList<Asset> assets = session_employees.get(current_employee_final).get_office().get_repository().get_assets();
											
											String data[][] = new String[assets.size()][4];
											
											for(int i = 0; i < assets.size(); i++) {
												data[i][0] = assets.get(i).get_assetId();
												data[i][1] = assets.get(i).get_type().name();
												data[i][2] = Double.toString(assets.get(i).get_amount());
												data[i][3] = Double.toString(assets.get(i).get_equivalent_to());
											}
											
											JTable assets_table = new JTable(data, columns);
											JScrollPane p2 = new JScrollPane(assets_table);
											repo_frame.add(p1); repo_frame.add(p2);
											repo_frame.setLayout(new GridLayout(2,1,10,10));
											repo_frame.setVisible(true);
											manager_frame.setVisible(false);
											repo_frame.setTitle("X Banking System");
											repo_frame.setSize(800, 800);
											
										}
									});
									
									JPanel p1 = new JPanel();
									JPanel p2 = new JPanel();
									JPanel p3 = new JPanel();
									
									p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
									p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
									p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
									p1.add(sign_out); p1.add(msg); p2.add(salary); p3.add(repo);
									manager_frame.setLayout(new GridLayout(3, 1, 10, 10));
									manager_frame.add(p1); manager_frame.add(p2); manager_frame.add(p3);
									manager_frame.setVisible(true);
									login_frame.setVisible(false);
									manager_frame.setTitle("X Banking System");
									manager_frame.setSize(800, 800);
								}
								break;
							}
						}
						
					}
				});
				
				JPanel p1 = new JPanel();
				JPanel p2 = new JPanel();
				JPanel p3 = new JPanel();
				JPanel p4 = new JPanel();
				JPanel p5 = new JPanel();

				p1.add(back); p1.add(empty2);
				p2.add(msg); p2.add(empty);
				p3.add(username); p3.add(username_field);
				p4.add(password);  p4.add(password_field);
				p5.add(empty3); p5.add(login);


				login_frame.add(p1);
				login_frame.add(p2);
				login_frame.add(p3);
				login_frame.add(p4);
				login_frame.add(p5);
				
				p1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p5.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

				login_frame.setLayout(new GridLayout(5, 1, 20, 20));
				login_frame.setTitle("X Banking System");
				login_frame.setSize(800, 800);
				main_frame.setVisible(false);
				login_frame.setVisible(true);
				
			}
		});
		
		alogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame login_frame = new JFrame();
				
				JLabel msg = new JLabel("Please, enter your credentials");
				JLabel empty = new JLabel(" ");
				JLabel empty2 = new JLabel(" ");
				JLabel empty3 = new JLabel(" ");
				JLabel username = new JLabel("Username: ");
				JLabel password = new JLabel("Password: ");
		
				JTextField username_field = new JTextField(40);
				JPasswordField password_field = new JPasswordField(40);
				
				
				JButton back = new JButton("back");
				JButton login = new JButton("login");
				
				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						login_frame.dispose();
						main_frame.setVisible(true);
					}
				});
				
				login.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFrame account_dashboard = new JFrame();
						
						String username = username_field.getText();
						String password = new String(password_field.getPassword());
						
						JButton sign_out = new JButton("Sign out");
						JButton deposit = new JButton("Deposit");
						JButton withdraw = new JButton("Withdraw");
						JButton send_money = new JButton("Send Money");
						JButton request_loan = new JButton("Request Loan");
						JButton current_loans = new JButton("Current Loans");
						JButton transactions = new JButton("Transactions");
						JButton make_investment = new JButton("Make Investment");
						JButton investments = new JButton("Investments");
						JButton request_card = new JButton("Request Card");
						JButton cards = new JButton("Cards");
						
						JLabel msg = new JLabel();
						JLabel current_balance = new JLabel();
						
						sign_out.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								account_dashboard.dispose();
								main_frame.setVisible(true);
							}
						});
						
						int current_account = -1;
						
						
						for(int i = 0; i < session_accounts.size(); i++) {
							if(username.equals(session_accounts.get(i).get_username()) &&
									password.equals(session_accounts.get(i).get_password())) {
								current_account = i;
							}
						}
						
						final int current_account_final = current_account;
						
						if (current_account > -1) {
							msg.setText("Welcome back, " + session_accounts.get(current_account).get_name());
							account_dashboard.setVisible(true);
							login_frame.dispose();
							current_balance.setText("Current Balance: " + session_accounts.get(current_account).get_balance());
						}
						
						deposit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame deposit_frame = new JFrame();
								
								JButton back = new JButton("Back");
								JLabel msg = new JLabel("please select amount to deposit: ");
								SpinnerNumberModel model = new SpinnerNumberModel(0.0, 0.0, 50*200.0, 50.0);
								JSpinner deposit_value = new JSpinner(model);
								JButton deposit1 = new JButton("Deposit");

								JPanel p1 = new JPanel();
								JPanel p2 = new JPanel();
								JPanel p3 = new JPanel();
								JPanel p4 = new JPanel();

								
								back.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										deposit_frame.dispose();
										current_balance.setText("Current Balance: " + session_accounts.get(current_account_final).get_balance());
										account_dashboard.setVisible(true);
										account_dashboard.repaint();
										account_dashboard.revalidate();
									}
								});
								 
								
								deposit1.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										JLabel successful_deposit = new JLabel(((double) deposit_value.getValue()) + " has been added to your account");
										p3.add(successful_deposit);
										session_accounts.get(current_account_final).deposit((double)deposit_value.getValue());
										deposit_frame.repaint();
										deposit_frame.revalidate();
									}
								});
								

								
								p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p4.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								
								p1.add(back); p2.add(msg); p2.add(deposit_value); p2.add(deposit1);
								
								deposit_frame.setVisible(true);
								account_dashboard.setVisible(false);
								deposit_frame.add(p1); deposit_frame.add(p2); deposit_frame.add(p3); deposit_frame.add(p4);
								deposit_frame.setSize(800, 800);
								deposit_frame.setTitle("X Banking System");
								deposit_frame.setLayout(new GridLayout(4,1, 10, 10));
							}
						});
						
						withdraw.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame withdraw_frame = new JFrame();
								
								JButton back = new JButton("Back");
								JLabel msg = new JLabel("please select amount to withdraw: ");
								SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50*200, 50);
								JSpinner withdraw_value  = new JSpinner(model);
								JButton withdraw1 = new JButton("Withdraw");
								JLabel unsuccessful_withdraw = new JLabel("Insufficient funds!");
								
								JPanel p1 = new JPanel(); 
								JPanel p2 = new JPanel();
								JPanel p3 = new JPanel();
								
								back.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										current_balance.setText("Current Balance: " + session_accounts.get(current_account_final).get_balance());
										withdraw_frame.dispose();
										account_dashboard.setVisible(true);
										account_dashboard.repaint();
										account_dashboard.revalidate();
									}
								});
								
								withdraw1.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if((int) withdraw_value.getValue() > session_accounts.get(current_account_final).get_balance()) {
											p3.add(unsuccessful_withdraw);
											withdraw_frame.repaint();
											withdraw_frame.revalidate();
										}
											
										else {
											JLabel successful_withdraw = new JLabel(((int) withdraw_value.getValue()) + " has been withdrawn from your account");
											p3.add(successful_withdraw);
											session_accounts.get(current_account_final).withdraw((int)withdraw_value.getValue());
											withdraw_frame.repaint();
											withdraw_frame.revalidate();
											
										}
									}
								});
								

								
								p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								
								p1.add(back); p2.add(msg); p2.add(withdraw_value); p2.add(withdraw1);
								
								withdraw_frame.setVisible(true);
								account_dashboard.setVisible(false);
								withdraw_frame.add(p1); withdraw_frame.add(p2); withdraw_frame.add(p3);
								withdraw_frame.setSize(800, 800);
								withdraw_frame.setTitle("X Banking System");
								withdraw_frame.setLayout(new GridLayout(3,1, 10, 10));
							}
						});

						send_money.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame send_money_frame = new JFrame();
								
								JButton back = new JButton("Back");
								JButton send_money = new JButton("Send Money");
								
								JLabel account_number_label = new JLabel("Reciever Account Number: ");
								JLabel amount_label = new JLabel("Amount to send: ");
								
								SpinnerNumberModel model = new SpinnerNumberModel(0.0, 0.0, 50*200, 50);
								JSpinner amount_spinner = new JSpinner(model);
								
								JTextField account_number_field = new JTextField(20);
								
								back.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										current_balance.setText("Current Balance: " + session_accounts.get(current_account_final).get_balance());
										send_money_frame.dispose();
										account_dashboard.setVisible(true);
										account_dashboard.repaint();
										account_dashboard.revalidate();
									}
								});
								
								JPanel p1 = new JPanel();
								JPanel p2 = new JPanel();
								JPanel p3 = new JPanel();
								JPanel p4 = new JPanel();

								
								p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p4.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

								p1.add(back);
								p2.add(account_number_label); p2.add(account_number_field);
								p3.add(amount_label); p3.add(amount_spinner); p3.add(send_money);
								
								send_money.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										JLabel invalid_account_number = new JLabel("The account number doesn't exist or invalid!");
										JLabel insufficient_funds = new JLabel("Insufficient funds!");
										String account_number = account_number_field.getText();
										double amount = (double) amount_spinner.getValue();
										
										int reciever_account_index = -1;
										boolean valid = false;
										for(int i = 0; i < session_accounts.size(); i++) {
											if(session_accounts.get(i).get_account_number().equals(account_number)) {
												valid = true;
												reciever_account_index = i;
												break;
											}
										}
										
										if(valid) {
											if(session_accounts.get(current_account_final).get_balance() >= amount) {
												session_accounts.get(current_account_final).send_transaction(session_accounts.get(reciever_account_index), amount);
												JLabel successful_transaction = new JLabel(amount + " have been transferred to account number " + account_number + "registered under the name: " + session_accounts.get(current_account_final).get_name());
												p4.add(successful_transaction);
											}
											else {
												p4.add(insufficient_funds);
											}
										}
										else {
											p2.add(invalid_account_number);
										}
									}
								});
								
								send_money_frame.setLayout(new GridLayout(4, 1, 10, 10));
								send_money_frame.add(p1); send_money_frame.add(p2);
								send_money_frame.add(p3); send_money_frame.add(p4);
								send_money_frame.setSize(800, 800);
								send_money_frame.setTitle("X Banking System");
								send_money_frame.setVisible(true);
								account_dashboard.setVisible(false);
							}
						});
						
						request_loan.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame request_loan_frame = new JFrame();
								
								JLabel amount_label = new JLabel("Requested Loan Amount: ");
								JLabel interest_rate_label = new JLabel("Offered Interest Rate: ");
								JLabel payback_duration_label = new JLabel("Payback duration in months: ");
								
								SpinnerNumberModel amount_model = new SpinnerNumberModel(50000.0, 50000.0, 100_000_000_000.0, 5000);
								SpinnerNumberModel interest_rate_model = new SpinnerNumberModel(0.1, 0.1, 1, 0.01);
								SpinnerNumberModel payback_duration_model = new SpinnerNumberModel(1, 1, 600, 1);
								JSpinner amount_spinner = new JSpinner(amount_model);
								JSpinner interest_rate_spinner = new JSpinner(interest_rate_model);
								JSpinner payback_duration_spinner = new JSpinner(payback_duration_model);
								
								JButton back = new JButton("Back");
								JButton request_loan = new JButton("Send Request");
								
								JPanel p1 = new JPanel();
								JPanel p2 = new JPanel();
								JPanel p3 = new JPanel();
								JPanel p4 = new JPanel();
								
								p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p4.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								
								p1.add(back);
								p2.add(amount_label); p2.add(amount_spinner); p2.add(interest_rate_label); p2.add(interest_rate_spinner);
								p3.add(payback_duration_label); p3.add(payback_duration_spinner); p3.add(request_loan);
								
								back.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										current_balance.setText("Current Balance: " + session_accounts.get(current_account_final).get_balance());
										request_loan_frame.dispose();
										account_dashboard.setVisible(true);
										account_dashboard.repaint();
										account_dashboard.revalidate();
									}
								});
								
								request_loan.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										JLabel loan_request_sent = new JLabel("Loan request is sent and being processed, check acceptance later");
										Loan loan = new Loan(session_accounts.get(current_account_final), ((double) amount_spinner.getValue()), ((double) interest_rate_spinner.getValue()), ((int) payback_duration_spinner.getValue()));
										session_accounts.get(current_account_final).add_loan(loan);
										p4.add(loan_request_sent);
									}
								});
								
								
								request_loan_frame.add(p1); request_loan_frame.add(p2);
								request_loan_frame.add(p3); request_loan_frame.add(p4);
								request_loan_frame.setLayout(new GridLayout(4,1,10, 10));
								request_loan_frame.setSize(800, 800);
								request_loan_frame.setTitle("X Banking System");
								request_loan_frame.setVisible(true);
								account_dashboard.setVisible(false);
								
							}
						});

						current_loans.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame current_loans_frame = new JFrame();
								
								JPanel p1 = new JPanel();
								
								p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								JButton back = new JButton("back");
								
								back.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										current_loans_frame.dispose();
										account_dashboard.setVisible(true);
									}
								});
								
								ArrayList<Loan> loans = new ArrayList<Loan>();
								loans = session_accounts.get(current_account_final).get_loans();
								String columns[] = {"Amount", "Interest Rate", "Duration", "Reviewed", "Accepted"};
								String data[][] = new String[loans.size()][5];
								
								for(int i = 0; i < loans.size(); i++) {
									data[i][0] = Double.toString(loans.get(i).get_amount());
									data[i][1] = Double.toString(loans.get(i).get_interest_rate());
									data[i][2] = Integer.toString(loans.get(i).get_duration_months());
									if(loans.get(i).is_reviewed()) data[i][3] = "Yes";
									else data[i][3] = "No";
									
									if(loans.get(i).is_approved()) data[i][4] = "Yes";
									else data[i][4] = "No";
									
								}
								JTable loans_table = new JTable(data, columns);
								JScrollPane p2 = new JScrollPane(loans_table);

								p1.add(back);
								
								current_loans_frame.add(p1); current_loans_frame.add(p2);
								current_loans_frame.setVisible(true);
								account_dashboard.setVisible(false);
								current_loans_frame.setSize(800, 800);
								current_loans_frame.setLayout(new GridLayout(2, 1, 10, 10));
							}
						});
						
						transactions.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame transactions_frame = new JFrame();
								
								JPanel p1 = new JPanel();
								
								p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

								JButton back = new JButton("back");
								
								back.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										transactions_frame.dispose();
										account_dashboard.setVisible(true);
									}
								});
								
								ArrayList<Transaction> transactions_list = new ArrayList<Transaction>();
								transactions_list = session_accounts.get(current_account_final).get_transactions();
								String columns[] = {"Sender", "Reciever", "Amount", "Time sent"};
								String data[][] = new String[transactions_list.size()][4];
								
								for(int i = 0; i < transactions_list.size(); i++) {
									
									data[i][0] = transactions_list.get(i).get_sender().get_username();
									data[i][1] = transactions_list.get(i).get_reciever().get_username();
									data[i][2] = Double.toString(transactions_list.get(i).get_transaction_amount());
									data[i][3] = transactions_list.get(i).get_time_string();
									
									
								}
								JTable transactions_table = new JTable(data, columns);
								
								p1.add(back);
								JScrollPane p2 = new JScrollPane(transactions_table);

								transactions_frame.add(p1); transactions_frame.add(p2);
								transactions_frame.setVisible(true);
								account_dashboard.setVisible(false);
								transactions_frame.setSize(800, 800);
								transactions_frame.setLayout(new GridLayout(2, 1, 10, 10));
							}
						});

						make_investment.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame make_investment_frame = new JFrame();
								
								JLabel price_label = new JLabel("Investment price: ");
								JLabel return_rate_label = new JLabel("return rate is 0.20");
								JLabel duration_label = new JLabel("Duration: ");
								JLabel payment_duration = new JLabel("Payment Duration is 3 months");
								
								SpinnerNumberModel price_model = new SpinnerNumberModel(5000.0, 5000.0, 50_000_000_000.0, 5000.0);
								SpinnerNumberModel duration_model = new SpinnerNumberModel(3, 3, 300, 3);
								JSpinner price_spinner = new JSpinner(price_model);
								JSpinner duration_spinner = new JSpinner(duration_model);
								
								JPanel p1 = new JPanel();
								JPanel p2 = new JPanel();
								JPanel p3 = new JPanel();
								JPanel p4 = new JPanel();
								
								p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p4.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

								
								
								JButton back = new JButton("Back");
								JButton make_investment1 = new JButton("Make investment");
								
								p1.add(back);
								p2.add(price_label); p2.add(price_spinner); p2.add(return_rate_label);
								p3.add(duration_label); p3.add(duration_spinner); p3.add(payment_duration);
								p4.add(make_investment1);
								
								back.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										current_balance.setText("Current Balance: " + session_accounts.get(current_account_final).get_balance());
										account_dashboard.setVisible(true);
										make_investment_frame.dispose();
										account_dashboard.repaint();
										account_dashboard.revalidate();
									}
								});
								
								make_investment1.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(((double)price_spinner.getValue()) > session_accounts.get(current_account_final).get_balance()) {
											JLabel insufficient_funds = new JLabel("Insufficient funds");
											p4.add(insufficient_funds);
										}
										
										else {
											Investment investment = new Investment(((double) price_spinner.getValue()), 0.2, ((int) duration_spinner.getValue()), 3);
											session_accounts.get(current_account_final).add_investment(investment);
											JLabel investment_made = new JLabel("Investment made successfully");
											p4.add(investment_made);
										}
									}
								});
								
								make_investment_frame.setLayout(new GridLayout(4,1, 10, 10));
								make_investment_frame.add(p1); make_investment_frame.add(p2);
								make_investment_frame.add(p3); make_investment_frame.add(p4);
								make_investment_frame.setVisible(true);
								account_dashboard.setVisible(false);
								make_investment_frame.setSize(800,800);
								make_investment_frame.setTitle("X Banking System");
							}
						});
						
						investments.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame investments_frame = new JFrame();
								
								JPanel p1 = new JPanel();
								
								p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

								JButton back = new JButton("back");
								
								back.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										investments_frame.dispose();
										account_dashboard.setVisible(true);
									}
								});
								
								ArrayList<Investment> investments_list = new ArrayList<Investment>();
								investments_list = session_accounts.get(current_account_final).get_investments();
								String columns[] = {"Price", "Return Rate", "Duration", "Payback Period"};
								String data[][] = new String[investments_list.size()][4];
								
								for(int i = 0; i < investments_list.size(); i++) {
									data[i][0] = Double.toString(investments_list.get(i).get_price());
									data[i][1] = Double.toString(investments_list.get(i).get_return_rate());
									data[i][2] = Integer.toString(investments_list.get(i).get_duration_months());
									data[i][3] = Integer.toString(investments_list.get(i).get_payment_duration());
									
								}
								JTable investments_table = new JTable(data, columns);
								JScrollPane p2 = new JScrollPane(investments_table);

								p1.add(back); 
								
								investments_frame.add(p1); investments_frame.add(p2);
								investments_frame.setVisible(true);
								account_dashboard.setVisible(false);
								investments_frame.setSize(800, 800);
								investments_frame.setLayout(new GridLayout(2, 1, 10, 10));
							}
						});

						request_card.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								
								JFrame request_card_frame = new JFrame();
								JPanel p1 = new JPanel();
								JPanel p2 = new JPanel();
								
								p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
								
								JButton back = new JButton("back");
								JButton request_card = new JButton("Request card");
								JComboBox<String> card_type_combobox = new JComboBox<String>();									
								card_type_combobox.addItem("Debit"); card_type_combobox.addItem("Credit");
								card_type_combobox.addItem("Prepaid"); 	card_type_combobox.addItem("Purchases");


								
								back.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										request_card_frame.dispose();
										account_dashboard.setVisible(true);
									}
								});
								
								request_card.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										JLabel request_success = new JLabel("request has been sent");
										CardType type;
										if(card_type_combobox.getSelectedIndex() == 0) type = CardType.Debit;
										else if (card_type_combobox.getSelectedIndex() == 1) type = CardType.Credit;
										else if (card_type_combobox.getSelectedIndex() == 2) type = CardType.Prepaid;
										else type = CardType.Purchases;
										Card card = new Card(session_accounts.get(current_account_final), type, Network.Visa);
										session_accounts.get(current_account_final).add_card(card);
										p2.add(request_success);
									}
								});
								
								p1.add(back); p2.add(card_type_combobox); p2.add(request_card);
								request_card_frame.add(p1); request_card_frame.add(p2);
								request_card_frame.setVisible(true);
								account_dashboard.setVisible(false);
								request_card_frame.setTitle("X Banking System");
								request_card_frame.setSize(800, 800);
								request_card_frame.setLayout(new GridLayout(2, 1, 10, 10));

							}
						});
						
						cards.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame cards_frame = new JFrame();
								
								JPanel p1 = new JPanel();
								
								p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

								JButton back = new JButton("back");
								
								back.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										cards_frame.dispose();
										account_dashboard.setVisible(true);
									}
								});
								
								ArrayList<Card> cards = new ArrayList<Card>();
								cards = session_accounts.get(current_account_final).get_cards();
								String columns[] = {"Card Number", "Expiration", "CVV", "Type", "Network","Reviewed", "Accepted", "Removed"};
								String data[][] = new String[cards.size()][8];
								
								for(int i = 0; i < cards.size(); i++) {
									data[i][0] = cards.get(i).get_card_number();
									data[i][1] = cards.get(i).get_expiration_string();
									data[i][2] = Integer.toString(cards.get(i).get_cvv());
									if(cards.get(i).get_type() == CardType.Debit) data[i][3] = "Debit";
									else if(cards.get(i).get_type() == CardType.Credit) data[i][3] = "Credit";
									else if(cards.get(i).get_type() == CardType.Prepaid) data[i][3] = "Prepaid";
									else if(cards.get(i).get_type() == CardType.Purchases) data[i][3] = "Purchases";
									data[i][4] = "Visa";
									if (cards.get(i).get_reviewed()) data[i][5] = "Yes";
									else data[i][5] = "No";
									if(cards.get(i).get_accepted()) data[i][6] = "Yes";
									else data[i][6] = "No";
									if(cards.get(i).get_remove()) data[i][7] = "Yes";
									else data[i][7] = "No";
									
								}
								JTable cards_table = new JTable(data, columns);
								JScrollPane p2 = new JScrollPane(cards_table);
								p1.add(back);
								
								cards_frame.add(p1); cards_frame.add(p2);
								cards_frame.setVisible(true);
								account_dashboard.setVisible(false);
								cards_frame.setSize(800, 800);
								cards_frame.setLayout(new GridLayout(2, 1, 10, 10));
							}
						});

						
						JPanel p1 = new JPanel();
						JPanel p2 = new JPanel();
						JPanel p3 = new JPanel();
						JPanel p4 = new JPanel();
						JPanel p5 = new JPanel();
						JPanel p6 = new JPanel();
						
						p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
						p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
						p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
						p4.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
						p5.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
						p6.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

						
						p1.add(sign_out); p1.add(msg);
						p2.add(current_balance);
						p3.add(deposit); p3.add(withdraw); p3.add(send_money);
						p4.add(request_loan); p4.add(current_loans); p4.add(transactions);
						p5.add(make_investment); p5.add(investments);
						p6.add(request_card); p6.add(cards);
						
						account_dashboard.add(p1);
						account_dashboard.add(p2); account_dashboard.add(p3);
						account_dashboard.add(p4); account_dashboard.add(p5);
						account_dashboard.add(p6);
						
						account_dashboard.setLayout(new GridLayout(6,1, 10, 10));
						account_dashboard.setTitle("X Banking System");
						account_dashboard.setSize(800,800);
						
						
					}
				});
				
				JPanel p1 = new JPanel();
				JPanel p2 = new JPanel();
				JPanel p3 = new JPanel();
				JPanel p4 = new JPanel();
				JPanel p5 = new JPanel();

				p1.add(back); p1.add(empty2);
				p2.add(msg); p2.add(empty);
				p3.add(username); p3.add(username_field);
				p4.add(password);  p4.add(password_field);
				p5.add(empty3); p5.add(login);


				login_frame.add(p1);
				login_frame.add(p2);
				login_frame.add(p3);
				login_frame.add(p4);
				login_frame.add(p5);
				
				p1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p5.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

				login_frame.setLayout(new GridLayout(5, 1, 20, 20));
				login_frame.setTitle("X Banking System");
				login_frame.setSize(800, 800);
				main_frame.setVisible(false);
				login_frame.setVisible(true);
				
			}
		});

		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame register_frame = new JFrame();
				
				JButton back = new JButton("Back");
				JButton send_request = new JButton("Register");
				
				JLabel msg = new JLabel("Please fill the following form: ");
				JLabel empty = new JLabel("                        ");
				JLabel empty1 = new JLabel("                        ");
				JLabel empty3 = new JLabel("                        ");
				JLabel empty4 = new JLabel("                        ");
				JLabel fname_label = new JLabel("first name: ");
				JLabel mname_label = new JLabel("middle name: ");
				JLabel lname_label = new JLabel("last name: ");				
				JLabel id_label = new JLabel("National ID: ");
				JLabel username_label = new JLabel("username: ");
				JLabel password_label = new JLabel("password: ");
				JTextField fname = new JTextField(25);
				JTextField mname = new JTextField(25);
				JTextField lname = new JTextField(25);
				JTextField id = new JTextField(25);
				JTextField username = new JTextField(25);
				JPasswordField password = new JPasswordField(25);
				

				
				JPanel p1 = new JPanel();
				JPanel p2 = new JPanel();
				JPanel p3 = new JPanel();
				JPanel p4 = new JPanel();
				JPanel p5 = new JPanel();
				JPanel p6 = new JPanel();
				JPanel p7 = new JPanel();
				JPanel p8 = new JPanel();
				JPanel p9 = new JPanel();

				
				p1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p5.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p6.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p7.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p8.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				p9.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				
				p1.add(back); p1.add(empty);
				p2.add(msg); p2.add(empty1);
				p3.add(fname_label); p3.add(fname);
				p4.add(mname_label); p4.add(mname);
				p5.add(lname_label); p5.add(lname);
				p6.add(id_label); p6.add(id);
				p7.add(username_label); p7.add(username);
				p8.add(password_label); p8.add(password);
				p9.add(empty3); p9.add(send_request);
				
				register_frame.add(p1);
				register_frame.add(p2);
				register_frame.add(p3);
				register_frame.add(p4);
				register_frame.add(p5);
				register_frame.add(p6);
				register_frame.add(p7);
				register_frame.add(p8);
				register_frame.add(p9);

				
				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						register_frame.dispose();
						main_frame.setVisible(true);
					}
				});
				
				send_request.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String first_name = fname.getText();
						String middle_name = mname.getText();
						String last_name = lname.getText();
						String national_id = id.getText();
						String user_name = username.getText();
						char[] pass = password.getPassword();
						
						JLabel invalid_fname = new JLabel("Invalid first name, name should contain only letters");
						JLabel long_fname = new JLabel("First name is too long!");
						JLabel short_fname = new JLabel("Very short first name!");
						
						JLabel invalid_mname = new JLabel("Invalid middle name, name should contain only letters");
						JLabel long_mname = new JLabel("Middle name is too long!");
						JLabel short_mname = new JLabel("Very short middle name!");
						JLabel invalid_lname = new JLabel("Invalid last name, name should contain only letters");
						JLabel long_lname = new JLabel("Last name is too long!");
						JLabel short_lname = new JLabel("Very short last name!");
						
						JLabel invalid_id = new JLabel("Invalid id, id is 16 digits number only with no letters");
						
						JLabel invalid_username = new JLabel("Invalid username, must be at least 3 characters and contain at least one letter");
						JLabel invalid_password = new JLabel("Invalid password, must be of length 8 to 32");
						
						boolean valid = true;
						
						if(first_name.length() > 25) {
							p3.add(long_fname);
							valid = false;
						}
						else if(first_name.length() <2) {
							p3.add(short_fname);
							valid = false;
						}
						else {
							for(int i=0; i < first_name.length(); i++) {
								char c = first_name.charAt(i);
								if(Character.isDigit(c)) {
									p3.add(invalid_fname);
									valid = false;
									break;
								}
							}
						}

						
						if(middle_name.length() > 25) {
							p4.add(long_mname);
							valid = false;
						}
						else if(middle_name.length() <2) {
							p4.add(short_mname);
							valid = false;
						}
						else {
							for(int i=0; i < middle_name.length(); i++) {
								char c = middle_name.charAt(i);
								if(Character.isDigit(c)) {
									p4.add(invalid_mname);
									valid = false;
									break;
								}
							}
						}
						
						if(last_name.length() > 25) {
							p5.add(long_lname);
							valid = false;
						}
						else if(last_name.length() <2) {
							p5.add(short_lname);
							valid = false;
						}
						else {
							for(int i=0; i < last_name.length(); i++) {
								char c = last_name.charAt(i);
								if(Character.isDigit(c)) {
									p5.add(invalid_lname);
									valid = false;
									break;
								}
							}
						}
						
						if(national_id.length() != 16) {
							p6.add(invalid_id);
							valid = false;
						}
						else
						{
							for(int i=0; i < national_id.length(); i++) {
								char c = national_id.charAt(i);
								if(Character.isLetter(c)) {
									p6.add(invalid_id);
									valid = false;
									break;
								}
							}
						}
						
						if(user_name.length() > 32) {
							p7.add(invalid_username);
							valid = false;
						}
						else if (user_name.length() < 3) {
							p7.add(invalid_username);
							valid = false;
						}
						
						if(pass.length > 32) p8.add(invalid_password);
						else if (pass.length < 8) p8.add(invalid_password);
						
						boolean new_customer = true;
						if(valid) {
							for (int i = 0; i < session_customers.size(); i++) {
								JLabel registered = new JLabel("This customer is already registered");
								if (national_id.equals(session_customers.get(i).get_id())) {
									register_frame.add(registered);
									new_customer = false;
									break;
								}
							 
							}
						}
						
						if(new_customer && valid) {
							// create new customer
							String pass_string = new String(pass);
							Customer c = new Customer(user_name, pass_string, first_name, middle_name, last_name, national_id);
							session_customers.add(c);
							JFrame success = new JFrame();
							
							JLabel success_label = new JLabel("Your data has been registered successfully, you can login with it now!");
							JButton return_home = new JButton("Return to main page");
							
							success.add(success_label);
							success.add(return_home);
							
							return_home.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									success.dispose();
									main_frame.setVisible(true);
									
								}
							});
							
							success.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
							success.setVisible(true);
							register_frame.setVisible(false);
							success.setSize(800,800);
							success.setTitle("X Banking System");
							
						}
						
					}
				});
				
				register_frame.setLayout(new GridLayout(10, 1, 20, 20));
				main_frame.setVisible(false);
				register_frame.setVisible(true);
				register_frame.setTitle("X Banking System");
				register_frame.setSize(800, 800);
			}
		});
		
		main_frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		main_frame.setTitle("X Banking System");
		main_frame.setSize(800, 800);
		main_frame.setVisible(true);
	}
	
	 public static void main(String[] args) {
		new Gui();
	}
}
