package com.taskmanagement.taskmanager.service;

import com.taskmanagement.taskmanager.dto.project.ProjectCreateRequestDto;
import com.taskmanagement.taskmanager.dto.project.ProjectResponseDto;

public interface ProjectService {

    ProjectResponseDto createProject(
            ProjectCreateRequestDto requestDto, String userEmail);
}
