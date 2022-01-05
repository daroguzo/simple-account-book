package com.api.simpleaccountbook.accountbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ModifyAccountBookInput {

    @NotNull(message = "수정할 금액을 입력해주세요.")
    private int usedMoney;

    @NotBlank(message = "수정할 메모를 입력해주세요")
    private String memo;

}
