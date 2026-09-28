package com.example.multithread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MultithreadApplication {

    public static void main(String[] args) throws InterruptedException {
        org.springframework.boot.SpringApplication.run(MultithreadApplication.class, args);
    }
}

