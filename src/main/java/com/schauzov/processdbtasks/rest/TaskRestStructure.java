package com.schauzov.processdbtasks.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskRestStructure {
    @JsonProperty("execTime")
    private Integer execTime;

    public TaskRestStructure(Integer execTime) {
        this.execTime = execTime;
    }

    public TaskRestStructure() {
    }

    public Integer getExecTime() {
        return execTime;
    }
}
