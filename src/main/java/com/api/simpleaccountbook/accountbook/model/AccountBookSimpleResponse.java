package com.api.simpleaccountbook.accountbook.model;

import com.api.simpleaccountbook.accountbook.entity.AccountBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class AccountBookSimpleResponse {

    private Long id;

    private String subject;

    private LocalDateTime regDt;

    public static AccountBookSimpleResponse of(AccountBook accountBook) {
        return AccountBookSimpleResponse.builder()
                .id(accountBook.getId())
                .subject(accountBook.getSubject())
                .regDt(accountBook.getRegDt())
                .build();

    }
}
