package com.schauzov.processdbtasks.service;

import com.schauzov.processdbtasks.model.Task;
import com.schauzov.processdbtasks.repository.TaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);
    private Set<Long> processingQueue = new HashSet<>();

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void processTasks() {
        // Do no start processing when it is already in progress
        if(!this.processingQueue.isEmpty()) {
            return;
        }

        List<Task> tasks = taskRepository.findByStatus(Status.NEW.getValue());
        tasks.forEach(task -> this.processingQueue.add(task.getTaskId()));

        ExecutorService service = Executors.newFixedThreadPool(3);
        tasks.forEach(task -> service.execute(new TaskRunnable(task)));
    }

    private void processTask(Task task) throws InterruptedException {
        logger.info("Starting the task with ID {}", task.getTaskId());
        task.setStatus(Status.RUNNING);
        taskRepository.save(task);
        TimeUnit.MILLISECONDS.sleep(task.getExecTime());
        task.setStatus(Status.COMPLETED);
        taskRepository.save(task);
        this.processingQueue.remove(task.getTaskId());
        logger.info("The task with ID {} has completed", task.getTaskId());
    }

    private class TaskRunnable implements Runnable {
        Task task;

        public TaskRunnable(Task task) {
            this.task = task;
        }

        public void run() {
            try {
                TaskServiceImpl.this.processTask(this.task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
