package models;

import java.util.Date;
import java.util.List;

import com.MainApplication;

public class Organizer extends User {

    public Organizer(String username, String password, String date) {
        super(username, password, date);
    }

    public Event createEvent(String name, String description, Date date, double ticketPrice, Room room, Category category) {
        if (room.checkRoom(room.getRoomNo(), date)) {
            Event newEvent = new Event(category, date, description, name, this, room, ticketPrice);
            return newEvent;
        } else {
            return null;
        }
    }

    public void displayAttendeesForEvent(Event someEvent) {
        List<Attendee> attendees = someEvent.getAttendees();
        System.out.println("Attendees for event: " + someEvent.getName());
        for (Attendee person : attendees) {
            System.out.println("- " + person.getUsername());
        }
    }
    
    @Override
    public String toString() {
        return "Organizer{" +
                "username='" + username + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}