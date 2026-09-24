package com.taskmanagement.taskmanager.service;

import com.taskmanagement.taskmanager.dto.user.UserProfileUpdateRequestDto;
import com.taskmanagement.taskmanager.dto.user.UserRegistrationRequestDto;
import com.taskmanagement.taskmanager.dto.user.UserResponseDto;
import com.taskmanagement.taskmanager.dto.user.UserRoleUpdateRequestDto;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequestDto requestDto);
    
    UserResponseDto getByEmail(String email);
    
    UserResponseDto updateUserProfile(
            String email,
            UserProfileUpdateRequestDto requestDto
    );
    
    UserResponseDto updateUserRole(
            Long userId,
            UserRoleUpdateRequestDto requestDto
    );
}
