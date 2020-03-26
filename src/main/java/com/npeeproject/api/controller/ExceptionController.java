package com.npeeproject.api.controller;

import com.npeeproject.api.advice.exception.CustomAuthenticationEntryPointException;
import com.npeeproject.api.config.security.CustomAuthenticationEntryPoint;
import com.npeeproject.api.model.response.config.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public CommonResult entrypointException() {
        throw new CustomAuthenticationEntryPointException();
    }
}
