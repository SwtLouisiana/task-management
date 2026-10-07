package com.taskmanagement.taskmanager.validation;

import com.taskmanagement.taskmanager.dto.project.ProjectDates;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProjectDatesValidator
        implements ConstraintValidator<ValidProjectDates, ProjectDates> {
    
    @Override
    public boolean isValid(
            ProjectDates value,
            ConstraintValidatorContext context) {
        
        if (value == null
                || value.getStartDate() == null
                || value.getEndDate() == null) {
            return true;
        }
        
        return !value.getEndDate().isBefore(value.getStartDate());
    }
}
