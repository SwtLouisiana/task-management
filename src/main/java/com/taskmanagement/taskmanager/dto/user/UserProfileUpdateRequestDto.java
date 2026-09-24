package com.taskmanagement.taskmanager.dto.user;

import com.taskmanagement.taskmanager.validation.NormalizeText;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileUpdateRequestDto {
    
    @NormalizeText
    @Size(min = 3, max = 32,
            message = "Username must contain between 3 and 32 characters")
    private String username;
    
    @NormalizeText
    @Size(min = 1, max = 100,
            message = "First name must contain between 1 and 100 characters")
    private String firstName;
    
    @NormalizeText
    @Size(min = 1, max = 100,
            message = "Last name must contain between 1 and 100 characters")
    private String lastName;
}
