package com.bank.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.bank.model.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:bank.db";
    private static DatabaseManager instance;
    
    private DatabaseManager() {
        initializeDatabase();
    }
    
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            createTables(conn);
            insertSampleData(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createTables(Connection conn) throws SQLException {
        // Create customers table
        String createCustomersTable = "CREATE TABLE IF NOT EXISTS customers (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "first_name TEXT NOT NULL," +
            "last_name TEXT NOT NULL," +
            "email TEXT UNIQUE NOT NULL," +
            "phone TEXT," +
            "address TEXT," +
            "date_of_birth TEXT," +
            "ssn TEXT UNIQUE," +
            "created_date TEXT" +
            ")";
        
        // Create accounts table
        String createAccountsTable = "CREATE TABLE IF NOT EXISTS accounts (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "account_number TEXT UNIQUE NOT NULL," +
            "customer_id INTEGER," +
            "account_type TEXT NOT NULL," +
            "balance REAL DEFAULT 0.0," +
            "interest_rate REAL DEFAULT 0.0," +
            "minimum_balance REAL DEFAULT 0.0," +
            "status TEXT DEFAULT 'ACTIVE'," +
            "created_date TEXT," +
            "FOREIGN KEY (customer_id) REFERENCES customers (id)" +
            ")";
        
        // Create transactions table
        String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "transaction_number TEXT UNIQUE NOT NULL," +
            "sender_account TEXT," +
            "receiver_account TEXT," +
            "amount REAL NOT NULL," +
            "transaction_type TEXT NOT NULL," +
            "description TEXT," +
            "transaction_date TEXT," +
            "status TEXT DEFAULT 'COMPLETED'" +
            ")";
        
        // Create employees table
        String createEmployeesTable = "CREATE TABLE IF NOT EXISTS employees (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "employee_id TEXT UNIQUE NOT NULL," +
            "first_name TEXT NOT NULL," +
            "last_name TEXT NOT NULL," +
            "email TEXT UNIQUE NOT NULL," +
            "role TEXT NOT NULL," +
            "salary REAL," +
            "department TEXT," +
            "manager_id INTEGER," +
            "hire_date TEXT," +
            "status TEXT DEFAULT 'ACTIVE'" +
            ")";
        
        // Create cards table
        String createCardsTable = "CREATE TABLE IF NOT EXISTS cards (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "card_number TEXT UNIQUE NOT NULL," +
            "account_id INTEGER," +
            "card_type TEXT NOT NULL," +
            "network TEXT NOT NULL," +
            "holder_name TEXT NOT NULL," +
            "expiry_date TEXT," +
            "cvv TEXT," +
            "credit_limit REAL DEFAULT 0.0," +
            "status TEXT DEFAULT 'ACTIVE'," +
            "issue_date TEXT," +
            "FOREIGN KEY (account_id) REFERENCES accounts (id)" +
            ")";
        
        // Create loans table
        String createLoansTable = "CREATE TABLE IF NOT EXISTS loans (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "loan_number TEXT UNIQUE NOT NULL," +
            "customer_id INTEGER," +
            "loan_type TEXT NOT NULL," +
            "principal_amount REAL NOT NULL," +
            "interest_rate REAL NOT NULL," +
            "term_months INTEGER NOT NULL," +
            "monthly_payment REAL," +
            "outstanding_balance REAL," +
            "start_date TEXT," +
            "end_date TEXT," +
            "status TEXT DEFAULT 'ACTIVE'," +
            "FOREIGN KEY (customer_id) REFERENCES customers (id)" +
            ")";
        
        // Create investments table
        String createInvestmentsTable = "CREATE TABLE IF NOT EXISTS investments (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "investment_id TEXT UNIQUE NOT NULL," +
            "customer_id INTEGER," +
            "investment_type TEXT NOT NULL," +
            "amount REAL NOT NULL," +
            "current_value REAL," +
            "purchase_date TEXT," +
            "maturity_date TEXT," +
            "interest_rate REAL," +
            "status TEXT DEFAULT 'ACTIVE'," +
            "FOREIGN KEY (customer_id) REFERENCES customers (id)" +
            ")";
        
        // Create assets table
        String createAssetsTable = "CREATE TABLE IF NOT EXISTS assets (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "asset_id TEXT UNIQUE NOT NULL," +
            "asset_type TEXT NOT NULL," +
            "description TEXT," +
            "value REAL NOT NULL," +
            "purchase_date TEXT," +
            "location TEXT," +
            "status TEXT DEFAULT 'ACTIVE'" +
            ")";
        
        // Create offices table
        String createOfficesTable = "CREATE TABLE IF NOT EXISTS offices (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "office_code TEXT UNIQUE NOT NULL," +
            "office_name TEXT NOT NULL," +
            "address TEXT," +
            "city TEXT," +
            "state TEXT," +
            "zip_code TEXT," +
            "phone TEXT," +
            "manager_id INTEGER," +
            "status TEXT DEFAULT 'ACTIVE'," +
            "FOREIGN KEY (manager_id) REFERENCES employees (id)" +
            ")";
        
        // Create loan_payments table
        String createLoanPaymentsTable = "CREATE TABLE IF NOT EXISTS loan_payments (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "loan_id INTEGER," +
            "payment_date TEXT," +
            "amount REAL," +
            "principal_amount REAL," +
            "interest_amount REAL," +
            "remaining_balance REAL," +
            "FOREIGN KEY (loan_id) REFERENCES loans (id)" +
            ")";
        
        conn.createStatement().execute(createCustomersTable);
        conn.createStatement().execute(createAccountsTable);
        conn.createStatement().execute(createTransactionsTable);
        conn.createStatement().execute(createEmployeesTable);
        conn.createStatement().execute(createCardsTable);
        conn.createStatement().execute(createLoansTable);
        conn.createStatement().execute(createInvestmentsTable);
        conn.createStatement().execute(createAssetsTable);
        conn.createStatement().execute(createOfficesTable);
        conn.createStatement().execute(createLoanPaymentsTable);
    }
    
    private void insertSampleData(Connection conn) throws SQLException {
        // Check if data already exists
        ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM customers");
        if (rs.next() && rs.getInt(1) > 0) {
            return; // Data already exists
        }
        
        // Insert sample customers with Egyptian names
        String insertCustomer = "INSERT INTO customers (first_name, last_name, email, phone, address, date_of_birth, ssn, created_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(insertCustomer);
        
        stmt.setString(1, "Ahmed");
        stmt.setString(2, "Hassan");
        stmt.setString(3, "ahmed.hassan@gmail.com");
        stmt.setString(4, "01012345678");
        stmt.setString(5, "15 Tahrir Square, Cairo, Egypt");
        stmt.setString(6, "1985-03-15");
        stmt.setString(7, "28503151234567");
        stmt.setString(8, "2024-01-15");
        stmt.executeUpdate();
        
        stmt.setString(1, "Fatima");
        stmt.setString(2, "Ali");
        stmt.setString(3, "fatima.ali@gmail.com");
        stmt.setString(4, "01123456789");
        stmt.setString(5, "25 Nile Corniche, Alexandria, Egypt");
        stmt.setString(6, "1990-07-22");
        stmt.setString(7, "29007222345678");
        stmt.setString(8, "2024-01-20");
        stmt.executeUpdate();
        
        stmt.setString(1, "Mohamed");
        stmt.setString(2, "Ibrahim");
        stmt.setString(3, "mohamed.ibrahim@gmail.com");
        stmt.setString(4, "01234567890");
        stmt.setString(5, "10 Pyramid Street, Giza, Egypt");
        stmt.setString(6, "1982-11-10");
        stmt.setString(7, "28211103456789");
        stmt.setString(8, "2024-01-25");
        stmt.executeUpdate();
        
        stmt.setString(1, "Yasmin");
        stmt.setString(2, "Mahmoud");
        stmt.setString(3, "yasmin.mahmoud@gmail.com");
        stmt.setString(4, "01098765432");
        stmt.setString(5, "8 Nasr City, Cairo, Egypt");
        stmt.setString(6, "1988-05-18");
        stmt.setString(7, "28805184567890");
        stmt.setString(8, "2024-02-01");
        stmt.executeUpdate();
        
        stmt.setString(1, "Omar");
        stmt.setString(2, "Farouk");
        stmt.setString(3, "omar.farouk@gmail.com");
        stmt.setString(4, "01156789012");
        stmt.setString(5, "22 Heliopolis, Cairo, Egypt");
        stmt.setString(6, "1992-12-03");
        stmt.setString(7, "29212035678901");
        stmt.setString(8, "2024-02-05");
        stmt.executeUpdate();
        
        stmt.setString(1, "Nour");
        stmt.setString(2, "Saad");
        stmt.setString(3, "nour.saad@gmail.com");
        stmt.setString(4, "01067890123");
        stmt.setString(5, "5 Maadi, Cairo, Egypt");
        stmt.setString(6, "1987-09-25");
        stmt.setString(7, "28709256789012");
        stmt.setString(8, "2024-02-10");
        stmt.executeUpdate();
        
        stmt.setString(1, "Karim");
        stmt.setString(2, "El-Sayed");
        stmt.setString(3, "karim.elsayed@gmail.com");
        stmt.setString(4, "01178901234");
        stmt.setString(5, "12 Zamalek, Cairo, Egypt");
        stmt.setString(6, "1991-04-14");
        stmt.setString(7, "29104147890123");
        stmt.setString(8, "2024-02-15");
        stmt.executeUpdate();
        
        stmt.setString(1, "Mariam");
        stmt.setString(2, "Youssef");
        stmt.setString(3, "mariam.youssef@gmail.com");
        stmt.setString(4, "01089012345");
        stmt.setString(5, "18 Dokki, Giza, Egypt");
        stmt.setString(6, "1989-08-30");
        stmt.setString(7, "28908308901234");
        stmt.setString(8, "2024-02-20");
        stmt.executeUpdate();
        
        // Insert sample accounts
        String insertAccount = "INSERT INTO accounts (account_number, customer_id, account_type, balance, interest_rate, minimum_balance, created_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement accountStmt = conn.prepareStatement(insertAccount);
        
        // Ahmed Hassan - Checking Account
        accountStmt.setString(1, "ACC001");
        accountStmt.setInt(2, 1);
        accountStmt.setString(3, "CHECKING");
        accountStmt.setDouble(4, 2847.32);
        accountStmt.setDouble(5, 0.01);
        accountStmt.setDouble(6, 100.00);
        accountStmt.setString(7, "2024-01-15");
        accountStmt.executeUpdate();
        
        // Fatima Ali - Savings Account
        accountStmt.setString(1, "ACC002");
        accountStmt.setInt(2, 2);
        accountStmt.setString(3, "SAVINGS");
        accountStmt.setDouble(4, 15234.67);
        accountStmt.setDouble(5, 0.035);
        accountStmt.setDouble(6, 500.00);
        accountStmt.setString(7, "2024-01-20");
        accountStmt.executeUpdate();
        
        // Mohamed Ibrahim - Business Account
        accountStmt.setString(1, "ACC003");
        accountStmt.setInt(2, 3);
        accountStmt.setString(3, "BUSINESS");
        accountStmt.setDouble(4, 8750.50);
        accountStmt.setDouble(5, 0.02);
        accountStmt.setDouble(6, 1000.00);
        accountStmt.setString(7, "2024-01-25");
        accountStmt.executeUpdate();
        
        // Yasmin Mahmoud - Checking Account
        accountStmt.setString(1, "ACC004");
        accountStmt.setInt(2, 4);
        accountStmt.setString(3, "CHECKING");
        accountStmt.setDouble(4, 4521.18);
        accountStmt.setDouble(5, 0.01);
        accountStmt.setDouble(6, 100.00);
        accountStmt.setString(7, "2024-02-01");
        accountStmt.executeUpdate();
        
        // Omar Farouk - Savings Account
        accountStmt.setString(1, "ACC005");
        accountStmt.setInt(2, 5);
        accountStmt.setString(3, "SAVINGS");
        accountStmt.setDouble(4, 12890.75);
        accountStmt.setDouble(5, 0.03);
        accountStmt.setDouble(6, 500.00);
        accountStmt.setString(7, "2024-02-05");
        accountStmt.executeUpdate();
        
        // Nour Saad - Business Account
        accountStmt.setString(1, "ACC006");
        accountStmt.setInt(2, 6);
        accountStmt.setString(3, "BUSINESS");
        accountStmt.setDouble(4, 25340.90);
        accountStmt.setDouble(5, 0.025);
        accountStmt.setDouble(6, 1000.00);
        accountStmt.setString(7, "2024-02-10");
        accountStmt.executeUpdate();
        
        // Karim El-Sayed - Checking Account
        accountStmt.setString(1, "ACC007");
        accountStmt.setInt(2, 7);
        accountStmt.setString(3, "CHECKING");
        accountStmt.setDouble(4, 3654.22);
        accountStmt.setDouble(5, 0.01);
        accountStmt.setDouble(6, 100.00);
        accountStmt.setString(7, "2024-02-15");
        accountStmt.executeUpdate();
        
        // Mariam Youssef - Savings Account
        accountStmt.setString(1, "ACC008");
        accountStmt.setInt(2, 8);
        accountStmt.setString(3, "SAVINGS");
        accountStmt.setDouble(4, 18765.43);
        accountStmt.setDouble(5, 0.032);
        accountStmt.setDouble(6, 500.00);
        accountStmt.setString(7, "2024-02-20");
        accountStmt.executeUpdate();
        
        // Ahmed Hassan - Credit Card Account
        accountStmt.setString(1, "ACC009");
        accountStmt.setInt(2, 1);
        accountStmt.setString(3, "CREDIT");
        accountStmt.setDouble(4, -1234.56);
        accountStmt.setDouble(5, 0.18);
        accountStmt.setDouble(6, -5000.00);
        accountStmt.setString(7, "2024-01-15");
        accountStmt.executeUpdate();
        
        // Insert sample employees with Egyptian names
        String insertEmployee = "INSERT INTO employees (employee_id, first_name, last_name, email, role, salary, department, hire_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement empStmt = conn.prepareStatement(insertEmployee);
        
        empStmt.setString(1, "EMP001");
        empStmt.setString(2, "Amina");
        empStmt.setString(3, "Abdel-Rahman");
        empStmt.setString(4, "amina.abdelrahman@securebank.com");
        empStmt.setString(5, "BANKER");
        empStmt.setDouble(6, 85000.00);
        empStmt.setString(7, "Customer Service");
        empStmt.setString(8, "2023-06-01");
        empStmt.executeUpdate();
        
        empStmt.setString(1, "EMP002");
        empStmt.setString(2, "Khaled");
        empStmt.setString(3, "Mansour");
        empStmt.setString(4, "khaled.mansour@securebank.com");
        empStmt.setString(5, "LOAN_PROCESSOR");
        empStmt.setDouble(6, 75000.00);
        empStmt.setString(7, "Loan Department");
        empStmt.setString(8, "2023-08-15");
        empStmt.executeUpdate();
        
        empStmt.setString(1, "EMP003");
        empStmt.setString(2, "Salma");
        empStmt.setString(3, "Fathy");
        empStmt.setString(4, "salma.fathy@securebank.com");
        empStmt.setString(5, "TELLER");
        empStmt.setDouble(6, 55000.00);
        empStmt.setString(7, "Branch Operations");
        empStmt.setString(8, "2024-01-10");
        empStmt.executeUpdate();
        
        empStmt.setString(1, "EMP004");
        empStmt.setString(2, "Hassan");
        empStmt.setString(3, "Mostafa");
        empStmt.setString(4, "hassan.mostafa@securebank.com");
        empStmt.setString(5, "MANAGER");
        empStmt.setDouble(6, 120000.00);
        empStmt.setString(7, "Branch Management");
        empStmt.setString(8, "2022-03-15");
        empStmt.executeUpdate();
        
        empStmt.setString(1, "EMP005");
        empStmt.setString(2, "Dina");
        empStmt.setString(3, "Zaki");
        empStmt.setString(4, "dina.zaki@securebank.com");
        empStmt.setString(5, "FINANCIAL_ADVISOR");
        empStmt.setDouble(6, 95000.00);
        empStmt.setString(7, "Investment Services");
        empStmt.setString(8, "2023-11-20");
        empStmt.executeUpdate();
        
        empStmt.setString(1, "EMP006");
        empStmt.setString(2, "Mahmoud");
        empStmt.setString(3, "Soliman");
        empStmt.setString(4, "mahmoud.soliman@securebank.com");
        empStmt.setString(5, "SECURITY_OFFICER");
        empStmt.setDouble(6, 65000.00);
        empStmt.setString(7, "Security Department");
        empStmt.setString(8, "2023-09-10");
        empStmt.executeUpdate();
        
        // Insert sample cards
        String insertCard = "INSERT INTO cards (card_number, account_id, card_type, network, holder_name, expiry_date, cvv, credit_limit, issue_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement cardStmt = conn.prepareStatement(insertCard);
        
        cardStmt.setString(1, "4532-1234-5678-9012");
        cardStmt.setInt(2, 1);
        cardStmt.setString(3, "DEBIT");
        cardStmt.setString(4, "VISA");
        cardStmt.setString(5, "Ahmed Hassan");
        cardStmt.setString(6, "12/27");
        cardStmt.setString(7, "123");
        cardStmt.setDouble(8, 0.0);
        cardStmt.setString(9, "2024-01-15");
        cardStmt.executeUpdate();
        
        cardStmt.setString(1, "5425-2345-6789-0123");
        cardStmt.setInt(2, 2);
        cardStmt.setString(3, "CREDIT");
        cardStmt.setString(4, "MASTERCARD");
        cardStmt.setString(5, "Fatima Ali");
        cardStmt.setString(6, "11/26");
        cardStmt.setString(7, "456");
        cardStmt.setDouble(8, 10000.0);
        cardStmt.setString(9, "2024-01-20");
        cardStmt.executeUpdate();
        
        // Insert sample loans
        String insertLoan = "INSERT INTO loans (loan_number, customer_id, loan_type, principal_amount, interest_rate, term_months, monthly_payment, outstanding_balance, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement loanStmt = conn.prepareStatement(insertLoan);
        
        loanStmt.setString(1, "LOAN001");
        loanStmt.setInt(2, 1);
        loanStmt.setString(3, "Personal");
        loanStmt.setDouble(4, 10000.0);
        loanStmt.setDouble(5, 0.08);
        loanStmt.setInt(6, 36);
        loanStmt.setDouble(7, 313.36);
        loanStmt.setDouble(8, 8500.0);
        loanStmt.setString(9, "2024-01-15");
        loanStmt.setString(10, "2027-01-15");
        loanStmt.executeUpdate();
        
        // Insert sample investments
        String insertInvestment = "INSERT INTO investments (investment_id, customer_id, investment_type, amount, current_value, purchase_date, interest_rate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement invStmt = conn.prepareStatement(insertInvestment);
        
        invStmt.setString(1, "INV001");
        invStmt.setInt(2, 2);
        invStmt.setString(3, "Fixed Deposit");
        invStmt.setDouble(4, 25000.0);
        invStmt.setDouble(5, 26500.0);
        invStmt.setString(6, "2024-01-20");
        invStmt.setDouble(7, 0.06);
        invStmt.executeUpdate();
        
        // Insert sample transactions
        String insertTransaction = "INSERT INTO transactions (transaction_number, sender_account, receiver_account, amount, transaction_type, description, transaction_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement transStmt = conn.prepareStatement(insertTransaction);
        
        transStmt.setString(1, "TXN001");
        transStmt.setString(2, "ACC001");
        transStmt.setString(3, "ACC002");
        transStmt.setDouble(4, 250.00);
        transStmt.setString(5, "TRANSFER");
        transStmt.setString(6, "Monthly payment");
        transStmt.setString(7, "2024-01-15 10:30:00");
        transStmt.executeUpdate();
        
        transStmt.setString(1, "TXN002");
        transStmt.setString(2, "ACC002");
        transStmt.setString(3, "ACC003");
        transStmt.setDouble(4, 500.00);
        transStmt.setString(5, "TRANSFER");
        transStmt.setString(6, "Business payment");
        transStmt.setString(7, "2024-01-22 14:15:00");
        transStmt.executeUpdate();
        
        transStmt.setString(1, "TXN003");
        transStmt.setString(2, "ACC003");
        transStmt.setString(3, "ACC001");
        transStmt.setDouble(4, 100.00);
        transStmt.setString(5, "TRANSFER");
        transStmt.setString(6, "Refund");
        transStmt.setString(7, "2024-01-25 09:45:00");
        transStmt.executeUpdate();
        
        transStmt.setString(1, "TXN004");
        transStmt.setString(2, "ACC001");
        transStmt.setString(3, "ACC002");
        transStmt.setDouble(4, 75.00);
        transStmt.setString(5, "TRANSFER");
        transStmt.setString(6, "Grocery payment");
        transStmt.setString(7, "2024-01-26 16:20:00");
        transStmt.executeUpdate();
        
        transStmt.setString(1, "TXN005");
        transStmt.setString(2, "ACC002");
        transStmt.setString(3, "ACC001");
        transStmt.setDouble(4, 200.00);
        transStmt.setString(5, "TRANSFER");
        transStmt.setString(6, "Loan payment");
        transStmt.setString(7, "2024-01-27 11:00:00");
        transStmt.executeUpdate();
        
        transStmt.setString(1, "TXN006");
        transStmt.setString(2, "ACC003");
        transStmt.setString(3, "ACC002");
        transStmt.setDouble(4, 1200.00);
        transStmt.setString(5, "TRANSFER");
        transStmt.setString(6, "Contract payment");
        transStmt.setString(7, "2024-01-28 13:30:00");
        transStmt.executeUpdate();
        
        transStmt.setString(1, "TXN007");
        transStmt.setString(2, "ACC001");
        transStmt.setString(3, "ACC003");
        transStmt.setDouble(4, 350.00);
        transStmt.setString(5, "TRANSFER");
        transStmt.setString(6, "Utility payment");
        transStmt.setString(7, "2024-01-29 08:15:00");
        transStmt.executeUpdate();
        
        // Insert sample offices
        String insertOffice = "INSERT INTO offices (office_code, office_name, address, city, state, zip_code, phone, manager_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement officeStmt = conn.prepareStatement(insertOffice);
        
        officeStmt.setString(1, "BR001");
        officeStmt.setString(2, "Main Branch");
        officeStmt.setString(3, "100 Financial St");
        officeStmt.setString(4, "City");
        officeStmt.setString(5, "State");
        officeStmt.setString(6, "12345");
        officeStmt.setString(7, "555-BANK");
        officeStmt.setInt(8, 1);
        officeStmt.executeUpdate();
    }
    
    public List<String[]> getAllCustomers() {
        List<String[]> customers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT first_name, last_name, email, phone, address FROM customers";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                customers.add(new String[]{
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    public boolean addCustomer(String firstName, String lastName, String email, String phone, String address) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO customers (first_name, last_name, email, phone, address, created_date) VALUES (?, ?, ?, ?, ?, datetime('now'))";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean transferMoney(String fromAccount, String toAccount, double amount, String description) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);
            
            // Update sender account
            String debitSql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            PreparedStatement debitStmt = conn.prepareStatement(debitSql);
            debitStmt.setDouble(1, amount);
            debitStmt.setString(2, fromAccount);
            
            // Update receiver account
            String creditSql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement creditStmt = conn.prepareStatement(creditSql);
            creditStmt.setDouble(1, amount);
            creditStmt.setString(2, toAccount);
            
            // Insert transaction record
            String transSql = "INSERT INTO transactions (transaction_number, sender_account, receiver_account, amount, transaction_type, description, transaction_date) VALUES (?, ?, ?, ?, ?, ?, datetime('now'))";
            PreparedStatement transStmt = conn.prepareStatement(transSql);
            transStmt.setString(1, "TXN" + System.currentTimeMillis());
            transStmt.setString(2, fromAccount);
            transStmt.setString(3, toAccount);
            transStmt.setDouble(4, amount);
            transStmt.setString(5, "TRANSFER");
            transStmt.setString(6, description);
            
            int debitResult = debitStmt.executeUpdate();
            int creditResult = creditStmt.executeUpdate();
            int transResult = transStmt.executeUpdate();
            
            if (debitResult > 0 && creditResult > 0 && transResult > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public double getAccountBalance(String accountNumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT balance FROM accounts WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    // Card Management Methods
    public boolean addCard(String cardNumber, int accountId, String cardType, String network, String holderName, String expiryDate, String cvv, double creditLimit) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO cards (card_number, account_id, card_type, network, holder_name, expiry_date, cvv, credit_limit, issue_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, datetime('now'))";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cardNumber);
            stmt.setInt(2, accountId);
            stmt.setString(3, cardType);
            stmt.setString(4, network);
            stmt.setString(5, holderName);
            stmt.setString(6, expiryDate);
            stmt.setString(7, cvv);
            stmt.setDouble(8, creditLimit);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<String[]> getAllCards() {
        List<String[]> cards = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT c.card_number, c.card_type, c.network, c.holder_name, c.expiry_date, c.status, a.account_number FROM cards c JOIN accounts a ON c.account_id = a.id WHERE c.status = 'ACTIVE'";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                cards.add(new String[]{
                    rs.getString("card_number"),
                    rs.getString("card_type"),
                    rs.getString("network"),
                    rs.getString("holder_name"),
                    rs.getString("expiry_date"),
                    rs.getString("account_number")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }
    
    // Loan Management Methods
    public boolean addLoan(String loanNumber, int customerId, String loanType, double principalAmount, double interestRate, int termMonths) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            double monthlyPayment = calculateMonthlyPayment(principalAmount, interestRate, termMonths);
            String sql = "INSERT INTO loans (loan_number, customer_id, loan_type, principal_amount, interest_rate, term_months, monthly_payment, outstanding_balance, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, datetime('now'), datetime('now', '+' || ? || ' months'))";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, loanNumber);
            stmt.setInt(2, customerId);
            stmt.setString(3, loanType);
            stmt.setDouble(4, principalAmount);
            stmt.setDouble(5, interestRate);
            stmt.setInt(6, termMonths);
            stmt.setDouble(7, monthlyPayment);
            stmt.setDouble(8, principalAmount);
            stmt.setInt(9, termMonths);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private double calculateMonthlyPayment(double principal, double annualRate, int months) {
        double monthlyRate = annualRate / 12;
        return principal * (monthlyRate * Math.pow(1 + monthlyRate, months)) / (Math.pow(1 + monthlyRate, months) - 1);
    }
    
    public List<String[]> getAllLoans() {
        List<String[]> loans = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT l.loan_number, l.loan_type, l.principal_amount, l.interest_rate, l.term_months, l.monthly_payment, l.outstanding_balance, l.status, c.first_name, c.last_name FROM loans l JOIN customers c ON l.customer_id = c.id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                loans.add(new String[]{
                    rs.getString("loan_number"),
                    rs.getString("loan_type"),
                    String.format("%.2f", rs.getDouble("principal_amount")),
                    String.format("%.2f%%", rs.getDouble("interest_rate") * 100),
                    rs.getString("term_months"),
                    String.format("%.2f", rs.getDouble("monthly_payment")),
                    String.format("%.2f", rs.getDouble("outstanding_balance")),
                    rs.getString("first_name") + " " + rs.getString("last_name")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }
    
    // Investment Management Methods
    public boolean addInvestment(String investmentId, int customerId, String investmentType, double amount, double interestRate) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO investments (investment_id, customer_id, investment_type, amount, current_value, purchase_date, interest_rate) VALUES (?, ?, ?, ?, ?, datetime('now'), ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, investmentId);
            stmt.setInt(2, customerId);
            stmt.setString(3, investmentType);
            stmt.setDouble(4, amount);
            stmt.setDouble(5, amount);
            stmt.setDouble(6, interestRate);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<String[]> getAllInvestments() {
        List<String[]> investments = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT i.investment_id, i.investment_type, i.amount, i.current_value, i.interest_rate, i.purchase_date, c.first_name, c.last_name FROM investments i JOIN customers c ON i.customer_id = c.id WHERE i.status = 'ACTIVE'";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                investments.add(new String[]{
                    rs.getString("investment_id"),
                    rs.getString("investment_type"),
                    String.format("%.2f", rs.getDouble("amount")),
                    String.format("%.2f", rs.getDouble("current_value")),
                    String.format("%.2f%%", rs.getDouble("interest_rate") * 100),
                    rs.getString("purchase_date"),
                    rs.getString("first_name") + " " + rs.getString("last_name")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return investments;
    }
    
    // Employee Management Methods
    public boolean addEmployee(String employeeId, String firstName, String lastName, String email, String role, double salary, String department) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO employees (employee_id, first_name, last_name, email, role, salary, department, hire_date) VALUES (?, ?, ?, ?, ?, ?, ?, datetime('now'))";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, employeeId);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, email);
            stmt.setString(5, role);
            stmt.setDouble(6, salary);
            stmt.setString(7, department);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<String[]> getAllEmployees() {
        List<String[]> employees = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT employee_id, first_name, last_name, email, role, salary, department, hire_date, status FROM employees";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                employees.add(new String[]{
                    rs.getString("employee_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("role"),
                    String.format("%.2f", rs.getDouble("salary")),
                    rs.getString("department"),
                    rs.getString("hire_date")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    // Transaction History Methods
    public List<String[]> getAllTransactions() {
        List<String[]> transactions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT transaction_number, sender_account, receiver_account, amount, transaction_type, description, transaction_date, status FROM transactions ORDER BY transaction_date DESC";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                transactions.add(new String[]{
                    rs.getString("transaction_number"),
                    rs.getString("sender_account"),
                    rs.getString("receiver_account"),
                    String.format("%.2f", rs.getDouble("amount")),
                    rs.getString("transaction_type"),
                    rs.getString("description"),
                    rs.getString("transaction_date"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    
    // Account Management Methods
    public List<String[]> getAllAccounts() {
        List<String[]> accounts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT a.account_number, a.account_type, a.balance, a.interest_rate, a.status, c.first_name, c.last_name FROM accounts a JOIN customers c ON a.customer_id = c.id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                accounts.add(new String[]{
                    rs.getString("account_number"),
                    rs.getString("account_type"),
                    String.format("%.2f", rs.getDouble("balance")),
                    String.format("%.2f%%", rs.getDouble("interest_rate") * 100),
                    rs.getString("status"),
                    rs.getString("first_name") + " " + rs.getString("last_name")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
    
    // Customer by account lookup
    public int getCustomerIdByAccount(String accountNumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT customer_id FROM accounts WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("customer_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    // ========== ENHANCED CRUD OPERATIONS ==========
    
    // Customer CRUD Operations
    public boolean updateCustomer(int customerId, String firstName, String lastName, String email, String phone, String address) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE customers SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.setInt(6, customerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteCustomer(int customerId) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // Check if customer has active accounts
            String checkSql = "SELECT COUNT(*) FROM accounts WHERE customer_id = ? AND status = 'ACTIVE'";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, customerId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Cannot delete customer with active accounts
            }
            
            String sql = "UPDATE customers SET status = 'DELETED' WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String[] getCustomerById(int customerId) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT id, first_name, last_name, email, phone, address, date_of_birth, ssn FROM customers WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new String[]{
                    String.valueOf(rs.getInt("id")),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("date_of_birth"),
                    rs.getString("ssn")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Account CRUD Operations
    public boolean addAccount(String accountNumber, int customerId, String accountType, double initialBalance, double interestRate, double minimumBalance) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO accounts (account_number, customer_id, account_type, balance, interest_rate, minimum_balance, created_date) VALUES (?, ?, ?, ?, ?, ?, datetime('now'))";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            stmt.setInt(2, customerId);
            stmt.setString(3, accountType);
            stmt.setDouble(4, initialBalance);
            stmt.setDouble(5, interestRate);
            stmt.setDouble(6, minimumBalance);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateAccount(String accountNumber, String accountType, double interestRate, double minimumBalance, String status) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE accounts SET account_type = ?, interest_rate = ?, minimum_balance = ?, status = ? WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountType);
            stmt.setDouble(2, interestRate);
            stmt.setDouble(3, minimumBalance);
            stmt.setString(4, status);
            stmt.setString(5, accountNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean closeAccount(String accountNumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // Check if account has zero balance
            String balanceCheck = "SELECT balance FROM accounts WHERE account_number = ?";
            PreparedStatement balanceStmt = conn.prepareStatement(balanceCheck);
            balanceStmt.setString(1, accountNumber);
            ResultSet rs = balanceStmt.executeQuery();
            if (rs.next() && Math.abs(rs.getDouble("balance")) > 0.01) {
                return false; // Cannot close account with balance
            }
            
            String sql = "UPDATE accounts SET status = 'CLOSED' WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String[] getAccountByNumber(String accountNumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT a.*, c.first_name, c.last_name FROM accounts a JOIN customers c ON a.customer_id = c.id WHERE a.account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new String[]{
                    rs.getString("account_number"),
                    rs.getString("account_type"),
                    String.format("%.2f", rs.getDouble("balance")),
                    String.format("%.4f", rs.getDouble("interest_rate")),
                    String.format("%.2f", rs.getDouble("minimum_balance")),
                    rs.getString("status"),
                    rs.getString("first_name") + " " + rs.getString("last_name"),
                    rs.getString("created_date")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Card CRUD Operations
    public boolean updateCard(String cardNumber, String status, double creditLimit) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE cards SET status = ?, credit_limit = ? WHERE card_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setDouble(2, creditLimit);
            stmt.setString(3, cardNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deactivateCard(String cardNumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE cards SET status = 'INACTIVE' WHERE card_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cardNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String[] getCardByNumber(String cardNumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT c.*, a.account_number FROM cards c JOIN accounts a ON c.account_id = a.id WHERE c.card_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cardNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new String[]{
                    rs.getString("card_number"),
                    rs.getString("card_type"),
                    rs.getString("network"),
                    rs.getString("holder_name"),
                    rs.getString("expiry_date"),
                    rs.getString("status"),
                    String.format("%.2f", rs.getDouble("credit_limit")),
                    rs.getString("account_number")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Loan CRUD Operations
    public boolean updateLoanStatus(String loanNumber, String status) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE loans SET status = ? WHERE loan_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setString(2, loanNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean makeLoanPayment(String loanNumber, double paymentAmount) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);
            
            // Get current loan info
            String getLoanSql = "SELECT outstanding_balance, interest_rate FROM loans WHERE loan_number = ?";
            PreparedStatement getLoanStmt = conn.prepareStatement(getLoanSql);
            getLoanStmt.setString(1, loanNumber);
            ResultSet rs = getLoanStmt.executeQuery();
            
            if (rs.next()) {
                double currentBalance = rs.getDouble("outstanding_balance");
                double interestRate = rs.getDouble("interest_rate");
                
                // Calculate interest and principal portions
                double interestPortion = currentBalance * (interestRate / 12);
                double principalPortion = paymentAmount - interestPortion;
                double newBalance = currentBalance - principalPortion;
                
                // Update loan balance
                String updateLoanSql = "UPDATE loans SET outstanding_balance = ? WHERE loan_number = ?";
                PreparedStatement updateLoanStmt = conn.prepareStatement(updateLoanSql);
                updateLoanStmt.setDouble(1, Math.max(0, newBalance));
                updateLoanStmt.setString(2, loanNumber);
                
                // Insert payment record
                String insertPaymentSql = "INSERT INTO loan_payments (loan_id, payment_date, amount, principal_amount, interest_amount, remaining_balance) SELECT id, datetime('now'), ?, ?, ?, ? FROM loans WHERE loan_number = ?";
                PreparedStatement insertPaymentStmt = conn.prepareStatement(insertPaymentSql);
                insertPaymentStmt.setDouble(1, paymentAmount);
                insertPaymentStmt.setDouble(2, principalPortion);
                insertPaymentStmt.setDouble(3, interestPortion);
                insertPaymentStmt.setDouble(4, Math.max(0, newBalance));
                insertPaymentStmt.setString(5, loanNumber);
                
                int loanUpdate = updateLoanStmt.executeUpdate();
                int paymentInsert = insertPaymentStmt.executeUpdate();
                
                if (loanUpdate > 0 && paymentInsert > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
            conn.rollback();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String[] getLoanByNumber(String loanNumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT l.*, c.first_name, c.last_name FROM loans l JOIN customers c ON l.customer_id = c.id WHERE l.loan_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, loanNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new String[]{
                    rs.getString("loan_number"),
                    rs.getString("loan_type"),
                    String.format("%.2f", rs.getDouble("principal_amount")),
                    String.format("%.4f", rs.getDouble("interest_rate")),
                    String.valueOf(rs.getInt("term_months")),
                    String.format("%.2f", rs.getDouble("monthly_payment")),
                    String.format("%.2f", rs.getDouble("outstanding_balance")),
                    rs.getString("status"),
                    rs.getString("first_name") + " " + rs.getString("last_name"),
                    rs.getString("start_date"),
                    rs.getString("end_date")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Investment CRUD Operations
    public boolean updateInvestment(String investmentId, double currentValue, String status) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE investments SET current_value = ?, status = ? WHERE investment_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, currentValue);
            stmt.setString(2, status);
            stmt.setString(3, investmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean liquidateInvestment(String investmentId) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE investments SET status = 'LIQUIDATED', maturity_date = datetime('now') WHERE investment_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, investmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String[] getInvestmentById(String investmentId) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT i.*, c.first_name, c.last_name FROM investments i JOIN customers c ON i.customer_id = c.id WHERE i.investment_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, investmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new String[]{
                    rs.getString("investment_id"),
                    rs.getString("investment_type"),
                    String.format("%.2f", rs.getDouble("amount")),
                    String.format("%.2f", rs.getDouble("current_value")),
                    String.format("%.4f", rs.getDouble("interest_rate")),
                    rs.getString("status"),
                    rs.getString("purchase_date"),
                    rs.getString("maturity_date"),
                    rs.getString("first_name") + " " + rs.getString("last_name")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Employee CRUD Operations
    public boolean updateEmployee(String employeeId, String firstName, String lastName, String email, String role, double salary, String department, String status) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, role = ?, salary = ?, department = ?, status = ? WHERE employee_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, role);
            stmt.setDouble(5, salary);
            stmt.setString(6, department);
            stmt.setString(7, status);
            stmt.setString(8, employeeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean terminateEmployee(String employeeId) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE employees SET status = 'TERMINATED' WHERE employee_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, employeeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String[] getEmployeeById(String employeeId) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM employees WHERE employee_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new String[]{
                    rs.getString("employee_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("role"),
                    String.format("%.2f", rs.getDouble("salary")),
                    rs.getString("department"),
                    rs.getString("hire_date"),
                    rs.getString("status")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Transaction Operations
    public boolean updateTransactionStatus(String transactionNumber, String status) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE transactions SET status = ? WHERE transaction_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setString(2, transactionNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<String[]> getTransactionsByAccount(String accountNumber) {
        List<String[]> transactions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM transactions WHERE sender_account = ? OR receiver_account = ? ORDER BY transaction_date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            stmt.setString(2, accountNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(new String[]{
                    rs.getString("transaction_number"),
                    rs.getString("sender_account"),
                    rs.getString("receiver_account"),
                    String.format("%.2f", rs.getDouble("amount")),
                    rs.getString("transaction_type"),
                    rs.getString("description"),
                    rs.getString("transaction_date"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    
    public List<String[]> getTransactionsByDateRange(String startDate, String endDate) {
        List<String[]> transactions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM transactions WHERE DATE(transaction_date) BETWEEN ? AND ? ORDER BY transaction_date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(new String[]{
                    rs.getString("transaction_number"),
                    rs.getString("sender_account"),
                    rs.getString("receiver_account"),
                    String.format("%.2f", rs.getDouble("amount")),
                    rs.getString("transaction_type"),
                    rs.getString("description"),
                    rs.getString("transaction_date"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    
    // Get next available numbers for IDs
    public String getNextAccountNumber() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT COUNT(*) + 1 as next_id FROM accounts";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                return String.format("ACC%03d", rs.getInt("next_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ACC001";
    }
    
    private static final java.security.SecureRandom RNG = new java.security.SecureRandom();

    public String getNextCardNumber() {
        // Generate a random 16-digit card number (simplified for demo)
        return String.format("4532-%04d-%04d-%04d",
            RNG.nextInt(10000),
            RNG.nextInt(10000),
            RNG.nextInt(10000));
    }
    
    // Get next available numbers for IDs
    public String getNextLoanNumber() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT COUNT(*) + 1 as next_id FROM loans";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                return String.format("LOAN%03d", rs.getInt("next_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "LOAN001";
    }
    
    public String getNextInvestmentId() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT COUNT(*) + 1 as next_id FROM investments";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                return String.format("INV%03d", rs.getInt("next_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "INV001";
    }
    
    public String getNextEmployeeId() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT COUNT(*) + 1 as next_id FROM employees";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                return String.format("EMP%03d", rs.getInt("next_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "EMP001";
    }
} 