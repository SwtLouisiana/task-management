package com.taskmanagement.taskmanager.service.impl;

import com.taskmanagement.taskmanager.dto.project.ProjectCreateRequestDto;
import com.taskmanagement.taskmanager.dto.project.ProjectResponseDto;
import com.taskmanagement.taskmanager.dto.project.ProjectUpdateRequestDto;
import com.taskmanagement.taskmanager.exception.ProjectNotFoundException;
import com.taskmanagement.taskmanager.exception.UserNotFoundException;
import com.taskmanagement.taskmanager.mapper.ProjectMapper;
import com.taskmanagement.taskmanager.models.Project;
import com.taskmanagement.taskmanager.models.ProjectMember;
import com.taskmanagement.taskmanager.models.User;
import com.taskmanagement.taskmanager.models.enums.ProjectRole;
import com.taskmanagement.taskmanager.models.enums.ProjectStatus;
import com.taskmanagement.taskmanager.repository.ProjectRepository;
import com.taskmanagement.taskmanager.repository.UserRepository;
import com.taskmanagement.taskmanager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() and #userEmail == authentication.name")
    public ProjectResponseDto createProject(
            ProjectCreateRequestDto requestDto,
            @P("userEmail") String userEmail) {

        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Project project = projectMapper.toEntity(requestDto);
        project.setOwner(owner);
        project.setStatus(ProjectStatus.INITIATED);

        ProjectMember membership = new ProjectMember();
        membership.setProject(project);
        membership.setUser(owner);
        membership.setRole(ProjectRole.MEMBER);

        project.getMemberships().add(membership);

        Project savedProject = projectRepository.save(project);
        return projectMapper.toDto(savedProject);
    }
    
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated() and #userEmail == authentication.name")
    public Page<ProjectResponseDto> getAccessibleProjects(
            @P("userEmail") String userEmail, Pageable pageable) {
        return projectRepository.findAllAccessibleByUserEmail(userEmail, pageable)
                .map(projectMapper::toDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("@projectAccess.canView(#projectId, authentication)")
    public ProjectResponseDto getProjectById(
            @P("projectId") Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        
        return projectMapper.toDto(project);
    }
    
    @Override
    @Transactional
    @PreAuthorize("@projectAccess.canManage(#projectId, authentication)")
    public ProjectResponseDto updateProject(
            @P("projectId") Long projectId,
            ProjectUpdateRequestDto requestDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        
        projectMapper.updateProject(requestDto, project);
        
        return projectMapper.toDto(project);
    }
}
