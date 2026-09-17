package com.example.demo.service;


import com.example.demo.task.CounterTask;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Service
public class ThreadDemoService {

    public void runViaThread(){
        Thread thread = new Thread(new CounterTask(5), "CounterWorker");
        thread.start();
        System.out.println("Запущен поток: " + thread.getName());
    }

    public void runViaRunnable(){
        Runnable task = new CounterTask(5);
        Thread thread = new Thread(task, "LoggerThread");
        thread.start();
        System.out.println("Запущен поток: " + thread.getName());
    }
    public void printActiveThreads() {
        Set<Thread> threads = Thread.getAllStackTraces().keySet();

        Map<String, Thread> sorted = new TreeMap<>();
        for (Thread t : threads) {
            sorted.put(t.getName(), t);
        }

        System.out.println("=== Активные потоки (" + sorted.size() + ") ===");
        sorted.forEach((name, t) -> {
            System.out.printf("name=%-25s state=%-12s daemon=%s priority=%d%n",
                    name, t.getState(), t.isDaemon(), t.getPriority());
        });
        System.out.println("===================================");
    }
}
