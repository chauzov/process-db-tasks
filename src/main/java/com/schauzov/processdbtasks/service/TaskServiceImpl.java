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
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);
    private Integer incomingRequestsCounter = 0;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void processTasks() {
        this.incomingRequestsCounter++;

        List<Task> tasks = this.getTasksForProcessing();

        if (tasks.isEmpty()) {
            return;
        }

        ExecutorService service = Executors.newFixedThreadPool(3);
        logger.info("Request to process tasks #{}: scheduling tasks started", this.incomingRequestsCounter);
        tasks.forEach(task -> service.execute(new TaskRunnable(task)));
        logger.info("Request to process tasks #{}: scheduling tasks finished", this.incomingRequestsCounter);
    }

    private List<Task> getTasksForProcessing() {
        List<Task> tasks = taskRepository.findByStatus(Status.NEW.getValue());
        List<Long> taskIds = tasks.stream().map(Task::getTaskId).collect(Collectors.toList());

        taskRepository.setTasksStatuses(Status.SCHEDULED.getValue(), taskIds);
        return tasks;
    }

    private void processTask(Task task) throws InterruptedException {
        logger.info("Starting the task with ID {}, duration: {}ms", task.getTaskId(), task.getExecTime());
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
