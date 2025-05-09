package models;

public interface Login {
    public boolean authenticate(String username, String password);
}