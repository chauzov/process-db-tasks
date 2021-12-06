package com.schauzov.processdbtasks.service;

import com.schauzov.processdbtasks.model.Task;
import com.schauzov.processdbtasks.repository.TaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void processTasks() {
        List<Task> tasks = taskRepository.findByStatus(Status.NEW.getValue());

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
