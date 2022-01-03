package com.api.simpleaccountbook.member.service;

import com.api.simpleaccountbook.accountbook.util.PasswordUtils;
import com.api.simpleaccountbook.member.entity.Member;
import com.api.simpleaccountbook.member.exception.PasswordNotMatchException;
import com.api.simpleaccountbook.member.model.MemberInput;
import com.api.simpleaccountbook.member.model.MemberLogin;
import com.api.simpleaccountbook.member.model.MemberLoginToken;
import com.api.simpleaccountbook.member.repository.MemberRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void register(MemberInput input) {
        Optional<Member> byEmail = memberRepository.findByEmail(input.getEmail());

        // 중복된 이메일 주소
        if (byEmail.isPresent()) { throw new UsernameNotFoundException("이미 등록된 이메일입니다."); }

        String encryptedPassword = BCrypt.hashpw(input.getPassword(), BCrypt.gensalt());

        Member newMember = Member.builder()
                .email(input.getEmail())
                .password(encryptedPassword)
                .build();
        memberRepository.save(newMember);

    }

    @Override
    public MemberLoginToken login(MemberLogin memberLogin) {
        Optional<Member> byEmail = memberRepository.findByEmail(memberLogin.getEmail());
        Member member = byEmail.orElseThrow(() -> new UsernameNotFoundException("일치하는 이메일이 없습니다."));

        String password = memberLogin.getPassword();
        String encryptedPassword = member.getPassword();

        if (!PasswordUtils.isEqualPassword(password, encryptedPassword)) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        return createToken(member);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Member> byEmail = memberRepository.findByEmail(email);
        Member member = byEmail.orElseThrow(() -> new UsernameNotFoundException("일치하는 이메일이 없습니다."));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
    }

    /**
     * 로그인 token 생성
     */
    private MemberLoginToken createToken(Member member) {
        // 30분 뒤 토근 만료
        LocalDateTime expiredDateTime = LocalDateTime.now().plusMinutes(30);
        Date expiredDate = Timestamp.valueOf(expiredDateTime);

        String token = JWT.create()
                .withExpiresAt(expiredDate)
                .withClaim("member_id®", member.getId())
                .withIssuer(member.getEmail())
                .sign(Algorithm.HMAC512("jinoo".getBytes()));

        return MemberLoginToken.builder()
                .message("로그인에 성공하였습니다.")
                .token(token)
                .build();
    }
}
