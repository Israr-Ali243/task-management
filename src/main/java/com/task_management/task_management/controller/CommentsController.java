package com.task_management.task_management.controller;

import com.task_management.task_management.dto.CommentDTO;
import com.task_management.task_management.service.CommentService;
import com.task_management.task_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create-comment/taskId/{taskId}")
    public BaseResponse createComment(@PathVariable("taskId") Long taskId, @RequestBody CommentDTO commentDTO) {
        return commentService.addComment(taskId, commentDTO);
    }

    @GetMapping("/find-comment-bytaskId/{taskId}")
    public BaseResponse getCommentsByTask(@PathVariable("taskId") Long taskId) {
        return commentService.getCommentsByTask(taskId);
    }


}
