package com.api.simpleaccountbook.accountbook.service;

import com.api.simpleaccountbook.accountbook.model.AccountBookInput;
import com.api.simpleaccountbook.accountbook.model.AccountBookDetailResponse;
import com.api.simpleaccountbook.accountbook.model.AccountBookSimpleResponse;
import com.api.simpleaccountbook.accountbook.model.ModifyAccountBookInput;

import java.util.List;

public interface AccountBookService {
    /**
     * 해당 유저의 간단한 가계부 목록 가져오기
     */
    List<AccountBookSimpleResponse> getAccountBookList(String email);

    /**
     * 해당 유저의 간단한 삭제된 가계부 목록 가져오기
     */
    List<AccountBookSimpleResponse> getDeletedAccountBookList(String email);

    /**
     * 가계부 등록
     */
    void postAccountBook(AccountBookInput accountBookInput, String email);

    /**
     * 해당 가계부 상세정보 가져오기
     */
    AccountBookDetailResponse getDetailAccountBook(Long accountBookId, String email);

    /**
     * 해당 가계부 삭제
     */
    boolean deleteAccountBook(Long accountBookId, String email);

    /**
     * 삭제된 가계부 복구
     */
    boolean restoreAccountBook(Long accountBookId, String email);

    /**
     * 해당 가계부 수정
     */
    AccountBookDetailResponse modifyAccountBook(ModifyAccountBookInput modifyAccountBookInput, Long accountBookId, String email);
}
