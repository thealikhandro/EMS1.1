package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

// Import your classes
import models.Attendee;
import models.Database;
import models.Event;
import models.User;
import utils.SessionManager;

import java.util.List;


import com.MainApplication;

public class AttendeeDashboardController {
    @FXML
    private TextArea outputArea;
    
    private Database db = Database.getInstance();
    private Attendee currentAttendee;
    
    public void initialize() {
        // Get the current user from session
        User user = SessionManager.getInstance().getCurrentUser();
        if (user instanceof Attendee) {
            currentAttendee = (Attendee) user;
        } else {
            // Handle error case
            System.err.println("Error: User in session is not an Attendee");
        }
    }
    
    public void setCurrentAttendee(Attendee attendee) {
        this.currentAttendee = attendee;
    }
    
    @FXML
    private void browseEvents() {
        outputArea.clear();
        
        List<Event> events = db.getAllEvents();
        
        if (events.isEmpty()) {
            outputArea.setText("No events available.");
            return;
        }
        
        outputArea.appendText("Available Events:\n");
        int index = 1;
        for (Event event : events) {
            outputArea.appendText(index + ". " + event.getName() + 
                                 " - " + event.getDescription() + 
                                 " - Price: $" + event.getTicketPrice() + "\n");
            index++;
        }
        
        outputArea.appendText("\nTo buy a ticket, select an event and click 'Buy Ticket'.");
    }
    
    @FXML
    private void viewMyTickets() {
        outputArea.clear();
        
        if (currentAttendee == null) {
            outputArea.setText("Error: Current attendee not set!");
            return;
        }
        
        List<Event> myEvents = currentAttendee.getEventsJoined();
        
        if (myEvents.isEmpty()) {
            outputArea.setText("You haven't purchased any tickets yet.");
            return;
        }
        
        outputArea.appendText("Your Tickets:\n");
        for (Event event : myEvents) {
            outputArea.appendText(event.getName() + " - " + event.getDescription() + 
                                 " - Date: " + event.getDate() + "\n");
        }
    }
    
    @FXML
    private void checkWallet() {
        if (currentAttendee == null) {
            outputArea.setText("Error: Current attendee not set!");
            return;
        }
        
        double balance = currentAttendee.getWallet().getBalance();
        outputArea.setText("Your current wallet balance: $" + balance);
    }
    
    @FXML
    private void logout() {
        try {
            // Clear the session
            SessionManager.getInstance().clearSession();
            
            // Close the attendee dashboard
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