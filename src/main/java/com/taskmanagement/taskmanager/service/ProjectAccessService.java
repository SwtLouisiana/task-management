package com.taskmanagement.taskmanager.service;

import org.springframework.security.core.Authentication;

public interface ProjectAccessService {
    
    boolean canView(Long projectId, Authentication authentication);
    
    boolean canManage(Long projectId, Authentication authentication);
    
    boolean canDelete(Long projectId, Authentication authentication);
}
