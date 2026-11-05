package com.bank.service;

import com.bank.database.DatabaseManager;
import java.util.List;

public class BankingService {
    private DatabaseManager dbManager;
    
    public BankingService() {
        this.dbManager = DatabaseManager.getInstance();
    }
    
    public boolean createCustomer(String firstName, String lastName, String email, String phone, String address) {
        if (firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            return false;
        }
        
        return dbManager.addCustomer(firstName, lastName, email, phone, address);
    }
    
    public boolean transferMoney(String fromAccount, String toAccount, double amount, String description) {
        if (fromAccount == null || toAccount == null || amount <= 0 || fromAccount.equals(toAccount)) {
            return false;
        }

        // Check if sender has sufficient balance
        double senderBalance = dbManager.getAccountBalance(fromAccount);
        if (senderBalance < amount) {
            return false;
        }
        
        return dbManager.transferMoney(fromAccount, toAccount, amount, description);
    }
    
    public double getAccountBalance(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return 0.0;
        }
        return dbManager.getAccountBalance(accountNumber);
    }
    
    public List<String[]> getAllCustomers() {
        return dbManager.getAllCustomers();
    }
    
    public boolean validateAccountNumber(String accountNumber) {
        return accountNumber != null && accountNumber.matches("ACC\\d{3}");
    }
    
    public boolean validateEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    public boolean validateAmount(String amountStr) {
        try {
            double amount = Double.parseDouble(amountStr);
            return amount > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
} 