package com.task_management.task_management.controller;

import com.task_management.task_management.dto.UserDTO;
import com.task_management.task_management.service.UserService;
import com.task_management.task_management.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create-user")
    public BaseResponse createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }
    @GetMapping("/find-all-users")
    public BaseResponse getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/find-user-by-id/{id}")
    public BaseResponse getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

}
