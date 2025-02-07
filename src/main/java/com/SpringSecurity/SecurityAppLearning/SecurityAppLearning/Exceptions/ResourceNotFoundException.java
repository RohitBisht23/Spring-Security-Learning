package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Exceptions;


public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super();
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
