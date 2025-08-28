package com.task_management.task_management.service.serviceImpl;

import com.task_management.task_management.dto.TaskDTO;
import com.task_management.task_management.exception.ResourceNotFoundException;
import com.task_management.task_management.exception.TaskNotFoundException;
import com.task_management.task_management.model.Task;
import com.task_management.task_management.model.Team;
import com.task_management.task_management.model.User;
import com.task_management.task_management.repository.TaskRepository;
import com.task_management.task_management.repository.TeamRepository;
import com.task_management.task_management.repository.UserRepository;
import com.task_management.task_management.service.TaskService;
import com.task_management.task_management.utils.BaseResponse;
import com.task_management.task_management.utils.Mapper;
import com.task_management.task_management.utils.PaginatedResponseDTO;
import com.task_management.task_management.utils.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.task_management.task_management.utils.BaseResponse.success;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl  implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Override
    public BaseResponse<TaskDTO> createTask(TaskDTO taskDTO) {
        try {
            log.info("Creating task with title: {}", taskDTO.getTitle());

            User assignedUser = userRepository.findById(taskDTO.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + taskDTO.getAssignedToId()));

            Team team = teamRepository.findById(taskDTO.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Team not found with ID: " + taskDTO.getTeamId()));

            Task newTask = Mapper.toTaskEntity(taskDTO, assignedUser, team);

            if (taskRepository.existsByAssignedTo_Id(taskDTO.getAssignedToId())){
                return BaseResponse.failure("Team member already exist with ID: "+ taskDTO.getAssignedToId(), true, HttpStatus.CONFLICT.value() );
            }
            Task savedTask = taskRepository.save(newTask);

            log.info("Task created successfully with ID: {}", savedTask.getId());

            return success(
                    Mapper.toTaskDTO(savedTask),
                    "Task has been created successfully",
                    HttpStatus.CREATED.value()
            );

        } catch (Exception ex) {
            log.error("Error occurred while creating task: {}", ex.getMessage(), ex);
            return BaseResponse.failure("Failed to create task: "+ex.getMessage() , true, HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public BaseResponse<List<TaskDTO>> getAllTasksByStatusTeamIdAndUserId(String status, Long teamId, Long userId) {
        try {
            List<Task> tasks = taskRepository.findAll();

            List<TaskDTO> tasks2 = tasks.stream()
                    .filter(task -> status == null || task.getStatus().name().equalsIgnoreCase(status))
                    .filter(task -> teamId == null || (task.getTeam() != null && Objects.equals(task.getTeam().getId(), teamId)))
                    .filter(task -> userId == null || (task.getAssignedTo() != null && Objects.equals(task.getAssignedTo().getId(), userId)))
                    .map(Mapper::toTaskDTO)
                    .collect(Collectors.toList());

            if (tasks2.isEmpty()) {
                return BaseResponse.failure("No tasks found", true, HttpStatus.NOT_FOUND.value());
            }

            return BaseResponse.success(tasks2, "Tasks fetched successfully", HttpStatus.OK.value());
        } catch (Exception e) {
            return BaseResponse.failure("Failed to fetch tasks: " + e.getMessage(), true, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }





    @Override
    public BaseResponse<TaskDTO> updateTask(Long id, TaskDTO taskDTO) {
        try {
            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));

            if (taskDTO.getTitle() != null) task.setTitle(taskDTO.getTitle());
            if (taskDTO.getDescription() != null) task.setDescription(taskDTO.getDescription());
            if (taskDTO.getPriority() != null) task.setPriority(taskDTO.getPriority());
            if (taskDTO.getDueDate() != null) task.setDueDate(taskDTO.getDueDate());

            Task updatedTask = taskRepository.save(task);
            return BaseResponse
                    .success(Mapper.toTaskDTO(updatedTask), "Task updated successfully", HttpStatus.OK.value());

        } catch (TaskNotFoundException e) {
            return BaseResponse.failure(e.getMessage(),true, HttpStatus.BAD_REQUEST.value());

        } catch (Exception e) {
            return BaseResponse
                    .failure("Error updating task: " + e.getMessage(),true, HttpStatus.BAD_REQUEST.value());
        }
    }


    @Override
    public BaseResponse<TaskDTO> updateTaskStatus(Long id, String status) throws BadRequestException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        try {
            Status newStatus = Status.valueOf(status.toUpperCase());
            task.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status value: " + status
                    + ". Allowed values are: " + Arrays.toString(Status.values()));
        }

        Task updatedTask = taskRepository.save(task);
        TaskDTO dto = Mapper.toTaskDTO(updatedTask);

        return  BaseResponse.success(dto, "Task status updated successfully", HttpStatus.OK.value());
    }



    @Override
    public BaseResponse<List<TaskDTO>> getTasksByUser(Long userId) {
        try {
            if (!userRepository.existsById(userId)) {
                throw new ResourceNotFoundException("User not found with id: " + userId);
            }

            List<Task> userTasks = taskRepository.findByAssignedTo_Id(userId);

            if (userTasks.isEmpty()) {
                return BaseResponse
                        .success(Collections.emptyList(), "No tasks found for this user", HttpStatus.NOT_FOUND.value());
            }

            List<TaskDTO> taskDTOs = userTasks.stream()
                    .map(Mapper::toTaskDTO)
                    .collect(Collectors.toList());

            return BaseResponse.success(taskDTOs, "Tasks retrieved successfully", HttpStatus.OK.value());

        } catch (ResourceNotFoundException ex) {
            return BaseResponse
                    .failure("USER_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND.value());

        } catch (Exception ex) {
            return BaseResponse.failure(null, "An unexpected error occurred while fetching tasks",HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public BaseResponse<PaginatedResponseDTO<TaskDTO>> getAllTasks(int page, int size) {
        log.info("Fetching tasks with pagination: page={}, size={}", page, size);

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Task> taskPage = taskRepository.findAll(pageable);

            List<TaskDTO> taskList = taskPage.getContent()
                    .stream()
                    .map(Mapper::toTaskDTO)
                    .toList();

            if (taskList.isEmpty()) {
                log.warn("No tasks found in database");
                return BaseResponse.failure("No tasks found", true, HttpStatus.NOT_FOUND.value());
            }

            PaginatedResponseDTO<TaskDTO> responseDTO = new PaginatedResponseDTO<>(
                    taskList,
                    taskPage.getNumber(),
                    taskPage.getTotalElements(),
                    taskPage.getTotalPages()
            );

            log.info("Successfully fetched {} tasks", taskList.size());
            return BaseResponse.success(responseDTO, "Tasks found successfully", HttpStatus.OK.value());

        } catch (Exception e) {
            log.error("Error occurred while fetching tasks: {}", e.getMessage(), e);
            return BaseResponse.failure("An unexpected error occurred while fetching tasks", true, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }



}
