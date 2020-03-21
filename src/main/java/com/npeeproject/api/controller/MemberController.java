package com.npeeproject.api.controller;

import com.npeeproject.api.service.ResponseService;
import com.npeeproject.api.model.request.MemberRequestDto;
import com.npeeproject.api.model.response.MemberResponseDto;
import com.npeeproject.api.service.MemberService;
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

    /*
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    */

    @ApiOperation(value = "회원 리스트 출력", notes = "테이블에 모든 회원 리스트를 출력하는 기능")
    @GetMapping("/members")
    //public List<MemberResponseDto> findAllMembers() {
    public ListResult<MemberResponseDto> findAllMembers() {
        return responseService.getListResult(memberService.findAll());
    //    return memberService.findAll();
    }

    @ApiOperation(value = "회원 조회", notes = "id로 회원 1명 조회")
    @GetMapping(value = "/member/{id}")
    public SingleResult<MemberResponseDto> findMemberById (
            @ApiParam(value = "회원id", required = true)
            @PathVariable Long id) throws Exception {
        return responseService.getSingleResult(memberService.findById(id));
    }

    @ApiOperation(value = "회원가입", notes = "입력된 회원 정보를 DB에 저장하는 기능")
    @PostMapping("/member")
    public SingleResult<MemberResponseDto> saveMember(
            @ApiParam(value = "회원가입정보Dto", required = true)
            @RequestBody
            @Valid MemberRequestDto memberRequestDto) {
        return responseService.getSingleResult(memberService.save(memberRequestDto));
    }

    @ApiOperation(value = "회원정보 수정", notes = "회원정보 수정하기(Email 중복 체크 주의: 기존 Email도 포함됨)")
    @PutMapping(value = "/member")
    public SingleResult<MemberResponseDto> modifyMemberInfo (
            @ApiParam(value = "회원가입정보Dto", required = true)
            @RequestBody
            @Valid MemberRequestDto memberRequestDto) {
        return responseService.getSingleResult(memberService.update(memberRequestDto));
    }

    @ApiOperation(value = "회원 탈퇴", notes = "회원정보 삭제하기")
    @DeleteMapping(value = "/member/{id}")
    public CommonResult deleteMemeber(
            @ApiParam(value = "회원id", required = true) @PathVariable Long id) {
        memberService.deleteById(id);
        return responseService.getSuccessResult();
    }


}
