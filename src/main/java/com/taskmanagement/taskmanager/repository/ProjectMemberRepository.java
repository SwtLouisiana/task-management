package com.taskmanagement.taskmanager.repository;

import com.taskmanagement.taskmanager.models.ProjectMember;
import com.taskmanagement.taskmanager.models.enums.ProjectRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository
        extends JpaRepository<ProjectMember, Long> {

    Optional<ProjectMember> findByProject_IdAndUser_Id(
            Long projectId, Long userId);
    
    boolean existsByProject_IdAndUser_Email(Long projectId, String email);
    
    boolean existsByProject_IdAndUser_EmailAndRole(
            Long projectId, String email, ProjectRole role);
}
