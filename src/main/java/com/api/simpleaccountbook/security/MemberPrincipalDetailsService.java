package com.api.simpleaccountbook.security;

import com.api.simpleaccountbook.member.entity.Member;
import com.api.simpleaccountbook.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberPrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public MemberPrincipal loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        Member member = byEmail.orElseThrow(() -> new UsernameNotFoundException("일치하는 이메일이 없습니다."));

        return new MemberPrincipal(member);
    }
}
