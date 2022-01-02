package com.api.simpleaccountbook.accountbook.service;

import com.api.simpleaccountbook.accountbook.entity.AccountBook;
import com.api.simpleaccountbook.member.model.MemberResponse;

import java.util.List;

public interface AccountBookService {
    /**
     * 해당 유저 가계부 가져오기
     */
    List<AccountBook> getAccountBooks(MemberResponse memberResponse);
}
