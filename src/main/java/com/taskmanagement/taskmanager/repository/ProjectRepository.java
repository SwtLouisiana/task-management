package com.taskmanagement.taskmanager.repository;

import com.taskmanagement.taskmanager.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
