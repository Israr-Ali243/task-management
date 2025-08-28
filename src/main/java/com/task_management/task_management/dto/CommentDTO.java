package com.task_management.task_management.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private Long taskId;
    private String commentText;
    private Long createdById;
    private Instant createdAt;
}
