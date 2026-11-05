package com.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import com.bank.service.BankingService;
import com.bank.database.DatabaseManager;

public class BankingSystemTest {
    
    private BankingService bankingService;
    private DatabaseManager dbManager;
    
    @BeforeEach
    void setUp() {
        bankingService = new BankingService();
        dbManager = DatabaseManager.getInstance();
    }
    
    @Test
    @DisplayName("Test Customer Creation")
    void testCreateCustomer() {
        String testEmail = "test" + System.currentTimeMillis() + "@test.com";
        boolean result = bankingService.createCustomer("Test", "User", testEmail, "555-0000", "Test Address");
        assertTrue(result, "Customer creation should succeed");
    }
    
    @Test
    @DisplayName("Test Email Validation")
    void testEmailValidation() {
        assertTrue(bankingService.validateEmail("valid@email.com"), "Valid email should pass validation");
        assertFalse(bankingService.validateEmail("invalid-email"), "Invalid email should fail validation");
        assertFalse(bankingService.validateEmail(""), "Empty email should fail validation");
        assertFalse(bankingService.validateEmail(null), "Null email should fail validation");
    }
    
    @Test
    @DisplayName("Test Account Number Validation")
    void testAccountNumberValidation() {
        assertTrue(bankingService.validateAccountNumber("ACC001"), "Valid account number should pass");
        assertTrue(bankingService.validateAccountNumber("ACC999"), "Valid account number should pass");
        assertFalse(bankingService.validateAccountNumber("INVALID"), "Invalid format should fail");
        assertFalse(bankingService.validateAccountNumber(""), "Empty string should fail");
        assertFalse(bankingService.validateAccountNumber(null), "Null should fail");
    }
    
    @Test
    @DisplayName("Test Amount Validation")
    void testAmountValidation() {
        assertTrue(bankingService.validateAmount("100.50"), "Valid positive amount should pass");
        assertTrue(bankingService.validateAmount("1"), "Valid integer amount should pass");
        assertFalse(bankingService.validateAmount("-10"), "Negative amount should fail");
        assertFalse(bankingService.validateAmount("0"), "Zero amount should fail");
        assertFalse(bankingService.validateAmount("invalid"), "Non-numeric should fail");
        assertFalse(bankingService.validateAmount(""), "Empty string should fail");
    }
    
    @Test
    @DisplayName("Test Account Balance Retrieval")
    void testGetAccountBalance() {
        double balance = bankingService.getAccountBalance("ACC001");
        assertTrue(balance >= 0, "Balance should be non-negative");
    }
    
    @Test
    @DisplayName("Test Money Transfer Validation")
    void testTransferValidation() {
        // Test with invalid accounts
        boolean result = bankingService.transferMoney("INVALID", "ACC002", 100.0, "Test");
        assertFalse(result, "Transfer with invalid sender should fail");
        
        // Test with same account
        result = bankingService.transferMoney("ACC001", "ACC001", 100.0, "Test");
        assertFalse(result, "Transfer to same account should fail");
        
        // Test with zero amount
        result = bankingService.transferMoney("ACC001", "ACC002", 0.0, "Test");
        assertFalse(result, "Transfer with zero amount should fail");
    }
    
    @Test
    @DisplayName("Test Database Connection")
    void testDatabaseConnection() {
        assertNotNull(dbManager, "Database manager should be initialized");
        
        // Test that we can retrieve data
        assertDoesNotThrow(() -> {
            dbManager.getAllCustomers();
            dbManager.getAllAccounts();
            dbManager.getAllTransactions();
        }, "Database operations should not throw exceptions");
    }
    
    @Test
    @DisplayName("Test Card Management")
    void testCardManagement() {
        assertDoesNotThrow(() -> {
            dbManager.getAllCards();
        }, "Card retrieval should not throw exceptions");
    }
    
    @Test
    @DisplayName("Test Loan Management")
    void testLoanManagement() {
        assertDoesNotThrow(() -> {
            dbManager.getAllLoans();
            String nextLoanNumber = dbManager.getNextLoanNumber();
            assertNotNull(nextLoanNumber, "Next loan number should not be null");
            assertTrue(nextLoanNumber.startsWith("LOAN"), "Loan number should start with LOAN");
        }, "Loan operations should not throw exceptions");
    }
    
    @Test
    @DisplayName("Test Investment Management")
    void testInvestmentManagement() {
        assertDoesNotThrow(() -> {
            dbManager.getAllInvestments();
            String nextInvestmentId = dbManager.getNextInvestmentId();
            assertNotNull(nextInvestmentId, "Next investment ID should not be null");
            assertTrue(nextInvestmentId.startsWith("INV"), "Investment ID should start with INV");
        }, "Investment operations should not throw exceptions");
    }
    
    @Test
    @DisplayName("Test Employee Management")
    void testEmployeeManagement() {
        assertDoesNotThrow(() -> {
            dbManager.getAllEmployees();
            String nextEmployeeId = dbManager.getNextEmployeeId();
            assertNotNull(nextEmployeeId, "Next employee ID should not be null");
            assertTrue(nextEmployeeId.startsWith("EMP"), "Employee ID should start with EMP");
        }, "Employee operations should not throw exceptions");
    }
} 