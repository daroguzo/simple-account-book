package com.api.simpleaccountbook.member.service;

import com.api.simpleaccountbook.member.entity.Member;
import com.api.simpleaccountbook.member.model.MemberInput;
import com.api.simpleaccountbook.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;


    @Override
    public boolean register(MemberInput input) {
        Optional<Member> byUsername = memberRepository.findByUsername(input.getUsername());

        // 중복된 사용자 이름
        if (byUsername.isPresent()) { return false; }

        String encodedPassword = BCrypt.hashpw(input.getPassword(), BCrypt.gensalt());

        Member newMember = Member.builder()
                .username(input.getUsername())
                .password(encodedPassword)
                .build();
        memberRepository.save(newMember);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> byUsername = memberRepository.findByUsername(username);
        Member member = byUsername.orElseThrow(() -> new UsernameNotFoundException("일치하는 사용자가 없습니다."));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));


        return new User(member.getUsername(), member.getPassword(), grantedAuthorities);
    }
}
