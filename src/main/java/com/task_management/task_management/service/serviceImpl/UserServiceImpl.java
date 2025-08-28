package com.task_management.task_management.service.serviceImpl;

import com.task_management.task_management.dto.UserDTO;
import com.task_management.task_management.exception.ResourceNotFoundException;
import com.task_management.task_management.model.User;
import com.task_management.task_management.repository.UserRepository;
import com.task_management.task_management.service.UserService;
import com.task_management.task_management.utils.BaseResponse;
import com.task_management.task_management.utils.Mapper;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public BaseResponse<UserDTO> createUser(UserDTO userDTO) {
        try {
            if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
                throw new BadRequestException("Email is required");
            }

            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new BadRequestException("User already exists with email: " + userDTO.getEmail());
            }

            User user = Mapper.toUserEntity(userDTO);
            User savedUser = userRepository.save(user);
            return new BaseResponse<>(true,"User created successfully", Mapper.toUserDTO(savedUser) ,false, HttpStatus.NOT_FOUND.value());

        } catch (BadRequestException e) {
            return  BaseResponse.failure( "User already exists with email: "+userDTO.getEmail(), true, HttpStatus.FORBIDDEN.value());
        } catch (Exception e) {
            return  BaseResponse.failure( "Internal server error: " + e.getMessage(), true, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public BaseResponse<List<UserDTO>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();

            if (users.isEmpty()) {
                throw new ResourceNotFoundException("No users found");
            }

            List<UserDTO> userDTOs = users.stream()
                    .map(Mapper::toUserDTO)
                    .collect(Collectors.toList());

            return new BaseResponse<>(true,"Users fetched successfully", userDTOs,  false, HttpStatus.OK.value());

        } catch (ResourceNotFoundException e) {
            return BaseResponse.failure("User Not Found",  true, HttpStatus.NO_CONTENT.value());
        } catch (Exception e) {
            return BaseResponse.failure("Internal server error: " + e.getMessage(), true, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public BaseResponse getById(Long userId) {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                throw new ResourceNotFoundException("User does not exists: " + userId);
            }
            return new BaseResponse<>(true,"User found successfully", Mapper.toUserDTO(user.orElse(null)) ,false, HttpStatus.OK.value());

        } catch (ResourceNotFoundException e) {
            return  BaseResponse.failure( "User not found", true, HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            return  BaseResponse.failure( "Internal server error: " + e.getMessage(), true, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
