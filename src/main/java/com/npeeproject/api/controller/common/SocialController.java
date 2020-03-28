package com.npeeproject.api.controller.common;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/social/login")
public class SocialController {

    private final Environment environment;
    private final RestTemplate restTemplate;
    private final Gson gson;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    @GetMapping
    public ModelAndView socialLogin(ModelAndView mv) {

        StringBuilder loginUrl = new StringBuilder()
                .append(environment.getProperty("spring.social.kakao.url.login"))
                .append("?client_id=")
                .append(kakaoClientId)
                .append("&response_type-code")
                .append("&redirect_url=")
                .append(baseUrl)
                .append(kakaoRedirect);

        mv.addObject("loginUrl", loginUrl);
        mv.setViewName("social/login");
        return mv;
    }

}
