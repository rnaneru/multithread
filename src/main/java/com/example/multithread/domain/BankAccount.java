package com.example.multithread.domain;

public class BankAccount {
    private final int id; // Для предотвращения deadlock
    private double balance;

    public BankAccount(int id, double initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public int getId() { return id; }
    public synchronized double getBalance() { return balance; }
    public void deposit(double amount) { this.balance += amount; }
    public void withdraw(double amount) { this.balance -= amount; }
}
