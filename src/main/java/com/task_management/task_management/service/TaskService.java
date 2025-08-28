package com.task_management.task_management.service;

import com.task_management.task_management.dto.TaskDTO;
import com.task_management.task_management.utils.BaseResponse;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface TaskService {
    BaseResponse createTask(TaskDTO taskDTO);
    BaseResponse getAllTasksByStatusTeamIdAndUserId(String status, Long teamId, Long userId);
    BaseResponse updateTask(Long id, TaskDTO taskDTO);
    BaseResponse updateTaskStatus(Long id, String status) throws BadRequestException;
    BaseResponse getTasksByUser(Long userId);
    BaseResponse getAllTasks(int page, int size);
}
