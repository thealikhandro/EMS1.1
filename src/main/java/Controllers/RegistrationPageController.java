package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import models.*;
import utils.SessionManager;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistrationPageController {

    @FXML
    private ComboBox<String> userTypeComboBox;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField initialBalanceField;

    @FXML
    private TextField roleField;

    @FXML
    private TextField workingHoursField;

    @FXML
    private HBox attendeeFieldsBox;

    @FXML
    private HBox attendeeWalletBox;

    @FXML
    private HBox adminFieldsBox;

    @FXML
    private HBox adminHoursBox;

    @FXML
    private Label messageLabel;

    // Observable list for user types
    private ObservableList<String> userTypes = FXCollections.observableArrayList(
            "Attendee", "Organizer", "Admin"
    );

    // Observable list for genders
    private ObservableList<String> genders = FXCollections.observableArrayList(
            "Male", "Female"
    );

    // Database reference
    private Database db;

    @FXML
    public void initialize() {
        // Get database instance
        db = Database.getInstance();

        // Set up user type combo box
        userTypeComboBox.setItems(userTypes);
        userTypeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Hide all specific fields first
            attendeeFieldsBox.setVisible(false);
            attendeeWalletBox.setVisible(false);
            adminFieldsBox.setVisible(false);
            adminHoursBox.setVisible(false);

            // Show specific fields based on user type
            if (newValue.equals("Attendee")) {
                attendeeFieldsBox.setVisible(true);
                attendeeWalletBox.setVisible(true);
            } else if (newValue.equals("Admin")) {
                adminFieldsBox.setVisible(true);
                adminHoursBox.setVisible(true);
            }
        });

        // Set up gender combo box
        genderComboBox.setItems(genders);
    }

    @FXML
    private void registerUser() {
        // Clear previous messages
        messageLabel.setText("");

        // Get user input
        String userType = userTypeComboBox.getValue();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate inputs
        if (userType == null || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            messageLabel.setText("Please fill in all required fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match");
            return;
        }

        // Check if username already exists
        if (db.findUserByUsername(username) != null) {
            messageLabel.setText("Username already exists");
            return;
        }

        // Current date string in format DD/MM/YYYY
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Create user based on type
        User newUser = null;

        try {
            switch (userType) {
                case "Attendee":
                    // Validate attendee fields
                    String genderStr = genderComboBox.getValue();
                    String balanceStr = initialBalanceField.getText();

                    if (genderStr == null || balanceStr.isEmpty()) {
                        messageLabel.setText("Please fill in all attendee fields");
                        return;
                    }

                    // Parse balance
                    double balance;
                    try {
                        balance = Double.parseDouble(balanceStr);
                        if (balance < 0) {
                            messageLabel.setText("Balance cannot be negative");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        messageLabel.setText("Please enter a valid balance");
                        return;
                    }

                    // Create wallet and user
                    Wallet wallet = new Wallet(balance);
                    gender gender = genderStr.equals("Male") ? models.gender.Male : models.gender.Female;
                    newUser = new Attendee(username, password, currentDate, gender, wallet);
                    break;

                case "Organizer":
                    newUser = new Organizer(username, password, currentDate);
                    break;

                case "Admin":
                    // Validate admin fields
                    String role = roleField.getText();
                    String workingHours = workingHoursField.getText();

                    if (role.isEmpty() || workingHours.isEmpty()) {
                        messageLabel.setText("Please fill in all admin fields");
                        return;
                    }

                    newUser = new Admin(username, password, currentDate, role, workingHours);
                    break;
            }

            // User is already added to database in the constructor
            messageLabel.setText("Registration successful!");
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);

            // Clear fields after successful registration
            clearFields();

            // Automatically return to login after a brief delay
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        javafx.application.Platform.runLater(() -> {
                            try {
                                cancelRegistration();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                },
                2000
            );

        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        userTypeComboBox.getSelectionModel().clearSelection();
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        genderComboBox.getSelectionModel().clearSelection();
        initialBalanceField.clear();
        roleField.clear();
        workingHoursField.clear();
    }

    @FXML
    private void cancelRegistration() {
        try {
            // Get current stage
            Stage currentStage = (Stage) userTypeComboBox.getScene().getWindow();
            
            // Load login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login-page.fxml"));
            Parent root = loader.load();
            
            // Create new scene
            Scene scene = new Scene(root);
            
            // Set scene to current stage
            currentStage.setScene(scene);
            currentStage.setTitle("Event Management System - Login");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error returning to login screen");
        }
    }
}