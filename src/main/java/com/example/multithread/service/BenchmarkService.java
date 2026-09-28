package com.example.multithread.service;

import com.example.multithread.domain.BankAccount;
import com.example.multithread.domain.BenchmarkResult;
import com.example.multithread.domain.Item;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BenchmarkService {
    private int processedCount = 0;
    private final Set<String> processedKeys = new HashSet<>();
    private final List<Item> queue = new ArrayList<>();
    private final int capacity = 10;

    // Очередь с wait/notify
    public synchronized void collectItem(Item item) {
        while (queue.size() >= capacity) {
            try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        queue.add(item);
        notifyAll();
    }

    public synchronized Item getNextItem() throws InterruptedException {
        while (queue.isEmpty()) { wait(); }
        Item item = queue.remove(0);
        notifyAll();
        return item;
    }

    public synchronized void incrementProcessed() { processedCount++; }

    public synchronized boolean isAlreadyProcessed(String key) {
        if (processedKeys.contains(key)) return true;
        processedKeys.add(key);
        return false;
    }

    public synchronized int getProcessedCount() { return processedCount; }

    // Безопасный перевод (Блокировка по возрастанию ID исключает Deadlock)
    public boolean transferFundsSync(BankAccount from, BankAccount to, double amount) {
        if (from.getId() == to.getId()) return false;
        BankAccount firstLock = from.getId() < to.getId() ? from : to;
        BankAccount secondLock = from.getId() < to.getId() ? to : from;

        synchronized (firstLock) {
            synchronized (secondLock) {
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                    return true;
                }
                return false;
            }
        }
    }

    // Небезопасный перевод (Вызывает Race Condition)
    public boolean transferFundsUnsync(BankAccount from, BankAccount to, double amount) {
        if (from.getBalance() >= amount) {
            from.withdraw(amount);
            to.deposit(amount);
            return true;
        }
        return false;
    }

    public BenchmarkResult runPerformanceBenchmark() throws InterruptedException {
        int ops = 300_000;
        BankAccount acc1 = new BankAccount(1, 999_999);
        BankAccount acc2 = new BankAccount(2, 999_999);

        long startSync = System.currentTimeMillis();
        Thread t1 = new Thread(() -> { for (int i = 0; i < ops; i++) transferFundsSync(acc1, acc2, 1); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < ops; i++) transferFundsSync(acc2, acc1, 1); });
        t1.start(); t2.start(); t1.join(); t2.join();
        long endSync = System.currentTimeMillis();

        long startUnsync = System.currentTimeMillis();
        Thread t3 = new Thread(() -> { for (int i = 0; i < ops; i++) transferFundsUnsync(acc1, acc2, 1); });
        Thread t4 = new Thread(() -> { for (int i = 0; i < ops; i++) transferFundsUnsync(acc2, acc1, 1); });
        t3.start(); t4.start(); t3.join(); t4.join();
        long endUnsync = System.currentTimeMillis();

        return new BenchmarkResult(endSync - startSync, endUnsync - startUnsync, getProcessedCount());
    }
}
