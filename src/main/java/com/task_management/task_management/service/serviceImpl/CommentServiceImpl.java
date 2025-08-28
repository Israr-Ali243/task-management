package com.task_management.task_management.service.serviceImpl;

import com.task_management.task_management.dto.CommentDTO;
import com.task_management.task_management.model.Comment;
import com.task_management.task_management.model.Task;
import com.task_management.task_management.model.User;
import com.task_management.task_management.repository.CommentRepository;
import com.task_management.task_management.repository.TaskRepository;
import com.task_management.task_management.repository.UserRepository;
import com.task_management.task_management.service.CommentService;
import com.task_management.task_management.utils.BaseResponse;
import com.task_management.task_management.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public BaseResponse addComment(Long taskId, CommentDTO commentDTO) {
        try {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

            User user = userRepository.findById(commentDTO.getCreatedById())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + commentDTO.getCreatedById()));

            Comment comment = Mapper.toCommentEntity(commentDTO);
            comment.setTask(task);
            comment.setCreatedBy(user);
            comment.setCreatedAt(Instant.now());

            Comment savedComment = commentRepository.save(comment);
            CommentDTO responseDto = Mapper.toCommentDTO(savedComment);

            return BaseResponse.success(responseDto, "Comment added successfully", HttpStatus.CREATED.value());

        } catch (Exception ex) {
            log.error("Error adding comment for taskId {}: {}", taskId, ex.getMessage(), ex);
            return BaseResponse.failure("Failed to add comment: " + ex.getMessage(), true, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


    @Override
    public BaseResponse<List<CommentDTO>> getCommentsByTask(Long taskId) {
        log.info("Fetching comments for taskId: {}", taskId);

        try {
            List<CommentDTO> comments = commentRepository.findAll()
                    .stream()
                    .filter(comment -> {
                        if (comment.getTask() == null || comment.getTask().getId() == null) {
                            log.warn("Skipping comment with id {} because task is null", comment.getId());
                            return false;
                        }
                        return comment.getTask().getId().equals(taskId);
                    })
                    .map(Mapper::toCommentDTO)
                    .collect(Collectors.toList());

            if (comments.isEmpty()) {
                log.info("No comments found for taskId: {}", taskId);
                return BaseResponse.failure("No comments found for taskId: " + taskId, false,HttpStatus.NOT_FOUND.value());
            } else {
                log.info("Found {} comments for taskId: {}", comments.size(), taskId);
                return BaseResponse.success(comments, "Comments fetched successfully", HttpStatus.OK.value());
            }

        } catch (Exception e) {
            log.error("Error while fetching comments for taskId: {}", taskId, e);
            return BaseResponse.failure("Unable to fetch comments for taskId: " + taskId, true,  HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}