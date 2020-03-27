package com.npeeproject.api.repository;

import com.npeeproject.api.model.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

// @RunWith(BlockJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenFindByEmailThenReturnMember () {
        String email = "j@m.a"; // Email is unique value.
        String name = "npee";
        String phoneNumber = "01012345678";

        memberRepository.save(Member.builder()
            .email(email)
            .password(passwordEncoder.encode("1234"))
            .name(name)
            .phoneNumber(phoneNumber)
            .roles(Collections.singletonList("ROLE_MEMBER"))
            .build());

        Optional<Member> member = memberRepository.findByEmail(email);

        assertNotNull(member);
        assertTrue(member.isPresent());
        assertEquals(member.get().getName(), name);
        assertThat(member.get().getName(), is(name));
    }

}
