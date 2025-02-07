package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Exceptions;

public class ResourceConflictException extends RuntimeException{
    public ResourceConflictException() {
        super();
    }

    public ResourceConflictException(String message) {
        super(message);
    }
}
