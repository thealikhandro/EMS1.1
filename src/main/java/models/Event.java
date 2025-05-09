package models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.MainApplication;

public class Event {
    private String name;
    private String description;
    private Date date;
    private double ticketPrice;
    private Room room;
    private Organizer organizer;
    private Category category;
    private List<Attendee> attendees;

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Event(Category category, Date date, String description, String name, Organizer organizer, Room room, double ticketPrice) {
        this.category = category;
        this.date = date;
        this.description = description;
        this.name = name;
        this.organizer = organizer;
        this.room = room;
        this.ticketPrice = ticketPrice;
        this.attendees = new ArrayList<>();

        // Add event to database
        Database.getInstance().addEvent(this);
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public Category getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public Room getRoom() {
        return room;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void registerAttendee(Attendee attendee) {
        attendees.add(attendee);
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", ticketPrice=" + ticketPrice +
                ", room=" + room +
                ", category=" + category +
                '}';
    }
}