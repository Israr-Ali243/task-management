package com.task_management.task_management.dto;

import com.task_management.task_management.model.User;
import com.task_management.task_management.utils.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
