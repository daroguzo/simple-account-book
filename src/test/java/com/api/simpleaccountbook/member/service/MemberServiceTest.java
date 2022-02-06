package com.api.simpleaccountbook.member.service;

import com.api.simpleaccountbook.member.model.MemberInput;
import com.api.simpleaccountbook.member.repository.MemberRepository;
import com.api.simpleaccountbook.security.MemberPrincipal;
import com.api.simpleaccountbook.security.MemberPrincipalDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("새로운 사용자 등록")
    public void registerMember() throws Exception {
        // given
        String email = "email@email.com";
        String password = "password";
        MemberInput newMember = MemberInput.builder()
                .email(email)
                .password(password)
                .build();

        memberService.register(newMember);

        // when
        MemberPrincipalDetailsService memberPrincipalDetailsService = new MemberPrincipalDetailsService(memberRepository);
        MemberPrincipal memberPrincipal = memberPrincipalDetailsService.loadUserByUsername(email);

        // then
        assertThat(passwordEncoder.matches(password, memberPrincipal.getPassword())).isTrue();
    }

    @Test
    @DisplayName("중복된 이메일 등록")
    public void registerMemberFail() throws Exception {
        // given
        String email = "email@email.com";
        String password = "password";
        MemberInput newMember = MemberInput.builder()
                .email(email)
                .password(password)
                .build();

        memberService.register(newMember);

        // when
        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> {
            memberService.register(newMember);
        });

        // then
        assertEquals("이미 등록된 이메일입니다.", usernameNotFoundException.getMessage());
    }
}