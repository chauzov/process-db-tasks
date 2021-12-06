package com.schauzov.processdbtasks.service;

import com.schauzov.processdbtasks.model.Task;
import com.schauzov.processdbtasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceImplTest {

    @Autowired
    private TaskRepository taskRepository;
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(taskRepository);
    }

    @Test
    void createSomeTasks() {
        for (int i = 0; i < 20; i++) {
            // Task execution time from 100 to 1000ms
            Integer execTime = new Random().nextInt(900) + 100;
            taskService.addTask(new Task(execTime, TaskService.Status.NEW));
        }
        taskRepository.findAll();
        assertTrue(true);
    }

}