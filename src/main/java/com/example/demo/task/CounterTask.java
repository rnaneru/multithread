package com.example.demo.task;

public class CounterTask implements Runnable{
    private final int maxCount;
    public CounterTask(int maxCount){
        this.maxCount = maxCount;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        for (int i = 1; i <= maxCount; i++) {
            System.out.printf("[%s] номер: %d%n", threadName, i);
            try {
                Thread.sleep(300); // небольшая задержка, чтобы потоки шли вперемешку
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.printf("[%s] прерван%n", threadName);
                return;
            }
        }
        System.out.printf("[%s] завершён%n", threadName);
    }
}
