package com.task_management.task_management.service;

import com.task_management.task_management.dto.CommentDTO;
import com.task_management.task_management.utils.BaseResponse;


public interface CommentService {
    BaseResponse addComment(Long taskId, CommentDTO commentDTO);
    BaseResponse getCommentsByTask(Long taskId);
}
