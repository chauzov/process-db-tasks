package com.schauzov.processdbtasks.controller;

import com.schauzov.processdbtasks.rest.TaskRestStructureList;
import com.schauzov.processdbtasks.service.TaskRestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(TaskController.class);

    public TaskController(TaskRestService taskRestService) {
        this.taskRestService = taskRestService;
    }

    @PostMapping
    public void addTasks(@RequestBody TaskRestStructureList body) {
        logger.info("New bunch of tasks received");
        taskRestService.addTasks(body);
    }
}
