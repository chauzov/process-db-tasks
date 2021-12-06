package com.schauzov.processdbtasks.service;

import com.schauzov.processdbtasks.model.Task;

public interface TaskService {
    public enum Status {
        NEW('n'),
        RUNNING('r'),
        COMPLETED('c');

        private char value;
        Status(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }
    }
    void addTask(Task task);
    void processTasks();
}
