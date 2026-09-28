package com.example.multithread.domain;

public record BenchmarkResult(long syncTimeMs, long unsyncTimeMs, int processedCount) {}
