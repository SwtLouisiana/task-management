package com.taskmanagement.taskmanager.validation;

import com.taskmanagement.taskmanager.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordsMatchValidator
        implements ConstraintValidator<PasswordsMatch, UserRegistrationRequestDto> {

    @Override
    public boolean isValid(
            UserRegistrationRequestDto request,
            ConstraintValidatorContext context
    ) {
        if (request == null) {
            return true;
        }

        return Objects.equals(request.getPassword(), request.getRepeatPassword());
    }
}
