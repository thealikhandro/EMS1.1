package models;


import com.MainApplication;

public class Wallet {
    private double balance;

    public Wallet(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    public void withdraw(double ticketPrice) {
        balance -= ticketPrice;
    }
    
    @Override
    public String toString() {
        return "Wallet{balance=" + balance + '}';
    }
}