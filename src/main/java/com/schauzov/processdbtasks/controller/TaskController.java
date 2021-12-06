package com.schauzov.processdbtasks.controller;

import com.schauzov.processdbtasks.model.Task;
import com.schauzov.processdbtasks.rest.TaskRestStructure;
import com.schauzov.processdbtasks.rest.TaskRestStructureList;
import com.schauzov.processdbtasks.service.TaskRestService;
import com.schauzov.processdbtasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {

    @Autowired
    private final TaskRestService taskRestService;

    @Autowired
    private final TaskService taskService;

    public TaskController(TaskRestService taskRestService, TaskService taskService) {
        this.taskRestService = taskRestService;
        this.taskService = taskService;
    }

    @PostMapping
    public void addTasks(@RequestBody TaskRestStructureList body) {
        taskRestService.addTasks(body);
        taskService.processTasks();
    }
}
