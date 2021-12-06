package com.schauzov.processdbtasks.service;

import com.schauzov.processdbtasks.rest.TaskRestStructureList;


public interface TaskRestService {
    void addTasks(TaskRestStructureList body);
}
