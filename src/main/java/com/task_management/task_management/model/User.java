package com.task_management.task_management.model;

import com.task_management.task_management.utils.Role;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false, unique = true)
        private String email;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Role role;


}
