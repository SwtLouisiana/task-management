package com.taskmanagement.taskmanager.repository;

import com.taskmanagement.taskmanager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}