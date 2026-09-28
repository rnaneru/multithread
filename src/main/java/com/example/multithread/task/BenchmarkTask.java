package com.example.multithread.task;

import com.example.multithread.domain.BankAccount;
import com.example.multithread.domain.Item;
import com.example.multithread.service.BenchmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BenchmarkTask {

    @Autowired
    private BenchmarkService benchmarkService;

    @EventListener(ApplicationReadyEvent.class)
    public void runFunctionalTest() throws InterruptedException {
        BankAccount acc1 = new BankAccount(1, 50000);
        BankAccount acc2 = new BankAccount(2, 50000);

        // Поток-обработчик CounterWorker
        Thread counterWorker = new Thread(() -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    Item item = benchmarkService.getNextItem();
                    if (!benchmarkService.isAlreadyProcessed(item.getKey())) {
                        if (benchmarkService.transferFundsSync(item.getFromAccount(), item.getToAccount(), item.getAmount())) {
                            benchmarkService.incrementProcessed();
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "CounterWorker");

        // Поток-поставщик LoggerThread
        Thread loggerThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                Item item = (i % 2 == 0) ? new Item("TX-" + i, acc1, acc2, 10) : new Item("TX-" + i, acc2, acc1, 10);
                benchmarkService.collectItem(item);
            }
        }, "LoggerThread");

        counterWorker.start();
        loggerThread.start();
        counterWorker.join();
        loggerThread.join();

        System.out.println("=== РЕЗУЛЬТАТЫ ФУНКЦИОНАЛЬНОГО ТЕСТА ===");
        System.out.println("Обработано транзакций: " + benchmarkService.getProcessedCount());
        System.out.println("Итоговый баланс Acc1: " + acc1.getBalance() + " | Acc2: " + acc2.getBalance());
    }
}
