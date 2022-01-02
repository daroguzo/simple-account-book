package com.api.simpleaccountbook.member.model;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MemberInput {

    private String username;
    private String password;
}
