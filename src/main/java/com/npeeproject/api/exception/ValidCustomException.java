package com.npeeproject.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidCustomException extends RuntimeException {

    private Error[] errors;

    public ValidCustomException(String defaultMessage, String field) {
        this.errors = new Error[]{new Error(defaultMessage, field)};
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Error {

        private String defaultMessage;
        private String field;
    }

}
