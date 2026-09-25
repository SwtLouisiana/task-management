package com.taskmanagement.taskmanager.dto.user;

import com.taskmanagement.taskmanager.validation.NormalizeText;
import com.taskmanagement.taskmanager.validation.PasswordsMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordsMatch
public class UserRegistrationRequestDto {
    
    @NormalizeText
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 32,
            message = "Username must contain between 3 and 32 characters")
    private String username;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 64,
            message = "Password must contain between 8 and 64 characters")
    @Pattern(regexp = "^[\\x20-\\x7E]*$",
            message = "Password may contain only English letters, digits, "
                    + "ASCII symbols and spaces")
    private String password;
    
    @NotBlank(message = "Password confirmation is required")
    private String repeatPassword;
    
    @NormalizeText
    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 100,
            message = "First name must contain between 1 and 100 characters")
    private String firstName;
    
    @NormalizeText
    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 100,
            message = "Last name must contain between 1 and 100 characters")
    private String lastName;
}
