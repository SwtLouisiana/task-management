package com.taskmanagement.taskmanager.repository;

import com.taskmanagement.taskmanager.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByIdAndOwner_Email(Long projectId, String email);
    
    @Query("""
            SELECT p
            FROM Project p
            WHERE p.owner.email = :email
                OR EXISTS (
                    SELECT pm.id
                    FROM ProjectMember pm
                    WHERE pm.project = p
                        AND pm.user.email = :email
                )
            """)
    Page<Project> findAllAccessibleByUserEmail(
            @Param("email") String email, Pageable pageable);
}
