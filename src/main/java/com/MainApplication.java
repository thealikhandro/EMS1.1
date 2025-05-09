package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Import models with proper package declarations
import models.Admin;
import models.Attendee;
import models.Database;
import models.Organizer;
import models.gender;
import models.Wallet;
import models.Room;
import models.Category;
import models.Event;

import java.util.Date;
import java.net.URL;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize database with test data
        initializeTestData();

        // Load the login screen - FIXED PATH
        URL fxmlLocation = getClass().getResource("/fxml/Login-page.fxml");

        if (fxmlLocation == null) {
            // Debug: Print resources directory structure if location is null
            System.err.println("FXML file not found at /fxml/Login-page.fxml");
            System.err.println("Checking alternative locations...");

            // Try some alternative locations
            String[] possibleLocations = {
                    "/Login-page.fxml",
                    "/Login.fxml",
                    "/fxml/Login.fxml",
                    "/com/fxml/Login-page.fxml"
            };

            for (String location : possibleLocations) {
                URL alternativeLocation = getClass().getResource(location);
                if (alternativeLocation != null) {
                    System.out.println("Found FXML at: " + location);
                    fxmlLocation = alternativeLocation;
                    break;
                }
            }

            if (fxmlLocation == null) {
                throw new RuntimeException("Cannot find FXML file. Make sure it's in the correct resources directory.");
            }
        }

        // Load FXML file with explicitly checked location
        Parent root = FXMLLoader.load(fxmlLocation);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Event Management System - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeTestData() {
        Database db = Database.getInstance();

        // Clear any existing data
        db.reset();

        // Add test users
        Admin admin = new Admin("admin", "admin123", "01/01/2023", "System Administrator", "9am-5pm");
        Organizer organizer = new Organizer("org1", "org123", "01/02/2023");
        Attendee attendee1 = new Attendee("user1", "user123", "01/03/2023", gender.Male, new Wallet(1000));
        Attendee attendee2 = new Attendee("user2", "user123", "01/04/2023", gender.Female, new Wallet(2000));

        // Add test categories
        Category music = new Category("Music");
        Category tech = new Category("Technology");
        Category sports = new Category("Sports");

        db.addCategory(music);
        db.addCategory(tech);
        db.addCategory(sports);

        // Add test rooms
        Room room1 = new Room(101, 50, new Date());
        Room room2 = new Room(102, 100, new Date());

        db.addRoom(room1);
        db.addRoom(room2);

        // Create some sample events
        Date eventDate = new Date(); // Current date
        Event musicEvent = organizer.createEvent(
                "Summer Concert",
                "Annual summer concert featuring local artists",
                eventDate,
                75.0,
                room1,
                music
        );

        Event techEvent = organizer.createEvent(
                "Tech Conference",
                "Conference showcasing the latest technology trends",
                eventDate,
                120.0,
                room2,
                tech
        );

        // Have an attendee buy a ticket
        attendee1.buyTicket(musicEvent);

        System.out.println("Test data initialized");
    }

    public static void main(String[] args) {
        launch(args);
    }
}