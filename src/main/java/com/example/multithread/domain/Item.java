package com.example.multithread.domain;

public class Item {
    private final String key;
    private final BankAccount fromAccount;
    private final BankAccount toAccount;
    private final double amount;

    public Item(String key, BankAccount fromAccount, BankAccount toAccount, double amount) {
        this.key = key;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public String getKey() { return key; }
    public BankAccount getFromAccount() { return fromAccount; }
    public BankAccount getToAccount() { return toAccount; }
    public double getAmount() { return amount; }
}
