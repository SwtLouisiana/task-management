package com.taskmanagement.taskmanager.dto.user;

import com.taskmanagement.taskmanager.models.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}
