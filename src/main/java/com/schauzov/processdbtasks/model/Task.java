package com.schauzov.processdbtasks.model;


import com.schauzov.processdbtasks.service.TaskService;

import javax.persistence.*;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "exec_time") // how long to execute, in milliseconds
    private Integer execTime;

    @Column(name = "status") // 'n' - new, 'r' - running, 'c' - completed
    private char status;

    public Task(Integer execTime, TaskService.Status status) {
        this.execTime = execTime;
        this.status = status.getValue();
    }

    public Task() {}

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getExecTime() {
        return execTime;
    }

    public void setExecTime(Integer execTime) {
        this.execTime = execTime;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(TaskService.Status status) {
        this.status = status.getValue();
    }
}
