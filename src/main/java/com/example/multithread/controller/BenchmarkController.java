package com.example.multithread.controller;

import com.example.multithread.domain.BenchmarkResult;
import com.example.multithread.service.BenchmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/benchmark")
public class BenchmarkController {

    @Autowired
    private BenchmarkService benchmarkService;

    @GetMapping("/start")
    public BenchmarkResult runBenchmark() throws InterruptedException {
        return benchmarkService.runPerformanceBenchmark();
    }
}
