package com.example.multithread.task;

import com.example.multithread.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ThreadTask {

    @Autowired
    private ThreadService threadService;

    // Сработает один раз сразу после полного запуска Spring Boot приложения
    @EventListener(ApplicationReadyEvent.class)
    public void initThreads() {
        System.out.println("Фоновая задача: Запуск кастомных потоков...");
        threadService.startCustomThreads();
    }

    // Будет автоматически выполняться каждые 5000 мс (5 секунд)
    @Scheduled(fixedRate = 5000)
    public void monitorThreads() {
        threadService.printActiveThreads();
    }
}