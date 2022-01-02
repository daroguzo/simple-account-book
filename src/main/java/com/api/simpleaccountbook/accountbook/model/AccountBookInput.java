package com.api.simpleaccountbook.accountbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class AccountBookInput {

    @NotBlank(message = "제목을 입력해주세요.")
    private String subject;

    @NotBlank(message = "사용 금액을 입력해주세요.")
    private int usedMoney;

    private String memo;

    private LocalDateTime regDt;

    private boolean isDeleted;
}
