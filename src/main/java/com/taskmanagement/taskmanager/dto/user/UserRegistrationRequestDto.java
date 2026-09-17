package com.taskmanagement.taskmanager.dto.user;

import com.taskmanagement.taskmanager.validation.PasswordsMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordsMatch
public class UserRegistrationRequestDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50,
            message = "Username must contain between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100,
            message = "Password must contain at least 8 characters")
    private String password;

    @NotBlank(message = "Password confirmation is required")
    private String repeatPassword;

    @NotBlank(message = "First name is required")
    @Size(max = 255)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 255)
    private String lastName;
}
