package com.npeeproject.api.service;

import com.npeeproject.api.advice.exception.MemberNotFoundException;
import com.npeeproject.api.advice.exception.ValidCustomException;
import com.npeeproject.api.model.Member;
import com.npeeproject.api.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member save(Member member) {

        verifyDuplicateEmail(member.getEmail(), "email");
        return memberRepository.save(member);
    }

    private void verifyDuplicateEmail(String email, String field) {

        if (memberRepository.findByEmail(email).isPresent()) {
            throw new ValidCustomException("이미 사용중인 이메일입니다", field);
        }
    }

    @Transactional
    public Member update(Member member) {

        verifyDuplicateEmail(member.getEmail(), "update-email");
        return memberRepository.save(member);
    }

    @Transactional
    public void deleteById(Long id) {

        memberRepository.deleteById(id);
    }

}
