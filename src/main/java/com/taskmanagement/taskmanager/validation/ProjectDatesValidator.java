package com.taskmanagement.taskmanager.validation;

import com.taskmanagement.taskmanager.dto.project.ProjectCreateRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProjectDatesValidator
        implements ConstraintValidator<ValidProjectDates, ProjectCreateRequestDto> {

    @Override
    public boolean isValid(
            ProjectCreateRequestDto value,
            ConstraintValidatorContext context) {

        if (value == null
                || value.getStartDate() == null
                || value.getEndDate() == null) {
            return true;
        }

        return !value.getEndDate().isBefore(value.getStartDate());
    }
}
