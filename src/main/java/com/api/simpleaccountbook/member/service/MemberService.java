package com.api.simpleaccountbook.member.service;

import com.api.simpleaccountbook.member.entity.Member;
import com.api.simpleaccountbook.member.model.MemberInput;
import com.api.simpleaccountbook.member.model.MemberLogin;
import com.api.simpleaccountbook.member.model.MemberLoginToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface MemberService extends UserDetailsService {

    /**
     * 회원가입
     */
    void register(MemberInput memberInput);

    /**
     * 로그인
     * @return
     */
    MemberLoginToken login(MemberLogin memberLogin);
}
