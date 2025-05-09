package utils;

import models.User;

/**
 * A singleton class to maintain session information across different screens
 */
public class SessionManager {
    private static SessionManager instance;
    
    private User currentUser;
    
    // Private constructor for singleton pattern
    private SessionManager() {
    }
    
    // Get singleton instance
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    // Set the current user
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    // Get the current user
    public User getCurrentUser() {
        return currentUser;
    }
    
    // Clear the session
    public void clearSession() {
        currentUser = null;
    }
}