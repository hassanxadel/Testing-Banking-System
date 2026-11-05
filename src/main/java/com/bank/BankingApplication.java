package com.bank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import com.bank.gui.MainController;
import com.bank.gui.ComprehensiveMainController;

public class BankingApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Starting Comprehensive Banking Management System...");
        // Use the comprehensive GUI as the primary interface
        createComprehensiveUI(primaryStage);
    }
    
    private void createComprehensiveUI(Stage primaryStage) {
        try {
            ComprehensiveMainController controller = new ComprehensiveMainController();
            controller.createComprehensiveUI(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            // Final fallback to original controller
            createFallbackUI(primaryStage);
        }
    }
    
    private void createFallbackUI(Stage primaryStage) {
        try {
            MainController controller = new MainController();
            controller.createProgrammaticUI(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("All UI creation methods failed!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
} 