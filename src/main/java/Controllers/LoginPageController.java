package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// Import your Database and User classes
import models.Admin;
import models.Attendee;
import models.Database;
import models.Organizer;
import models.User;
import utils.SessionManager;

public class LoginPageController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label wrongLogin;

    @FXML
    private void authenticate() {
        // Get user input
        String inputUsername = username.getText();
        String inputPassword = password.getText();

        // Basic validation
        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
            wrongLogin.setText("Please enter both username and password");
            return;
        }

        // Get the database instance
        Database db = Database.getInstance();

        // Find the user in the database
        User user = db.findUserByUsername(inputUsername);

        // Check if user exists and password is correct
        if (user == null || !user.authenticate(inputUsername, inputPassword)) {
            wrongLogin.setText("Invalid username or password");
            return;
        }

        // Login successful
        wrongLogin.setText("Login successful!");

        // Store the authenticated user in the session
        SessionManager.getInstance().setCurrentUser(user);

        try {
            // Close the login window
            Stage loginStage = (Stage) wrongLogin.getScene().getWindow();
            loginStage.close();

            // Open appropriate dashboard based on user type
            Stage dashboardStage = new Stage();
            Parent root = null;
            FXMLLoader loader = null;

            if (user instanceof Admin) {
                // Load Admin dashboard
                loader = new FXMLLoader(getClass().getResource("/fxml/AdminDashboard.fxml"));
                root = loader.load();
                dashboardStage.setTitle("Admin Dashboard");

                // No need to set any controller properties for Admin
            } else if (user instanceof Organizer) {
                // Load Organizer dashboard
                loader = new FXMLLoader(getClass().getResource("/fxml/OrganizerDashboard.fxml"));
                root = loader.load();
                dashboardStage.setTitle("Organizer Dashboard");

                // Set the current organizer in the controller
                OrganizerDashboardController controller = loader.getController();
                controller.setCurrentOrganizer((Organizer) user);
            } else if (user instanceof Attendee) {
                // Load Attendee dashboard
                loader = new FXMLLoader(getClass().getResource("/fxml/AttendeeDashboard.fxml"));
                root = loader.load();
                dashboardStage.setTitle("Attendee Dashboard");

                // Set the current attendee in the controller
                AttendeeDashboardController controller = loader.getController();
                controller.setCurrentAttendee((Attendee) user);
            }

            if (root != null) {
                Scene scene = new Scene(root);
                dashboardStage.setScene(scene);
                dashboardStage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            wrongLogin.setText("Error opening dashboard: " + e.getMessage());
        }
    }

    @FXML
    private void openRegistration() {
        try {
            // Get current stage
            Stage currentStage = (Stage) username.getScene().getWindow();

            // Load registration screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Registration-page.fxml"));
            Parent root = loader.load();

            // Create new scene
            Scene scene = new Scene(root);

            // Set scene to current stage
            currentStage.setScene(scene);
            currentStage.setTitle("Event Management System - Registration");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            wrongLogin.setText("Error opening registration page: " + e.getMessage());
        }
    }
}