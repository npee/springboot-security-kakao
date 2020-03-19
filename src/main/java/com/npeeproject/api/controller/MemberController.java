package com.npeeproject.api.controller;

import com.npeeproject.api.member.MemberRequestDto;
import com.npeeproject.api.member.MemberResponseDto;
import com.npeeproject.api.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"1. Member"})
@RestController
@RequestMapping(value = "/api")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ApiOperation(value = "회원 리스트 출력", notes = "테이블에 모든 회원 리스트를 출력하는 기능")
    @GetMapping("/members")
    public List<MemberResponseDto> findAllMembers() {
        return memberService.findAll();
    }

    @ApiOperation(value = "회원가입", notes = "입력된 회원 정보를 DB에 저장하는 기능")
    @PostMapping("/member")
    public Long saveMember(
            @ApiParam(value = "회원가입정보Dto", required = true)
            @RequestBody
            @Valid MemberRequestDto memberRequestDto) {
        return memberService.save(memberRequestDto);
    }


}
