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
public class AccountBookResponse {

    private int usedMoney;

    private String subject;

    private String memo;

    private LocalDateTime regDt;

    public static AccountBookResponse of(AccountBook accountBook) {
        return AccountBookResponse.builder()
                .subject(accountBook.getSubject())
                .usedMoney(accountBook.getUsedMoney())
                .memo(accountBook.getMemo())
                .regDt(accountBook.getRegDt())
                .build();

    }
}
