package com.taskmanagement.taskmanager.controller;

import com.taskmanagement.taskmanager.config.OpenApiConfig;
import com.taskmanagement.taskmanager.dto.user.UserProfileUpdateRequestDto;
import com.taskmanagement.taskmanager.dto.user.UserResponseDto;
import com.taskmanagement.taskmanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users", description = "User profile management")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
public class UserController {
    private final UserService userService;
    
    @Operation(
            summary = "Get my profile",
            description = "Returns the profile of the authenticated user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile returned successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/me")
    public UserResponseDto getMyProfile(Authentication authentication) {
        return userService.getByEmail(authentication.getName());
    }
    
    @PatchMapping("/me")
    @Operation(
            summary = "Update user profile",
            description = "Updates the provided profile fields of the authenticated user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated"),
            @ApiResponse(responseCode = "400", description = "Invalid profile data"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "409", description = "Username is already taken")
    })
    public UserResponseDto updateUserProfile(
            Authentication authentication,
            @Valid @RequestBody UserProfileUpdateRequestDto requestDto) {
        return userService.updateUserProfile(
                authentication.getName(),
                requestDto
        );
    }
}
