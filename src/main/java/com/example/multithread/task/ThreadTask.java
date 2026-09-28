package com.example.multithread.task;

import com.example.multithread.service.ThreadStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ThreadTask {

    private final ThreadStateService threadStateService;
    private boolean isDemonstrationRunning = false;

    @EventListener(ApplicationReadyEvent.class)
    public void startDemonstration() {
        new Thread(() -> {
            try {
                isDemonstrationRunning = true;
                threadStateService.demonstrateStates();
                isDemonstrationRunning = false;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "DemoOrchestrator").start();
    }

    @Scheduled(fixedRate = 1000)
    public void monitorThreads() {
        if (isDemonstrationRunning) {
            System.out.println("\nАвтоматический мониторинг");
            threadStateService.printSpecificThreadsState("Мониторинг");
        }
    }
}