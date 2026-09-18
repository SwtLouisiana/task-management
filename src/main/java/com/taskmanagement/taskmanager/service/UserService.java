package com.taskmanagement.taskmanager.service;

import com.taskmanagement.taskmanager.dto.user.UserRegistrationRequestDto;
import com.taskmanagement.taskmanager.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequestDto requestDto);
}
