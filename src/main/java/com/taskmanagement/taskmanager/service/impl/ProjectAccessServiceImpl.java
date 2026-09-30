package com.taskmanagement.taskmanager.service.impl;

import com.taskmanagement.taskmanager.models.enums.ProjectRole;
import com.taskmanagement.taskmanager.repository.ProjectMemberRepository;
import com.taskmanagement.taskmanager.repository.ProjectRepository;
import com.taskmanagement.taskmanager.service.ProjectAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("projectAccess")
@RequiredArgsConstructor
public class ProjectAccessServiceImpl implements ProjectAccessService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public boolean canView(Long projectId, Authentication authentication) {
        if (projectId == null || !isAuthenticated(authentication)) {
            return false;
        }

        String email = authentication.getName();

        return projectRepository.existsByIdAndOwner_Email(projectId, email)
                || projectMemberRepository
                        .existsByProject_IdAndUser_Email(projectId, email);
    }

    @Override
    public boolean canManage(Long projectId, Authentication authentication) {
        if (projectId == null || !isAuthenticated(authentication)) {
            return false;
        }

        String email = authentication.getName();

        return projectRepository.existsByIdAndOwner_Email(projectId, email)
                || projectMemberRepository.existsByProject_IdAndUser_EmailAndRole(
                        projectId, email, ProjectRole.MANAGER);
    }

    @Override
    public boolean canDelete(Long projectId, Authentication authentication) {
        if (projectId == null || !isAuthenticated(authentication)) {
            return false;
        }

        return projectRepository.existsByIdAndOwner_Email(
                projectId, authentication.getName());
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }
}
