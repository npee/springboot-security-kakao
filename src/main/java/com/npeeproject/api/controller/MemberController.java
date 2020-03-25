package com.npeeproject.api.controller;

import com.npeeproject.api.model.Member;
import com.npeeproject.api.repository.MemberRepository;
import com.npeeproject.api.service.MemberService;
import com.npeeproject.api.service.ResponseService;
import com.npeeproject.api.model.response.config.CommonResult;
import com.npeeproject.api.model.response.config.ListResult;
import com.npeeproject.api.model.response.config.SingleResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"1. Member"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;
    private final MemberRepository memberRepository;

    /*
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    */

    @ApiOperation(value = "회원 리스트 출력", notes = "테이블에 모든 회원 리스트를 출력하는 기능")
    @GetMapping(value = "/members")
    public ListResult<Member> findAllMembers() {
        return responseService.getListResult(memberRepository.findAll());
    }

    @ApiOperation(value = "회원 조회", notes = "회원번호로 회원 1명 조회")
    @GetMapping(value = "/member/{id}")
    public SingleResult<Member> findUserById(@ApiParam(value = "회원 번호", required = true) @PathVariable Long id) {
        return responseService.getSingleResult(memberRepository.findById(id).orElse(null));
    }

    @ApiOperation(value = "회원 등록", notes = "입력된 회원 정보를 DB에 저장하는 기능")
    @PostMapping("/member")
    public SingleResult<Member> saveMember(
//            @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
//            @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password,
//            @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
//            @ApiParam(value = "회원 전화번호", required = true) @RequestParam String phoneNumber) {
            @ApiParam(value = "회원정보Dto", required = true) @RequestBody @Valid Member member) {
//        Member member = Member.builder()
//                .email(email)
//                .password(password)
//                .name(name)
//                .phoneNumber(phoneNumber)
//                .build();
        return responseService.getSingleResult(memberService.save(member));
    }

    @ApiOperation(value = "회원정보 수정", notes = "회원정보 수정하기(Email 중복 체크 주의: 기존 Email도 포함됨)")
    @PutMapping(value = "/member")
    public SingleResult<Member> modifyMemberInfo (
//            @ApiParam(value = "회원 no", required = true) @RequestParam Long id,
//            @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
//            @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password,
//            @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
//            @ApiParam(value = "회원 전화번호", required = true) @RequestParam String phoneNumber) {
            @ApiParam(value = "회원정보Dto", required = true) @RequestBody @Valid Member member) {
//        Member member = Member.builder()
//                .id(id)
//                .email(email)
//                .password(password)
//                .name(name)
//                .phoneNumber(phoneNumber)
//                .build();
        return responseService.getSingleResult(memberService.update(member));
    }

    @ApiOperation(value = "회원 삭제", notes = "회원정보 삭제하기")
    @DeleteMapping(value = "/member/{id}")
    public CommonResult deleteMemeber(
            @ApiParam(value = "회원 번호", required = true) @PathVariable Long id) {
        memberRepository.deleteById(id);
        return responseService.getSuccessResult();
    }



}
