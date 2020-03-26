package com.npeeproject.api.advice.exception;

public class CustomAuthenticationEntryPointException extends RuntimeException {
    public CustomAuthenticationEntryPointException() {
        super();
    }

    public CustomAuthenticationEntryPointException(String message) {
        super(message);
    }

    public CustomAuthenticationEntryPointException(String message, Throwable cause) {
        super(message, cause);
    }
}
