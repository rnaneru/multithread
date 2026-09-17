package com.example.demo;

import com.example.demo.service.ThreadDemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		var ctx = SpringApplication.run(DemoApplication.class, args);
		ThreadDemoService service = ctx.getBean(ThreadDemoService.class);

		service.runViaThread();
		service.runViaRunnable();
		service.printActiveThreads();
	}

}
