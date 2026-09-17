package com.taskmanagement.taskmanager.repository;

import com.taskmanagement.taskmanager.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}