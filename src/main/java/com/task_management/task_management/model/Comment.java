package com.task_management.task_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    @Column(length = 1000)
    private String commentText;
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    private Instant createdAt;
}
