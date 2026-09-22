package com.taskmanagement.taskmanager.dto.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileUpdateRequestDto {

    @Size(min = 3, max = 50)
    @Pattern(regexp = "(?s).*\\S.*", message = "Username must not be blank")
    private String username;

    @Size(max = 255)
    @Pattern(regexp = "(?s).*\\S.*", message = "First name must not be blank")
    private String firstName;

    @Size(max = 255)
    @Pattern(regexp = "(?s).*\\S.*", message = "Last name must not be blank")
    private String lastName;
}
