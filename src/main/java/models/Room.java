package models;

import java.util.Date;

import com.MainApplication;

public class Room {
    private int roomNo;
    private int capacity;
    private String availableHours;
    private static int roomCount;
    private Date date;

    public void setAvailableHours(String availableHours) {
        this.availableHours = availableHours;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static void setRoomCount(int roomCount) {
        Room.roomCount = roomCount;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public Room(int roomNo, int capacity, Date date) {
        this.roomNo = roomNo;
        this.date = date;
        this.capacity = capacity;
        roomCount++;
    }

    public Room(int roomNo) {
        this.roomNo = roomNo;
        roomCount++;
    }

    public Room() {
        roomCount++;
    }

    public String getAvailableHours() {
        return availableHours;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public static int getRoomCount() {
        return roomCount;
    }

    public boolean checkRoom(int roomNo, Date date) {
        // In a real application, this would check against reserved dates
        // For now, return true to simplify the example
        return true;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNo=" + roomNo +
                ", capacity=" + capacity +
                ", availableHours='" + availableHours + '\'' +
                '}';
    }
}