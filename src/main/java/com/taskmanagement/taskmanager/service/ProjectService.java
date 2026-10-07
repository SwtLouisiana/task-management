package com.taskmanagement.taskmanager.service;

import com.taskmanagement.taskmanager.dto.project.ProjectCreateRequestDto;
import com.taskmanagement.taskmanager.dto.project.ProjectResponseDto;
import com.taskmanagement.taskmanager.dto.project.ProjectUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    ProjectResponseDto createProject(
            ProjectCreateRequestDto requestDto, String userEmail);
    
    Page<ProjectResponseDto> getAccessibleProjects(
            String userEmail, Pageable pageable);
    
    ProjectResponseDto getProjectById(Long projectId);
    
    ProjectResponseDto updateProject(
            Long projectId, ProjectUpdateRequestDto requestDto);
}
