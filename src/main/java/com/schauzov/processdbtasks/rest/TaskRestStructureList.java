package com.schauzov.processdbtasks.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class TaskRestStructureList {
    @JsonProperty("tasks")
    List<TaskRestStructure> tasks;

    public TaskRestStructureList(List<TaskRestStructure> taskRestStructureList) {
        this.tasks = taskRestStructureList;
    }

    public TaskRestStructureList() {
        this.tasks = new ArrayList<>();
    }

    public List<TaskRestStructure> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskRestStructure> tasks) {
        this.tasks = tasks;
    }
}
