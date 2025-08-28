package com.task_management.task_management.dto;

import lombok.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDTO {
    private Long id;
    private String name;
    private String description;
    private Set<Long> memberIds;
}
