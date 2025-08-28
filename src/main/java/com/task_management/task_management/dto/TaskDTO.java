package com.task_management.task_management.dto;

import com.task_management.task_management.utils.Priority;
import com.task_management.task_management.utils.Status;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Instant dueDate;
    private Long assignedToId;
    private Long teamId;
}
