package com.npeeproject.api.advice.exception;

public class CustomMemberExistException extends RuntimeException {
    public CustomMemberExistException() {
        super();
    }

    public CustomMemberExistException(String message) {
        super(message);
    }

    public CustomMemberExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
