package com.bank.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import com.bank.service.BankingService;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    
    @FXML private TabPane tabPane;
    @FXML private TextField balanceAccountField;
    @FXML private Label balanceLabel;
    @FXML private TextField fromAccountField;
    @FXML private TextField toAccountField;
    @FXML private TextField amountField;
    @FXML private TextField descriptionField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextArea addressArea;
    
    private BankingService bankingService;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bankingService = new BankingService();
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleCheckBalance() {
        String accountNumber = balanceAccountField.getText().trim();
        
        if (accountNumber.isEmpty()) {
            showAlert("Error", "Please enter an account number.", Alert.AlertType.ERROR);
            return;
        }
        
        if (!bankingService.validateAccountNumber(accountNumber)) {
            showAlert("Error", "Invalid account number format. Use format: ACC001", Alert.AlertType.ERROR);
            return;
        }
        
        double balance = bankingService.getAccountBalance(accountNumber);
        balanceLabel.setText(String.format("Balance: $%.2f", balance));
    }

    @FXML
    private void handleTransfer() {
        String fromAccount = fromAccountField.getText().trim();
        String toAccount = toAccountField.getText().trim();
        String amountStr = amountField.getText().trim();
        String description = descriptionField.getText().trim();
        
        if (fromAccount.isEmpty() || toAccount.isEmpty() || amountStr.isEmpty()) {
            showAlert("Error", "Please fill in all required fields.", Alert.AlertType.ERROR);
            return;
        }
        
        if (!bankingService.validateAccountNumber(fromAccount) || !bankingService.validateAccountNumber(toAccount)) {
            showAlert("Error", "Invalid account number format. Use format: ACC001", Alert.AlertType.ERROR);
            return;
        }
        
        if (!bankingService.validateAmount(amountStr)) {
            showAlert("Error", "Please enter a valid positive amount.", Alert.AlertType.ERROR);
            return;
        }
        
        double amount = Double.parseDouble(amountStr);
        
        if (bankingService.transferMoney(fromAccount, toAccount, amount, description)) {
            showAlert("Success", "Transfer completed successfully!", Alert.AlertType.INFORMATION);
            clearTransferFields();
        } else {
            showAlert("Error", "Transfer failed. Please check account numbers and balance.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAddCustomer() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressArea.getText().trim();
        
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            showAlert("Error", "Please fill in all required fields (First Name, Last Name, Email).", Alert.AlertType.ERROR);
            return;
        }
        
        if (!bankingService.validateEmail(email)) {
            showAlert("Error", "Please enter a valid email address.", Alert.AlertType.ERROR);
            return;
        }
        
        if (bankingService.createCustomer(firstName, lastName, email, phone, address)) {
            showAlert("Success", "Customer created successfully!", Alert.AlertType.INFORMATION);
            clearCustomerFields();
        } else {
            showAlert("Error", "Failed to create customer. Email might already exist.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleRefresh() {
        // Clear all fields and refresh data
        clearAllFields();
        balanceLabel.setText("Balance: $0.00");
    }

    private void clearTransferFields() {
        fromAccountField.clear();
        toAccountField.clear();
        amountField.clear();
        descriptionField.clear();
    }

    private void clearCustomerFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
        addressArea.clear();
    }

    private void clearAllFields() {
        balanceAccountField.clear();
        clearTransferFields();
        clearCustomerFields();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Programmatic UI creation as fallback
    public void createProgrammaticUI(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        
        // Title
        Label titleLabel = new Label("Banking Management System");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create tabs programmatically
        TabPane tabPane = new TabPane();
        
        // Balance Check Tab
        Tab balanceTab = createBalanceTab();
        
        // Transfer Tab
        Tab transferTab = createTransferTab();
        
        // Customer Tab
        Tab customerTab = createCustomerTab();
        
        tabPane.getTabs().addAll(balanceTab, transferTab, customerTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        root.getChildren().addAll(titleLabel, tabPane);
        
        Scene scene = new Scene(root, 1200, 800);
        
        // Add CSS styling
        scene.getRoot().setStyle("-fx-background-color: #ecf0f1;");
        
        primaryStage.setTitle("Banking Management System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);
        primaryStage.show();
    }
    
    private Tab createBalanceTab() {
        Tab tab = new Tab("Check Balance");
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);
        
        Label titleLabel = new Label("Account Balance Inquiry");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        
        balanceAccountField = new TextField();
        balanceAccountField.setPromptText("Enter account number (e.g., ACC001)");
        balanceAccountField.setPrefWidth(200);
        
        Button checkButton = new Button("Check Balance");
        checkButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px;");
        checkButton.setOnAction(e -> handleCheckBalance());
        
        inputBox.getChildren().addAll(new Label("Account Number:"), balanceAccountField, checkButton);
        
        balanceLabel = new Label("Balance: $0.00");
        balanceLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
        
        content.getChildren().addAll(titleLabel, inputBox, balanceLabel);
        tab.setContent(content);
        return tab;
    }
    
    private Tab createTransferTab() {
        Tab tab = new Tab("Transfer Money");
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        Label titleLabel = new Label("Money Transfer");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        fromAccountField = new TextField();
        fromAccountField.setPromptText("From account (e.g., ACC001)");
        
        toAccountField = new TextField();
        toAccountField.setPromptText("To account (e.g., ACC002)");
        
        amountField = new TextField();
        amountField.setPromptText("Amount");
        
        descriptionField = new TextField();
        descriptionField.setPromptText("Description (optional)");
        
        Button transferButton = new Button("Transfer Money");
        transferButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14px;");
        transferButton.setOnAction(e -> handleTransfer());
        
        grid.add(new Label("From Account:"), 0, 0);
        grid.add(fromAccountField, 1, 0);
        grid.add(new Label("To Account:"), 0, 1);
        grid.add(toAccountField, 1, 1);
        grid.add(new Label("Amount:"), 0, 2);
        grid.add(amountField, 1, 2);
        grid.add(new Label("Description:"), 0, 3);
        grid.add(descriptionField, 1, 3);
        grid.add(transferButton, 1, 4);
        
        content.getChildren().addAll(titleLabel, grid);
        tab.setContent(content);
        return tab;
    }
    
    private Tab createCustomerTab() {
        Tab tab = new Tab("Add Customer");
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        Label titleLabel = new Label("Add New Customer");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        
        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        
        emailField = new TextField();
        emailField.setPromptText("Email");
        
        phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        
        addressArea = new TextArea();
        addressArea.setPromptText("Address");
        addressArea.setPrefRowCount(3);
        
        Button addButton = new Button("Add Customer");
        addButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 14px;");
        addButton.setOnAction(e -> handleAddCustomer());
        
        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Phone:"), 0, 3);
        grid.add(phoneField, 1, 3);
        grid.add(new Label("Address:"), 0, 4);
        grid.add(addressArea, 1, 4);
        grid.add(addButton, 1, 5);
        
        content.getChildren().addAll(titleLabel, grid);
        tab.setContent(content);
        return tab;
    }
} 