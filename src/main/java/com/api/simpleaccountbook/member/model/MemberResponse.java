package com.api.simpleaccountbook.member.model;

import com.api.simpleaccountbook.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class MemberResponse {

    private Long id;
    private String username;
    private String password;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .build();
    }
}
