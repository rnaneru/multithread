package com.example.multithread.service;


import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ThreadService {

    public void startCustomThreads() {
        // Создание потока через Thread
        Thread loggerThread = new Thread("LoggerThread") {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("[" + Thread.currentThread().getName() + "] Порядковый номер: " + i);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        // Создание потока через Runnable
        Runnable counterLogic = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("[" + Thread.currentThread().getName() + "] Порядковый номер: " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        Thread counterWorker = new Thread(counterLogic, "CounterWorker");


        loggerThread.start();
        counterWorker.start();
    }

    public void printActiveThreads() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread t : threadSet) {
            System.out.println("Поток: " + t.getName() + " | Статус: " + t.getState());
        }
    }
}