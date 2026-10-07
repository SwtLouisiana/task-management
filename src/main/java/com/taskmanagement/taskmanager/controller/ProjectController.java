package com.taskmanagement.taskmanager.controller;

import com.taskmanagement.taskmanager.config.OpenApiConfig;
import com.taskmanagement.taskmanager.dto.project.ProjectCreateRequestDto;
import com.taskmanagement.taskmanager.dto.project.ProjectResponseDto;
import com.taskmanagement.taskmanager.dto.project.ProjectUpdateRequestDto;
import com.taskmanagement.taskmanager.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Projects", description = "Private project management")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
public class ProjectController {
    
    private final ProjectService projectService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a project",
            description = "Creates a private project owned by the authenticated user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Project created"),
            @ApiResponse(responseCode = "400", description = "Invalid project data"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ProjectResponseDto createProject(
            @Valid @RequestBody ProjectCreateRequestDto requestDto,
            Authentication authentication) {
        
        return projectService.createProject(
                requestDto,
                authentication.getName()
        );
    }
    
    @GetMapping
    @Operation(
            summary = "Get accessible projects",
            description = "Returns projects where the authenticated user "
                    + "is the owner or a member"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projects retrieved"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public Page<ProjectResponseDto> getAccessibleProjects(
            @ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable,
            Authentication authentication) {
        
        return projectService.getAccessibleProjects(
                authentication.getName(),
                pageable
        );
    }
    
    @GetMapping("/{projectId}")
    @Operation(
            summary = "Get project by ID",
            description = "Returns a project accessible to the authenticated user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project retrieved"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied or project does not exist"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project no longer exists after access was checked"
            )
    })
    public ProjectResponseDto getProjectById(
            @PathVariable("projectId") Long projectId) {
        return projectService.getProjectById(projectId);
    }
    
    @PutMapping("/{projectId}")
    @Operation(
            summary = "Update a project",
            description = "Replaces editable project fields. "
                    + "Only the owner or a project manager can update the project. "
                    + "Omitted or null optional fields are cleared."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project updated"),
            @ApiResponse(responseCode = "400", description = "Invalid project data"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied or project does not exist"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project no longer exists after access was checked"
            )
    })
    public ProjectResponseDto updateProject(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody ProjectUpdateRequestDto requestDto) {
        return projectService.updateProject(projectId, requestDto);
    }
}
