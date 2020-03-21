package com.npeeproject.api.repository;

import com.npeeproject.api.model.Member;
import com.npeeproject.api.model.response.MemberResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
      Optional<MemberResponseDto> findByEmail(String email);
}
