package com.task_management.task_management.controller;

import com.task_management.task_management.dto.TaskDTO;
import com.task_management.task_management.service.TaskService;
import com.task_management.task_management.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;


    @PostMapping("/create-task")
    public BaseResponse createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }


    @GetMapping("/find-AllTasks-ByStatus-TeamId-AndUserId")
    public BaseResponse getAllTaskByStatusTeamIdAndUserId(@RequestParam String status,
                                                          @RequestParam Long teamId,
                                                          @RequestParam Long userId) {
        return taskService.getAllTasksByStatusTeamIdAndUserId( status, teamId, userId);
    }

    @PutMapping("update/{id}")
    public BaseResponse updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(id, taskDTO);
    }

    @PatchMapping("/update-task-status")
    public BaseResponse updateTaskStatus(@RequestParam Long id, @RequestParam String status) throws BadRequestException {
        return taskService.updateTaskStatus(id, status);
    }

    @GetMapping("/user/{userId}")
    public BaseResponse getTasksByUser(@PathVariable Long userId) {
        return taskService.getTasksByUser(userId);
    }

    @GetMapping("/find-all-task")
    public BaseResponse getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return taskService.getAllTasks(page, size);
    }


}
