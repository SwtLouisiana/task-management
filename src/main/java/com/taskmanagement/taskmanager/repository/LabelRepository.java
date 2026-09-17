package com.taskmanagement.taskmanager.repository;

import com.taskmanagement.taskmanager.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
