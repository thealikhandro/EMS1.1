package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

// Import your models with proper package declarations
import models.Admin;
import models.Database;
import models.Event;
import models.Room;
import models.User;
import utils.SessionManager;

import com.MainApplication;


public class AdminDashboardController {
    @FXML
    private TextArea outputArea;

    private Database db = Database.getInstance();
    private Admin currentAdmin;

    public void initialize() {
        // Get the current user from session
        User user = SessionManager.getInstance().getCurrentUser();
        if (user instanceof Admin) {
            currentAdmin = (Admin) user;
        } else {
            // Handle error case
            System.err.println("Error: User in session is not an Admin");
        }
    }

    @FXML
    private void showUsers() {
        outputArea.clear();
        outputArea.appendText("All Users:\n");

        for (User user : db.getAllUsers()) {
            outputArea.appendText(user.getUsername() + "\n");
        }
    }

    @FXML
    private void showEvents() {
        outputArea.clear();
        outputArea.appendText("All Events:\n");

        for (Event event : db.getAllEvents()) {
            outputArea.appendText(event.getName() + " - " + event.getDescription() + "\n");
        }
    }

    @FXML
    private void showRooms() {
        outputArea.clear();
        outputArea.appendText("All Rooms:\n");

        for (Room room : db.getAllRooms()) {
            outputArea.appendText("Room " + room.getRoomNo() + " - Capacity: " + room.getCapacity() + "\n");
        }
    }

    @FXML
    private void logout() {
        try {
            // Clear the session
            SessionManager.getInstance().clearSession();

            // Close the admin dashboard
            Stage currentStage = (Stage) outputArea.getScene().getWindow();
            currentStage.close();

            // Open the login screen
            Stage loginStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login-page.fxml"));
            Scene scene = new Scene(root);
            loginStage.setTitle("Event Management System - Login");
            loginStage.setScene(scene);
            loginStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}