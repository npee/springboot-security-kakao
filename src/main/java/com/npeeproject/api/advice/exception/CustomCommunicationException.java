package com.npeeproject.api.advice.exception;

public class CustomCommunicationException extends RuntimeException {
    public CustomCommunicationException() {
        super();
    }

    public CustomCommunicationException(String message) {
        super(message);
    }

    public CustomCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
