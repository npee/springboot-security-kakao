package com.npeeproject.api.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
      Optional<MemberResponseDto> findByEmail(String email);
}
