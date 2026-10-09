package com.thanjoz.jobportal_backend.exception;

public class AlreadyAppliedException extends RuntimeException {

    public AlreadyAppliedException(String message) {
        super(message);
    }
}