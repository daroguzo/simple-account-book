package com.api.simpleaccountbook.accountbook.service;

import com.api.simpleaccountbook.accountbook.model.AccountBookResponse;
import com.api.simpleaccountbook.member.entity.Member;

import java.util.List;

public interface AccountBookService {
    /**
     * 해당 유저의 가계부 목록 가져오기
     */
    List<AccountBookResponse> getAccountBooks(Member member);

    /**
     * 해당 유저의 삭제된 가계부 목록 가져오기
     */
    List<AccountBookResponse> getDeletedAccountBooks(Member member);
}
