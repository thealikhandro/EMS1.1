package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.MainApplication;

public class Database {
    // Collection to store all system entities
    private static List<User> users = new ArrayList<>();
    private static List<Event> events = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();
    private static List<Category> categories = new ArrayList<>();

    // Singleton instance
    private static Database instance;

    // Private constructor for singleton pattern
    private Database() {
        // Initialize collections if needed
    }

    // Get singleton instance
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // User management methods
    public void addUser(User user) {
        users.add(user);
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean updateUser(String username, String newPassword) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setPassword(newPassword);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                // Remove user from events they're attending
                for (Event event : events) {
                    List<Attendee> attendees = event.getAttendees();
                    for (int j = 0; j < attendees.size(); j++) {
                        if (attendees.get(j).getUsername().equals(username)) {
                            attendees.remove(j);
                            break;
                        }
                    }
                }
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // Added for Main.java compatibility
    public User findUserByUsername(String username) {
        return getUserByUsername(username);
    }

    // Event management methods
    public void addEvent(Event event) {
        events.add(event);
    }

    public Event getEventByName(String name) {
        for (Event event : events) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    public boolean updateEvent(String oldName, String newName, String newDescription) {
        for (Event event : events) {
            if (event.getName().equals(oldName)) {
                event.setName(newName);
                event.setDescription(newDescription);
                return true;
            }
        }
        return false;
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(events);
    }

    // Room management methods
    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room getRoomByNumber(int roomNo) {
        for (Room room : rooms) {
            if (room.getRoomNo() == roomNo) {
                return room;
            }
        }
        return null;
    }

    public boolean updateRoom(int roomNo, int newCapacity) {
        for (Room room : rooms) {
            if (room.getRoomNo() == roomNo) {
                room.setCapacity(newCapacity);
                return true;
            }
        }
        return false;
    }

    public boolean deleteRoom(int roomNo) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomNo() == roomNo) {
                // Check if any events use this room
                boolean canDelete = true;
                for (Event event : events) {
                    if (event.getRoom().getRoomNo() == roomNo) {
                        canDelete = false;
                        break;
                    }
                }
                if (canDelete) {
                    rooms.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    // Category management methods
    public void addCategory(Category category) {
        categories.add(category);
    }

    public Category getCategoryByName(String name) {
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    public boolean updateCategory(String oldName, String newName) {
        for (Category category : categories) {
            if (category.getName().equals(oldName)) {
                category.setName(newName);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String name) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getName().equals(name)) {
                // Check if any events use this category
                boolean canDelete = true;
                for (Event event : events) {
                    if (event.getCategory().getName().equals(name)) {
                        canDelete = false;
                        break;
                    }
                }
                if (canDelete) {
                    categories.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Event> getEventsByCategory(Category category) {
        List<Event> result = new ArrayList<>();
        for (Event event : events) {
            if (event.getCategory().getName().equals(category.getName())) {
                result.add(event);
            }
        }
        return result;
    }

    public List<Event> getEventsByOrganizer(Organizer organizer) {
        List<Event> result = new ArrayList<>();
        for (Event event : events) {
            if (event.getOrganizer() != null &&
                    event.getOrganizer().getUsername().equals(organizer.getUsername())) {
                result.add(event);
            }
        }
        return result;
    }

    public List<Attendee> getAttendeesByEvent(Event event) {
        return event.getAttendees();
    }

    // Find available rooms for a given date
    public List<Room> getAvailableRooms(Date date) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.checkRoom(room.getRoomNo(), date)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // Reset database (for testing)
    public void reset() {
        users.clear();
        events.clear();
        rooms.clear();
        categories.clear();
    }
}