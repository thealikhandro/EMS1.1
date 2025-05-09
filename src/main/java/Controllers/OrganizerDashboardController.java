package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

// Import your classes
import models.Database;
import models.Event;
import models.Organizer;
import models.Room;
import models.Category;
import models.User;
import utils.SessionManager;

import java.util.Date;
import java.util.List;


import com.MainApplication;

public class OrganizerDashboardController {
    @FXML
    private TextArea outputArea;
    
    private Database db = Database.getInstance();
    private Organizer currentOrganizer;
    
    public void initialize() {
        // Get the current user from session
        User user = SessionManager.getInstance().getCurrentUser();
        if (user instanceof Organizer) {
            currentOrganizer = (Organizer) user;
        } else {
            // Handle error case
            System.err.println("Error: User in session is not an Organizer");
        }
    }
    
    public void setCurrentOrganizer(Organizer organizer) {
        this.currentOrganizer = organizer;
    }
    
    @FXML
    private void createEvent() {
        // In a real application, you would open a dialog to collect event details
        // For now, let's create a sample event with hard-coded values
        
        if (currentOrganizer == null) {
            outputArea.setText("Error: Current organizer not set!");
            return;
        }
        
        // Get a room and category
        List<Room> rooms = db.getAllRooms();
        List<Category> categories = db.getAllCategories();
        
        if (rooms.isEmpty() || categories.isEmpty()) {
            outputArea.setText("Cannot create event: No rooms or categories available");
            return;
        }
        
        Room room = rooms.get(0);
        Category category = categories.get(0);
        
        // Create a new event
        Date eventDate = new Date(); // Current date
        Event newEvent = currentOrganizer.createEvent(
            "New Event", 
            "This is a test event created from the Organizer Dashboard", 
            eventDate, 
            50.0, // Ticket price
            room,
            category
        );
        
        if (newEvent != null) {
            outputArea.setText("Event created successfully:\n" + newEvent);
        } else {
            outputArea.setText("Failed to create event. Room might be unavailable.");
        }
    }
    
    @FXML
    private void viewMyEvents() {
        outputArea.clear();
        
        if (currentOrganizer == null) {
            outputArea.setText("Error: Current organizer not set!");
            return;
        }
        
        List<Event> myEvents = db.getEventsByOrganizer(currentOrganizer);
        
        if (myEvents.isEmpty()) {
            outputArea.setText("You haven't created any events yet.");
            return;
        }
        
        outputArea.appendText("Your Events:\n");
        for (Event event : myEvents) {
            outputArea.appendText(event.getName() + " - " + event.getDescription() + "\n");
        }
    }
    
    @FXML
    private void logout() {
        try {
            // Clear the session
            SessionManager.getInstance().clearSession();
            
            // Close the organizer dashboard
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