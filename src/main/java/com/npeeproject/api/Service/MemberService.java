package com.npeeproject.api.Service;

import com.npeeproject.api.member.MemberRepository;
import com.npeeproject.api.member.MemberRequestDto;
import com.npeeproject.api.member.MemberResponseDto;
import com.npeeproject.api.member.ValidCustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long save(MemberRequestDto memberRequestDto) {

        verifyDuplicateEmail(memberRequestDto.getEmail());
        // return memberRepository.save(memberRequestDto.toEntity()).getId();
        return memberRepository.save(memberRequestDto.toEntity()).getId();
    }

    private void verifyDuplicateEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new ValidCustomException("이미 사용중인 이메일입니다", "email");
        }
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        return memberRepository
                .findAll()
                .stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(MemberRequestDto memberRequestDto) {
        verifyDuplicateEmail(memberRequestDto.getEmail());
        return memberRepository.save(memberRequestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {
        return memberRepository
                .findById(id)
                .map(MemberResponseDto::new)
                .get();
    }

    @Transactional
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

}
