package com.task_management.task_management.repository;

import com.task_management.task_management.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository  extends JpaRepository<Team, Long> {
    boolean existsByName(String name);



}
