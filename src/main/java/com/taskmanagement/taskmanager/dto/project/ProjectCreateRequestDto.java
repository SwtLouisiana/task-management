package com.taskmanagement.taskmanager.dto.project;

import com.taskmanagement.taskmanager.validation.NormalizeText;
import com.taskmanagement.taskmanager.validation.ValidProjectDates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidProjectDates
public class ProjectCreateRequestDto {

    @NormalizeText
    @NotBlank(message = "Project name is required")
    @Size(max = 255,
            message = "Project name must not exceed 255 characters")
    private String name;

    @NormalizeText
    @Size(max = 500,
            message = "Project description must not exceed 500 characters")
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}
