package com.taskmanagement.taskmanager.dto.project;

import com.taskmanagement.taskmanager.models.enums.ProjectStatus;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponseDto {

    private Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private ProjectStatus status;

    private Long ownerId;
}
