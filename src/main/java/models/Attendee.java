package models;

import java.util.List;
import java.util.ArrayList;

import com.MainApplication;

public class Attendee extends User {
    private Wallet wallet;
    private gender Gender;
    private List<Event> eventsJoined;

    public Attendee(String username, String password, String date, gender gender, Wallet wallet) {
        super(username, password, date);
        this.Gender = gender;
        this.wallet = wallet;
        this.eventsJoined = new ArrayList<>();
    }

    public List<Event> getEventsJoined() {
        return eventsJoined;
    }

    public boolean buyTicket(Event event) {
        if (wallet.getBalance() >= event.getTicketPrice()) {
            wallet.withdraw(event.getTicketPrice());
            eventsJoined.add(event);
            event.registerAttendee(this);
            return true;
        } else {
            return false; // Failure (or throw exception)
        }
    }

    public gender getGender() {
        return Gender;
    }

    public Wallet getWallet() {
        return wallet;
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "date='" + date + '\'' +
                ", eventsJoined=" + eventsJoined +
                ", Gender=" + Gender +
                ", wallet=" + wallet +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}