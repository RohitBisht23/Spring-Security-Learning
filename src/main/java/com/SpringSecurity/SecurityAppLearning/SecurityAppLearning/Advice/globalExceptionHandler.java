package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Advice;

import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Exceptions.ResourceConflictException;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class globalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException e) {
        ApiError apiError = new ApiError(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiError> handleResourceConflictException(ResourceConflictException e) {
        ApiError apiError = new ApiError(e.getLocalizedMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJWTException(JwtException e) {
        ApiError apiError = new ApiError(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
}
