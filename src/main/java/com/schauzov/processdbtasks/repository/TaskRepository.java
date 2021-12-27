package com.schauzov.processdbtasks.repository;

import com.schauzov.processdbtasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(char status);

    @Transactional
    @Modifying
    @Query("update Task t set t.status = :status where t.taskId in (:taskIds) ")
    void setTasksStatuses(@Param("status") char status, @Param("taskIds") List<Long> tasksIds);
}
