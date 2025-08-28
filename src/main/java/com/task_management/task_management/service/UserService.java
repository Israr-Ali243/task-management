package com.task_management.task_management.service;

import com.task_management.task_management.dto.UserDTO;
import com.task_management.task_management.utils.BaseResponse;
import org.springframework.web.bind.annotation.PathVariable;


public interface UserService {
    BaseResponse createUser(UserDTO userDTO);
    BaseResponse getAllUsers();
    BaseResponse getById(Long userId);
}
