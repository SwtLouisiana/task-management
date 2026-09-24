package com.taskmanagement.taskmanager.dto.user;

import com.taskmanagement.taskmanager.models.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleUpdateRequestDto {

    @NotNull(message = "Role is required")
    private Role role;
}
