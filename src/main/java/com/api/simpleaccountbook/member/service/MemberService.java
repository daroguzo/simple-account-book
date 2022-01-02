package com.api.simpleaccountbook.member.service;

import com.api.simpleaccountbook.member.model.MemberInput;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface MemberService extends UserDetailsService {

    /**
     * 회원가입
     */
    boolean register(MemberInput memberInput);

}
