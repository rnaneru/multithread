package com.example.multithread.service;

import com.example.multithread.domain.BenchmarkResult;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BenchmarkService {

    private static final int SIZE = 1_000_000;
    private List<Integer> numbers;

    @PostConstruct
    public void init() {
        System.out.println("Генерация списка из " + SIZE + " элементов для бенчмарка...");
        numbers = new ArrayList<>(SIZE);
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            numbers.add(random.nextInt(1000));
        }
        System.out.println("Список для бенчмарка готов.");
    }

    public BenchmarkResult runBenchmark() {
        warmup();

        long startSeq = System.nanoTime();
        long sumSeq = numbers.stream()
                .filter(n -> n % 2 == 0)
                .mapToLong(n -> n * 2L)
                .sum();
        long endSeq = System.nanoTime();
        double timeSeq = (endSeq - startSeq) / 1_000_000.0;

        long startPar = System.nanoTime();
        long sumPar = numbers.parallelStream()
                .filter(n -> n % 2 == 0)
                .mapToLong(n -> n * 2L)
                .sum();
        long endPar = System.nanoTime();
        double timePar = (endPar - startPar) / 1_000_000.0;

        String winner;
        String analysis;
        if (timeSeq < timePar) {
            winner = "Обычный stream()";
            double diff = ((timePar - timeSeq) / timeSeq) * 100;
            analysis = String.format("Обычный stream() быстрее на %.2f%%. Накладные расходы на ForkJoinPool превышают выгоду от параллелизма.", diff);
        } else if (timePar < timeSeq) {
            winner = "parallelStream()";
            double diff = ((timeSeq - timePar) / timePar) * 100;
            analysis = String.format("parallelStream() быстрее на %.2f%%. Многоядерный процессор эффективно распределил нагрузку.", diff);
        } else {
            winner = "Ничья";
            analysis = "Время выполнения практически идентично.";
        }

        return new BenchmarkResult(SIZE, sumSeq, timeSeq, sumPar, timePar, winner, analysis);
    }

    private void warmup() {
        for (int i = 0; i < 5; i++) {
            numbers.stream().filter(n -> n % 2 == 0).mapToLong(n -> n * 2L).sum();
            numbers.parallelStream().filter(n -> n % 2 == 0).mapToLong(n -> n * 2L).sum();
        }
    }
}