package com.example.multithread.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ThreadStateService {

    private final Object lockForBlocking = new Object();
    private final Object lockForWaiting = new Object();

    private Thread sleeperThread;
    private Thread waiterThread;
    private Thread blockerThread;

    public void demonstrateStates() throws InterruptedException {
        System.out.println("Начало демонстрации работ потоков\n");

        sleeperThread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "1-SleeperThread");

        waiterThread = new Thread(() -> {
            synchronized (lockForWaiting) {
                try {
                    lockForWaiting.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "2-WaiterThread");

        blockerThread = new Thread(() -> {
            synchronized (lockForBlocking) {
                System.out.println("Поток 3 получил блокировку и завершается");
            }
        }, "3-BlockerThread");

        printSpecificThreadsState("ШАГ 1: Потоки созданы, но start() не вызван");

        synchronized (lockForBlocking) {

            blockerThread.start();

            Thread.sleep(100);
            printSpecificThreadsState("ШАГ 2: Поток 3 пытается войти в synchronized, но монитор занят главным потоком");

            waiterThread.start();
            Thread.sleep(100);
            printSpecificThreadsState("ШАГ 3: Поток 2 вошел в монитор и вызвал wait()");

            sleeperThread.start();
            Thread.sleep(100);
            printSpecificThreadsState("ШАГ 4: Поток 1 вызвал Thread.sleep()");

            System.out.println("\nОсвобождение потоков");

        }

        synchronized (lockForWaiting) {
            lockForWaiting.notifyAll();
        }

        sleeperThread.join();
        waiterThread.join();
        blockerThread.join();

        printSpecificThreadsState("ШАГ 5: Все потоки завершили выполнение");
        System.out.println("=== КОНЕЦ ДЕМОНСТРАЦИИ ===");
    }


    public void printSpecificThreadsState(String stepDescription) {
        System.out.println("\n[" + stepDescription + "]");
        List<Thread> targetThreads = Arrays.asList(sleeperThread, waiterThread, blockerThread);

        for (Thread t : targetThreads) {
            if (t != null) {
                System.out.printf("Поток: %-18s | Статус: %s%n", t.getName(), t.getState());
            }
        }
    }
}