package com.thanjoz.jobportal_backend.exception;

public class AlreadySavedJobException extends RuntimeException {

    public AlreadySavedJobException(String message) {
        super(message);
    }
}