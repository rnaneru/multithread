package com.example.multithread.controller;

import com.example.multithread.domain.BenchmarkResult;
import com.example.multithread.service.BenchmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/benchmark")
@RequiredArgsConstructor
public class BenchmarkController {

    private final BenchmarkService benchmarkService;

    @GetMapping("/streams")
    public BenchmarkResult getStreamBenchmark() {
        return benchmarkService.runBenchmark();
    }
}