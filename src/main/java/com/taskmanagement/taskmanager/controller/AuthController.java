package com.taskmanagement.taskmanager.controller;

import com.taskmanagement.taskmanager.dto.user.UserLoginRequestDto;
import com.taskmanagement.taskmanager.dto.user.UserLoginResponseDto;
import com.taskmanagement.taskmanager.dto.user.UserRegistrationRequestDto;
import com.taskmanagement.taskmanager.dto.user.UserResponseDto;
import com.taskmanagement.taskmanager.security.AuthenticationService;
import com.taskmanagement.taskmanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "User registration and authentication")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final UserService userService;
    private final AuthenticationService authenticationService;
    
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with the USER role"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid registration data"),
            @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(
            @Valid @RequestBody UserRegistrationRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }
    
    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Authenticates a user by email and password "
                    + "and returns a JWT access token"
    )
    public UserLoginResponseDto login(
            @Valid @RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
