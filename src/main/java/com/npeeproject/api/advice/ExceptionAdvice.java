package com.npeeproject.api.advice;

import com.npeeproject.api.advice.exception.MemberNotFoundException;
import com.npeeproject.api.model.response.config.CommonResult;
import com.npeeproject.api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.npeeproject.api")
public class ExceptionAdvice {

    private final ResponseService responseService;

    private final MessageSource messageSource;


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.message"));
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult memberNotFountException(HttpServletRequest request, MemberNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("memberNotFound.code")), getMessage("memberNotFound.message"));
    }

    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
