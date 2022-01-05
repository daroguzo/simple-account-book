package com.api.simpleaccountbook.accountbook.service;

import com.api.simpleaccountbook.accountbook.model.AccountBookInput;
import com.api.simpleaccountbook.accountbook.model.AccountBookResponse;
import com.api.simpleaccountbook.accountbook.model.ModifyAccountBookInput;
import com.api.simpleaccountbook.member.entity.Member;

import java.util.List;

public interface AccountBookService {
    /**
     * 해당 유저의 가계부 목록 가져오기
     */
    List<AccountBookResponse> getAccountBookList(String email);

    /**
     * 해당 유저의 삭제된 가계부 목록 가져오기
     */
    List<AccountBookResponse> getDeletedAccountBookList(String email);

    /**
     * 가계부 등록
     */
    void postAccountBook(AccountBookInput accountBookInput, String email);

    /**
     * 해당 가계부 가져오기
     */
    AccountBookResponse getAccountBook(Long accountBookId);

    /**
     * 해당 가계부 삭제
     */
    boolean deleteAccountBook(Long accountBookId);

    /**
     * 삭제된 가계부 복구
     */
    boolean restoreAccountBook(Long accountBookId);

    /**
     * 해당 가계부 수정
     */
    void modifyAccountBook(ModifyAccountBookInput modifyAccountBookInput);
}
