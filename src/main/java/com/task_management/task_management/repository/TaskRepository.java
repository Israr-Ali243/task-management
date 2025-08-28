package com.task_management.task_management.repository;

import com.task_management.task_management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedTo_Id(Long userId);

    List<Task> findByTeam_Id(Long teamId);

    List<Task> findByStatus(String status);
    Boolean existsByAssignedTo_Id(Long userId);

}
