package com.task_management.task_management.repository;

import com.task_management.task_management.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTask_Id(Long taskId);

    List<Comment> findByCreatedBy_Id(Long userId);
}