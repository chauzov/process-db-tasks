package com.schauzov.processdbtasks.controller;

import com.schauzov.processdbtasks.rest.TaskRestStructureList;
import com.schauzov.processdbtasks.service.TaskRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {

    @Autowired
    private final TaskRestService taskRestService;

    public TaskController(TaskRestService taskRestService) {
        this.taskRestService = taskRestService;
    }

    @PostMapping
    public void addTasks(@RequestBody TaskRestStructureList body) {
        taskRestService.addTasks(body);
    }
}
