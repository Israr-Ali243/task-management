package com.task_management.task_management.model;

import com.task_management.task_management.utils.Priority;
import com.task_management.task_management.utils.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Column(length = 2000)
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private Instant dueDate;
    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;


}
