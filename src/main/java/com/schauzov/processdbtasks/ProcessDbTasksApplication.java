package com.schauzov.processdbtasks;

import com.schauzov.processdbtasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class ProcessDbTasksApplication {

	@Autowired
	TaskService taskService;

	public static void main(String[] args) {
		SpringApplication.run(ProcessDbTasksApplication.class, args);
	}

	public ProcessDbTasksApplication(TaskService taskService) {
		this.taskService = taskService;
	}

	public ProcessDbTasksApplication() {
	}

	@Scheduled(fixedDelay = 3000L)
	void processTasks() {
		taskService.processTasks();
	}
}


@Configuration
@EnableScheduling
class SchedulingConfiguration {
}