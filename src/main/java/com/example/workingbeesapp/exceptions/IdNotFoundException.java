package com.example.workingbeesapp.exceptions;

public class IdNotFoundException extends RuntimeException {

    public IdNotFoundException(String message) {
        super(message);
    }

    public IdNotFoundException(String message, Throwable cause) {
        super(message, cause); // TODO : still need a cause here //
    }
}
