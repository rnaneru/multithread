package com.example.multithread.task;

import com.example.multithread.service.BenchmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BenchmarkTask { // Можно переименовать класс

    private final BenchmarkService benchmarkService;

    @EventListener(ApplicationReadyEvent.class)
    public void runBenchmarkOnStartup() {
        System.out.println("--- Запуск бенчмарка стримов ---");
        // Вызываем правильный метод для BenchmarkService
        benchmarkService.runBenchmark();
    }
}