package com.taskmanagement.taskmanager.exception;

import com.taskmanagement.taskmanager.dto.error.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ErrorResponseDto> handleRegistrationException(
            RegistrationException exception,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        
        ErrorResponseDto response = new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        String message = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .distinct()
                .collect(Collectors.joining("; "));
        
        ErrorResponseDto response = new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        
        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(
            UserNotFoundException exception,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        
        ErrorResponseDto response = new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUsernameAlreadyExists(
            UsernameAlreadyExistsException exception,
            HttpServletRequest request) {
        
        HttpStatus status = HttpStatus.CONFLICT;
        
        ErrorResponseDto response = new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(LastAdminException.class)
    public ResponseEntity<ErrorResponseDto> handleLastAdminException(
            LastAdminException exception,
            HttpServletRequest request) {
        
        HttpStatus status = HttpStatus.CONFLICT;
        
        ErrorResponseDto response = new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponseDto> handleOptimisticLockingFailure(
            OptimisticLockingFailureException exception,
            HttpServletRequest request) {
        
        HttpStatus status = HttpStatus.CONFLICT;
        
        ErrorResponseDto response = new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                "User data was changed by another request. Please retry",
                request.getRequestURI()
        );
        
        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolation(
            DataIntegrityViolationException exception,
            HttpServletRequest request) {
        
        if (!isUniqueConstraintViolation(exception)) {
            throw exception;
        }
        
        HttpStatus status = HttpStatus.CONFLICT;
        
        ErrorResponseDto response = new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                "A record with the provided unique value already exists",
                request.getRequestURI()
        );
        
        return ResponseEntity.status(status).body(response);
    }
    
    private boolean isUniqueConstraintViolation(Throwable exception) {
        Throwable cause = exception;
        
        while (cause != null) {
            if (cause instanceof ConstraintViolationException violation
                    && violation.getKind()
                    == ConstraintViolationException.ConstraintKind.UNIQUE) {
                return true;
            }
            
            cause = cause.getCause();
        }
        
        return false;
    }
}
