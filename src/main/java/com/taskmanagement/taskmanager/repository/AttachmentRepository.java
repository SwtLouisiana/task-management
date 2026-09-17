package com.taskmanagement.taskmanager.repository;

import com.taskmanagement.taskmanager.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}