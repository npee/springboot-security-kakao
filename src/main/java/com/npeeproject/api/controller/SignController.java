package com.npeeproject.api.controller;

import com.npeeproject.api.advice.exception.CustomEmailSigninFailedException;
import com.npeeproject.api.advice.exception.MemberNotFoundException;
import com.npeeproject.api.config.security.JwtTokenProvider;
import com.npeeproject.api.model.Member;
import com.npeeproject.api.model.response.config.CommonResult;
import com.npeeproject.api.model.response.config.SingleResult;
import com.npeeproject.api.model.social.KakaoProfile;
import com.npeeproject.api.repository.MemberRepository;
import com.npeeproject.api.service.KakaoService;
import com.npeeproject.api.service.MemberService;
import com.npeeproject.api.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@Api(tags = {"2. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final KakaoService kakaoService;

    @ApiOperation(value = "로그인", notes = "회원 로그인")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "이메일", required = true) @RequestParam String email,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
        Member member = memberRepository.findByEmail(email).orElseThrow(CustomEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, member.getPassword()))
            throw new CustomEmailSigninFailedException();

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(member.getId()), member.getRoles()));

    }

    @ApiOperation(value = "소셜 로그인", notes = "소셜로 로그인")
    @PostMapping(value = "/signin/{provider}")
    public SingleResult<String> signinByProvider(
            @ApiParam(value = "서비스 제공자", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Member member = memberRepository.findByEmailAndProvider(String.valueOf(profile.getId()), provider).orElseThrow(MemberNotFoundException::new);

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(member.getId()), member.getRoles()));
    }

    @ApiOperation(value = "회원가입", notes = "회원 등록")
    @PostMapping("/signup")
    public CommonResult signup(
            @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
            @ApiParam(value = "회원 전화번호", required = true) @RequestParam String phoneNumber) {
//            @ApiParam(value = "회원정보Dto", required = true) @RequestBody @Valid Member requestMember) {
//        Member member = Member.builder()
//                .email(requestMember.getEmail())
//                .password(passwordEncoder.encode(requestMember.getPassword()))
//                .name(requestMember.getName())
//                .phoneNumber(requestMember.getPhoneNumber())
//                .roles(Collections.singletonList("ROLE_MEMBER"))
//                .build();
        memberService.save(Member.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .name(name)
            .phoneNumber(phoneNumber)
            .roles(Collections.singletonList("ROLE_MEMBER"))
            .build());

        // return responseService.getSingleResult(memberService.save(member));
        return responseService.getSuccessResult();
    }
}
