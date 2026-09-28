package com.example.multithread.domain;

public record BenchmarkResult(
        int listSize,
        long sequentialSum,
        double sequentialTimeMs,
        long parallelSum,
        double parallelTimeMs,
        String winner,
        String analysis
) {}