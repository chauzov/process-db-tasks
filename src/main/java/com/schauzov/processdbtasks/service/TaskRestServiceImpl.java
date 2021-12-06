package com.schauzov.processdbtasks.service;

import com.schauzov.processdbtasks.model.Task;
import com.schauzov.processdbtasks.rest.TaskRestStructure;
import com.schauzov.processdbtasks.rest.TaskRestStructureList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskRestServiceImpl implements TaskRestService {

    @Autowired
    private final TaskService taskService;

    public TaskRestServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void addTasks(TaskRestStructureList body) {
        for (TaskRestStructure taskRest : body.getTasks()) {
            taskService.addTask(
                    new Task(taskRest.getExecTime(), TaskService.Status.NEW));
        }
    }
}
