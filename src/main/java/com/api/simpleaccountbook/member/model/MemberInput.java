package com.api.simpleaccountbook.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class MemberInput {

    @Size(min = 4, message = "이메일은 4자 이상 입력해야 합니다.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 안으로 입력해야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;
}
