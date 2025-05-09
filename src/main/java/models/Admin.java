package models;

import java.util.Date;

import com.MainApplication;

public class Admin extends User {
    private String role;
    private String workingHours;

    public Admin(String username, String password, String date, String role, String workingHours) {
        super(username, password, date);
        this.role = role;
        this.workingHours = workingHours;
    }

    public Admin() {
        // Default constructor
    }

    public void addRoom(int roomNo, int capacity, Date date) {
        Room room = new Room(roomNo, capacity, date);
        Database.getInstance().addRoom(room);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public void showAll() {
        System.out.println("All Users:");
        for (User user : Database.getInstance().getAllUsers()) {
            System.out.println(user);
        }

        System.out.println("\nAll Events:");
        for (Event event : Database.getInstance().getAllEvents()) {
            System.out.println(event);
        }

        System.out.println("\nAll Rooms:");
        for (Room room : Database.getInstance().getAllRooms()) {
            System.out.println(room);
        }

        System.out.println("\nAll Categories:");
        for (Category category : Database.getInstance().getAllCategories()) {
            System.out.println(category.getName());
        }
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", workingHours='" + workingHours + '\'' +
                '}';
    }
}